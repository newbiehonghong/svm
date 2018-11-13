import request from '@/utils/request';
import qs from 'qs';

/**
 * 登录
 * @param {String} username 用户名
 * @param {String} password 密码 
 */
export function login(username, password) {
    return request({
        url: '/login',
        data: qs.stringify({
            username,
            password
        }),
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    });
};

/**
 * 登出
 */
export function logout() {
    return request({
        url: '/logout'
    });
}
