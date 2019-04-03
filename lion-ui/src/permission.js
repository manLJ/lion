import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 验权

// permission judge function
function hasPermission(router) {
  // return true;
  if(router.meta && router.meta.code) return _.some(store.getters.visitPermissions,{code:router.meta.code});
  else return true;
}

router.beforeEach((to, from, next) => {
  NProgress.start();
  if (!to.meta || !to.meta.code) {//无需权限控制的路由
    next()
  } else if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.visitPermissions.length === 0) {
        store.dispatch('GetInfo').then(res => { // 拉取用户信息
          next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })
      } else {
        // 判断用户是否有权限访问
        if (hasPermission(to)) {
          next()
        } else {
          next({ path: '/401', replace: true, query: { noGoBack: true }})
        }
      }
    }
  } else {
    next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
    NProgress.done()
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
