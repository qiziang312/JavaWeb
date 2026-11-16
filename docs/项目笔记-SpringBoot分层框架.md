# 项目笔记：Spring Boot 分层框架（一句话版）

> 技术栈：Spring Boot + MyBatis + MySQL ｜ 本项目：tilas-web-management（部门管理）

## 〇、文件结构

```
main
├── java/com/qza
│   ├── controller          → DeptController.java   控制层
│   ├── service             → DeptService.java      业务接口
│   │   └── impl            → DeptServiceImpl.java  业务实现
│   ├── mapper              → DeptMapper.java       数据库访问
│   ├── pojo                → Dept.java / Result.java  实体类
│   └── TilasWebManagementApplication.java          启动类
└── resources
    └── application.yml                             配置文件（数据库连接）
```

## 一、文件夹作用

| 文件夹 | 一句话作用 |
|--------|-----------|
| `controller` | 接收前端请求，调用 service，把结果返回给浏览器 |
| `service` | 定义业务接口（规定要做什么） |
| `service.impl` | 写具体的业务逻辑（接口怎么实现） |
| `mapper` | 操作数据库，执行增删改查 |
| `pojo` | 实体类，用来封装数据（`Dept`=数据库表，`Result`=统一返回格式） |
| `resources` | 放配置文件（`application.yml` 配数据库连接） |
| `test` | 放测试代码 |
| `pom.xml` | 管理项目依赖的 jar 包 |

## 二、调用结构：controller → service → mapper

```
浏览器 → Controller（接请求）→ Service（做业务）→ Mapper（查数据库）→ MySQL
```

- **Controller**：只负责"接请求、调 Service、回结果"，不写业务、不碰数据库
- **Service**：只负责"业务逻辑"，不处理 HTTP
- **Mapper**：只负责"跟数据库打交道"，不写业务

数据必须逐层传递，不能跳层（Controller 不能直接调 Mapper）。

## 三、以查询全部部门为例

```
GET /depts
 → DeptController.list()      收到请求
 → DeptService.findall()      业务处理
 → DeptMapper.findAll()       执行 select * from dept
 → List<Dept>                 结果封装成 Dept 对象
 → Result.success(depts)      包装成统一格式 {code:1, data:[...]} 返回浏览器
```

## 四、Controller 接收参数

> 一句话：前端参数放在哪里，后端就用对应的方式接。

| 参数位置 | 请求示例 | 接收方式 | 一句话说明 |
|---------|---------|---------|-----------|
| URL 问号后 `?id=1` | `DELETE /depts?id=1` | `@RequestParam` | 接查询参数，自动转类型 |
| URL 路径里 `/depts/1` | `DELETE /depts/1` | `@PathVariable` | 接路径变量（RESTful 风格） |
| 多个同名参数 `name=a&createUser=b` | `POST /depts?name=a` | POJO 对象（不加注解） | 参数名 = 属性名，自动逐个绑定 |
| 请求体 JSON `{"name":"a"}` | `POST /depts` | `@RequestBody` | 把 JSON 转成对象 |

### 1. 原始方式：HttpServletRequest（不推荐）

```java
@DeleteMapping("/depts")
public Result delete(HttpServletRequest request){
    String idStr = request.getParameter("id");   // 拿到的是字符串
    int id = Integer.parseInt(idStr);            // 要自己转类型
    System.out.println("根据ID删除部门: " + id);
    return Result.success();
}
```

Servlet 原生 API，什么都要手动来：取字符串、转类型、判空。

### 2. @RequestParam：接 `?id=1`

```java
@DeleteMapping("/depts")
public Result delete(@RequestParam Integer id){
    System.out.println("根据ID删除部门: " + id);
    return Result.success();
}
```

Spring 自动类型转换；默认参数必传（`required = true`），不传直接报 400。

### 3. @PathVariable：接 `/depts/1`

```java
@DeleteMapping("/depts/{id}")
public Result delete(@PathVariable Integer id){
    System.out.println("根据ID删除部门: " + id);
    return Result.success();
}
```

路径里用 `{id}` 占位，变量名要和形参名一致（不一致就写 `@PathVariable("id")`）。

### 4. POJO 接收：参数多时打包成对象

```java
@PostMapping("/depts")
public Result add(Dept dept){          // 不加注解
    deptService.add(dept);
    return Result.success();
}
```

请求参数名和 `Dept` 属性名相同就自动 set 进去，适合表单 / 查询串多参数。

### 5. @RequestBody：接 JSON 请求体

```java
@PostMapping("/depts")
public Result add(@RequestBody Dept dept){
    deptService.add(dept);
    return Result.success();
}
```

前端必须发 JSON（`Content-Type: application/json`），Spring 用消息转换器把 JSON 反序列化成对象。

### 易混点对比

- `@RequestParam` vs `@PathVariable`：一个接 `?` 后面的，一个接路径里的。
- POJO 不加注解 vs `@RequestBody`：不加注解接的是**键值对参数**，加 `@RequestBody` 接的是**JSON 体**，同一个 POJO 两种来源不能混。
- DELETE / GET 请求一般不带请求体，参数走 `@RequestParam` 或 `@PathVariable`。

## 五、代码存档：deptMapper.java（Mapper 层）

```java
package com.qza.mapper;

import com.qza.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface deptMapper {
    @Select("SELECT id, name, create_time, update_time FROM dept order by update_time desc")
    List<Dept> findAll();

}
```

### 一句话解读

- `@Mapper`：MyBatis 为这个接口生成代理实现类，接口不用自己写实现。
- `@Select(...)`：SQL 直接注解在方法上，调用 `findAll()` 就等于执行这条 SQL。
- 返回值 `List<Dept>`：查询结果每一行自动映射成一个 `Dept` 对象；`create_time` 列能装进 `createTime` 属性，靠 MyBatis 默认开启的下划线转驼峰映射。
- `order by update_time desc`：按更新时间倒序，最近改过的部门排最前。
- 小瑕疵：类名 `deptMapper` 首字母小写，不符合 Java 命名规范，建议改为 `DeptMapper`。
