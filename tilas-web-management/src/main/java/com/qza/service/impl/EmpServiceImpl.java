package com.qza.service.impl;

import com.qza.mapper.EmpMapper;
import com.qza.pojo.Emp;
import com.qza.pojo.PageResult;
import com.qza.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Override
    public PageResult<Emp> page(Integer page, Integer size)
    {
        //1. 调用mapper接口，查询总记录数
        Long total = empMapper.count();

        //2. 调用mapper接口，查询结果列表
        Integer start = (page - 1) * size;
        List<Emp> rows = empMapper.list(start, size);

        //3. 封装结果 PageResult
        return new PageResult<Emp>(total, rows);
    }
}
