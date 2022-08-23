package com.make.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.google.code.kaptcha.Producer;
import com.make.common.lang.Result;
import com.make.entity.LoginUser;
import com.make.entity.User;
import com.make.mapper.UserMapper;
import com.make.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.make.utils.JWTUtil;
import com.make.utils.JedisWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author make
 * @since 2022-08-19
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private Producer producer;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Result login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate == null) {
            throw new RuntimeException("登录失败");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = String.valueOf(loginUser.getUser().getId());
        String token = JWTUtil.createJWT(id);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        JedisWrapper.useJedis(jedis -> jedis.set("login:" + id, JSONUtil.toJsonStr(loginUser)));

        return Result.success(200, "登录成功", tokenMap);
    }

    @Override
    public Result logout() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = loginUser.getUser().getId();
        // 从redis中删除用户
        JedisWrapper.useJedis(jedis -> {
            return jedis.del("login:" + id);
        });
        return Result.success(200, "退出登录成功", null);
    }


    @Override
    public Result captcha() throws IOException {
        String code = producer.createText();
        String key = UUID.randomUUID().toString();
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:/image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());

        // 存入redis
        log.info("验证码 -- {} - {}", key, code);
        return Result.success(
                MapUtil.builder()
                        .put("token", key)
                        .put("base64Img", base64Img)
                        .build()
        );
    }

}
