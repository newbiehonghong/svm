import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";
import qs from "qs";
import _global from "./utils/global";
import Bus from "./Bus";

import "./plugins/element.js";
import "./assets/css/element-ui.scss";
import "./plugins/scroll.js";
import "./assets/css/framework.css";
import "./plugins/dragula.js";

Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
Vue.prototype.$global = _global;
Vue.prototype.bus = Bus;

Vue.config.productionTip = false;

function getToken() {
    return store.getters.token;
}

const whiteList = ["/login"]; // 不重定向白名单

router.beforeEach((to, from, next) => {
    if (getToken()) {
        if (to.path === "/login") {
            next({ path: "/" });
        } else {
            next();
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next();
        } else {
            next("/login");
        }
    }
});

window.addEventListener("unhandledrejection", event => {
    //console.log(event.reason);
});

//自定义指令，可以设置ElementUI元素自动获得焦点，
//手动获得焦点即使加了{value}参数也没好用
Vue.directive("focus", {
    inserted: function (el) {
        el.querySelector("input").focus();
    }
});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
