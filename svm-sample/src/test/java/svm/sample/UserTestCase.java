package svm.sample;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import svm.sample.dao.SampleUserMapper;
import svm.sample.entity.UserDO;
import svm.sample.service.UserService;

import java.sql.Date;

@SpringBootTest(classes={Application.class})
@RunWith(SpringRunner.class)
public class UserTestCase {
    @Autowired
    private SampleUserMapper sampleUserMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testQuery() {
        UserDO user = sampleUserMapper.getUser(4L);
        Assert.assertEquals(user.getName(), "英雄");
    }

    @Test
    public void testSave() {
        UserDO user = new UserDO();
        user.setName("test1120");
        user.setBirthday(new Date(System.currentTimeMillis()));
        user.setProvince("02");
        user.setCity("02");
        long id = userService.saveUser(user);
        System.out.println(id);
    }
}
