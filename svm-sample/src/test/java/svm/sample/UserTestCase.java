package svm.sample;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import svm.sample.dao.UserDAO;
import svm.sample.entity.UserDO;

@SpringBootTest(classes={Application.class})
@RunWith(SpringRunner.class)
public class UserTestCase {
    @Autowired
    private UserDAO userDAO;

    @Test
    public void testQuery() {
        UserDO user = userDAO.getUser(4L);
        Assert.assertEquals(user.getName(), "英雄");
    }
}
