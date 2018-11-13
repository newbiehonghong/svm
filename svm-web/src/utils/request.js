import axios from 'axios';
import { Message } from 'element-ui';
import store from '@/store';

export const apiURL = process.env.BASE_API;

export function addToken(obj) {
    if (store.getters.token) {
        obj['x-token'] = store.getters.token;
    }
}

export function getToken() {
    if (store.getters.token) {
        return 'x-token=' + store.getters.token;
    }
}

// 创建axios实例
const service = axios.create({
    baseURL: apiURL
});

// request拦截器
service.interceptors.request.use(config => {
        addToken(config.headers);

        if(!config.headers['Content-Type']) {
            config.headers['Content-Type'] = 'application/json';
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
        if(data && data.error) {
            Message({
                message: data.message,
                type: 'error',
                duration: 3000
            });
            return Promise.reject(data.message);
        }
        return response;
    }, error => {
        Message({
            message: error.message,
            type: 'error',
            duration: 3000
        });
        return Promise.reject(error);
    }
);

export default service;
