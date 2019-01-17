import BaseForm from './form/base-form'
import BaseTable from './table/base-table'
import Tabs from './tabs/main'
import Upload from './file/upload'
import Download from './file/download'
import Test from './test/main'

export const sampleRouter = [
    {
        path: '/sample_base_form',
        name: 'BaseForm',
        component: BaseForm,
        meta: { title: '基本表单' }
    },
    {
        path: '/sample_base_table',
        component: BaseTable,
        meta: { title: '基础表格' }
    },
    {
        path: '/sample_tabs',
        component: Tabs,
        meta: { title: 'tab选项卡' }
    },
    {
        path: '/sample_upload',
        component: Upload,
        meta: { title: '文件上传' }   
    },
    {
        path: '/sample_download',
        component: Download,
        meta: { title: '文件下载' }   
    },
    {
        path: '/sample_test',
        component: Test,
        meta: { title: '临时测试' }
    }
];