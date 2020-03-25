import request from '@/utils/request';
import md5 from "md5";

/****************** 用户 ************************/
/**
 * 保存用户信息
 * @param {Object} user 用户信息
 */
export function saveUser(user) {
    user.password = md5(user.password);
    return request({ url: '/security/user/save', data: user });
};

/**
 * 更新用户信息
 * @param {Object} user 用户信息 
 */
export function updateUser(user) {
    return request({ url: '/security/user/update', data: user });
};

/**
 * 修改密码
 * @param {String} user 用户信息 
 * 
 */
export function updatePassword(user) {
    user.password = md5(user.password);
    return request({ url: '/security/user/updatePwd', data: user });
};

/**
 * 删除用户信息
 * @param {String} id 用户ID 
 */
export function deleteUser(id) {
    return request({ url: '/security/user/delete/' + id });
};

/**
 * 查询所有记录
 */
export function queryAllUsers() {
    return request({ url: '/security/user/queryAll' });
};

/**
 * 查询角色下的用户列表
 * @param {String} roleId 角色ID 
 */
export function queryUsersByRoleId(roleId) {
    return request({ url: '/security/user/queryByRoleId/' + roleId });
};

/**
 * 保存用户所属角色信息
 * @param {Number} userId 用户ID 
 * @param {Array} roleIds 角色ID数组
 */
export function saveUserRoles(userId, roleIds) {
    return request({ url: '/security/user/saveUserRoles', params: { userId }, data: roleIds });
};