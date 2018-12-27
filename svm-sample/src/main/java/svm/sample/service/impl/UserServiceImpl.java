package svm.sample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svm.common.generator.IGenerator;
import svm.sample.dao.SampleUserMapper;
import svm.sample.entity.UserAttachment;
import svm.sample.entity.UserDO;
import svm.sample.service.UserService;

/**
 * 建议给每个service都起个名字，名字里包含包名，默认名称容易和其它模块中的service冲突
 */
@Service("sampleUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SampleUserMapper sampleUserMapper;

    @Autowired
    private IGenerator iGenerator;

    @Transactional
    @Override
    public Long saveUser(UserDO user) {
        long id = iGenerator.generateLong();
        user.setId(id);
        sampleUserMapper.save(user);
        return id;
    }

    @Override
    public void updateUser(UserDO user) {
        sampleUserMapper.update(user);
    }

    @Override
    public void deleteUsers(Long[] ids) {
        sampleUserMapper.delete(ids);
    }

    @Override
    public UserDO getUser(Long id) {
        return sampleUserMapper.getUser(id);
    }

    @Override
    public Long saveAttachment(UserAttachment attachment) {
        Long attachmentId = iGenerator.generateLong();
        attachment.setAttachmentId(attachmentId);
        sampleUserMapper.saveAttachment(attachment);
        return attachmentId;
    }

    @Override
    public UserAttachment queryAttachment(Long id) {
        return sampleUserMapper.queryAttachment(id);
    }
}

