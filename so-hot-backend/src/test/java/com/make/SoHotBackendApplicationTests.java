package com.make;

import com.make.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
@MapperScan("com.make.mapper")
class SoHotBackendApplicationTests {

    @Autowired
    MenuMapper menuMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void TestPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Test
    public void testSelectPermsByUserId() {
        System.out.println(menuMapper.selectPermsByUserId(9));
    }

}
