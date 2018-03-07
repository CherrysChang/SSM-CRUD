package com.study.crud.dao;

import com.study.crud.bean.Employee;
import com.study.crud.bean.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    /*带条件的查询 员工信息*/
    List<Employee> selectByExample(EmployeeExample example);

    /*根据主键查询 员工信息*/
    Employee selectByPrimaryKey(Integer empId);

    /*联合查询，部门信息和员工信息都查询出来*/
    List<Employee> selectByExampleWithDept(EmployeeExample example);

    Employee selectByPrimaryKeyWithDept(Integer empId);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}