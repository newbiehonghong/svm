package svm.app.entity.security;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private Long id;

    private String name;

    private String nickname;

    @JSONField(serialize = false)
    private String password;

    @JSONField(serialize = false)
    private Date createTime;
}