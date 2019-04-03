<template>
  <div class="app-container">
    <el-header>
      <el-card class="box-card">
        <el-form :inline="true" :model="queryParams" class="demo-form-inline">
          <el-form-item label="开始时间：">
            <el-date-picker v-model="queryParams.beginTime" value-format="yyyy-MM-dd HH:mm:ss"
                            type="datetime" size="mini"></el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间：">
            <el-date-picker v-model="queryParams.endTime" value-format="yyyy-MM-dd HH:mm:ss"
                            type="datetime" size="mini"></el-date-picker>
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
          慢SQL列表
        </div>
        <el-table
          v-loading="listLoading"
          :data="slowSqlList"
          element-loading-text="Loading"
          border
          fit
          highlight-current-row>
          <el-table-column label="执行时间超过限定的SQL" min-width="300">
            <template slot-scope="scope">
              <!--{{ scope.row.slowSql }}-->
              <el-popover placement="left" width="700" trigger="hover">
                <p>{{scope.row.slowSql}}</p>
                <i title="执行时间超过限定的SQL" slot="reference"
                   style="width: 500px;text-overflow:ellipsis;white-space: nowrap;overflow: hidden;">
                  {{scope.row.slowSql}}
                </i>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="执行时间" align="center" width="180">
            <template slot-scope="scope">
              {{ scope.row.operateTime }}
            </template>
          </el-table-column>
          <el-table-column label="执行时效(秒)" align="center" width="120">
            <template slot-scope="scope">
              {{ scope.row.duration / 1000 }}
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :total="queryParams.total"
          :page-sizes="[10,15,20]"
          :page.sync="queryParams.current"
          :limit.sync="queryParams.size"
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
        slowSqlList: [],
        queryParams: {
          beginTime: "",
          endTime: "",
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
        this.request.get('/system/slowSql', this.queryParams).then(result => {
          this.slowSqlList = result.records;
          this.queryParams.total = result.total;
          this.listLoading = false;
        });
      }
    }
  }
</script>
