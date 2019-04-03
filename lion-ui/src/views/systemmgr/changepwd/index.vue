<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :offset="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix" style="height: 28px">
            <span style="font-size: 20px;">更改密码</span>
          </div>
          <el-form ref="form" :model="user" lable-width="80px" class="demo-form-inline">
            <el-form-item label="原密码：">
              <el-input v-model="user.oldToken" placeholder="请输入原密码"></el-input>
            </el-form-item>

            <el-form-item label="新密码：">
              <el-input v-model="user.newToken" placeholder="请输入新密码"></el-input>
            </el-form-item>

            <el-form-item label="确认密码：">
              <el-input v-model="user.ensureToken" placeholder="请输入确认密码"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" style="margin-left: 150px"
                         @click="submitChange">提交
              </el-button>
            </el-form-item>

          </el-form>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>

  export default {
    data() {
      return {
        user: {
          oldToken: '',
          newToken: '',
          ensureToken: ''
        }
      }
    },
    created() {

    },
    methods: {
      submitChange: function () {
        if (!this.user.oldToken) {
          this.$message.warning('请提供原密码');
          return;
        }
        if (!this.user.newToken) {
          this.$message.warning('请提供新密码');
          return;
        }
        if (this.user.newToken.length < 5) {
          this.$message.warning('密码长度至少5位');
          return;
        }
        if (!this.user.ensureToken) {
          this.$message.warning('请提供确认密码');
          return;
        }
        if (this.user.ensureToken.length < 5) {
          this.$message.warning('密码长度至少5位');
          return;
        }
        if (this.user.newToken !== this.user.ensureToken) {
          this.$message.warning('两次输入的密码不一致');
          return;
        }
        this.request.put("/system/organization/users/changepwd", this.user).then(result => {
          this.$message({
            type: 'success',
            message: '密码修改成功，请重新登录'
          });
          this.$store.dispatch('LogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          });
        });

      }


    }

  }

</script>
