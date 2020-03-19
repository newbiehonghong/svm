import request from '@/utils/request';

/****************** 角色 ************************/
/**
 * 保存角色信息
 * @param {Object} role 角色信息
 */
export function saveRole(role) {
    return request({ url: '/security/role/save', data: role });
};

/**
 * 更新角色信息
 * @param {Object} role 角色信息 
 */
export function updateRole(role) {
    return request({ url: '/security/role/update', data: role });
};

/**
 * 删除角色信息
 * @param {String} id 角色ID 
 */
export function deleteRole(id) {
    return request({ url: '/security/role/delete/' + id });
};

/**
 * 查询所有记录
 */
export function queryAllRoles() {
    return request({ url: '/security/role/queryAll' });
};

/**
 * 查询用户所属的角色列表
 * @param {Number} userId 用户ID 
 */
export function queryRolesByUserId(userId) {
    return request({ url: '/security/role/queryByUserId/' + userId });
};

/**
 * 查询权限项所属的角色列表
 * @param {String} permissionId 选项项ID 
 */
export function queryRolesByPermissionId(permissionId) {
    return request({ url: '/security/role/queryByPermissionId/' + permissionId });
};

/**
 * 保存角色下的用户信息
 * @param {String} roleId 角色ID 
 * @param {Array} userIds 用户ID数组
 */
export function saveRoleUsers(roleId, userIds) {
    return request({ url: '/security/role/saveRoleUsers', params: { roleId }, data: userIds });
};

/**
 * 保存角色下的权限项信息
 * @param {String} roleId 角色ID 
 * @param {Array} permissionIds 权限项ID数组
 */
export function saveRolePermissions(roleId, permissionIds) {
    return request({ url: '/security/role/saveRolePermissions', params: { roleId }, data: permissionIds });
};