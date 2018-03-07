package crud;

import com.github.pagehelper.PageInfo;
import com.study.crud.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Desc:
 * 使用 Spring测试模块 提供的测试请求功能，测试curd请求的正确性
 * 注意： Spring4测试的时候，需要servlet3.0的支持
 *
 * @author qian
 * @date 2018-03-07 15:10
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml" })
public class MVCTest {
    // 传入Springmvc的 IOC（IOC容器如何自动注入自己，需要注解 @WebAppConfiguration）
    @Autowired
    WebApplicationContext context;
    // 虚拟mvc请求，获取到处理结果。
    MockMvc mockMvc;

    //初始化 mockMvc
    @Before
    public void initMokcMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        //模拟请求 拿到返回值。此示例模拟get请求，/emps，传入参数pageNo的值，拿到返回值。
        MvcResult result = mockMvc.perform(
                    MockMvcRequestBuilders.get("/emps").param("pageNo", "6")
                ).andReturn();

        //请求成功以后，请求域中会有pageInfo；我们可以取出pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码："+pi.getPageNum());
        System.out.println("总页码："+pi.getPages());
        System.out.println("总记录数："+pi.getTotal());
        System.out.println("在页面需要连续显示的页码");
        int[] nums = pi.getNavigatepageNums();
        for (int i : nums) {
            System.out.print(" "+i);
        }
        System.out.println();
        //获取员工数据
        List<Employee> list = pi.getList();
        for (Employee employee : list) {
            System.out.println("ID："+employee.getEmpId()+"==>Name:"+employee.getEmpName());
        }
        /**
         * 传入pageNo=2 运行结果1：
         当前页码：2
         总页码：201
         总记录数：1001
         在页面需要连续显示的页码
         1 2 3 4 5
         ID：6==>Name:75a714
         ID：7==>Name:756285
         ID：8==>Name:eae5f6
         ID：9==>Name:f06897
         ID：10==>Name:7f4e08
         */

        /** 传入pageNo=5 运行结果2：
         当前页码：5
         总页码：201
         总记录数：1001
         在页面需要连续显示的页码
         3 4 5 6 7
         ID：21==>Name:0bfe519
         ID：22==>Name:5993920
         ID：23==>Name:346a621
         ID：24==>Name:dd02222
         ID：25==>Name:7869e23
         */

        /** 传入pageNo=6 运行结果3：
         当前页码：6
         总页码：201
         总记录数：1001
         在页面需要连续显示的页码
         4 5 6 7 8
         ID：26==>Name:b7d6c24
         ID：27==>Name:a6ee825
         ID：28==>Name:7807526
         ID：29==>Name:faeef27
         ID：30==>Name:e8edf28
         */
    }

}
