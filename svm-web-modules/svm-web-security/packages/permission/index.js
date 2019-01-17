import PermissionManage from './src/permission-manage.vue'

PermissionManage.install = function(Vue) {
  Vue.component(PermissionManage.name, PermissionManage)
}

export default PermissionManage