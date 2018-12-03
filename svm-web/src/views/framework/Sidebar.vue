<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
            text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in menus">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                        </template>
                        <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">
                            <i :class="subItem.icon"></i><span>{{ subItem.title }}</span>
                        </el-menu-item>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from '@/bus';
    export default {
        data() {
            return {
                collapse: false,
                items: [
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
                                icon: 'el-icon-warning',
                                index: 'permission',
                                title: '权限测试'
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
                                index: '404',
                                title: '角色管理'
                            }
                        ]
                    }
                ]
            }
        },
        computed: {
            menus() {
                let routeList = [];
                // 将路由数据扁平化为一级
                function flatRoutes(routes) {
                    for(let route of routes) {
                        if(route.children && route.children.length > 0) {
                            flatRoutes(route.children);
                        } else {
                            routeList.push(route.path.replace('/', ''));
                        }
                    }
                }
                flatRoutes(this.$store.getters.routes);

                function filterMenus(items) {
                    return items.filter(item => {
                        if (item.subs && item.subs.length > 0) {
                            item.subs = filterMenus(item.subs);
                            if(item.subs && item.subs.length > 0) {
                                return true;
                            }
                            return false;
                        }
                        return routeList.indexOf(item.index) >= 0;
                    });
                }

                return filterMenus(this.items);
            },
            onRoutes() {
                return this.$route.path.replace('/', '');
            }
        },
        created() {
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
            });
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom:0;
        overflow-y: scroll;
    }
    .sidebar::-webkit-scrollbar{
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse){
        width: 250px;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
