export default [
    {
        path: '/form',
        name: 'BaseForm',
        component: () => import(/* webpackChunkName: "sample" */'@/views/sample/BaseForm.vue'),
        meta: { title: '基本表单' }
    },
    {
        path: '/table',
        component: () => import(/* webpackChunkName: "sample" */'@/views/sample/BaseTable.vue'),
        meta: { title: '基础表格' }
    },
    {
        path: '/tabs',
        component: () => import(/* webpackChunkName: "sample" */'@/views/sample/Tabs.vue'),
        meta: { title: 'tab选项卡' }
    },
    {
        path: '/upload',
        component: () => import(/* webpackChunkName: "sample" */'@/views/sample/Upload.vue'),
        meta: { title: '文件上传' }   
    },
    {
        path: '/download',
        component: () => import(/* webpackChunkName: "sample" */'@/views/sample/Download.vue'),
        meta: { title: '文件下载' }   
    },
    {
        // 拖拽列表组件
        path: '/drag',
        component: () => import(/* webpackChunkName: "sample" */'@/views/sample/DragList.vue'),
        meta: { title: '拖拽列表' }
    },
    {
        // 临时测试组件
        path: '/test',
        component: () => import(/* webpackChunkName: "sample" */'@/views/sample/Test.vue'),
        meta: { title: '临时测试' }
    }
];