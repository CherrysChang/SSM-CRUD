package com.study.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.crud.bean.Employee;
import com.study.crud.service.EmployeeService;
import com.study.crud.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Desc: 处理 Employee 员工 CRUD 请求
 *
 * @author qian
 * @date 2018-03-07 14:44
 **/
@Controller
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    /**
     *查询员工数据（分页查询）
     *
     * index.jsp中“<jsp:forward page="/emps"/>”直接跳转到emps请求的方式
     *
     * 如果方法的入参为 Map 或 Model 类型，Spring MVC 会将隐含模型的引用传递给这些入参。
     * 在方法体内，开发者可以通过这个入参对象访问到模型中的所有数据，也可以向模型中添加新的属性数据
     * Map或 Model中存放的数据，实际都放到了request请求域里面。
     * @return
     */
//    @RequestMapping("/emps")
    public String getEmps( @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                           Model model) {
        // 如果只是 List<Employee> emps = employeeService.getAll();这不是一个分页查询

        // 引入PageHelper分页插件
        // 在查询之前只需要调用 startPage，传入页码 pageNo，以及每页的大小 5
        PageHelper.startPage(pageNo, 5);

        // startPage 后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();

        // 使用pageInfo 包装查询后的结果，只需要将 pageInfo 交给页面就行了。
        // pageInfo 封装了详细的分页信息,包括有我们查询出来的数据。
        PageInfo page = new PageInfo(emps, 5);//传入连续显示的页数5，页面上可以通过page.getNavigatePages()获取
        model.addAttribute("pageInfo", page);

        return "list";
    }

    /**
     * 查询员工数据（分页查询）
     * 返回值为 PageInfo，也可以设置通用的返回类 Msg
     *  利用 @ResponseBody返回json数据，而不是页面，
     *  访问地址：http://localhost:8080/ssm-crud/emps?pageNo=3 可以查看返回的 JSON字符串
     *
     * 注意：@ResponseBody正常工作需要 导入jackson包。负责将 对象 转换为 json字符串
     */
    @RequestMapping("/emps")
    @ResponseBody //返回json数据
    public Msg getEmpsWithJson(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                               Model model) {
        // 引入PageHelper分页插件
        // 在查询之前只需要调用 startPage，传入页码 pageNo，以及每页的大小 5
        PageHelper.startPage(pageNo, 5);

        // startPage 后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();

        // 使用pageInfo 包装查询后的结果，只需要将 pageInfo 交给页面就行了。
        // pageInfo 封装了详细的分页信息,包括有我们查询出来的数据。
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo",page);
    }

    /**
     * 员工保存
     *  RESTful风格的URI
     *
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(Employee employee) {
        employeeService.saveEmp(employee);
        return Msg.success();
    }

    /**
     * 检查用户名是否可用
     * @param empName
     * @return
     */
    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkuser(@RequestParam("empName") String empName){
        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }
}
