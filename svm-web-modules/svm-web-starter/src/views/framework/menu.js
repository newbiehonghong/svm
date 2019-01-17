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
                index: 'sample_base_form',
                title: '基本表单'
            },
            {
                icon: 'el-icon-tickets',
                index: 'sample_base_table',
                title: '基础表格'
            },
            {
                icon: 'el-icon-message',
                index: 'sample_tabs',
                title: 'tab选项卡'
            },
            {
                icon: 'el-icon-picture',
                index: 'sample_upload',
                title: '文件上传'
            },
            {
                icon: 'el-icon-picture-outline',
                index: 'sample_download',
                title: '文件下载'
            },
            {
                icon: 'el-icon-check',
                index: 'sample_test',
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