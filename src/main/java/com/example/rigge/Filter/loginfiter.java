package com.example.rigge.Filter;

import com.alibaba.fastjson.JSON;
import com.example.rigge.common.BaseContext;
import com.example.rigge.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: loginfiter
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/2 22:18
 */
@WebFilter(filterName = "loginFilter" , urlPatterns = {"/*"})
@Slf4j
public class loginfiter implements Filter {

    public static final AntPathMatcher MATH_PATH = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        log.info("Filter:{}.........",request1);



        //1、获取本次请求的URI
        String Url = request1.getRequestURI();
        log.info("请求路径:{}",Url);
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };


        //2、判断本次请求是否需要处理
        //3、如果不需要处理，则直接放行
        if (check(urls,Url)){
            log.info("本次请求{}不需要处理",Url);
            chain.doFilter(request1,response1);
            return;
        }
        //4、判断登录状态，如果已登录，则直接放行
        //4、判断登录状态，如果已登录，则直接放行
        if(request1.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为：{}",request1.getSession().getAttribute("employee"));
            Long empId = (Long) ((HttpServletRequest) request).getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            chain.doFilter(request1,response1);
            return;
        }

        //4-2、判断登录状态，如果已登录，则直接放行
        if(request1.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request1.getSession().getAttribute("user"));

            Long userId = (Long) request1.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            chain.doFilter(request1,response);
            return;
        }

        log.info("用户未登录");
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public Boolean check(String[] urls, String Path){
        for (String url: urls
             ) {
            boolean match = MATH_PATH.match(url, Path);
            if (match){
                return true;
            }
        }
        return false;
    }


}
