package com.make.controller;


import com.make.common.lang.Result;
import com.make.entity.User;
import com.make.service.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author make
 * @since 2022-08-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/logout")
    public Result logout() {
        return userService.logout();
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('teacher')")
    public String hello() {
        return "hello";
    }
    @GetMapping("/hello2")
    @PreAuthorize("hasAuthority('student')")
    public String hello2() {
        return "hello2";
    }

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        return userService.captcha();
    }
}
