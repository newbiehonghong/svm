import core from './core'
import UserManage from './user/index'
import RoleManage from './role/index'
import PermissionManage from './permission/index'

const components = [
    UserManage,
    RoleManage,
    PermissionManage
];

const install = function(Vue, opts = {}) {
    if(install.installed) return;
    components.map(component => Vue.component(component.name, component));
    if(opts.request) {
        for(let name in opts.request) {
            core[name] = opts.request[name];
        }
    }
};

export const securityRouter = [
    {
        // 用户管理
        path: '/security_user',
        component: UserManage,
        meta: { title: '用户管理', permissions: ['security_p_admin'] }
    },
    {
        // 角色管理
        path: '/security_role',
        component: RoleManage,
        meta: { title: '角色管理', permissions: ['security_p_admin'] }
    },
    {
        // 权限项管理
        path: '/security_permission',
        component: PermissionManage,
        meta: { title: '权限项管理', permissions: ['security_p_admin'] }
    }
];

export default {
    install,
    UserManage,
    RoleManage,
    PermissionManage
};