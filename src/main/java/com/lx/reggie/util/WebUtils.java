package com.lx.reggie.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 小兴
 * @description TODO
 * @className WebUtils
 * @date 2022/6/11 0:21
 */
public class WebUtils
{
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // 可以自定义 code 值了
    public static String renderString(HttpServletResponse response, String string, int code) {
        try
        {
            response.setStatus(code);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}