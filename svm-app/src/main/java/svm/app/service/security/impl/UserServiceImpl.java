package svm.app.service.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svm.app.dao.security.UserMapper;
import svm.app.dao.security.UserRoleMapper;
import svm.app.entity.security.User;
import svm.app.service.security.UserService;
import svm.common.crypto.MD5Utils;
import svm.common.generator.IGenerator;

import java.util.Date;
import java.util.List;

@Service("securityUserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IGenerator iGenerator;

    @Override
    public User saveUser(User user) {
        long id = iGenerator.generateLong();
        user.setId(id);
        user.setPassword(MD5Utils.digest(user.getPassword()));
        user.setCreateTime(new Date(System.currentTimeMillis()));
        userMapper.save(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.delete(id);
    }

    @Override
    public User getUser(Long id) {
        return userMapper.get(id);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public List<Long> queryByRoleId(String roleId) {
        return userRoleMapper.queryUsersByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolesMap(Long userId, String[] roleIds) {
        userRoleMapper.deleteByUserId(userId);
        if(roleIds.length > 0) {
            userRoleMapper.saveUserRoles(userId, roleIds);
        }
    }
}
