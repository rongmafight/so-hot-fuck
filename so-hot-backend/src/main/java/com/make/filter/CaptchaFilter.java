package com.make.filter;

import cn.hutool.core.util.StrUtil;
import com.make.utils.JedisWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.make.common.lang.Const.CAPTCHA_KEY;

/**
 * @Author make
 * @Description 验证码的过滤器
 * @Date 2022/8/29 15:28
 */

@Component
public class CaptchaFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if ("/user/login".equals(requestURI) && request.getMethod().equals("POST")) {
            String code = request.getParameter("code");
            String token = request.getParameter("token");
            if (StrUtil.isEmpty(code) || StrUtil.isEmpty(token)) {
                throw new RuntimeException("验证码为空");
            }
            JedisWrapper.useJedis(jedis -> {
                String key = CAPTCHA_KEY + ":" + token;
                String result = jedis.get(key);
                if (StrUtil.isEmpty(result)) {
                    throw new RuntimeException("验证码已过期");
                }
                if (!code.toLowerCase().equals(result)) {
                    throw new RuntimeException("验证码错误");
                }
                return jedis.del(key);
            });
        }
        filterChain.doFilter(request, response);
    }
}
