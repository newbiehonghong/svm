package svm.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.common.exception.BaseRuntimeException;
import svm.security.annotation.RequiredPermission;
import svm.security.entity.User;
import svm.security.service.RoleService;
import svm.security.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/security/user")
@RequiredPermission("security_user_query")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/save")
    @RequiredPermission("security_user_add")
    public Long saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/update")
    @RequiredPermission("security_user_add")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PostMapping("/delete/{id}")
    @RequiredPermission("security_user_delete")
    public void deleteUser(@PathVariable Long id) {
        List<String> roles = roleService.queryByUserId(id);
        if(roles.size() > 0) {
            throw new BaseRuntimeException("请先取消用户拥有的角色后再删除！");
        }
        userService.deleteUser(id);
    }

    @PostMapping("/get/{id}")
    public Object getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/queryAll")
    @ResponseBody
    public Object queryAllUsers(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize) {
        return userService.queryAllUsers(pageNum, pageSize);
    }

    @PostMapping("/queryByRoleId/{roleId}")
    public Object queryByRoleId(@PathVariable String roleId) {
        return userService.queryByRoleId(roleId);
    }

    @PostMapping("/saveUserRoles")
    @RequiredPermission("security_user_role")
    public void saveRoles(@RequestParam Long userId, @RequestBody String[] roleIds) {
        userService.saveRolesMap(userId, roleIds);
    }
}
