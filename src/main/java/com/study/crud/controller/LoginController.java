package com.study.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Desc:
 *
 * @author qianqian.zhang
 * @date 2018-03-13 10:17
 **/
@Controller
public class LoginController {

    /**
     * 用户登录
     * @param httpSession
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/userLogin")
    public String userLogin(HttpSession httpSession, String username, String password) {
        if (username.equals("admin") && password.equals("666666")) {
            //登陆成功
            httpSession.setAttribute("username", username);
            return "index";
        } else {
            //登陆失败
            System.out.println("登录失败！请重新登录");
            return "login";
        }
    }

    /**
     * 用户登出
     * @param httpSession
     * @return
     */
    @RequestMapping("/userLogout")
    public String userLogout(HttpSession httpSession){
        httpSession.invalidate();
        return "login";
    }
}
