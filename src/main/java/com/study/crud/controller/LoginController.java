package com.study.crud.controller;

import com.study.crud.utils.PdfGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    public String userLogin(HttpSession httpSession, String username, String password, Model model) {
        if (username.equals("admin") && password.equals("666666")) {
            //登陆成功
            httpSession.setAttribute("username", username);
            return "redirect:/index";
        } else {
            //登陆失败
            model.addAttribute("errmsg","登录失败，请重新登录！");
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

    /**
     * pdf下载
     * @param request
     * @param response
     * @author qianqian 2017-3-16 下午4:19:35
     */
    @RequestMapping("/testDownload")
    public void downloadLetter(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> variables = new HashMap<String,Object>();
            variables.put("name", "zxxx");
            String basePath = request.getSession().getServletContext().getRealPath("/");
            variables.put("contextPath", basePath);
            PdfGenerator.printPDF(basePath, variables, "hello", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
