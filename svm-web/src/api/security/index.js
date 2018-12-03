import request from '@/utils/request';

/**
 * 保存用户信息
 * @param {Object} user 用户信息
 */
export function saveUser(user) {
    return request({
        url: '/security/user/save',
        data: user
    });
};

/**
 * 更新用户信息
 * @param {Object} user 用户信息 
 */
export function updateUser(user) {
    return request({
        url: '/security/user/update',
        data: user
    });
};

/**
 * 删除用户信息
 * @param {String} id 用户ID 
 */
export function deleteUser(id) {
    return request({
        url: '/security/user/delete/' + id
    });
};

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 */
export function queryAllUsers(pageNum) {
    return request({
        url: '/security/user/queryAll',
        params: {pageNum}
    });
};