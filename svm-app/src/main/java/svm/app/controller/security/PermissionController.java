package svm.app.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.app.common.annotation.RequiredPermission;
import svm.app.dao.security.PermissionMapper;
import svm.app.entity.security.Permission;
import svm.app.service.security.PermissionService;
import svm.app.service.security.RoleService;
import svm.common.exception.BusinessException;

import java.util.List;

@RestController
@RequestMapping(value = "/security/permission")
@RequiredPermission("s_security_permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public Permission savePermission(@RequestBody Permission permission) {
        return permissionService.savePermission(permission);
    }

    @PostMapping("/update")
    public Permission updatePermission(@RequestBody Permission permission) {
        return permissionService.updatePermission(permission);
    }

    @PostMapping("/delete/{id}")
    public void deletePermission(@PathVariable String id) {
        List<String> roles = roleService.queryByPermissionId(id);
        if(roles.size() > 0) {
            throw new BusinessException("请先取消权限项所属的角色后再删除！");
        }
        permissionService.deletePermission(id);
    }

    @PostMapping("/queryByType/{type}")
    @ResponseBody
    public Object queryPermissions(@PathVariable String type) {
        return permissionMapper.queryPermissions(type);
    }

    @PostMapping("/queryByRoleId")
    public Object queryByRoleId(@RequestParam(name = "type") String type, @RequestParam(name = "roleId") String roleId) {
        return permissionService.queryByRoleId(type, roleId);
    }

    @PostMapping("/savePermissionRoles")
    public void saveRoles(@RequestParam String permissionId, @RequestBody String[] roleIds) {
        permissionService.saveRolesMap(permissionId, roleIds);
    }
}
