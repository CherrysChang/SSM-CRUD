package com.study.crud.controller;

import com.study.crud.exception.BusinessException;
import com.study.crud.exception.error.EmBusinessError;
import com.study.crud.utils.PdfGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
//@SessionAttributes(value = {"errmsg"})
@Controller
public class LoginController extends BaseController{

    /*@RequestMapping("/test")
    public void test(){
        //测试异常1
        int a=5/0;//此处会抛出ArithmeticException异常。测试SpringMVC的异常处理
        System.out.println(a);

        //测试异常2
        *//*throw new BusinessException(EmBusinessError.UNKNOW_ERROR);*//*
    }*/

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String toLogin(@RequestParam(required = false)String errmsg,Map<String,Object> map){
        if(errmsg !=null){
            map.put("errmsg", errmsg);
        }
        return "loginUI";
    }

    /**
     * 用户登录
     * @param httpSession
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/userLogin")
    public String userLogin(HttpSession httpSession, String username, String password/*, Model model*/) {
        if (username.equals("admin") && password.equals("666666")) {
            //登陆成功
            httpSession.setAttribute("username", username);
//            httpSession.removeAttribute("errmsg");
            return "redirect:/index";
        } else {
            //登陆失败
//            model.addAttribute("errmsg","登录失败，请重新登录！");
//            return "redirect:/login";
              return "redirect:/login?errmsg=-1";
            /**一般情况下，控制器方法返回 字符串类型的值 会被当成 逻辑视图名 处理。
             * 如果返回的字符串中带 forward: 或 redirect: 前缀时，SpringMVC 会对他们进行特殊处理：
             * 将 forward: 和redirect: 当成指示符，其后的字符串作为 URL 来处理.
             */
            /**
             * Spring MVC 提供了以下几种途径输出模型数据：
             * ModelAndView: 处理方法返回值类型为 ModelAndView 时, 方法体即可通过该对象添加模型数据（放在请求域中）
             * Map 及 Model: 入参为org.springframework.ui.Model、org.springframework.ui.ModelMap 或 java.uti.Map 时，处理方法返回时，Map中的数据会自动添加到模型中。（放在请求域中）
             * @SessionAttributes: 将模型中的某个属性暂存到 HttpSession 中，以便多个请求之间可以共享这个属性。（放在Session域中）
             * @ModelAttribute: 方法入参标注该注解后, 入参的对象 就会放到数据模型中
             */
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
        return "redirect:/login";
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
