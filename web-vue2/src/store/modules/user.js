import {getInfo, login, logout} from '@/api/user'
import {getToken, removeToken, setToken} from '@/utils/auth'
import router, {resetRouter} from '@/router'

const state = {
  token: getToken(),
  name: '',
  avatar: '',
  introduction: '',
  perrouters: [],
  permissions: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_PERROUTERS: (state, perrouters) => {
    state.perrouters = perrouters
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissios = permissions
  }
}

const actions = {
  // 登录
  login({commit}, userInfo) {
    const {userName, password} = userInfo
    return new Promise((resolve, reject) => {
      login({userName: userName.trim(), password: password}).then(response => {
        const {data} = response
        commit('SET_TOKEN', data.accessToken)
        setToken(data.accessToken)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 加载用户信息
  getInfo({commit, state}) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const {data} = response

        if (!data) {
          reject('验证失败，请重新登录！')
        }

        const {permissions, perrouters} = data
        const {nickName, avatar, introduce} = data.user

        commit('SET_PERMISSIONS', permissions)
        commit('SET_PERROUTERS', perrouters)
        commit('SET_NAME', nickName)
        commit('SET_AVATAR', avatar)
        commit('SET_INTRODUCTION', introduce)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 退出登录
  logout({commit, state, dispatch}) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_PERROUTERS', [])
        commit('SET_PERMISSIONS', [])
        removeToken()
        resetRouter()

        // 重置标签缓存
        dispatch('tagsView/delAllViews', null, {root: true})

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 清除Token
  resetToken({commit}) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_PERROUTERS', [])
      commit('SET_PERMISSIONS', [])
      removeToken()
      resolve()
    })
  },

  // 动态修改权限
  async changeRoles({commit, dispatch}, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const {roles} = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, {root: true})
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, {root: true})
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
