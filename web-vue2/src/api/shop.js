import request from '@/utils/request'

// 查询
export function shopList() {
  return request({
    url: '/shop/list',
    method: 'get'
  })
}

export function shopAdd(data) {
  return request({
    url: '/shop/add',
    method: 'post',
    data
  })
}

export function shopUpdate(data) {
  return request({
    url: '/shop/update',
    method: 'post',
    data
  })
}

export function shopDelete(data) {
  return request({
    url: '/shop/delete',
    method: 'delete',
    data
  })
}

