package com.qza.service;

import com.qza.pojo.Dept;
//import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptService
{
    //查询全部部门数据
    List<Dept> findAll();

    //根据id查询部门
    Dept getById(Integer id);

    //根据id删除部门
    void delete(Integer id);

    //添加部门
    void add(Dept dept);

    //修改部门
    void update(Dept dept);
}


