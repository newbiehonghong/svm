export default [
    {
        // 权限页面
        path: '/permission',
        component: resolve => require(['@/views/sample/Permission.vue'], resolve),
        meta: { title: '权限测试', permissions: ['sample/p_admin'] }
    }
];