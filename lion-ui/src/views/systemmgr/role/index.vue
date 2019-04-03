<template>
  <div class="app-container">
    <el-card style="width: 28%;float: left;margin-right: 5px" class="box-card">
      <div slot="header">
        <span>角色列表</span>
        <el-button type="primary" size="small" style="float: right;margin-right: 15px" @click="createRole()">新&nbsp;增
        </el-button>
      </div>
      <el-table
        v-loading="listLoading"
        :data="roles"
        element-loading-text="Loading"
        fit
        @row-click="showRolePrivileges"
        highlight-current-row>
        <el-table-column align="center" label="#" width="50">
          <template slot-scope="scope">
            {{ scope.$index + 1}}
          </template>
        </el-table-column>
        <el-table-column label="名称" width="150">
          <template slot-scope="scope">
            <span v-show="editIndex!=scope.$index">{{ scope.row.roleName }}</span>
            <el-input v-show="editIndex==scope.$index" v-model="role.roleName" placeholder="配置名称"/>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" width="50">
          <template slot-scope="scope">
            <i v-show="editIndex!=scope.$index && scope.row.delstatus=='1'" class="el-icon-circle-check"></i>
            <i v-show="editIndex!=scope.$index && scope.row.delstatus=='0'" class="el-icon-circle-close-outline"></i>
            <el-checkbox v-show="editIndex==scope.$index" v-model="role.delstatus"
                         :true-label="1" :false-label="0"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="80">
          <template slot-scope="scope">
            <el-button v-show="editIndex!=scope.$index" @click="editRole(scope.$index, scope.row)"
                       type="primary" title="编辑" icon="el-icon-edit" circle size="mini"></el-button>

            <el-button v-show="editIndex==scope.$index" @click="saveRole()"
                       type="success" title="保存" icon="el-icon-check" circle size="mini"></el-button>

            <el-button v-show="editIndex==scope.$index" @click="cancelRoleEdit(scope.row)"
                       type="danger" title="取消" icon="el-icon-close" circle size="mini"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card style="width: 30%;float: left;margin-right: 5px" class="box-card">
      <div slot="header">
        <span>权限列表</span>
        <el-button type="primary" size="small" style="float: right;margin-right: 15px" @click="savePrivileges()">保&nbsp;存
        </el-button>
      </div>
      <div>
        <el-tree
          :data="menus"
          show-checkbox
          node-key="id"
          @check-change="restPrivileges"
          @node-click="loadMenuOperations"
          ref="tree"
          highlight-current
          :props="defaultProps">
        </el-tree>
      </div>
    </el-card>

    <el-card style="width: 40%;float: left;" class="box-card">
      <div slot="header">
        <span>操作权限列表</span>
        <el-button type="primary" size="small" style="float: right;margin-right: 15px"
                   @click="savePrivileges()">保&nbsp;存
        </el-button>
      </div>
      <el-table
        v-loading="listLoading"
        ref="menuOperationsRef"
        :data="menuOperations"
        element-loading-text="Loading"
        fit
        highlight-current-row
        @select="handleSelection"
        @select-all="handleSelection"
      >
        <el-table-column
          label="选择"
          type="selection"
          width="200"
          :selectable="checkSelectable"
          align="center">
        </el-table-column>
        <el-table-column
          label="名称"
          min-width="290"
          prop="name">
          <template slot-scope="scope">
            {{scope.row.name }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        roles: null,
        editIndex: -1,
        role: {
          roleName: '',
          delstatus: 1
        },
        listLoading: true,
        createRoleDialogVisible: false,
        rules: {
          roleName: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
          ],
          delstatus:
            [
              {required: true, message: '请输选择状态', trigger: 'blur'},
            ]
        },
        rolePrivileges: [],
        menus: [],
        defaultProps: {
          children: 'items',
          label: 'name'
        },
        menuOperations: null
      }
    },
    watch: {
      menuOperations: function () {
        this.$nextTick(function () {
          this.bindOperationCheckStatus();
        });
      }
    },
    created() {
      this.initRoleList();
      this.initMenuTree();
    },
    methods: {
      //#region 角色管理
      initRoleList() {//角色列表查询
        this.listLoading = true;
        let params = {"type": "MANAGEMENT"};
        this.request.get('/system/rbac/roles', params).then(result => {
          this.roles = result;
          this.listLoading = false;
        });
      },
      createRole() {
        this.role = {
          roleName: '',
          delstatus: 1
        };
        this.roles.push(this.role);
        this.editIndex = this.roles.length - 1;
      },
      editRole(index, role) {
        this.editIndex = index;
        this.role = role;
      },
      cancelRoleEdit(role) {
        this.editIndex = -1;
        if (!this.role.id) {
          this.roles = this.roles.filter(t => t.id);
        }
      },
      saveRole() {//新增 or 修改 角色
        let _this = this;
        if (this.role.id) {
          this.request.put('/system/rbac/roles/' + this.role.id, this.role).then(result => {
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            this.editIndex = -1;
            _this.initRoleList();
          });
        } else {
          if (!this.role.roleName || this.role.roleName.length === 0) {
            this.$message({
              type: 'warning',
              showClose: true,
              duration: 2000,
              message: '请提供角色名称。'
            });
            return;
          }

          this.request.post('/system/rbac/roles', this.role).then(result => {
            this.$message({
              type: 'success',
              message: '新增成功!'
            });
            this.editIndex = -1;
            _this.initRoleList();
          });
        }
      },
      //#endregion 角色管理

      //#region 角色权限管理
      initMenuTree() {//角色列表查询
        this.request.get('/system/metadata/menus').then(result => {
          this.menus = result;
        });
      },
      showRolePrivileges(role) {
        this.role = role;
        this.request.get('/system/rbac/roles/' + role.id + "/menus").then(result => {
          this.rolePrivileges = result;
          let arr = [];
          for (let item of result) {
            if (item.code.indexOf('.') >= 0) {
              arr.push(item.id);
            }
          }
          this.$refs.tree.setCheckedKeys(arr);
        });
        this.request.get('/system/rbac/roles/' + role.id + "/operations").then(result => {
          this.roleOperations = result;
        });
      },
      savePrivileges() {
        let menuIds = this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys());
        this.request.post('/system/rbac/roles/' + this.role.id + "/menus", menuIds).then(result => {
          this.saveMenuOperations();
        });
      },
      restPrivileges(menu, status) {
        if (!status) {
          //如果取消当前菜单权限选择，则需要将该菜单的所有操作权限移除
          this.$lodash.remove(this.roleOperations, function (roleOp) {
            return roleOp.menuId == menu.id;
          });
          this.bindOperationCheckStatus();
        }
        this.rolePrivileges = this.$refs.tree.getCheckedNodes();
      },
      //#endregion 角色权限管理


      //#region 角色操作权限管理
      loadMenuOperations(rolePrivilege) {
        let _this = this;
        this.listLoading = false;
        this.request.get('/system/metadata/menus/' + rolePrivilege.id + "/operations").then(result => {
          this.menuOperations = result;
        });
      },
      handleSelection(opSelections, row) {
        let $this = this;
        $this.$lodash.forEach($this.menuOperations, function (menuOp) {
          if (!$this.$lodash.some(opSelections, {id: menuOp.id})) {
            //如果操作权限未被选中 则从操作权限列表中移除该操作权限
            $this.$lodash.remove($this.roleOperations, function (roleOp) {
              return roleOp.id == menuOp.id;
            });
          } else if (!$this.$lodash.some($this.roleOperations, {id: menuOp.id})) {
            //如果是新追加的操作权限
            $this.roleOperations.push(menuOp);
          }
        });
      },

      bindOperationCheckStatus() {
        let _this = this;
        this.$lodash.forEach(this.menuOperations, function (menuOperation) {
          if (_this.$lodash.some(_this.roleOperations, {id: menuOperation.id})) {
            _this.$refs.menuOperationsRef.toggleRowSelection(menuOperation, true);
          } else {
            _this.$refs.menuOperationsRef.toggleRowSelection(menuOperation, false);
          }
        });
      },
      checkSelectable(row, index) {
        return this.$lodash.some(this.rolePrivileges, {'id': row.menuId});
      },
      saveMenuOperations() {
        this.request.post('/system/rbac/roles/' + this.role.id + "/operations", this.roleOperations).then(result => {
          this.$message({
            type: 'success',
            message: '保存成功!'
          });
        });
      }
      //#endregion 角色操作权限管理


    }
  }
</script>

