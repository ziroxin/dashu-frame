import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/permission/user/all',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/login/logout',
    method: 'get'
  })
}
