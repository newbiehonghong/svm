package svm.sample.dao;

import svm.sample.entity.UserAttachment;
import svm.sample.entity.UserDO;
import svm.sample.service.dto.UserQueryDTO;

import java.util.List;

public interface UserDAO {
    void save(UserDO user);

    void update(UserDO user);

    void delete(Long id);

    UserDO getUser(Long id);

    List<UserDO> queryUsers(UserQueryDTO userQuery);

    List<UserDO> queryAllUsers();

    List<UserDO> queryDownloadUsers();

    void saveAttachment(UserAttachment attachment);

    UserAttachment queryAttachment(Long id);
}
