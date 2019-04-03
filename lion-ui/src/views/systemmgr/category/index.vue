<template>
  <div class="app-container">
    <div style="width: 40%;float: left">
      <el-card class="box-card">
        <div slot="header">
          <span>字典列表</span>
          <el-button type="primary" size="small" style="float: right;margin-right: 15px" @click="createConfig()">新&nbsp;增</el-button>
        </div>
        <el-table
          v-loading="listLoading"
          :data="list"
          @row-click="showConfig"
          element-loading-text="Loading"
          border
          fit
          highlight-current-row>
          <el-table-column align="center" label="#" width="40">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="配置名称">
            <template slot-scope="scope">
              <span v-show="editIndex!=scope.$index">{{ scope.row.name }}</span>
              <el-input v-show="editIndex==scope.$index" v-model="config.name" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="配置别名" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editIndex!=scope.$index">{{ scope.row.alias }}</span>
              <el-input v-show="editIndex==scope.$index" v-model="config.alias" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="配置描述" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editIndex!=scope.$index">{{ scope.row.description }}</span>
              <el-input v-show="editIndex==scope.$index" v-model="config.description" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="状态" width="50" align="center">
            <template slot-scope="scope">
              <i v-show="editIndex!=scope.$index && scope.row.status=='1'" class="el-icon-circle-check"></i>
              <i v-show="editIndex!=scope.$index && scope.row.status=='0'" class="el-icon-circle-close"></i>
              <el-checkbox v-show="editIndex==scope.$index" v-model="config.status"
                           true-label="1" false-label="0"/>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="操作" width="50" align="center">
            <template slot-scope="scope">
              <el-button v-show="editIndex!=scope.$index" @click="editConfig(scope.$index, scope.row)"
                         type="primary" title="编辑" icon="el-icon-edit" circle size="mini"></el-button>
              <el-button v-show="editIndex==scope.$index" @click="saveConfig()"
                         type="success" title="保存" icon="el-icon-check" circle size="mini"></el-button>
              <el-button v-show="editIndex==scope.$index" @click="cancelEdit(scope.row)"
                         type="danger" title="取消" icon="el-icon-close" circle size="mini"></el-button>
              <!--<el-button v-show="editIndex!=scope.$index" @click="deleteOperation(scope.row)"
                         type="danger" title="删除" icon="el-icon-delete" circle></el-button>-->
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>

    <div style="width: 60%; float: left;padding: 0 5px">
      <el-card class="box-card">
        <div slot="header">
          <span>字典项目列表</span>
          <el-button type="primary" size="small" style="float: right;margin-right: 15px;" @click="createConfigItem()">
            新&nbsp;增
          </el-button>
        </div>
        <el-table
          v-loading="listLoading"
          :data="configItems"
          @row-click="showConfigItemChild"
          element-loading-text="Loading"
          border
          fit
          height="270"
          highlight-current-row>
          <el-table-column align="center" label="#" width="40">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="配置名称">
            <template slot-scope="scope">
              <span v-show="editItemIndex!=scope.$index">{{ scope.row.name }}</span>
              <el-input v-show="editItemIndex==scope.$index" v-model="configItem.name" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="配置别名" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editItemIndex!=scope.$index">{{ scope.row.alias }}</span>
              <el-input v-show="editItemIndex==scope.$index" v-model="configItem.alias" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="配置项值" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editItemIndex!=scope.$index">{{ scope.row.value }}</span>
              <el-input v-show="editItemIndex==scope.$index" v-model="configItem.value" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="排序号" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editItemIndex!=scope.$index">{{ scope.row.sort }}</span>
              <el-input v-show="editItemIndex==scope.$index" v-model="configItem.sort" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="状态" width="50" align="center">
            <template slot-scope="scope">
              <i v-show="editItemIndex!=scope.$index && scope.row.status=='1'" class="el-icon-circle-check"></i>
              <i v-show="editItemIndex!=scope.$index && scope.row.status=='0'" class="el-icon-circle-close"></i>
              <el-checkbox v-show="editItemIndex==scope.$index" v-model="configItem.status"
                           true-label="1" false-label="0"/>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="操作" width="50" align="center">
            <template slot-scope="scope">
              <el-button v-show="editItemIndex!=scope.$index" @click="editConfigItem(scope.$index, scope.row)"
                         type="primary" title="编辑" icon="el-icon-edit" circle size="mini"></el-button>
              <el-button v-show="editItemIndex==scope.$index" @click="saveConfigItem()"
                         type="success" title="保存" icon="el-icon-check" circle size="mini"></el-button>
              <el-button v-show="editItemIndex==scope.$index" @click="cancelItemEdit(scope.row)"
                         type="danger" title="取消" icon="el-icon-close" circle size="mini"></el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--查询分页-->
        <pagination
          :total="page.total"
          :page-sizes="[10,15,20]"
          :page.sync="page.current"
          :limit.sync="page.size"
          @pagination="pageChange"/>
      </el-card>

      <el-card class="box-card" style="margin-top: 5px;">
        <div slot="header">
          <span>字典项目子列表</span>
          <el-button type="primary" size="small" style="float: right;margin-right: 15px;"
                     @click="createConfigItemChild()">
            新&nbsp;增
          </el-button>
        </div>
        <el-table
          v-loading="listLoading"
          :data="configItemsChilds"
          @row-click=""
          element-loading-text="Loading"
          border
          fit
          height="270"
          highlight-current-row>
          <el-table-column align="center" label="#" width="40">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="配置名称">
            <template slot-scope="scope">
              <span v-show="editItemChildIndex!=scope.$index">{{ scope.row.name }}</span>
              <el-input v-show="editItemChildIndex==scope.$index" v-model="configItemsChild.name" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="配置别名" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editItemChildIndex!=scope.$index">{{ scope.row.alias }}</span>
              <el-input v-show="editItemChildIndex==scope.$index" v-model="configItemsChild.alias"
                        placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="配置项值" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editItemChildIndex!=scope.$index">{{ scope.row.value }}</span>
              <el-input v-show="editItemChildIndex==scope.$index" v-model="configItemsChild.value"
                        placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column label="序号" width="110" align="center">
            <template slot-scope="scope">
              <span v-show="editItemChildIndex!=scope.$index">{{ scope.row.sort }}</span>
              <el-input v-show="editItemChildIndex==scope.$index" v-model="configItemsChild.sort" placeholder="配置名称"/>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="状态" width="50" align="center">
            <template slot-scope="scope">
              <i v-show="editItemChildIndex!=scope.$index && scope.row.status=='1'" class="el-icon-circle-check"></i>
              <i v-show="editItemChildIndex!=scope.$index && scope.row.status=='0'" class="el-icon-circle-close"></i>
              <el-checkbox v-show="editItemChildIndex==scope.$index" v-model="configItemsChild.status"
                           true-label="1" false-label="0"/>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="操作" width="50" align="center">
            <template slot-scope="scope">
              <el-button v-show="editItemChildIndex!=scope.$index"
                         @click="editConfigItemChild(scope.$index, scope.row)"
                         type="primary" title="编辑" icon="el-icon-edit" circle size="mini"></el-button>
              <el-button v-show="editItemChildIndex==scope.$index" @click="saveConfigItemChild()"
                         type="success" title="保存" icon="el-icon-check" circle size="mini"></el-button>
              <el-button v-show="editItemChildIndex==scope.$index" @click="cancelItemChildEdit(scope.row)"
                         type="danger" title="取消" icon="el-icon-close" circle size="mini"></el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--查询分页-->
        <pagination
          :total="pageTwo.total"
          :page-sizes="[10,15,20]"
          :page.sync="pageTwo.current"
          :limit.sync="pageTwo.size"
          @pagination="pageTwoChange"/>
      </el-card>

    </div>

  </div>
