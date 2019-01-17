<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" unique-opened router>
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
    import menu from './menu';
    export default {
        props: {
            collapse: {default: false}
        },
        data() {
            return {
                items: menu
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
        }
    }
</script>

<style scoped>
    .sidebar {
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom:0;
        overflow-y: scroll;
    }
    .sidebar::-webkit-scrollbar {
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse) {
        width: 250px;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
