<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <breadcrumb />

    <el-tooltip :content="'全屏'" effect="dark" placement="bottom">
      <screenfull class="screenfull right-menu-item"/>
    </el-tooltip>

    <div class="avatar-container"  @click="logout">
      <el-tooltip :content="'退出系统'" effect="dark" placement="bottom">
        <svg-icon icon-class="shutdown" class="svg-shutdown"/>
      </el-tooltip>
    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Screenfull from '@/components/Screenfull'
import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb,
    Screenfull,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      console.log("1231231");
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 70px;
    top: 17px;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    top: 16px;
    right: 35px;
  }
  .svg-shutdown{
    cursor: pointer;
    fill: #0c0c0c;;
    width: 25px;
    height: 25px;
  }
}
</style>

