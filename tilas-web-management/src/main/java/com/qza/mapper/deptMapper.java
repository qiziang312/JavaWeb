package com.qza.mapper;

import com.qza.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface deptMapper
{
    //查询所有部门
    @Select("SELECT id, name, create_time, update_time FROM dept order by update_time desc")
    List<Dept> findAll();

    //根据id查询部门
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    //根据id删除部门
    @Delete("DELETE FROM dept WHERE id = #{id}")
    void delete(Integer id);

    //添加部门
    @Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    //修改部门
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}   
