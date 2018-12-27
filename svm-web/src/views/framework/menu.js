export default [
    {
        icon: 'el-icon-view',
        index: 'dashboard',
        title: '系统首页'
    },
    {
        icon: 'el-icon-star-on',
        index: '1',
        title: '开发样例',
        subs: [
            {
                icon: 'el-icon-edit-outline',
                index: 'form',
                title: '基本表单'
            },
            {
                icon: 'el-icon-tickets',
                index: 'table',
                title: '基础表格'
            },
            {
                icon: 'el-icon-message',
                index: 'tabs',
                title: 'tab选项卡'
            },
            {
                icon: 'el-icon-picture',
                index: 'upload',
                title: '文件上传'
            },
            {
                icon: 'el-icon-picture-outline',
                index: 'download',
                title: '文件下载'
            },
            {
                icon: 'el-icon-rank',
                index: 'drag',
                title: '拖拽列表'
            },
            {
                icon: 'el-icon-check',
                index: 'test',
                title: '临时测试'
            }
        ]
    },
    {
        icon: 'el-icon-star-off',
        index: '2',
        title: '权限管理',
        subs: [
            {
                icon: 'el-icon-star-off',
                index: 'security_user',
                title: '用户管理'
            },
            {
                icon: 'el-icon-star-off',
                index: 'security_role',
                title: '角色管理'
            },
            {
                icon: 'el-icon-star-off',
                index: 'security_permission',
                title: '权限项管理'
            }
        ]
    }
];