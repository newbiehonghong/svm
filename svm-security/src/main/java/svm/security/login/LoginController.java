package svm.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import svm.security.entity.User;
import svm.security.service.PermissionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    PermissionService permissionService;

    @PostMapping("/login")
    @ResponseBody
    public LoginResponse login(HttpServletRequest request) {
        User user = (User) request.getAttribute(LoginConstants.ATTRIBUTE_USER);
        Long userId = user.getId();
        String nickname = user.getNickname();
        List<String> permissions = permissionService.getPagePermissionList(userId);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(user.getName());//TODO
        loginResponse.setNickname(nickname);
        loginResponse.setPermissions(permissions);

        return loginResponse;
    }
}
