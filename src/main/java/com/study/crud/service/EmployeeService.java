package com.study.crud.service;

import com.study.crud.bean.Employee;
import com.study.crud.bean.EmployeeExample;
import com.study.crud.bean.EmployeeExample.Criteria;
import com.study.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Desc:
 *
 * @author qian
 * @date 2018-03-07 14:45
 **/
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * @return
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 保存员工
     * @param employee
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);//有选择的插入，此处传入的id为空。如果选择insert方法需要id不能为空
    }

    /**
     * 检验用户名是否可用
     * @param empName
     * @return true：代表当前姓名可用  fasle：不可用
     */
    public boolean checkUser(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(employeeExample);
        return count==0;
    }

    /**
     * 按照员工id查询员工
     * @param id
     * @return
     */
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 员工更新
     * @param employee
     */
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);//有选择的更新
    }
}
