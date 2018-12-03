package svm.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.security.annotation.RequiredPermission;
import svm.security.entity.User;
import svm.security.service.UserService;

@RestController
@RequestMapping(value = "/security/user")
@RequiredPermission("security/user/query")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/save")
    @RequiredPermission("security/user/add")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("/update")
    @RequiredPermission("security/user/add")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PostMapping("/delete/{id}")
    @RequiredPermission("security/user/delete")
    public void deleteUser(@PathVariable Long id) {
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
}
