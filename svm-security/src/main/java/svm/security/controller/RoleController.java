package svm.security.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.security.annotation.RequiredPermission;
import svm.security.dao.RoleMapper;
import svm.security.entity.Role;
import svm.security.service.RoleService;

import java.util.List;

@RestController
@RequestMapping(value = "/security/role")
@RequiredPermission("security_role_query")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @PostMapping("/save")
    @RequiredPermission("security_role_add")
    public void saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @PostMapping("/update")
    @RequiredPermission("security_role_add")
    public void updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
    }

    @PostMapping("/delete/{id}")
    @RequiredPermission("security_role_delete")
    public void deleteRole(@PathVariable String id) {
        //TODO
        roleService.deleteRole(id);
    }

    @PostMapping("/get/{id}")
    public Object getRole(@PathVariable String id) {
        return roleService.getRole(id);
    }

    @PostMapping("/queryAll")
    @ResponseBody
    public Object queryAllRoles(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleMapper.queryAllRoles();
        return new PageInfo(roles);
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
    @RequiredPermission("security/user_role")
    public void saveUsers(@RequestParam String roleId, @RequestBody Long[] userIds) {
        roleService.saveUsersMap(roleId, userIds);
    }

    @PostMapping("/saveRolePermissions")
    @RequiredPermission("security_role_permission")
    public void savePermissions(@RequestParam String roleId, @RequestBody String[] permissionIds) {
        roleService.savePermissionsMap(roleId, permissionIds);
    }
}
