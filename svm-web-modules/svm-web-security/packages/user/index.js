import UserManage from './src/user-manage.vue'

UserManage.install = function(Vue) {
  Vue.component(UserManage.name, UserManage)
}

export default UserManage