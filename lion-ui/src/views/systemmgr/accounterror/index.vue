<template>
  <div class="app-container">
    <el-header>
      <el-card class="box-card">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-form-item label="账号名：">
            <el-input v-model="searchForm.account" size="mini"></el-input>
          </el-form-item>
          <el-form-item label="姓名：">
            <el-input v-model="searchForm.chinaName" size="mini"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 20px"
                       @click="fetchData">查询
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-header>
    <el-main>
      <el-card class="box-card">
        <div slot="header" style="height: 5px;">
          封禁用户列表
        </div>
        <el-table
          v-loading="listLoading"
          :data="lockUsers"
          element-loading-text="Loading"
          border
          fit
          highlight-current-row>
          <el-table-column label="登录人账号">
            <template slot-scope="scope">
              {{ scope.row.account }}
            </template>
          </el-table-column>
          <el-table-column label="姓名" align="center">
            <template slot-scope="scope">
              {{ scope.row.chinaName }}
            </template>
          </el-table-column>
          <el-table-column label="ip" align="center">
            <template slot-scope="scope">
              {{ scope.row.ip }}
            </template>
          </el-table-column>
          <el-table-column label="上一次登录错误的时间" align="center">
            <template slot-scope="scope">
              {{ scope.row.errorDate }}
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="操作" align="center">
            <template slot-scope="scope">
              <el-button @click="rollbackLockUser(scope.$index, scope.row)"
                         type="primary" title="解锁" size="mini">解锁
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :total="searchForm.total"
          :page-sizes="[10,15,20]"
          :page.sync="searchForm.current"
          :limit.sync="searchForm.size"
          @pagination="fetchData"/>
      </el-card>
    </el-main>
  </div>
</template>
<style>
  .pagination-container {
    height: 1px;
  }
</style>
<script>
  export default {
    filters: {},
    data() {
      return {
        listLoading: false,
        lockUsers: [],
        searchForm: {
          account: "",
          chinaName: "",
          size: 10,
          current: 1,
          total: 0,
        }
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true;
        this.request.get('/system/organization/users/lockUsers', this.searchForm).then(result => {
          this.lockUsers = result.records;
          this.searchForm.total = result.total;
          this.listLoading = false;
        });
      },
      rollbackLockUser(index, lockUser) {
        this.request.put("/system/organization/users/lockUsers/" + lockUser.id).then(result => {
          this.$message.success("解锁成功");
          this.fetchData();
        });
      }
    }
  }
</script>
