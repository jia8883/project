package board.practice.service;

import board.practice.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        //given
        User user = new User();
        user.setUsername("Jane");
        user.setPassword("password");

        //when
        userService.join(user);

        //then
        assertEquals(user, userService.findOne(user.getId()));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_검증() throws Exception {
        //given
        User user1 = new User();
        user1.setUsername("Jane");
        user1.setPassword("password");

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setPassword("password");

        //when
        userService.join(user1);
        userService.join(user2);

        //then
        fail("예외가 발생해야 합니다.");
    }
}