import request from '@/utils/request'

export function getApiList(params) {
  return request({
    url: '/api/listGroupApi',
    method: 'get',
    params
  })
}

export function permissionTreeList(params) {
  return request({
    url: '/permission/tree/list',
    method: 'get'
  })
}

export function scanApi() {
  return request({
    url: 'api/saveScanApi',
    method: 'get'
  })
}
