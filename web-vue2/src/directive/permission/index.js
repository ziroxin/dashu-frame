import store from '@/store'

function checkPermission(el, binding) {
  const {value} = binding
  // 获取当前用户权限列表
  const permissions = store.getters && store.getters.permissions
  // 获取当前标签值
  if (value.length > 0) {
    const permissionKey = value
    // 比较用户是否有权限
    const hasPermission = permissions.some(perm => {
      return permissionKey.includes(perm.permissionName)
    })
    // 无权限移除dom
    if (!hasPermission) {
      el.parentNode && el.parentNode.removeChild(el)
    }
  }
}

export default {
  inserted(el, binding) {
    checkPermission(el, binding)
  },
  update(el, binding) {
    checkPermission(el, binding)
  }
}
