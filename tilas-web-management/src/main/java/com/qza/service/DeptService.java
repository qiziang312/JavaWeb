package com.qza.service;

import com.qza.pojo.Dept;
//import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptService
{
    List<Dept> findAll();
    Dept getById(Integer id);
    void delete(Integer id);
    void add(Dept dept);
    void update(Dept dept);
}


