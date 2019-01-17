import Vue from 'vue';
import App from './App';
import router from './router';
import "babel-polyfill";
import ElementUI from 'element-ui';
//import 'element-ui/lib/theme-chalk/index.css'; //在index.html中静态引入css文件

import store from './store';
import _request from './utils/request';
import _global from './utils/global';

//import WebSecurity from 'svm-web-security';
//import WebSample from 'svm-web-sample';

Vue.use(ElementUI, { size: 'small' });
//Vue.use(WebSecurity, {request});
//Vue.use(WebSample, {request});

Vue.prototype.$api = _request;
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

//自定义指令，可以设置ElementUI元素自动获得焦点，
//手动获得焦点即使加了{value}参数也没好用
Vue.directive('focus', {
    inserted: function (el) {
        el.querySelector('input').focus();
    }
});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');