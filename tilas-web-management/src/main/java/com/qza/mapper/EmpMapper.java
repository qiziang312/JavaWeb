package com.qza.mapper;

import com.qza.pojo.Emp;
import com.qza.pojo.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapper {

    //查询总记录数
    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id;")
    public long count();

    //分页查询当前页面的数据
    @Select("select e.*, d.name from emp e left join dept d on e.dept_id = d.id limit #{start}, #{size}")
    public List<Emp> list(Integer start, Integer size);
}
