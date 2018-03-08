package com.study.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.crud.bean.Employee;
import com.study.crud.service.EmployeeService;
import com.study.crud.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 1、RESTful风格的URI
     * 2、支持JSR303校验:(后端校验)
     *      ①、导入 Hibernate-Validator
     *      ②、在 Bean 属性上标注类似于 @NotNull、@Max等标准的注解指定校验规则
     *      ③、在处理方法的入参上标注 @valid 注解即可让 Spring MVC在完成数据绑定后执行数据校验的工作
     *      ④、SpringMVC通过对处理方法签名的规约来保存校验结果的，保存校验结果的入参必须是 BindingResult 或Errors 类型
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if(result.hasErrors()){
            //校验失败，应该返回失败，在模态框中显示校验失败的错误信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();//所有字段的错误信息
            for (FieldError fieldError : errors) {
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        }else{
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * 检查用户名是否可用
     * @param empName
     * @return
     */
    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkuser(@RequestParam("empName") String empName){
        //1、先判断用户名是否是合法的表达式;
        // （以防出现先从后台检查出用户名不重复，在前台页面显示可用，但点击保存发现不符合前端js校验中表达式要求的内容又提示可用）
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";//注意Java中正则表达式首尾没有"/"
        if(!empName.matches(regx)){//matches方法返回true为匹配成功，false为失败
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
        }

        //2、数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id")Integer id){

        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    /**
     * 发送ajax=POST 形式的请求，请求参数带上 &_method=PUT 可以正常更新成功。（HiddenHttpMethodFilter过滤器的作用）
     *
     * 如果直接发送 ajax=PUT 形式的请求
     * 封装出来的数据为： 除了路径上带的 empId值不为空，其余全为 null
     * Employee[empId=1014, empName=null, gender=null, email=null, dId=null]
     *
     * 问题：
     * 请求体中有数据； 但是Employee对象封装不上；
     * 最后拼出的sql语句为：update tbl_emp  where emp_id = 1014;
     *
     * 原因：
     * 是由于 Tomcat 的问题：
     * 		1、将请求体中的数据，封装一个map。
     * 		2、比如 request.getParameter("empName") 就会从这个 map 中取值。
     * 		3、SpringMVC封装 POJO对象的时候，
     * 				会把 POJO 中每个属性的值调用 request.getParamter("email");来拿到。
     *
     * AJAX发送 PUT 请求引发的血案：
     * 		PUT请求，请求体中的数据，用request.getParameter("empName")拿不到
     * 		Tomcat一看是 PUT请求不会封装请求体中的数据为 map，只有 POST形式的请求才封装请求体为map
     * org.apache.catalina.connector.Request--parseParameters() (3111行);
     *
         protected String parseBodyMethods = "POST";
         if( !getConnector().isParseBodyMethod(getMethod()) ) {
            success = true;
            return;
         }
     *
     *
     * 解决方案：
     * 我们要能 支持直接发送 PUT之类的请求，还要封装请求体中的数据
     * 1、配置上 HttpPutFormContentFilter；（SpringMVC提供的解决方案）
     * 2、它的作用：将请求体中的数据解析包装成一个 map。
     * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据。
     *
     * 员工更新方法
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
    public Msg saveEmp(Employee employee){
//        System.out.println("请求体中的值："+request.getParameter("gender"));
        System.out.println("将要更新的员工数据："+employee);
        employeeService.updateEmp(employee);
        return Msg.success()	;
    }
}
