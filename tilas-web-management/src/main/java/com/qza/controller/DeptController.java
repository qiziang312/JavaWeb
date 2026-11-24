package com.qza.controller;

import com.qza.pojo.Dept;
import com.qza.pojo.Result;
import com.qza.service.DeptService;

import lombok.extern.slf4j.Slf4j;

//import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//----------------------------------------------------------------------------------------------------------------------
//controller主要用于路径匹配，主要用到以下两个注解来实现
//
//    // @RequestMapping 6个属性全写（实际很少用这么全）
//    @RequestMapping(
//            path        = "/depts",             接口 URL 路径
//            method      = RequestMethod.POST,   只接受哪种 HTTP 方法
//            params      = "source=web",         URL 查询串必须满足的条件
//            headers     = "X-Token=abc",        HTTP 头必须满足的条件
//            consumes    = "application/json",   请求体内容类型必须是 JSON
//            produces    = "application/json"    响应内容类型必须是 JSON
//    )
//    public Result add(@RequestBody Dept dept) { ... }
//
//    // @PostMapping 快捷写法 —— 没有 method，其余5个照样能写
//    @PostMapping(
//            path        = "/depts",
//            params      = "source=web",
//            headers     = "X-Token=abc",
//            consumes    = "application/json",
//            produces    = "application/json"
//    )
//    public Result add(@RequestBody Dept dept) { ... }
//----------------------------------------------------------------------------------------------------------------------


//log日志注解
@Slf4j 
@RestController
public class DeptController
{

    @Autowired
    private DeptService deptService;

    //查询全部部门数据
    //GET http://localhost:8080/depts
    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    public Result list()
    {
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }


//--------------------------------------------------删除部门原始方法-------------------------------------------------------
//    //根据id删除部门
//    //原始方法1：用注解把参数绑定到方法上
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam("id") Integer deptId){
//        System.out.println("根据ID删除部门: " + deptId);
//        return Result.success();
//    }
//
//    //根据id删除部门
//    //原始方法2：用HttpServletRequest获取参数
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request)
//    {
//        String id = request.getParameter("id");
//        System.out.println("根据ID删除部门: " + id);
//        return Result.success();
//    }
//----------------------------------------------------------------------------------------------------------------------

    //根据id删除部门
    //采用方法：前后端参数名保持一致
    //
    @DeleteMapping("/depts")
    public Result delete(Integer id)
    {
        log.info("根据ID删除部门" + id);
        deptService.delete(id);
        return Result.success();
    }

    //根据id查询部门
    //GET http://localhost:8080/depts/5
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id)
    {
        log.info("根据ID查询部门 : " + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    //添加部门
    //POST http://localhost:8080/depts
    //Content-Type: application/json
    //{
    //  "name": "市场部"
    //}
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept)
    {
        log.info("添加部门: " + dept);
        deptService.add(dept);
        return Result.success();
    }

    //修改部门
    //PUT http://localhost:8080/depts
    //Content-Type: application/json
    //{
    //  "id": 5,
    //  "name": "新部门名称"
    //}
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept)
    {
        log.info("修改部门: " + dept);
        deptService.update(dept);
        return Result.success();
    }

}
