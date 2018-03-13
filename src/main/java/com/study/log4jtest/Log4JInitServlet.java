package com.study.log4jtest;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 * Desc: 在 WEB项目中使用 log4j
 * 自定义一个Servlet，用来启动 log4jConfigLocation的servlet
 *
 * ps：在以往我们的Servlet都需要在web.xml文件中进行配置（Servlet3.0同样支持），
 * 但是在Servlet3.0中引入了注解，我们只需要在对应的Servlet类上使用@WebServlet注解进行标记，我们的应用启动之后就可以访问到该Servlet。
 * 对于一个@WebServlet而言，有一个属性是必须要的，那就是它的访问路径。@WebServlet中有两个属性可以用来表示Servlet的访问路径，分别是value和urlPatterns。
 * value和urlPatterns都是数组形式，表示我们可以把一个Servlet映射到多个访问路径，但是value和urlPatterns不能同时使用。
 *
 * 使用@WebServlet时也可以配置初始化参数，它是通过@WebServlet的initParams参数来指定的。
 * initParams是一个@WebInitParam数组，每一个@WebInitParam代表一个初始化参数。
 * @author qian
 * @date 2018-03-12 11:43
 **/
//@WebServlet(value = "/log4JInitServlet",initParams = {@WebInitParam(name="log4j-properties-location", value="/WEB-INF/classes/log4j.properties")})
public class Log4JInitServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log4JInitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Log4JInitServlet 正在初始化 log4j日志设置信息");
        String log4jLocation = config.getInitParameter("log4j-properties-location");

        ServletContext sc = config.getServletContext();

        if (log4jLocation == null) {
            System.err.println("*** 没有 log4j-properties-location 初始化的文件, 所以使用 BasicConfigurator初始化");
            BasicConfigurator.configure();
        } else {
            String webAppPath = sc.getRealPath("/");
            String log4jProp = webAppPath + log4jLocation;
            File yoMamaYesThisSaysYoMama = new File(log4jProp);
            if (yoMamaYesThisSaysYoMama.exists()) {
                System.out.println("使用: " + log4jProp+"初始化日志设置信息");
                PropertyConfigurator.configure(log4jProp);
            } else {
                System.err.println("*** " + log4jProp + " 文件没有找到， 所以使用 BasicConfigurator初始化");
                BasicConfigurator.configure();
            }
        }
        super.init(config);
    }
}
