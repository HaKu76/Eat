package com.lph.eat.filter;

import com.alibaba.fastjson.JSON;
import com.lph.eat.common.req;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 检查用户是否登录
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 请求体
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 过滤逻辑
        // 1.获取uri
        String requestURI = request.getRequestURI();

        log.info("拦截到请求：{}",requestURI);
        // 2.判断请求是否要处理，这些是不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
        };

        boolean check = check(urls, requestURI);
        // 3.不需要处理则放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        // 4.判断登录状态，已经登录则放行
        if (request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        // 5.若未登录则返回登录页面，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(req.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = pathMatcher.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
