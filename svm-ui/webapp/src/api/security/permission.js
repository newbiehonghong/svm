import request from '@/utils/request';

/****************** 权限项 ************************/
/**
 * 保存权限项信息
 * @param {Object} permission 权限项信息
 */
export function savePermission(permission) {
    return request({ url: '/security/permission/save', data: permission });
};

/**
 * 更新权限项信息
 * @param {Object} permission 权限项信息 
 */
export function updatePermission(permission) {
    return request({ url: '/security/permission/update', data: permission });
};

/**
 * 删除权限项信息
 * @param {String} id 权限项ID 
 */
export function deletePermission(id) {
    return request({ url: '/security/permission/delete/' + id });
};

/**
 * 查询所有记录
 * @param {String} type 权限项类型  
 */
export function queryPermissions(type) {
    return request({ url: '/security/permission/queryByType/' + type });
};

/**
 * 查询角色下的权限项列表
 * @param {String} type 权限项类型  
 * @param {String} roleId 角色ID 
 */
export function queryPermissionsByRoleId(type, roleId) {
    return request({ url: '/security/permission/queryByRoleId', params: { type, roleId } });
};

/**
 * 保存权限项所属角色信息
 * @param {String} permissionId 权限项ID 
 * @param {Array} roleIds 角色ID数组
 */
export function savePermissionRoles(permissionId, roleIds) {
    return request({ url: '/security/permission/savePermissionRoles', params: { permissionId }, data: roleIds });
};