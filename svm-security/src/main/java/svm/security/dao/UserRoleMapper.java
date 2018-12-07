package svm.security.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    void deleteByUserId(Long userId);

    void deleteByRoleId(String roleId);

    void saveUserRoles(@Param("userId") Long userId, @Param("roleIds") String[] roleIds);

    void saveRoleUsers(@Param("roleId") String roleId, @Param("userIds") Long[] userIds);

    List<Long> queryUsersByRoleId(String roleId);

    List<String> queryRolesByUserId(Long userId);
}
