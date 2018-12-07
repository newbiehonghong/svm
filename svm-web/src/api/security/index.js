import request from '@/utils/request';

/****************** 用户 ************************/
/**
 * 保存用户信息
 * @param {Object} user 用户信息
 */
export function saveUser(user) {
    return request({url: '/security/user/save', data: user});
};

/**
 * 更新用户信息
 * @param {Object} user 用户信息 
 */
export function updateUser(user) {
    return request({url: '/security/user/update', data: user});
};

/**
 * 删除用户信息
 * @param {String} id 用户ID 
 */
export function deleteUser(id) {
    return request({url: '/security/user/delete/' + id});
};

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 * @param {Number} pageSize 每页显示记录数
 */
export function queryAllUsers(pageNum, pageSize) {
    return request({url: '/security/user/queryAll', params: {pageNum, pageSize}});
};

/**
 * 查询角色下的用户列表
 * @param {String} roleId 角色ID 
 */
export function queryUsersByRoleId(roleId) {
    return request({url: '/security/user/queryByRoleId/' + roleId});
};

/**
 * 保存用户所属角色信息
 * @param {Number} userId 用户ID 
 * @param {Array} roleIds 角色ID数组
 */
export function saveUserRoles(userId, roleIds) {
    return request({url: '/security/user/saveUserRoles', params: {userId}, data: roleIds});
};

/****************** 角色 ************************/
/**
 * 保存角色信息
 * @param {Object} role 角色信息
 */
export function saveRole(role) {
    return request({url: '/security/role/save', data: role});
};

/**
 * 更新角色信息
 * @param {Object} role 角色信息 
 */
export function updateRole(role) {
    return request({url: '/security/role/update', data: role});
};

/**
 * 删除角色信息
 * @param {String} id 角色ID 
 */
export function deleteRole(id) {
    return request({url: '/security/role/delete/' + id});
};

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 * @param {Number} pageSize 每页显示记录数
 */
export function queryAllRoles(pageNum, pageSize) {
    return request({url: '/security/role/queryAll', params: {pageNum, pageSize}});
};

/**
 * 查询用户所属的角色列表
 * @param {Number} userId 用户ID 
 */
export function queryRolesByUserId(userId) {
    return request({url: '/security/role/queryByUserId/' + userId});
};

/**
 * 查询权限项所属的角色列表
 * @param {String} permissionId 选项项ID 
 */
export function queryRolesByPermissionId(permissionId) {
    return request({url: '/security/role/queryByPermissionId/' + permissionId});
};

/**
 * 保存角色下的用户信息
 * @param {String} roleId 角色ID 
 * @param {Array} userIds 用户ID数组
 */
export function saveRoleUsers(roleId, userIds) {
    return request({url: '/security/role/saveRoleUsers', params: {roleId}, data: userIds});
};

/**
 * 保存角色下的权限项信息
 * @param {String} roleId 角色ID 
 * @param {Array} permissionIds 权限项ID数组
 */
export function saveRolePermissions(roleId, permissionIds) {
    return request({url: '/security/role/saveRolePermissions', params: {roleId}, data: permissionIds});
};

/****************** 权限项 ************************/
/**
 * 保存权限项信息
 * @param {Object} permission 权限项信息
 */
export function savePermission(permission) {
    return request({url: '/security/permission/save', data: permission});
};

/**
 * 更新权限项信息
 * @param {Object} permission 权限项信息 
 */
export function updatePermission(permission) {
    return request({url: '/security/permission/update', data: permission});
};

/**
 * 删除权限项信息
 * @param {String} id 权限项ID 
 */
export function deletePermission(id) {
    return request({url: '/security/permission/delete/' + id});
};

/**
 * 查询所有记录
 * @param {String} type 权限项类型  
 * @param {Number} pageNum 页码 
 * @param {Number} pageSize 每页显示记录数
 */
export function queryPermissions(type, pageNum, pageSize) {
    return request({url: '/security/permission/query', params: {type, pageNum, pageSize}});
};

/**
 * 查询角色下的权限项列表
 * @param {String} roleId 角色ID 
 */
export function queryPermissionsByRoleId(roleId) {
    return request({url: '/security/permission/queryByRoleId/' + roleId});
};

/**
 * 保存权限项所属角色信息
 * @param {String} permissionId 权限项ID 
 * @param {Array} roleIds 角色ID数组
 */
export function savePermissionRoles(permissionId, roleIds) {
    return request({url: '/security/permission/savePermissionRoles', params: {permissionId}, data: roleIds});
};