package svm.sample.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import svm.common.generator.IGenerator;
import svm.sample.dao.UserDAO;
import svm.sample.entity.UserAttachment;
import svm.sample.entity.UserDO;
import svm.sample.service.UserService;
import svm.sample.service.dto.UserQueryDTO;

import java.util.List;

/**
 * 建议给每个service都起个名字，名字里包含包名，默认名称容易和其它模块中的service冲突
 */
@Service("sampleUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private IGenerator iGenerator;

    @Override
    public void saveUser(UserDO user) {
        user.setId(iGenerator.generateLong());
        userDAO.save(user);
    }

    @Override
    public void updateUser(UserDO user) {
        userDAO.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.delete(id);
    }

    @Override
    public UserDO getUser(Long id) {
        return userDAO.getUser(id);
    }

    @Override
    public PageInfo<UserDO> queryUsers(UserQueryDTO userQueryDTO, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserDO> users = userDAO.queryUsers(userQueryDTO);
        return new PageInfo(users);
    }

    @Override
    public PageInfo<UserDO> queryAllUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserDO> users = userDAO.queryAllUsers();
        return new PageInfo(users);
    }

    @Override
    public PageInfo<UserDO> queryDownloadUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserDO> users = userDAO.queryDownloadUsers();
        return new PageInfo(users);
    }

    @Override
    public Long saveAttachment(UserAttachment attachment) {
        Long attachmentId = iGenerator.generateLong();
        attachment.setAttachmentId(attachmentId);
        userDAO.saveAttachment(attachment);
        return attachmentId;
    }

    @Override
    public UserAttachment queryAttachment(Long id) {
        return userDAO.queryAttachment(id);
    }
}

