//------------------------------------------------员工表实体类------------------------------------------------------------
//id：        主键
//username：  用户名
//password：  密码
//name：      姓名
//gender：    性别       1表示男，0表示女
//phone：     电话
//job：       职位       1表示程序员，2表示测试，3表示经理，4表示总裁，5表示其他
//salary：    薪酬
//image：     头像
//entryDate： 入职日期
//deptId：    部门
//createTime：创建时间
//updateTime：更新时间
//----------------------------------------------------------------------------------------------------------------------


package com.qza.pojo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Emp
{
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer gender;
    private String phone;
    private Integer job;
    private Integer salary;
    private String image;
    private LocalDate entryDate;
    private Integer deptId;
    private LocalDate createTime;
    private LocalDate updateTime;
}
