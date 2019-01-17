import core from '../core'

/****************** 用户 ************************/
/**
 * 保存用户信息
 * @param {Object} user 用户信息
 */
export function saveUser(user) {
    return core.request({url: '/security/user/save', data: user});
};

/**
 * 更新用户信息
 * @param {Object} user 用户信息 
 */
export function updateUser(user) {
    return core.request({url: '/security/user/update', data: user});
};

/**
 * 删除用户信息
 * @param {String} id 用户ID 
 */
export function deleteUser(id) {
    return core.request({url: '/security/user/delete/' + id});
};

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 * @param {Number} pageSize 每页显示记录数
 */
export function queryAllUsers(pageNum, pageSize) {
    return core.request({url: '/security/user/queryAll', params: {pageNum, pageSize}});
};

/**
 * 查询角色下的用户列表
 * @param {String} roleId 角色ID 
 */
export function queryUsersByRoleId(roleId) {
    return core.request({url: '/security/user/queryByRoleId/' + roleId});
};

/**
 * 保存用户所属角色信息
 * @param {Number} userId 用户ID 
 * @param {Array} roleIds 角色ID数组
 */
export function saveUserRoles(userId, roleIds) {
    return core.request({url: '/security/user/saveUserRoles', params: {userId}, data: roleIds});
};