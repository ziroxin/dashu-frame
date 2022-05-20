import request from '@/utils/request'

// 查询
export function permissionList() {
  return request({
    url: '/permission/list',
    method: 'get'
  })
}

export function permissionAdd(data) {
  return request({
    url: '/permission/add',
    method: 'post',
    data
  })
}

export function permissionUpdate(data) {
  return request({
    url: '/permission/update',
    method: 'post',
    data
  })
}

export function permissionDelete(data) {
  return request({
    url: '/permission/delete',
    method: 'DELETE',
    data
  })
}
