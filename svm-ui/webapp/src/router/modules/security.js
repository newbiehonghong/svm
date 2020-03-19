export default [
    {
        // 用户管理
        path: '/security_user',
        component: () => import(/* webpackChunkName: "security" */'@/views/security/user/index.vue'),
        meta: { title: '用户管理', permissions: ['p_security_permission'] }
    },
    {
        // 角色管理
        path: '/security_role',
        component: () => import(/* webpackChunkName: "security" */'@/views/security/role/index.vue'),
        meta: { title: '角色管理', permissions: ['p_security_permission'] }
    },
    {
        // 权限项管理
        path: '/security_permission',
        component: () => import(/* webpackChunkName: "security" */'@/views/security/permission/index.vue'),
        meta: { title: '权限项管理', permissions: ['p_security_permission'] }
    }
];