import request from '@/utils/request'

// 查询餐桌信息
export function tableList() {
  return request({
    url: '/table/getTableList',
    method: 'get'
  })
}

// 添加餐桌
export function tableAdd(data) {
  return request({
    url: '/table/add',
    method: 'post',
    data
  })
}

// 修改餐桌信息
export function tableUpdate(data) {
  return request({
    url: '/table/update',
    method: 'post',
    data
  })
}

// 删除餐桌信息
export function tableDelete(data) {
  return request({
    url: '/table/delete',
    method: 'delete',
    data
  })
}

//添加餐桌二维码
export function tableCodeAdd(tableIds) {
  return request({
    url: '/table/qrcode/generate',
    method: 'get',
    params:{tableIds}
  })
}

// 查询餐桌信息
export function lookTableCode(tableId) {
  return request({
    url: '/table/qrcode/lookTableCode',
    method: 'get',
    params:{tableId}
  })
}
