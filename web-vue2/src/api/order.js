import request from '@/utils/request'

// 查询订单
export function orderList(time) {
  return request({
    url: '/order/list',
    method: 'get',
    params:{startTime:time.startTime,
    endTime:time.endTime}
  })
}

// 添加订单
export function orderAdd(data) {
  return request({
    url: '/order/add',
    method: 'post',
    data
  })
}

// 修改订单
export function orderUpdate(data) {
  return request({
    url: '/order/update',
    method: 'post',
    data
  })
}

// 删除发票信息
export function orderDelete(data) {
  return request({
    url: '/order/delete',
    method: 'delete',
    data
  })
}
