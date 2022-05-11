import Layout from '@/layout'

// 转换成组件
function convertComponent(component) {
  if (!component) {
    // 空直接返回
    return
  }
  if (component === 'Layout') {
    // 返回Layout
    return Layout
  } else {
    // 转换组件
    const view = component.indexOf('/') === 0 ? component.substring(1) : component

    if (process.env.NODE_ENV === 'development') {
      return resolve => require([`@/views/${view}`], resolve)
    } else {
      return () => import('@/views/' + view)
    }
  }
}

/**
 * 迭代（递归）循环出动态路由
 * @param routes asyncRoutes
 * @param permissions
 */
export function filterAsyncRoutes(routers) {
  const res = []
  // 遍历路由
  routers.forEach(route => {
      // 是否显示，是否禁用
      if (route.permissionIsShow && route.permissionIsEnabled) {
        if (route.children) {
          // 组装路由
          const temp = {
            path: route.permissionRouter,
            component: convertComponent(route.permissionComponent),
            name: route.permissionName || '',
            meta: {
              title: route.permissionTitle,
              icon: route.permissionIcon || ''
            },
            alwaysShow: true,
            // 迭代子路由
            children: filterAsyncRoutes(route.children)
          }
          res.push(temp)
        } else {
          const temp = {
            path: route.permissionRouter,
            component: Layout,
            children: [{
              path: 'index',
              component: convertComponent(route.permissionComponent),
              name: route.permissionName || '',
              meta: {
                title: route.permissionTitle,
                icon: route.permissionIcon || ''
              }
            }]
          }
          res.push(temp)
        }
      }
    }
  )
  return res
}

const state = {
  routes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.routes = routes
  }
}

const actions = {
  generateRoutes({commit}, routers) {
    return new Promise(resolve => {
      // 动态生成菜单
      const accessedRoutes = filterAsyncRoutes(routers)
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
