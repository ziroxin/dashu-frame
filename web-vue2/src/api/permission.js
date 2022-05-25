import request from '@/utils/request'

// 查询
export function permissionTreeList() {
  return request({
    url: '/permission/treeList',
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

export function getListById(permissionId) {
  return request({
    url: '/permission/getListById',
    method: 'GET',
    params:{permissionId}
  })
}

