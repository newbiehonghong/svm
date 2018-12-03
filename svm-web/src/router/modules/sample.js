export default [
    {
        path: '/form',
        component: resolve => require(['@/views/sample/BaseForm.vue'], resolve),
        meta: { title: '基本表单' }
    },
    {
        path: '/table',
        component: resolve => require(['@/views/sample/BaseTable.vue'], resolve),
        meta: { title: '基础表格' }
    },
    {
        path: '/tabs',
        component: resolve => require(['@/views/sample/Tabs.vue'], resolve),
        meta: { title: 'tab选项卡' }
    },
    {
        path: '/upload',
        component: resolve => require(['@/views/sample/Upload.vue'], resolve),
        meta: { title: '文件上传' }   
    },
    {
        path: '/download',
        component: resolve => require(['@/views/sample/Download.vue'], resolve),
        meta: { title: '文件下载' }   
    },
    {
        // 拖拽列表组件
        path: '/drag',
        component: resolve => require(['@/views/sample/DragList.vue'], resolve),
        meta: { title: '拖拽列表' }
    }
];