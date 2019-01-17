/**
 * 保存用户信息
 * @param {Object} user 用户信息
 */
export function saveUser(cmp, user) {
    return cmp.$api.request({
        url: '/sample/user/save',
        data: user
    });
};

/**
 * 更新用户信息
 * @param {Object} user 用户信息 
 */
export function updateUser(cmp, user) {
    return cmp.$api.request({
        url: '/sample/user/update',
        data: user
    });
};

/**
 * 删除用户信息
 * @param {String} id 用户ID 
 */
export function deleteUser(cmp, id) {
    return cmp.$api.request({
        url: '/sample/user/delete/' + id
    });
};

/**
 * 查询单条用户记录
 * @param {Number} id 用户ID
 */
export function getUser(cmp, id) {
    return cmp.$api.request({
        url: '/sample/user/get/' + id
    });
};

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 */
export function queryAllUsers(cmp, pageNum) {
    return cmp.$api.request({
        url: '/sample/user/queryAll',
        params: {pageNum}
    });
};

/**
 * 查询演示下载的记录
 * @param {Number} pageNum 页码 
 */
export function queryDownloadUsers(cmp, pageNum) {
    return cmp.$api.request({
        url: '/sample/user/queryDownload',
        params: {pageNum}
    });
};

/**
 * 下载文件
 * @param {String} attachmentId 附件ID 
 */
export function downloadPicture(cmp, attachmentId) {
    return cmp.$api.request({
        url: '/sample/user/download/' + attachmentId,
        responseType: 'blob'
    });
};

export function getDownloadUrl(cmp, attachmentId) {
    return cmp.$api.apiURL + "/sample/user/download/" + attachmentId + "?" + cmp.$api.getToken();
};

export function getUploadUrl(cmp) {
    return cmp.$api.apiURL + "/sample/user/upload";
};

export function getUploadHeader(cmp) {
    let headers = {};
    cmp.$api.addToken(headers);
    return headers;
};