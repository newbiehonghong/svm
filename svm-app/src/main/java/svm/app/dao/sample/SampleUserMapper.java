package svm.app.dao.sample;

import svm.app.entity.sample.UserAttachment;
import svm.app.entity.sample.UserDO;
import svm.app.service.sample.dto.UserQueryDTO;

import java.util.List;

public interface SampleUserMapper {
    void save(UserDO user);

    void update(UserDO user);

    void delete(Long[] ids);

    UserDO getUser(Long id);

    List<UserDO> queryUsers(UserQueryDTO userQuery);

    List<UserDO> queryAllUsers();

    List<UserDO> queryDownloadUsers();

    void saveAttachment(UserAttachment attachment);

    UserAttachment queryAttachment(Long id);
}
