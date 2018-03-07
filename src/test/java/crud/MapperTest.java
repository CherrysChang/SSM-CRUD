package crud;

import com.study.crud.bean.Department;
import com.study.crud.bean.Employee;
import com.study.crud.dao.DepartmentMapper;
import com.study.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * Desc: 测试 DAO层 的工作
 * <p>
 * 推荐 Spring的项目就可以使用 Spring的单元测试，可以自动注入我们需要的组件
 * 1、导入SpringTest模块（加入spring-test.jar）
 * 2、@ContextConfiguration 的 locations属性 指定 Spring配置文件 的位置
 * 3、直接 autowired 要使用的组件即可
 *
 * @author Qian
 * @date 2018-03-07 13:24
 **/
//运行单元测试时可以使用junit的 @RunWith 注解来指定哪个单元测试来运行，这里指定Spring的单元测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    //自动注入
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private SqlSession sqlSession;

    @Test
    public void test() {
        //原生的方式
        /*//1、创建SpringIOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2、从容器中获取 mapper
        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);*/

        System.out.println(departmentMapper);//MapperProxy
    }

    @Test
    public void testCRUD() {
        //1、插入几个部门
//		departmentMapper.insertSelective(new Department(null, "开发部"));
//		departmentMapper.insertSelective(new Department(null, "测试部"));

        //2、生成员工数据，测试员工插入
//        employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@atguigu.com", 1));

        //3、批量插入多个员工；批量，使用可以执行批量操作的sqlSession。
        //①、使用for循环 批量执行插入语句
//		for(){
//			employeeMapper.insertSelective(new Employee(null, , "M", "Jerry@atguigu.com", 1));
//		}
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for(int i = 0;i<1000;i++){
            String uid = UUID.randomUUID().toString().substring(0,5)+i;
            mapper.insertSelective(new Employee(null,uid, "M", uid+"@atguigu.com", 1));
        }
        System.out.println("批量完成");
    }

}
