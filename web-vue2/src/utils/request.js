import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'
import qs from 'qs'

// 创建axios
const service = axios.create({
  // 设置baseURL（url = base url + request url）
  baseURL: process.env.VUE_APP_BASE_API,
  // 跨域请求时，携带cookie
  // withCredentials: true,
  // 请求超时时间：20s
  timeout: 20000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 发送请求前操作

    if (store.getters.token) {
      // 给每个请求头，加上TOKEN：z_jwt_token
      config.headers['z_jwt_token'] = getToken()
    }
    if (config.method === 'get') {
      // 若是是get请求，且params是数组类型如arr=[1,2]，则转换成arr=1&arr=2
      config.paramsSerializer = function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' })
      }
    }
    return config
  },
  error => {
    // 请求出错时操作
    console.log(error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  // 如果想获取完整http响应信息（如headers,status），可以直接返回response
  response => {
    const res = response.data

    // 处理自定义状态码
    if (res.code === '200' || res.code === 200) {
      return res
    } else {
      Message({
        message: res.message || '请求出错',
        type: 'error',
        duration: 5 * 1000
      })

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 500 || res.code === 400 || res.code === 401 || res.code === 403 || res.code === 405) {
        // to re-login
        MessageBox.confirm('您的登录已失效，是否重新登录？', '是否重新登录', {
          confirmButtonText: 'Re-Login',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
