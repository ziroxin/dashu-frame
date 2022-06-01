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

export function getUserList() {
  return request({
    url: '/user/list',
    method: 'get'
  })
}

export function userAdd(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function userUpdate(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

export function userDelete(data) {
  return request({
    url: '/user/delete',
    method: 'delete',
    data
  })
}
