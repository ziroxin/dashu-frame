import request from '@/utils/request'

// 根据ID查询发票
export function orderBillById(orderId) {
  return request({
    url: '/orderbill/getOrderBillById',
    method: 'get',
    params:{orderId}
  })
}

// 查询发票
export function orderBillList() {
  return request({
    url: '/orderbill/list',
    method: 'get'
  })
}

// 添加发票信息
export function orderBillAdd(data) {
  return request({
    url: '/orderbill/add',
    method: 'post',
    data
  })
}

// 修改发票信息
export function orderBillUpdate(data) {
  return request({
    url: '/orderbill/update',
    method: 'post',
    data
  })
}

// 删除发票信息
export function orderBillDelete(data) {
  return request({
    url: '/orderbill/delete',
    method: 'delete',
    data
  })
}
