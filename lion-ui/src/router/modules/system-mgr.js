/** When your routing table is too long, you can split it into small modules**/

import Layout from '../../views/layout/Layout'

const systemMgrRouter = [
  {
    path: '/system-mgr',
    component: Layout,
    redirect: '/system-mgr/category',
    name: 'SystemMgr',
    meta: {title: '系统管理', code: 'systemmgr', icon: 'example'},
    sortIndex: 5,
    children: [
      {
        path: 'category',
        name: 'Category',
        component: () => import('@/views/systemmgr/category/index'),
        meta: {title: '字典管理', code: 'systemmgr.category', icon: 'table'}
      },
      {
        path: 'menu',
        name: 'Menu',
        component: () => import('@/views/systemmgr/menu/index'),
        meta: {title: '导航管理', code: 'systemmgr.menu', icon: 'tree'}
      }, {
        path: 'user',
        name: 'User',
        component: () => import('@/views/systemmgr/user/index'),
        meta: {title: '用户管理', code: 'systemmgr.user', icon: 'user'}
      },
      {
        path: 'role',
        name: 'role',
        component: () => import('@/views/systemmgr/role/index'),
        meta: {title: '角色管理', code: 'systemmgr.role', icon: 'user'}
      },
      {
        path: 'changepwd',
        name: 'Changepwd',
        component: () => import('@/views/systemmgr/changepwd/index'),
        meta: {title: '修改密码', code: 'systemmgr.changepwd', icon: 'password'}
      },
      {
        path: 'accounterror',
        name: 'Accounterror',
        component: () => import('@/views/systemmgr/accounterror/index'),
        meta: {title: '账号解锁', code: 'systemmgr.accounterror', icon: 'password'}
      },
      {
        path: 'slowsql',
        name: 'Slowsql',
        component: () => import('@/views/systemmgr/slowsql/index'),
        meta: {title: '慢SQL记录', code: 'systemmgr.slowsql', icon: 'form'}
      }
    ]
  }
];

export default systemMgrRouter
