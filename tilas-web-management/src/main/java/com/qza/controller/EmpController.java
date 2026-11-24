package com.qza.controller;

import com.qza.pojo.Emp;
import com.qza.pojo.PageResult;
import com.qza.pojo.Result;
import com.qza.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {


    @Autowired
    private EmpService empService;


    //@RequestParam(
    //    value        = "id",              请求参数名，形参名不一致时必须写
    //    required     = true,              是否必传，默认true，不传直接400
    //    defaultValue = "1"                缺参时的兜底值，写了它required失效
    //)
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页请求参数: {}, {}", page, pageSize);
        PageResult<Emp> pageResult = empService.page(page, pageSize);
        return Result.success(pageResult);
    }
}
