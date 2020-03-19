package svm.app.controller.login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePwdUser {
    private String name;
    private String oldPwd;
    private String newPwd;
}
