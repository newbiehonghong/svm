export default [
    {
        // 用户管理
        path: '/security_user',
        component: resolve => require(['@/views/security/UserManage.vue'], resolve),
        meta: { title: '用户管理', permissions: ['security_p_admin'] }
    },
    {
        // 角色管理
        path: '/security_role',
        component: resolve => require(['@/views/security/RoleManage.vue'], resolve),
        meta: { title: '角色管理', permissions: ['security_p_admin'] }
    },
    {
        // 权限项管理
        path: '/security_permission',
        component: resolve => require(['@/views/security/PermissionManage.vue'], resolve),
        meta: { title: '权限项管理', permissions: ['security_p_admin'] }
    }
];