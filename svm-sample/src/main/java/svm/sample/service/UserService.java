package svm.sample.service;

import com.github.pagehelper.PageInfo;
import svm.sample.entity.UserAttachment;
import svm.sample.entity.UserDO;
import svm.sample.service.dto.UserQueryDTO;

public interface UserService {
    Long saveUser(UserDO user);

    void updateUser(UserDO user);

    void deleteUsers(Long[] ids);

    UserDO getUser(Long id);

    PageInfo<UserDO> queryUsers(UserQueryDTO userQuery, int pageNum, int pageSize);

    PageInfo<UserDO> queryAllUsers(int pageNum, int pageSize);

    PageInfo<UserDO> queryDownloadUsers(int pageNum, int pageSize);

    Long saveAttachment(UserAttachment attachment);

    UserAttachment queryAttachment(Long id);
}
