package com.make.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author make
 * @Description 封装hutool的JWTUtil
 * @Date 2022/8/23 10:01
 */

public class JWTUtil {

    /**
     *  jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     */
    public static final byte[] JWT_SEC_BYTE = "make".getBytes();




    public static String createJWT(String username) {
        return createJWT(0, username);
    }

    /**
     * @param ttlMillis jwt过期时间(毫秒)
     * @param username  用户名 可根据需要传递的信息添加更多, 因为浏览器get传参url限制，不建议放置过多的参数
     */
    public static String createJWT(int ttlMillis, String username) {
        // 生成JWT的时间
        DateTime now = DateTime.now();

        Map<String,Object> payload = new HashMap<String,Object>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        if (ttlMillis > 0) {
            DateTime newTime = now.offsetNew(DateField.MILLISECOND, ttlMillis);
            payload.put(JWTPayload.EXPIRES_AT, newTime);
        }
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串
        payload.put(JWTPayload.SUBJECT, username);

        return cn.hutool.jwt.JWTUtil.createToken(payload, JWT_SEC_BYTE);
    }

    /**
     * Token的解密
     */
    public static JWT parseJWT(String token) {

        return cn.hutool.jwt.JWTUtil.parseToken(token);
    }

    /**
     * Token验证
     */
    public static boolean verify(String token) {
        return cn.hutool.jwt.JWTUtil.verify(token, JWT_SEC_BYTE);
    }


}
