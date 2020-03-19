package svm.app.service.sample.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;

public class UserQueryDTO {
    private String name;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp birthdayBegin;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp birthdayEnd;

    private String province;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBirthdayBegin() {
        return birthdayBegin;
    }

    public void setBirthdayBegin(Timestamp birthdayBegin) {
        this.birthdayBegin = birthdayBegin;
    }

    public Timestamp getBirthdayEnd() {
        return birthdayEnd;
    }

    public void setBirthdayEnd(Timestamp birthdayEnd) {
        this.birthdayEnd = birthdayEnd;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
