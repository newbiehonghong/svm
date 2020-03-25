package svm.app.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import svm.app.common.session.SessionContext;
import svm.app.common.session.Token;
import svm.app.entity.security.User;
import svm.app.service.security.PermissionService;
import svm.app.service.security.UserService;
import svm.common.exception.BusinessException;

import java.util.List;

@RestController
public class LoginController {
    private static final String MESSAGE_INVALID_INPUT = "用户名或密码错误";

    @Autowired
    PermissionService permissionService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public LoginResponse login(@RequestBody LoginUser loginUser) {
        String userName = loginUser.getName();
        User user = userService.getUserByName(userName);
        if (user == null) {
            throw new BusinessException(401, MESSAGE_INVALID_INPUT);
        }

        String password = loginUser.getPwd();
        //password = MD5Utils.digest(password);
        if (!user.getPassword().equals(password)) {
            throw new BusinessException(401, MESSAGE_INVALID_INPUT);
        }

        Long userId = user.getId();
        String nickname = user.getNickname();
        List<String> permissions = permissionService.getPagePermissionList(userId);

        Token token = new Token();
        token.setUserId(userId);
        token.setLastAccessTime(System.currentTimeMillis());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token.toString());
        loginResponse.setNickname(nickname);
        loginResponse.setPermissions(permissions);

        return loginResponse;
    }

    @PostMapping("/changePwd")
    public void changePassword(@RequestBody ChangePwdDTO pwdDTO) {
        User loginUser = SessionContext.get().getUser();
        if (loginUser == null) {
            throw new BusinessException(401, "未登录");
        }

        if (loginUser.getPassword().equals(pwdDTO.getOldPwd())) {
            userService.updatePassword(loginUser.getId(), pwdDTO.getNewPwd());
        } else {
            throw new BusinessException(401, "密码错误");
        }
    }
}
