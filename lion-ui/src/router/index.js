import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import RouterConfig from './modules' // 引入业务逻辑模块
import CommonRouters from './common' // 引入通用模块

/**
//当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
hidden: true // (默认 false)

//当设置 noredirect 的时候该路由在面包屑导航中不可被点击
redirect: 'noredirect'

//当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
//只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
//若你想不管路由下面的 children 声明的个数都显示你的根路由
//你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
alwaysShow: true

name: 'router-name' //设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
meta: {
  title: 'title' //设置该路由在侧边栏和面包屑中展示的名字
  code: 'menu code' //如果需要权限控制，则指定权限的唯一code
  icon: 'svg-name' //设置该路由的图标
  noCache: true //如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
  breadcrumb: false // 如果设置为false，则不会在breadcrumb面包屑中显示
}
**/
export default new VueRouter({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: RouterConfig.concat(CommonRouters)
})