</template>
<style>
  .pagination-container {
    height: 1px;
  }
</style>
<script>
  export default {
    filters: {
      statusFilter(status) {
        const statusMap = {
          0: '启用',
          1: '禁用'
        };
        return statusMap[status]
      }
    },
    data() {
      return {
        list: null,
        editIndex: -1,
        listLoading: false,
        config: {
          name: "",
          alias: "",
          description: "",
          status: '1'
        },

        configItems: null,
        editItemIndex: -1,
        configItem: {
          name: "",
          alias: "",
          value: "",
          sort: "",
          status: '1'
        },
        page: {
          size: 10,
          current: 1,
          total: 0,
        },

        configItemsChilds: null,
        editItemChildIndex: -1,
        configItemsChild: {
          name: "",
          alias: "",
          value: "",
          sort: "",
          status: '1',

        },
        pageTwo: {
          size: 10,
          current: 1,
          total: 0
        }

      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      //#region 字典列表 维护
      fetchData() {
        this.listLoading = true;
        this.editIndex = -1;
        this.request.get('/system/config/config').then(result => {
          this.list = result;
          this.listLoading = false;
        });
      },
      createConfig() {
        this.config = {
          name: "",
          alias: "",
          description: "",
          status: '1'
        };
        this.list.push(this.config);
        this.editIndex = this.list.length - 1;
      },
      editConfig(index, config) {
        this.editIndex = index;
        this.config = config;
      },
      cancelEdit() {
        this.editIndex = -1;
        this.list = this.list.filter(t => t.id);
      },
      saveConfig() {
        if (this.config.id) {
          this.request.put('/system/config/config/' + this.config.id, this.config)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editIndex = -1;
              this.fetchData();
            });
        } else {
          this.request.post('/system/config/config', this.config)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editIndex = -1;
              this.fetchData();
            });
        }
      },
      //#endregion 字典列表维护

      //#region 字典项目列表 维护
      showConfig(config) {
        this.config = config;
        this.editItemIndex = -1;
        let page = {};
        page.current = 1;
        page.size = this.page.size;
        this.request.get('/system/config/configItem/' + config.id, page).then(result => {
          this.page.total = result.total;
          this.configItems = result.records;
        });
      },
      pageChange() {
        let page = {};
        page.current = this.page.current;
        page.size = this.page.size;
        this.request.get('/system/config/configItem/' + this.config.id, page).then(result => {
          this.page.total = result.total;
          this.configItems = result.records;
        });
      },
      createConfigItem() {
        if (!this.config.id) {
          this.$message({
            type: 'warning',
            message: '请先选择字典列表！'
          });
          return;
        }
        this.configItem = {
          name: "",
          alias: "",
          value: "",
          sort: "",
          status: '1'
        };
        this.configItems.push(this.configItem);
        this.editItemIndex = this.configItems.length - 1;
      },
      editConfigItem(index, configItem) {
        this.editItemIndex = index;
        this.configItem = configItem;
      },
      cancelItemEdit() {
        this.editItemIndex = -1;
        this.configItems = this.configItems.filter(t => t.id);
      },
      saveConfigItem() {
        if (this.configItem.id) {
          this.request.put('/system/config/configItem/' + this.configItem.id, this.configItem)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editItemIndex = -1;
              this.showConfig(this.config);
            });
        } else {
          this.request.post('/system/config/configItem/' + this.config.id + "/" + null, this.configItem)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editItemIndex = -1;
              this.showConfig(this.config);
            });
        }
      },
      //#endregion字典项目列表维护

      //#region 字典项目子列表维护
      showConfigItemChild(row) {
        this.configItem = row;
        let page = {};
        page.current = this.pageTwo.current;
        page.size = this.pageTwo.size;
        this.request.get('/system/config/configItem/' + this.config.id + "/" + row.id, page).then(result => {
          this.pageTwo.total = result.total;
          this.configItemsChilds = result.records;
        });
      },
      createConfigItemChild() {
        if (!this.configItem.id) {
          this.$message({
            type: 'warning',
            message: '请先选择字典项目列表！'
          });
          return;
        }
        this.configItemsChild = {
          name: "",
          alias: "",
          value: "",
          sort: "",
          status: '1'
        };
        this.configItemsChilds.push(this.configItemsChild);
        this.editItemChildIndex = this.configItemsChilds.length - 1;
      },
      editConfigItemChild(index, configItemChild) {
        this.editItemChildIndex = index;
        this.configItemsChild = configItemChild;
      },
      saveConfigItemChild() {
        if (this.configItemsChild.id) {
          this.request.put('/system/config/configItem/' + this.configItemsChild.id, this.configItemsChild)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editItemChildIndex = -1;
              this.showConfigItemChild(this.configItem);
            });
        } else {
          this.request.post('/system/config/configItem/' + this.config.id + "/" + this.configItem.id, this.configItemsChild)
            .then(() => {
              this.$message({
                type: 'success',
                message: '保存成功！'
              });
              this.editItemChildIndex = -1;
              this.showConfigItemChild(this.configItem);
            });
        }
      },
      cancelItemChildEdit() {
        this.editItemChildIndex = -1;
        this.configItemsChilds = this.configItemsChilds.filter(t => t.id);
      },
      pageTwoChange() {
        this.showConfigItemChild(this.configItem);
      }
      //#endregion 字典项目子列表维护
    }
  }
</script>
