import request from '@/utils/request'

// 查询餐桌信息
export function tableList() {
  return request({
    url: '/table/list',
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
