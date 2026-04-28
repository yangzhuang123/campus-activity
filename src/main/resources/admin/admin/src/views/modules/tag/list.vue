<template>
  <div class="main-content">
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row :gutter="20" class="slt" :style="{justifyContent:contents.searchBoxPosition=='1'?'flex-start':contents.searchBoxPosition=='2'?'center':'flex-end'}">
          <el-form-item :label="contents.inputTitle == 1 ? '标签名称' : ''">
            <el-input v-model="searchForm.name" placeholder="标签名称" clearable></el-input>
          </el-form-item>
          <el-form-item :label="contents.inputTitle == 1 ? '标签类型' : ''">
            <el-select v-model="searchForm.type" placeholder="标签类型" clearable>
              <el-option label="全部" value=""></el-option>
              <el-option label="活动标签" value="活动标签"></el-option>
              <el-option label="社团标签" value="社团标签"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="search()">查询</el-button>
          </el-form-item>
        </el-row>
        <el-row class="ad">
          <el-form-item>
            <el-button v-if="isAuth('tag','新增')" type="success" icon="el-icon-plus" @click="addOrUpdateHandler()">新增</el-button>
            <el-button v-if="isAuth('tag','删除')" :disabled="dataListSelections.length <= 0" type="danger" icon="el-icon-delete" @click="deleteHandler()">删除</el-button>
          </el-form-item>
        </el-row>
      </el-form>
      <div class="table-content">
        <el-table class="tables" :data="dataList" v-loading="dataListLoading" @selection-change="selectionChangeHandler" border>
          <el-table-column type="selection" width="50"></el-table-column>
          <el-table-column label="索引" type="index" width="50"></el-table-column>
          <el-table-column prop="name" label="标签名称"></el-table-column>
          <el-table-column prop="type" label="标签类型">
            <template slot-scope="scope">
              <el-tag :type="scope.row.type=='活动标签'?'primary':'success'" size="small">{{scope.row.type}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="sort" label="排序" width="80"></el-table-column>
          <el-table-column prop="status" label="状态" width="100">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status=='正常'?'success':'info'" size="small">{{scope.row.status}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button v-if="isAuth('tag','修改')" type="primary" size="mini" @click="addOrUpdateHandler(scope.row.id)">修改</el-button>
              <el-button v-if="isAuth('tag','删除')" type="danger" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>
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
    <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>
  </div>
</template>
<script>
import AddOrUpdate from "./add-or-update";
export default {
  data() {
    return {
      searchForm: { name: '', type: '' },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      addOrUpdateFlag: false,
      contents: {}
    };
  },
  created() {
    this.getDataList();
  },
  components: { AddOrUpdate },
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
      if (this.searchForm.name) params['name'] = '%' + this.searchForm.name + '%';
      if (this.searchForm.type) params['type'] = this.searchForm.type;
      this.$http({
        url: "tag/page",
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
    addOrUpdateHandler(id) {
      this.showFlag = false;
      this.addOrUpdateFlag = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    deleteHandler(id) {
      var ids = id ? [Number(id)] : this.dataListSelections.map(item => Number(item.id));
      this.$confirm('确定进行[' + (id ? "删除" : "批量删除") + ']操作?', "提示", {
        confirmButtonText: "确定", cancelButtonText: "取消", type: "warning"
      }).then(() => {
        this.$http({
          url: "tag/delete", method: "post", data: ids
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
