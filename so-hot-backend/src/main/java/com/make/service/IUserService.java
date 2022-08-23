package com.make.service;

import com.make.common.lang.Result;
import com.make.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author make
 * @since 2022-08-19
 */
public interface IUserService extends IService<User> {

    Result login(User user);

    Result logout();
    /**
     * 图片验证码
     */
    Result captcha() throws IOException;
}
