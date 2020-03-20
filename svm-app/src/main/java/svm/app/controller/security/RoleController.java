package svm.app.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.app.common.annotation.RequiredPermission;
import svm.app.dao.security.RoleMapper;
import svm.app.entity.security.Role;
import svm.app.service.security.RoleService;

@RestController
@RequestMapping(value = "/security/role")
@RequiredPermission("s_security_permission")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @PostMapping("/save")
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PostMapping("/update")
    public Role updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @PostMapping("/delete/{id}")
    public void deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
    }

    @PostMapping("/get/{id}")
    public Object getRole(@PathVariable String id) {
        return roleService.getRole(id);
    }

    @PostMapping("/queryAll")
    @ResponseBody
    public Object queryAllRoles() {
        return roleMapper.queryAllRoles();
    }

    @PostMapping("/queryByUserId/{userId}")
    public Object queryByUserId(@PathVariable Long userId) {
        return roleService.queryByUserId(userId);
    }

    @PostMapping("/queryByPermissionId/{permissionId}")
    public Object queryByPermissionId(@PathVariable String permissionId) {
        return roleService.queryByPermissionId(permissionId);
    }

    @PostMapping("/saveRoleUsers")
    public void saveUsers(@RequestParam String roleId, @RequestBody Long[] userIds) {
        roleService.saveUsersMap(roleId, userIds);
    }

    @PostMapping("/saveRolePermissions")
    public void savePermissions(@RequestParam String permissionType, @RequestParam String roleId, @RequestBody String[] permissionIds) {
        roleService.savePermissionsMap(permissionType, roleId, permissionIds);
    }
}
