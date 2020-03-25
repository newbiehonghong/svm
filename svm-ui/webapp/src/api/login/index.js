import request from '@/utils/request';
import md5 from 'md5';

/**
 * 登录
 * @param {String} name 用户名
 * @param {String} password 密码 
 */
export function login(name, password) {
    return request({
        url: '/login',
        data: {
            'name': name,
            'pwd': md5(password)
        }
    });
};

/**
 * 修改密码
 * @param {String} oldPwd 旧密码 
 * @param {String} newPad 新密码 
 */
export function changePwd(oldPwd, newPwd) {
    return request({
        url: '/changePwd',
        data: {
            'oldPwd': md5(oldPwd),
            'newPwd': md5(newPwd)
        }
    });
};