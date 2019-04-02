import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n
import '@/styles/index.scss' // global css
import './assets/iconfont/iconfont.css'
import request from '@/utils/request'
import App from './App'
import router from './router'
import store from './store'
import '@/icons' // icon
import '@/permission' // permission control
import '@/components/global' // global components

import moment from 'moment';
import lodash from 'lodash'
/**
 * This project originally used easy-mock to simulate data,
 * but its official service is very unstable,
 * and you can build your own service if you need it.
 * So here I use Mock.js for local emulation,
 * it will intercept your request, so you won't see the request in the network.
 * If you remove `../mock` it will automatically request easy-mock data.
 */
import '../mock' // simulation data

Vue.use(ElementUI, { locale });
Vue.prototype.request = request;
Vue.config.productionTip = false;
Object.defineProperty(Vue.prototype, '$moment', { value: moment });
Object.defineProperty(Vue.prototype, '$lodash', { value: lodash });
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
