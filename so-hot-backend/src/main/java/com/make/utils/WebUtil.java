package com.make.utils;

import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author make
 * @Description
 * @Date 2022/8/24 11:36
 */

public class WebUtil {

    public static void renderString(HttpServletResponse response, Object writeObj) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(writeObj));
    }
}
