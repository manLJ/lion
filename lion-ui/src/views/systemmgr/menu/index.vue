<template>
  <div class="app-container">
    <div style="width: 30%;float: left">
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="height: 28px">
          <span style="font-size: 20px;">导航列表</span>
        </div>
        <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:20px;"/>
        <el-tree
          ref="menu"
          v-loading="loading"
          :data="treeData"
          :props="defaultProps"
          @node-click="loadMenuItem"
          :filter-node-method="filterMenu"
          class="filter-tree"
        />
      </el-card>
    </div>
    <div style="width: 69%; float: left;padding: 0 5px">
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="height: 28px">
          <el-button-group style="float: right">
            <el-button type="primary" size="mini" @click="createRootMenu();">新增顶级导航</el-button>
            <el-button type="primary" size="mini" @click="createChildMenu();">新增下级导航</el-button>
            <el-button type="primary" size="mini" @click="saveMenu();">保存</el-button>
            <el-button type="primary" size="mini" @click="deleteMenu();">删除</el-button>
          </el-button-group>
        </div>
        <el-form :model="menuForm" ref="menuForm">
          <el-col :span="11">
            <el-form-item prop="code">
              <el-input v-model="menuForm.code" placeholder="导航编码" style="margin-bottom:5px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="1">&nbsp;</el-col>
          <el-col :span="11">
            <el-form-item prop="name">
              <el-input v-model="menuForm.name" placeholder="导航名称" style="margin-bottom:5px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item>
              <el-input v-model="menuForm.sortIndex" placeholder="排序号" type="number" style="margin-bottom:5px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="1">&nbsp;</el-col>
          <el-col :span="11">
            <el-form-item>
              <el-radio-group v-model="menuForm.delstatus">
                <el-radio label="1">启用</el-radio>
                <el-radio label="0">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-form>
      </el-card>
      <el-card style="margin-top: 5px;">
        <div slot="header" class="clearfix">
          <span>页面操作控制列表</span>
          <el-button type="primary" size="mini" style="float: right" @click="createOperations()">新增操作</el-button>
        </div>
        <el-table
          :data="menuOperations"
          element-loading-text="Loading"
          fit
          highlight-current-row>
          <el-table-column align="center" label="#" width="100">
            <template slot-scope="scope">
              {{ scope.$index+1 }}
            </template>
          </el-table-column>
          <el-table-column
            label="编码"
            width="290">
            <template slot-scope="scope">
              <span v-show="editIndex!=scope.$index">{{ scope.row.code }}</span>
              <el-input v-show="editIndex==scope.$index" v-model="menuOperation.code" placeholder="编码"/>
            </template>
          </el-table-column>
          <el-table-column
            label="名称"
            width="290"
            prop="name">
            <template slot-scope="scope">
              <span v-show="editIndex!=scope.$index">{{ scope.row.name }}</span>
              <el-input v-show="editIndex==scope.$index" v-model="menuOperation.name" placeholder="名称"/>
            </template>
          </el-table-column>
          <el-table-column
            label="状态"
            width="200"
            align="center">
            <template slot-scope="scope">
              <i v-show="editIndex!=scope.$index && scope.row.delstatus=='1'" class="el-icon-circle-check"></i>
              <i v-show="editIndex!=scope.$index && scope.row.delstatus=='0'" class="el-icon-circle-close"></i>
              <el-checkbox v-show="editIndex==scope.$index" v-model="menuOperation.delstatus"
                           true-label="1" false-label="0"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="200" align="center">
            <template slot-scope="scope">
              <el-button v-show="editIndex!=scope.$index" @click="editOperation(scope.$index, scope.row)"
                         type="primary" title="编辑" icon="el-icon-edit" circle></el-button>
              <el-button v-show="editIndex==scope.$index" @click="saveOperation()"
                         type="success" title="保存" icon="el-icon-check" circle></el-button>
              <el-button v-show="editIndex==scope.$index" @click="cancelEdit(scope.row)"
                         type="danger" title="取消" icon="el-icon-close" circle></el-button>
              <el-button v-show="editIndex!=scope.$index" @click="deleteOperation(scope.row)"
                         type="danger" title="删除" icon="el-icon-delete" circle></el-button>
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
        menuForm: {
          id: '',
          code: '',
          name: '',
          parentId: '',
          sortIndex: '',
          delstatus: '0'
        },
        menuOperation: {
          code: '',
          name: '',
          delstatus: '0'
        },
        menuOperations: [],
        editIndex: -1,
        filterText: '',
        treeData: null,
        loading: true,
        defaultProps: {
          children: 'items',
          label: 'name'
        }
      }
    },
    mounted() {
      this.fetchData();
    },
    watch: {
      filterText(val) {
        this.$refs.menu.filter(val)
      }
    },
    methods: {
      fetchData() {
        this.loading = true;
        this.request.get('/system/metadata/menus').then(response => {
          this.treeData = response;
          this.loading = false;
        })
      },
      filterMenu(value, data) {
        console.log(value);
        console.log(data);
        if (!value) return true;
        return data.name.indexOf(value) !== -1
      },
      loadMenuItem(data) {
        this.menuForm.id = data.id;
        this.menuForm.code = data.code;
        this.menuForm.name = data.name;
        this.menuForm.sortIndex = data.sortIndex;
        this.menuForm.delstatus = data.delstatus.toString();
        this.editIndex = -1;
        this.loadOperations();
      },
      createRootMenu() {
        this.menuForm = {
          code: '',
          name: '',
          parentId: '',
          delstatus: 0
        };
        this.menuOperations = [];
      },
      createChildMenu() {
        this.menuForm = {
          code: "",
          name: "",
          parentId: this.menuForm.id,
          delstatus: 0
        };
        this.menuOperations = [];
      },
      saveMenu() {
        if (!this.menuForm.code) {
          this.$message.error("请输入导航编码。");
          return;
        }
        if (!this.menuForm.name) {
          this.$message.error("请输入导航名称。");
          return;
        }
        if (this.menuForm.id) {
          this.request.put('/system/metadata/menus/' + this.menuForm.id, this.menuForm).then(response => {
            this.menuForm = response;
            this.$message({
              message: '保存成功！',
              type: 'success'
            });
            this.fetchData();
          }).catch(error => {
            this.$message.error(error.response.data.description);
          });
        } else {
          this.request.post('/system/metadata/menus', this.menuForm).then(response => {
            this.menuForm = response;
            this.$message({
              message: '保存成功！',
              type: 'success'
            });
            this.fetchData();
          }).catch(error => {
            this.$message.error(error.response.data.description);
          });
        }
      },
      deleteMenu() {
        this.$confirm('确认要删除导航' + this.menuForm.name + '吗?', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.request.delete('/system/metadata/menus/' + this.menuForm.id).then(() => {
            this.$message({
              type: 'success',
              message: '删除成功！'
            });
            this.fetchData();
          }).catch(() => {
            this.$message.error('删除失败！');
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除！'
          });
        });
      },
      loadOperations() {
        this.request.get('/system/metadata/menus/' + this.menuForm.id + '/operations').then(response => {
          this.menuOperations = response;
        });
      },
      createOperations() {
        this.menuOperation = {
          code: '',
          name: '',
          delstatus: '0'
        };
        this.menuOperations.push(this.menuOperation);
        this.editIndex = this.menuOperations.length - 1;
      },
      editOperation(index, operation) {
        this.editIndex = index;
        this.menuOperation = operation;
      },
      cancelEdit() {
        this.editIndex = -1;
        if (!this.menuOperation.id) {
          this.menuOperations = this.menuOperations.filter(t => t.id);
        }
      },
      saveOperation() {
        if (this.menuOperation.id) {
          this.request.put('/system/metadata/menus/' + this.menuForm.id + '/operations/' + this.menuOperation.id, this.menuOperation)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editIndex = -1;
              this.loadOperations();
            });
        } else {
          this.request.post('/system/metadata/menus/' + this.menuForm.id + '/operations', this.menuOperation)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editIndex = -1;
              this.loadOperations();
            });
        }
      },
      deleteOperation(operation) {
        this.$confirm('确认要删除操作' + operation.name + '吗?', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.request.delete('/system/metadata/menus/' + operation.id + '/operations').then(() => {
            this.$message({
              type: 'success',
              message: '删除成功！'
            });
            this.loadOperations();
          }).catch(() => {
            this.$message.error('删除失败！');
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除！'
          });
        });
      }
    }
  }

</script>
