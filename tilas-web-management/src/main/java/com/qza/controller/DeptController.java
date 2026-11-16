package com.qza.controller;

import com.qza.pojo.Dept;
import com.qza.pojo.Result;
import com.qza.service.DeptService;
//import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController
{
    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    public Result list()
    {
        System.out.println("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }




//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam("id") Integer deptId){
//        System.out.println("根据ID删除部门: " + deptId);
//        return Result.success();
//    }
//
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request)
//    {
//        String id = request.getParameter("id");
//        System.out.println("根据ID删除部门: " + id);
//        return Result.success();
//    }
    @DeleteMapping("/depts")
    public Result delete(Integer id)
    {
        System.out.println("根据ID删除部门: " + id);
        deptService.delete(id);
        return Result.success();
    }

    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id)
    {
        System.out.println("根据ID查询部门 : " + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept)
    {
        System.out.println("添加部门: " + dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 修改部门
     */
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept)
    {
        System.out.println("修改部门: " + dept);
        deptService.update(dept);
        return Result.success();
    }

}
