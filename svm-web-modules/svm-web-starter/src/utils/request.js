import axios from 'axios';
import { Message } from 'element-ui';
import store from '@/store';

const apiURL = process.env.BASE_API;

function addToken(obj) {
    if (store.getters.token) {
        obj['x-token'] = store.getters.token;
    }
}

function getToken() {
    if (store.getters.token) {
        return 'x-token=' + store.getters.token;
    }
}

// 创建axios实例
const request = axios.create({
    baseURL: apiURL
});

// request拦截器
request.interceptors.request.use(config => {
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
request.interceptors.response.use(response => {
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

export default {
    request, 
    apiURL, 
    addToken, 
    getToken
};
