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
        component: () => import(/* webpackChunkName: "framework" */'@/views/login/Login.vue')
    },
    {
        path: '/403',
        component: () => import(/* webpackChunkName: "framework" */'@/views/errorpage/403.vue')
    },
    {
        path: '/404',
        component: () => import(/* webpackChunkName: "framework" */'@/views/errorpage/404.vue')
    }
];

function getContextPath () {
    let path = location.pathname;
    return path.substring(0, path.lastIndexOf('/') + 1);
}

export default new Router({
    //mode: 'history',//history模式下刷新或者直接请求http://yoursite.com/user/id，会报404错误
    base: getContextPath(),//default is '/', modify for vue is not at web root directory
    routes: constantRouterMap
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
        component: () => import(/* webpackChunkName: "framework" */'@/views/framework/Home.vue'),
        children: [
            {
                path: '/dashboard',
                component: () => import(/* webpackChunkName: "framework" */'@/views/framework/Dashboard.vue'),
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
