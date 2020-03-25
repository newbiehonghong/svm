package svm.app.controller.login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePwdDTO {
    private String oldPwd;
    private String newPwd;
}
