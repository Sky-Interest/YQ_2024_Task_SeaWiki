<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">修改用户信息</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username" />
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input type="password" placeholder="请输入新密码" v-model="form.password" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('loginForm')">修改</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="温馨提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false">确定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "UpdateUser",
  data() {
    return {
      form: {
        id: '',
        username: '',
        password: '',
      },
      rules: {
        username: [{ required: true, message: '账号不可为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不可为空', trigger: 'blur' }],
      },
      dialogVisible: false,
    }
  },
  methods: {
    handleClose() {
      console.log("Handle Close，空函数");
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/updateUser', {
            id: this.form.id,
            username: this.form.username,
            password: this.form.password,
          })
              .then(response => {
                if (response.data.code === 20000) {
                  this.$router.push("/userlist");
                  this.$message.success(response.data.msg);
                } else {
                  this.$message.error(response.data.msg);
                }
              })
              .catch(error => {
                console.error(error);
                this.$message.error("修改失败");
              });
        } else {
          this.dialogVisible = true;
          return false;
        }
      });
    },
    fetchData(userId) {
      this.$axios.get(`/getUserById?userId=${userId}`)
          .then(response => {
            const data = response.data.data;
            this.form.id = data.id;
            this.form.username = data.username;
          })
          .catch(error => {
            console.error(error);
            this.$message.error("获取用户信息失败");
          });
    }
  },
  created() {
    const userId = this.$route.query.id;
    if (userId) {
      this.fetchData(userId);
    }
  }
}
</script>

<style scoped>
.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 50px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>
