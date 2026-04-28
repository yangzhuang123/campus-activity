<template>
  <div class="addEdit-block">
    <el-form class="detail-form-content" ref="ruleForm" :model="ruleForm" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'" label="标签名称" prop="name">
            <el-input v-model="ruleForm.name" placeholder="标签名称" clearable :readonly="ro.name"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="标签名称" prop="name">
              <el-input v-model="ruleForm.name" placeholder="标签名称" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="select" v-if="type!='info'" label="标签类型" prop="type">
            <el-select v-model="ruleForm.type" placeholder="请选择标签类型">
              <el-option label="活动标签" value="活动标签"></el-option>
              <el-option label="社团标签" value="社团标签"></el-option>
            </el-select>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="标签类型" prop="type">
              <el-input v-model="ruleForm.type" placeholder="标签类型" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'" label="排序" prop="sort">
            <el-input v-model.number="ruleForm.sort" placeholder="排序值（越小越靠前）" clearable :readonly="ro.sort"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="排序" prop="sort">
              <el-input v-model="ruleForm.sort" placeholder="排序" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12" v-if="type=='info' || type=='else'">
          <el-form-item class="input" label="状态" prop="status">
            <el-input v-model="ruleForm.status" placeholder="状态" readonly></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item class="btn">
        <el-button v-if="type!='info'" type="primary" class="btn-success" @click="onSubmit">提交</el-button>
        <el-button v-if="type!='info'" class="btn-close" @click="back()">取消</el-button>
        <el-button v-if="type=='info'" class="btn-close" @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      id: '',
      type: '',
      ro: { name: false, type: false, sort: false },
      ruleForm: { name: '', type: '', sort: 0, status: '正常' },
      rules: {
        name: [{ required: true, message: '标签名称不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '标签类型不能为空', trigger: 'change' }]
      }
    };
  },
  props: ["parent"],
  created() {},
  methods: {
    init(id, type) {
      if (id) {
        this.id = id;
        this.type = type;
      }
      if (this.type == 'info' || this.type == 'else') {
        this.info(id);
      } else if (this.type == 'cross') {
        var obj = this.$storage.getObj('crossObj');
        for (var o in obj) {
          if (o == 'name') { this.ruleForm.name = obj[o]; this.ro.name = true; continue; }
          if (o == 'type') { this.ruleForm.type = obj[o]; this.ro.type = true; continue; }
          if (o == 'sort') { this.ruleForm.sort = obj[o]; this.ro.sort = true; continue; }
        }
      }
      this.$http({
        url: this.$storage.get('sessionTable') + '/session',
        method: "get"
      }).then(({ data }) => {});
    },
    info(id) {
      this.$http({
        url: 'tag/info/' + id,
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.ruleForm = data.data;
        }
      });
    },
    onSubmit() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: 'tag/' + (!this.ruleForm.id ? "save" : "update"),
            method: "post",
            data: this.ruleForm
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({ message: "操作成功", type: "success", duration: 1500, onClose: () => {
                this.parent.showFlag = true;
                this.parent.addOrUpdateFlag = false;
                this.parent.search();
              }});
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    back() {
      this.parent.showFlag = true;
      this.parent.addOrUpdateFlag = false;
    }
  }
};
</script>
<style lang="scss">
.addEdit-block { margin: -10px; }
.detail-form-content { padding: 12px; }
.btn .el-button { padding: 0; }
</style>
