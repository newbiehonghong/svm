package svm.security.service;

import svm.security.session.UserSession;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<String> getServicePermissionSet(String userId);

    List<String> getWebPermissionList(String userId);

    UserSession getUserSession(String token);
}
