import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '../store'
import {getToken} from '@/utils/auth'
import router from '../router'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 10000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['X-Auth-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
      return response.data
  },
  error => {
    let errorMsg = error.message;
    if (error && error.response) {
      switch (error.response.status) {
        case 400:
          errorMsg = '错误请求'
          break;
        case 401:
          errorMsg = '未授权，请重新登录'
          break;
        case 403:
          errorMsg = '拒绝访问'
          break;
        case 404:
          errorMsg = '请求错误,未找到该资源'
          break;
        case 405:
          errorMsg = '请求方法未允许'
          break;
        case 408:
          errorMsg = '请求超时'
          break;
        case 500:
          if (error.response.data && error.response.data.message) {
            errorMsg = error.response.data.message;
          } else errorMsg = '服务器端出错'
          break;
        case 501:
          errorMsg = '网络未实现'
          break;
        case 502:
          errorMsg = '网络错误'
          break;
        case 503:
          errorMsg = '服务不可用'
          break;
        case 504:
          errorMsg = '网络超时'
          break;
        case 505:
          errorMsg = 'http版本不支持该请求'
          break;
        default:
          errorMsg = `连接错误${error.response.status}`
      }
      if(error.response.data && error.response.data.message)errorMsg=error.response.data.message;

      if(error.response.status === 401 || error.response.status === 403){
        Message({
          message: errorMsg,
          type: 'error',
          duration: 5 * 1000
        })
        router.push({path: '/401'})
        return Promise.reject(error)
      }
      else if(error.response.status === 404){
        router.push({path: '/404'})
        return Promise.reject(error)
      }
    }

    Message({
      message: errorMsg,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default {
  get(url, params) {
    return service.get(url, {
      params: params
    });
  },
  put(url, data) {
    return service.put(url, data);
  },
  post(url, data) {
    return service.post(url, data);
  },
  delete(url, params) {
    return service.delete(url, {
      params: params
    });
  }
}
