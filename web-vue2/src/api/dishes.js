import request from '@/utils/request'

//查询菜品信息
export function dishesList() {
  return request({
    url: '/dishes/list',
    method: 'get'
  })
}

//添加菜品信息
export function dishesAdd(data) {
  return request({
    url: '/dishes/add',
    method: 'post',
    data
  })
}

//修改菜品信息
export function dishesUpdate(data) {
  return request({
    url: '/dishes/update',
    method: 'post',
    data
  })
}

//删除菜品信息
export function dishesDelete(data) {
  return request({
    url: '/dishes/delete',
    method: 'delete',
    data
  })
}
