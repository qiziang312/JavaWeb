package com.qza.service;

import com.qza.pojo.Emp;
import com.qza.pojo.PageResult;


public interface EmpService {
    public PageResult<Emp> page(Integer page, Integer size);
}
