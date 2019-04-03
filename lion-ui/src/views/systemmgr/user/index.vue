<template>
  <div class="app-container">
    <div style="width: 40%;float: left">
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="height: 28px">
          <span style="font-size: 20px;">用户列表</span>
        </div>
        <el-table
          :data="users"
          v-loading="loading"
          element-loading-text="Loading"
          fit
          @row-click="showUser"
          highlight-current-row>
          <el-table-column align="center" label="#" width="100">
            <template slot-scope="scope">
              {{ scope.$index+1 }}
            </template>
          </el-table-column>
          <el-table-column
            label="姓名"
            width="200">
            <template slot-scope="scope">
              {{ scope.row.chinaName }}
            </template>
          </el-table-column>
          <el-table-column
            label="账号"
            width="200"
            prop="name">
            <template slot-scope="scope">
              {{ scope.row.account }}
            </template>
          </el-table-column>
          <el-table-column
            label="状态"
            width="100"
            align="center">
            <template slot-scope="scope">
              <i v-show="scope.row.delstatus=='1'" class="el-icon-circle-check"></i>
              <i v-show="scope.row.delstatus=='0'" class="el-icon-circle-close"></i>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
    <div style="width: 60%; float: left;padding: 0 5px">
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="height: 28px">
          <el-button-group style="float: right">
            <el-button v-if="user" type="primary" size="mini" @click="createUser();">新增</el-button>
            <el-button v-if="user" type="primary" size="mini" @click="saveUser();">保存</el-button>
            <el-button type="primary" size="mini" @click="resetPwd();">重置密码</el-button>
          </el-button-group>
        </div>
        <el-form :model="user" ref="user">
          <el-col :span="11">
            <el-form-item prop="chinaName">
              <el-input v-model="user.chinaName" placeholder="姓名" style="margin-bottom:5px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="1">&nbsp;</el-col>
          <el-col :span="11">
            <el-form-item prop="account">
              <el-input v-model="user.account" placeholder="登录账号" style="margin-bottom:5px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item prop="phone">
              <el-input v-model="user.phone" placeholder="手机号" style="margin-bottom:5px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="11" style="padding-left: 100px">
            <el-form-item>
              <el-radio-group v-model="user.delstatus">
                <el-radio label="1">启用</el-radio>
                <el-radio label="0">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-form>
      </el-card>

      <el-card style="margin-top: 5px;">
        <div slot="header" class="clearfix">
          <span>角色分配</span>
          <el-button v-if="user && user.id" type="primary" size="mini" style="float: right"
                     @click="assignRoles()">保存
          </el-button>
        </div>
        <el-table
          ref="roleTable"
          :data="roles"
          element-loading-text="Loading"
          fit
          highlight-current-row
          @selection-change="handleSelectionChange">
          <el-table-column
            label="选择"
            type="selection"
            width="200"
            align="center">
          </el-table-column>
          <el-table-column
            label="名称"
            min-width="290"
            prop="name">
            <template slot-scope="scope">
              {{ scope.row.roleName }}
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>
<script>

  export default {
    data() {
      return {
        loading: true,
        users: [],
        user: {},
        roles: [],
        userRoles: [],
        chooseRoles: []
      }
    },
    mounted() {
      this.initUserList();
      this.initRoleList();
    },
    methods: {
      initUserList() {
        this.request.get('/system/organization/users').then(response => {
          this.users = response;
          this.loading = false;
        });
      },
      initRoleList() {
        this.request.get('/system/rbac/roles', {"type": "MANAGEMENT"}).then(response => {
          this.roles = response;
        })
      },
      showUser(user) {
        this.user = user;
        this.user.delstatus = user.delstatus.toString();
        this.$refs.roleTable.clearSelection();
        this.request.get('/system/organization/users/' + user.id + "/roles").then(response => {
          this.userRoles = response;
          let tempRoles = [];
          for (let role in this.roles) {
            for (let userRole in this.userRoles) {
              if (this.userRoles[userRole].id === this.roles[role].id) {
                tempRoles.push(this.roles[role]);
              }
            }
          }
          this.toggleSelection(tempRoles);
        });
      },
      createUser() {
        this.user.delstatus = "1";
        this.user = {
          chinaName: "",
          account: "",
          token: "",
          phone: "",
          delstatus: this.user.delstatus
        };
        this.userRoles = [];
      },
      saveUser() {
        if (!this.user.chinaName) {
          this.$message.error('请输入姓名!');
          return;
        }
        if (!this.user.account) {
          this.$message.error('请输入登录账号!');
          return;
        }
        if (!this.user.phone) {
          this.$message.error('请输入手机号!');
          return;
        }
        if (this.user.id) {
          this.request.put('/system/organization/users/' + this.user.id, this.user).then(() => {
            this.$message({
              message: '保存成功!',
              type: 'success'
            });
            this.initUserList();
          });
        } else {
          this.request.post('/system/organization/users', this.user).then(response => {
            this.$message({
              message: '保存成功!',
              type: 'success'
            });
            this.initUserList();
            this.user = response;
          })
        }
      },
      resetPwd() {
        this.request.put('/system/organization/users/' + this.user.id + '/resetpwd').then(response => {
          this.$message({
            message: '重置密码成功，新密码：' + response.token,
            type: 'success'
          });
        })
      },
      handleSelectionChange(role) {
        this.userRoles = role;
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.roleTable.toggleRowSelection(row);
          });
        }
      },
      assignRoles() {
        this.request.post('/system/organization/users/' + this.user.id + '/roles', this.userRoles).then(() => {
          this.$message({
            message: '保存成功！',
            type: 'success'
          });
        })
      }
    }
  }
</script>
