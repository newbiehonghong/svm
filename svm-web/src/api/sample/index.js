import request from '@/utils/request';

/**
 * 保存用户信息
 * @param {Object} user 用户信息
 */
export function saveUser(user) {
    return request({
        url: '/sample/user/save',
        data: user
    });
};

/**
 * 更新用户信息
 * @param {Object} user 用户信息 
 */
export function updateUser(user) {
    return request({
        url: '/sample/user/update',
        data: user
    });
};

/**
 * 删除用户信息
 * @param {String} id 用户ID 
 */
export function deleteUser(id) {
    return request({
        url: '/sample/user/delete/' + id
    });
};

/**
 * 查询单条用户记录
 * @param {Number} id 用户ID
 */
export function getUser(id) {
    return request({
        url: '/sample/user/get/' + id
    });
};

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 */
export function queryAllUsers(pageNum) {
    return request({
        url: '/sample/user/queryAll',
        params: {pageNum}
    });
};

/**
 * 查询演示下载的记录
 * @param {Number} pageNum 页码 
 */
export function queryDownloadUsers(pageNum) {
    return request({
        url: '/sample/user/queryDownload',
        params: {pageNum}
    });
};

/**
 * 下载文件
 * @param {String} attachmentId 附件ID 
 */
export function downloadPicture(attachmentId) {
    return request({
        url: '/sample/user/download/' + attachmentId,
        responseType: 'blob'
    });
};