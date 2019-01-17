import core from '../core'

/****************** 权限项 ************************/
/**
 * 保存权限项信息
 * @param {Object} permission 权限项信息
 */
export function savePermission(permission) {
    return core.request({url: '/security/permission/save', data: permission});
};

/**
 * 更新权限项信息
 * @param {Object} permission 权限项信息 
 */
export function updatePermission(permission) {
    return core.request({url: '/security/permission/update', data: permission});
};

/**
 * 删除权限项信息
 * @param {String} id 权限项ID 
 */
export function deletePermission(id) {
    return core.request({url: '/security/permission/delete/' + id});
};

/**
 * 查询所有记录
 * @param {String} type 权限项类型  
 * @param {Number} pageNum 页码 
 * @param {Number} pageSize 每页显示记录数
 */
export function queryPermissions(type, pageNum, pageSize) {
    return core.request({url: '/security/permission/query', params: {type, pageNum, pageSize}});
};

/**
 * 查询角色下的权限项列表
 * @param {String} roleId 角色ID 
 */
export function queryPermissionsByRoleId(roleId) {
    return core.request({url: '/security/permission/queryByRoleId/' + roleId});
};

/**
 * 保存权限项所属角色信息
 * @param {String} permissionId 权限项ID 
 * @param {Array} roleIds 角色ID数组
 */
export function savePermissionRoles(permissionId, roleIds) {
    return core.request({url: '/security/permission/savePermissionRoles', params: {permissionId}, data: roleIds});
};