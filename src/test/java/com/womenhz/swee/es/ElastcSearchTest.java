package com.womenhz.swee.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.womenhz.swee.model.User;
import com.womenhz.swee.service.UserService;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class ElastcSearchTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreate(){
       // userService.createUserIndex();

       /* userService.createUser(User.builder().address("杭州西溪花园")
                .description("帅气掉渣")
                .id(1L)
                .email("222@163.com")
                .hobe("旅行")
                .name("宇智波.伊塔器").build());*/

    }

}
