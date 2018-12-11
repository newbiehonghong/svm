export default [
    {
        // 用户管理
        path: '/security_user',
        component: () => import(/* webpackChunkName: "security" */'@/views/security/UserManage.vue'),
        meta: { title: '用户管理', permissions: ['security_p_admin'] }
    },
    {
        // 角色管理
        path: '/security_role',
        component: () => import(/* webpackChunkName: "security" */'@/views/security/RoleManage.vue'),
        meta: { title: '角色管理', permissions: ['security_p_admin'] }
    },
    {
        // 权限项管理
        path: '/security_permission',
        component: () => import(/* webpackChunkName: "security" */'@/views/security/PermissionManage.vue'),
        meta: { title: '权限项管理', permissions: ['security_p_admin'] }
    }
];