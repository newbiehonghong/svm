import axios from 'axios';
import qs from 'qs';
import { Message } from 'element-ui';
import store from '@/store';

export const apiURL = process.env.VUE_APP_BASE_API;

export function addToken(obj) {
    if (store.getters.token) {
        obj['Authorization'] = store.getters.token;
    }
}

export function getToken() {
    if (store.getters.token) {
        return 'Authorization=' + store.getters.token;
    }
}

// 创建axios实例
const service = axios.create({
    baseURL: apiURL
});

// request拦截器
service.interceptors.request.use(config => {
    addToken(config.headers);

    if (!config.headers['Content-Type']) {
        config.headers['Content-Type'] = 'application/json';
    } else if (config.headers['Content-Type'] == 'application/x-www-form-urlencoded') {
        config['transformRequest'] = [function (data) {
            return qs.stringify(data);
        }];
    }

    config.method = "post";//默认是get，此处统一成post目的是简化调用代码
    return config;
}, error => {
    Promise.reject(error)
}
);

// respone拦截器
service.interceptors.response.use(response => {
    const data = response.data;
    if (data && data.status && data.message) {
        Message({
            message: data.message,
            type: 'error'
        });
        //超时重新登录
        if (data.status === 400) {
            location.reload();
        }
        return Promise.reject(data.message);
    }
    return data || response;
}, error => {
    Message({
        message: error.message,
        type: 'error'
    });
    return Promise.reject(error);
}
);

export default service;
