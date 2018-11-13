import Vue from 'vue';
import Router from 'vue-router';
import sample from './modules/sample';
import security from './modules/security';

Vue.use(Router);

function hasPermission(permissions, route) {
    if (route.meta && route.meta.permissions) {
        return permissions.some(permission => route.meta.permissions.indexOf(permission) >= 0);
    } else {
        return true;
    }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routerMap
 * @param permissions
 */
export function filterAsyncRouter(routerMap, permissions) {
    const accessedRouters = routerMap.filter(route => {
        if (hasPermission(permissions, route)) {
            if (route.children && route.children.length) {
                route.children = filterAsyncRouter(route.children, permissions);
            }
            return true;
        }
        return false;
    });
    return accessedRouters;
}

const constantRouterMap = [
    {
        path: '/login',
        component: resolve => require(['@/views/login/Login.vue'], resolve)
    },
    {
        path: '/403',
        component: resolve => require(['@/views/errorpage/403.vue'], resolve)
    },
    {
        path: '/404',
        component: resolve => require(['@/views/errorpage/404.vue'], resolve)
    }
];

export default new Router({
    routes: constantRouterMap,
});

//合并路由
let moduleRoutes = [...sample, ...security];

export const asyncRouterMap = [
    {
        path: '/',
        redirect: '/dashboard'
    },
    {
        path: '/',
        component: resolve => require(['@/views/framework/Home.vue'], resolve),
        children: [
            {
                path: '/dashboard',
                component: resolve => require(['@/views/framework/Dashboard.vue'], resolve),
                meta: { title: '系统首页' }
            },
            ...moduleRoutes
        ]
    },
    {
        path: '*',
        redirect: '/404'
    }
];
