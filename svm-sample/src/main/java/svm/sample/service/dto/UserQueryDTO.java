package svm.sample.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class UserQueryDTO {
    private String name;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp birthdayBegin;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
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
