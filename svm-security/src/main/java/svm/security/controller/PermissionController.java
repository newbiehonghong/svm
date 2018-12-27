package svm.security.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.common.exception.BaseRuntimeException;
import svm.security.annotation.RequiredPermission;
import svm.security.dao.PermissionMapper;
import svm.security.entity.Permission;
import svm.security.service.PermissionService;
import svm.security.service.RoleService;

import java.util.List;

@RestController
@RequestMapping(value = "/security/permission")
@RequiredPermission("security_permission_query")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleService roleService;

    private Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @PostMapping("/save")
    @RequiredPermission("security_permission_add")
    public void savePermission(@RequestBody Permission permission) {
        permissionService.savePermission(permission);
    }

    @PostMapping("/update")
    @RequiredPermission("security_permission_add")
    public void updatePermission(@RequestBody Permission permission) {
        permissionService.updatePermission(permission);
    }

    @PostMapping("/delete/{id}")
    @RequiredPermission("security_permission_delete")
    public void deletePermission(@PathVariable String id) {
        List<String> roles = roleService.queryByPermissionId(id);
        if(roles.size() > 0) {
            throw new BaseRuntimeException("请先取消权限项所属的角色后再删除！");
        }
        permissionService.deletePermission(id);
    }

    @PostMapping("/query")
    @ResponseBody
    public Object queryPermissions(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> permissions = permissionMapper.queryPermissions(type);
        return new PageInfo(permissions);
    }

    @PostMapping("/queryByRoleId/{roleId}")
    public Object queryByRoleId(@PathVariable String roleId) {
        return permissionService.queryByRoleId(roleId);
    }

    @PostMapping("/savePermissionRoles")
    @RequiredPermission("security_role_permission")
    public void saveRoles(@RequestParam String permissionId, @RequestBody String[] roleIds) {
        permissionService.saveRolesMap(permissionId, roleIds);
    }
}
