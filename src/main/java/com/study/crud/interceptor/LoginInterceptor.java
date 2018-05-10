package com.study.crud.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Desc: 自定义拦截器
 * Spring Web MVC的处理器拦截器（如无特殊说明，下文所说的拦截器即处理器拦截器）类似于Servlet开发中的过滤器Filter，用于对处理器进行预处理和后处理。
 * 常见场景：日志记录、权限检查、性能监控、通用行为、OpenSessionInView等
 * SpringMVC 中的 Interceptor 拦截请求是通过 HandlerInterceptor 来实现的。
 * <p>
 * 在SpringMVC 中定义一个Interceptor 非常简单，主要有两种方式：
 * 1、要定义的Interceptor类要实现了Spring 的HandlerInterceptor 接口，或者是这个类继承实现了HandlerInterceptor 接口的类，比如Spring 已经提供的实现了HandlerInterceptor 接口的抽象类HandlerInterceptorAdapter
 * 2、实现Spring的 WebRequestInterceptor 接口，或者是继承实现了WebRequestInterceptor的类
 * <p>
 * 登陆拦截器
 * 场景：用户点击查看的时候，我们进行登陆拦截器操作，判断用户是否登陆？
 * 登陆，则不拦截，没登陆，则转到登陆界面；
 *
 * @author qianqian.zhang
 * @date 2018-03-13 9:59
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 该 preHandle 方法在目标方法之前被调用.
     * 若返回值为 true, 则继续调用后续的拦截器和目标方法.
     * 若返回值为 false, 则不会再调用后续的拦截器和目标方法.
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            //登陆成功的用户才能访问列表界面
            return true;
        } else {
            //没有登陆，请求转发到登陆界面
//            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            //重定向到登录界面
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
    }

    /**
     * postHandle 在 调用目标方法之后, 但渲染视图之前 被调用
     * 作用： 可以对 请求域中的属性 或 视图 做出修改.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        System.out.println("--------------postHandle");
    }

    /**
     * afterCompletion 在 渲染视图之后被调用.
     * 作用： 可以用来 释放资源
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        System.out.println("--------------afterCompletion");
    }
}
