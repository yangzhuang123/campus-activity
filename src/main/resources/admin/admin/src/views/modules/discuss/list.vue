<template>
  <div class="main-content">
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row :gutter="20" class="slt">
          <el-form-item label="评论内容">
            <el-input v-model="searchForm.content" placeholder="评论内容" clearable></el-input>
          </el-form-item>
          <el-form-item label="评论人">
            <el-input v-model="searchForm.userName" placeholder="评论用户名" clearable></el-input>
          </el-form-item>
          <el-form-item label="对象类型">
            <el-select v-model="searchForm.objectType" placeholder="全部" clearable>
              <el-option label="全部" value=""></el-option>
              <el-option label="社团活动" value="shetuanhuodong"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="search()">查询</el-button>
          </el-form-item>
        </el-row>
        <el-row class="ad">
          <el-form-item>
            <el-button v-if="isAuth('discuss','删除')" :disabled="dataListSelections.length <= 0" type="danger" icon="el-icon-delete" @click="deleteHandler()">删除</el-button>
          </el-form-item>
        </el-row>
      </el-form>
      <div class="table-content">
        <el-table class="tables" :data="dataList" v-loading="dataListLoading" @selection-change="selectionChangeHandler" border>
          <el-table-column type="selection" width="50"></el-table-column>
          <el-table-column label="索引" type="index" width="50"></el-table-column>
          <el-table-column prop="content" label="评论内容" min-width="200">
            <template slot-scope="scope">
              <el-tooltip :content="scope.row.content" placement="top">
                <span>{{ scope.row.content.length > 50 ? scope.row.content.substring(0, 50) + '...' : scope.row.content }}</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column prop="userName" label="评论人" width="120"></el-table-column>
          <el-table-column prop="objectType" label="评论对象" width="120">
            <template slot-scope="scope">
              {{scope.row.objectType == 'shetuanhuodong' ? '社团活动' : scope.row.objectType}}
            </template>
          </el-table-column>
          <el-table-column prop="objectId" label="对象ID" width="100"></el-table-column>
          <el-table-column prop="likeCount" label="点赞数" width="80"></el-table-column>
          <el-table-column prop="status" label="状态" width="80">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status=='正常'?'success':'danger'" size="small">{{scope.row.status}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="showDetail(scope.row)">详情</el-button>
              <el-button type="danger" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          background>
        </el-pagination>
      </div>
    </div>
    <!-- 评论详情弹窗 -->
    <el-dialog title="评论详情" :visible.sync="detailVisible" width="600px">
      <el-form label-width="100px">
        <el-form-item label="评论内容">
          <span>{{detailData.content}}</span>
        </el-form-item>
        <el-form-item label="评论人">
          <span>{{detailData.userName}}</span>
        </el-form-item>
        <el-form-item label="对象类型">
          <span>{{detailData.objectType == 'shetuanhuodong' ? '社团活动' : detailData.objectType}}</span>
        </el-form-item>
        <el-form-item label="对象ID">
          <span>{{detailData.objectId}}</span>
        </el-form-item>
        <el-form-item label="点赞数">
          <span>{{detailData.likeCount}}</span>
        </el-form-item>
        <el-form-item label="状态">
          <span>{{detailData.status}}</span>
        </el-form-item>
        <el-form-item label="创建时间">
          <span>{{detailData.createTime}}</span>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchForm: { content: '', userName: '', objectType: '' },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      detailVisible: false,
      detailData: {}
    };
  },
  created() {
    this.getDataList();
  },
  methods: {
    isAuth(tablename, button) {
      return true;
    },
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },
    getDataList() {
      this.dataListLoading = true;
      let params = { page: this.pageIndex, limit: this.pageSize };
      if (this.searchForm.content) params['content'] = '%' + this.searchForm.content + '%';
      if (this.searchForm.userName) params['userName'] = '%' + this.searchForm.userName + '%';
      if (this.searchForm.objectType) params['objectType'] = this.searchForm.objectType;
      this.$http({
        url: "discuss/page",
        method: "get",
        params: params
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.total;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },
    showDetail(row) {
      this.detailData = row;
      this.detailVisible = true;
    },
    deleteHandler(id) {
      var ids = id ? [Number(id)] : this.dataListSelections.map(item => Number(item.id));
      this.$confirm('确定进行[' + (id ? "删除" : "批量删除") + ']操作?', "提示", {
        confirmButtonText: "确定", cancelButtonText: "取消", type: "warning"
      }).then(() => {
        this.$http({
          url: "discuss/delete", method: "post", data: ids
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({ message: "操作成功", type: "success", duration: 1500, onClose: () => { this.search(); } });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    }
  }
};
</script>
<style lang="scss" scoped>
  .slt { margin: 0 !important; display: flex; }
  .ad { margin: 0 !important; display: flex; }
  .el-button+.el-button { margin: 0; }
</style>
