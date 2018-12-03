export default [
    {
        // 用户管理
        path: '/security_user',
        component: resolve => require(['@/views/security/UserManage.vue'], resolve),
        meta: { title: '用户管理', permissions: ['security/p_admin'] }
    }
];