package com.make.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import com.make.entity.LoginUser;
import com.make.utils.JWTUtil;
import com.make.utils.JedisWrapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author make
 * @Description
 * @Date 2022/8/23 12:09
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析Token
        JWT jwt = JWTUtil.parseJWT(token);
        String userId = (String) jwt.getPayload(JWTPayload.SUBJECT);
        // 从redis中获取用户信息
        LoginUser loginUser = JedisWrapper.useJedis(jedis -> {
            String result = jedis.get("login:" + userId);
            if (StrUtil.isEmpty(result)) {
                throw new RuntimeException("用户未登录");
            }
            return JSONUtil.toBean(result, LoginUser.class);
        });
        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getUser(), null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
