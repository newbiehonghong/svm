package svm.app.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.app.common.annotation.RequiredPermission;
import svm.app.dao.security.UserMapper;
import svm.app.entity.security.User;
import svm.app.service.security.RoleService;
import svm.app.service.security.UserService;
import svm.common.exception.BusinessException;

import java.util.List;

@RestController
@RequestMapping(value = "/security/user")
@RequiredPermission("s_security_permission")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/updatePwd")
    public void updatePassword(@RequestBody User user) {
        userService.updatePassword(user.getId(), user.getPassword());
    }

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        List<String> roles = roleService.queryByUserId(id);
        if (roles.size() > 0) {
            throw new BusinessException("请先取消用户拥有的角色后再删除！");
        }
        userService.deleteUser(id);
    }

    @RequestMapping("/get/{id}")
    public Object getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Object queryAllUsers() {
        return userMapper.queryAllUsers();
    }

    @RequestMapping("/queryByRoleId/{roleId}")
    public Object queryByRoleId(@PathVariable String roleId) {
        return userService.queryByRoleId(roleId);
    }

    @PostMapping("/saveUserRoles")
    public void saveRoles(@RequestParam Long userId, @RequestBody String[] roleIds) {
        userService.saveRolesMap(userId, roleIds);
    }
}
