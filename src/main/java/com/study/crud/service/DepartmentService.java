package com.study.crud.service;

import com.study.crud.bean.Department;
import com.study.crud.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Desc:
 *
 * @author qianqian.zhang
 * @date 2018-03-08 10:26
 **/
@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查出所有部门信息
     * @return
     */
    public List<Department> getDepts() {
        List<Department> departments = departmentMapper.selectByExample(null);//获取所有部门信息，传入null
        return departments;
    }
}
