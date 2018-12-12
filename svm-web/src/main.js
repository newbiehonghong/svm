import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import qs from 'qs';

import ElementUI from 'element-ui';
//import 'element-ui/lib/theme-chalk/index.css'; //在index.html中静态引入css文件
import "babel-polyfill";

import store from './store';
import _global from './utils/global';

Vue.use(ElementUI, { size: 'small' });
Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
Vue.prototype.$global = _global;

function getToken() {
    return store.getters.token;
}

const whiteList = ['/login'] // 不重定向白名单

router.beforeEach((to, from, next) => {
    if (getToken()) {
        if (to.path === '/login') {
            next({ path: '/' });
        } else {
            next();
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next();
        } else {
            next('/login');
        }
    }
});

window.addEventListener('unhandledrejection', event => {
    //console.log(event.reason);
});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');