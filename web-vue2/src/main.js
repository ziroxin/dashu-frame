import Vue from 'vue'

import Cookies from 'js-cookie'
// CSS重置工具，纠正错误和常见的浏览器的不一致性
import 'normalize.css/normalize.css'

import Element from 'element-ui'
import './styles/element-variables.scss'
// 如果使用中文语言包请默认支持，无需额外引入，请删除该依赖
// import enLang from 'element-ui/lib/locale/lang/en'

// 全局样式
import '@/styles/index.scss'

import App from './App'
import store from './store'
import router from './router'

import './icons'
import './permission'
import './utils/error-log'

// 导入全局filters
import * as filters from './filters'
// 导入权限指令
import permission from '@/directive/permission';

Vue.use(Element, {
  // 设置element-ui默认大小
  size: Cookies.get('size') || 'medium'
  // 英文语言包，如果使用中文，无需设置，请删除
  //, locale: enLang
})

// 注册全局过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})
// 注册权限指令
Vue.directive('permission', permission)

// 开发时不提示生产环境
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
