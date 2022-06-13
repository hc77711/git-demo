package com.lx.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.lx.reggie.common.R;
import com.lx.reggie.util.UserHolder;
import com.lx.reggie.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 小兴
 * @description TODO 校验是否登录
 * @className LoginCheckFilter
 * @date 2022/6/11 11:39
 */
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        // 1.   定义不需要处理的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/**"
        };
        // 2. 判断本次请求是否需要进行拦截处理
        boolean isIntercept = check(requestURI, urls);
        // 3. 如果不需要处理,直接放行
        if (isIntercept){
            filterChain.doFilter(request, response);
            return;
        }
        // 4. 判断登录状态,如果已经登陆了,则直接放行
        if (request.getSession().getAttribute("employee") != null){
            // 存到全局中一份,以便于其他的人访问
            UserHolder.saveUser((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("user") != null){
            // 存到全局中一份,以便于其他的人访问
            UserHolder.saveUser((Long) request.getSession().getAttribute("user"));
            filterChain.doFilter(request, response);
            return;
        }
        // 5. 如果未登录,则返回未登录结果
        WebUtils.renderString(response, JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 判断路径是否需要处理
     * @param requestUrl 请求的路径
     * @param urls 可以放行的路径
     * @return false 不可以放行 true 不需要拦截
     */
    public boolean check(String requestUrl, String[] urls){
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestUrl)){
                // 只要有一个匹配成功的,代表不需要拦截
                return true;
            }
        }
        return false;
    }
}
