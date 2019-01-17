import core from '../core'

/****************** 角色 ************************/
/**
 * 保存角色信息
 * @param {Object} role 角色信息
 */
export function saveRole(role) {
    return core.request({url: '/security/role/save', data: role});
};

/**
 * 更新角色信息
 * @param {Object} role 角色信息 
 */
export function updateRole(role) {
    return core.request({url: '/security/role/update', data: role});
};

/**
 * 删除角色信息
 * @param {String} id 角色ID 
 */
export function deleteRole(id) {
    return core.request({url: '/security/role/delete/' + id});
};

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 * @param {Number} pageSize 每页显示记录数
 */
export function queryAllRoles(pageNum, pageSize) {
    return core.request({url: '/security/role/queryAll', params: {pageNum, pageSize}});
};

/**
 * 查询用户所属的角色列表
 * @param {Number} userId 用户ID 
 */
export function queryRolesByUserId(userId) {
    return core.request({url: '/security/role/queryByUserId/' + userId});
};

/**
 * 查询权限项所属的角色列表
 * @param {String} permissionId 选项项ID 
 */
export function queryRolesByPermissionId(permissionId) {
    return core.request({url: '/security/role/queryByPermissionId/' + permissionId});
};

/**
 * 保存角色下的用户信息
 * @param {String} roleId 角色ID 
 * @param {Array} userIds 用户ID数组
 */
export function saveRoleUsers(roleId, userIds) {
    return core.request({url: '/security/role/saveRoleUsers', params: {roleId}, data: userIds});
};

/**
 * 保存角色下的权限项信息
 * @param {String} roleId 角色ID 
 * @param {Array} permissionIds 权限项ID数组
 */
export function saveRolePermissions(roleId, permissionIds) {
    return core.request({url: '/security/role/saveRolePermissions', params: {roleId}, data: permissionIds});
};