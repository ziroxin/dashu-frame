import Vue from 'vue'
import App from './App'
import uView from 'uview-ui'
import store from 'store'
import {
    copy,
    message,
    JumpPage,
    uploadImg,
    saveImg,
    orderStatusText
} from 'common/unit.js'

import {
    testURL,
    baseURL,
    imgURL,
} from 'api/config.js'

import http from 'common/request.js'

import oauth from 'common/oauth.js'

// 获取验证码
import getCode from 'components/get-auth-code/index.vue'
// 商品详情
import GoodDetail from 'components/good-detail/index.vue'

import uniForms from './uni_modules/uni-forms/components/uni-forms/uni-forms.vue'

import uniFormsItem from './uni_modules/uni-forms/components/uni-forms-item/uni-forms-item.vue'

import uniEasyinput from './uni_modules/uni-easyinput/components/uni-easyinput/uni-easyinput.vue'


Vue.component('uni-easyinput',uniEasyinput)
Vue.component('uni-forms-item',uniFormsItem)
Vue.component('uni-forms',uniForms)
Vue.component("getCode", getCode)
Vue.component('good-detail', GoodDetail)
Vue.use(uView)

Object.assign(Vue.prototype, {
    // 复制
    $copy: copy,
    // 消息提示框
    $message: message,
    // 路由跳转
    JumpPage,
    // 网络请求
    $http: http.mainRequest,
    // 小程序授权
    $oauth: oauth,
    // 上传图片
    $uploadImg: uploadImg,
    // 保存图片到本地
    $saveImg: saveImg,
    // 图片资源地址
    $imgURL: imgURL,
    // 接口资源地址
    $baseURL: baseURL,
    // 测试期间模拟订单
    // 状态以及操作按钮文本
    $orderStatusText: orderStatusText,

});
Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    store,
    ...App
})
app.$mount()
