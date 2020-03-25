package svm.app.entity.sample;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class UserDO {

    private Long id;

    private String name;

    @JSONField(format="yyyy-MM-dd")
    private Date birthday;

    private String province;

    private String city;

    private String deptId;

    private Double salary;

    private Long workYear;

    private String gender;

    private String memo;

    private String fileName;

    private Long attachmentId;
}
