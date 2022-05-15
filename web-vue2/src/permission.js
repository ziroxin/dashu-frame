import router from './router'
import store from './store'
import {Message} from 'element-ui'
// 进度条
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
// 从Cookie中读取token
import {getToken} from '@/utils/auth'
import getPageTitle from '@/utils/get-page-title'

// 进度条配置
NProgress.configure({showSpinner: false})

// 单页白名单
const whiteList = ['/login', '/auth-redirect']

router.beforeEach(async (to, from, next) => {
  // 进度条
  NProgress.start()

  // 设置标题
  document.title = getPageTitle(to.meta.title)

  // 判断用户是否登录（有token代表登录）
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // 登录后，跳转到首页
      next({path: '/'})
      NProgress.done()
    } else {
      // 登录后，跳转到原来打开的页面
      // 跳转前，先判断：store里是否有角色信息
      const hasRouters = store.getters.perrouters && store.getters.perrouters.length > 0
      if (hasRouters) {
        next()
      } else {
        try {
          // 查询角色信息
          const {perrouters} = await store.dispatch('user/getInfo')
          if (perrouters && perrouters.length > 0) {
            // 根据角色，生成可访问权限（路由）
            const accessRoutes = await store.dispatch('permission/generateRoutes', perrouters)

            // 动态加载路由
            router.addRoutes(accessRoutes)

            // 加载路由完成，跳转
            // replace: true 不缓存，每次页面都刷新
            next({...to, replace: true})
          } else {
            await store.dispatch('user/resetToken')
            next(`/login?redirect=${to.path}`)
            Message.error('抱歉，您没有权限！')
            NProgress.done()
          }
        } catch (error) {
          // 出现错误，删除token，重新登录
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    // 无token
    if (whiteList.indexOf(to.path) !== -1) {
      // 查询路由是否在白名单里，是直接跳转
      next()
    } else {
      // 否，无权限，跳到登录页
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

// 路由加载完成
router.afterEach(() => {
  // 关闭进度条
  NProgress.done()
})
