import request from '@/utils/request'

// 查询
export function groupList() {
  return request({
    url: '/group/list',
    method: 'get'
  })
}

// 查询
export function groupListById() {
  return request({
    url: '/group/getGroupById',
    method: 'get'
  })
}

//添加菜单分组
export function groupAdd(data) {
  return request({
    url: '/group/add',
    method: 'post',
    data
  })
}

//修改菜单分组
export function groupUpdate(data) {
  return request({
    url: '/group/update',
    method: 'post',
    data
  })
}

//添加菜单分组
export function groupDelete(data) {
  return request({
    url: '/group/delete',
    method: 'delete',
    data
  })
}
