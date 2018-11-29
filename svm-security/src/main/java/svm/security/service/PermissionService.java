package svm.security.service;

import svm.security.session.UserSession;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<String> getServicePermissionSet(Long userId);

    List<String> getPagePermissionList(Long userId);

    UserSession getUserSession(Long userId);
}
