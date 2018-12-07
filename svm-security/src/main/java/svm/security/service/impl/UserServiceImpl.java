package svm.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svm.common.generator.IGenerator;
import svm.security.dao.UserMapper;
import svm.security.dao.UserRoleMapper;
import svm.security.entity.User;
import svm.security.service.UserService;
import svm.security.util.MD5Digest;

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
    public Long saveUser(User user) {
        long id = iGenerator.generateLong();
        user.setId(id);
        user.setPassword(MD5Digest.digest(user.getPassword()));
        user.setCreateTime(new Date(System.currentTimeMillis()));
        userMapper.save(user);
        return id;
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
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
    public PageInfo<User> queryAllUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.queryAllUsers();
        return new PageInfo(users);
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
