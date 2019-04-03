import Layout from '../views/layout/Layout'

const commonRouter = [
  {path: '/login', component: () => import('@/views/login/index'), hidden: true},
  {path: '/401', component: () => import('@/views/401'), hidden: true},
  {path: '/404', component: () => import('@/views/404'), hidden: true},

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    children: [{
      path: 'dashboard',
      meta: {title: '面板', code: 'dashboard', icon: 'example'},
      component: () => import('@/views/dashboard/index')
    }]
  },
  {path: '/1', redirect: '/404', hidden: true}
]

export default commonRouter
