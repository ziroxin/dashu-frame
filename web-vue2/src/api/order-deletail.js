import request from '@/utils/request'

// 查询订单
export function orderDeletailsList(orderId) {
  return request({
    url: '/orderDetails/getOrderDetailsById',
    method: 'get',
    params:{orderId}
  })
}
