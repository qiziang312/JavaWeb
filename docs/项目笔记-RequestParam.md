# 项目笔记：@RequestParam（精简版）

> 配套阅读：《项目笔记-SpringBoot分层框架.md》《项目笔记-HttpServletRequest.md》

## 一、一句话认识它

Spring MVC 的注解（`org.springframework.web.bind.annotation` 包，Spring 自家的），把 **URL 问号后的查询串 / 表单键值对参数** 绑定到控制器方法形参，并自动转类型。

## 二、三个常用属性

| 属性 | 默认值 | 一句话 |
|------|--------|--------|
| `value` / `name` | 按形参名匹配 | 指定请求参数名；形参名和参数名不一致时**必须显式写** |
| `required` | `true` | 参数必传；不传直接 400 错误 |
| `defaultValue` | 无 | 缺参时的兜底值；写了它 `required` 就失效 |

## 三、结合项目代码看

```java
@DeleteMapping("/depts")
public Result delete(@RequestParam("id") Integer deptId){
```

- 请求长这样：`DELETE /depts?id=3`，`id=3` 就是被绑定的查询参数。
- 形参叫 `deptId`、参数叫 `id`，**名字不一致**，所以括号里显式写 `"id"`；若形参直接叫 `id`，可简写成 `@RequestParam Integer id` 甚至省略注解。
- `Integer` 类型：Spring 自动把字符串 `"3"` 转成整数，省掉 `Integer.parseInt`；传 `?id=abc` 转不了 → 400。
- `required` 默认 true：不传 `id` → 400，比 `HttpServletRequest` 版返回 null 更安全。

## 四、对比速记

| 写法 | 接什么 | 缺参表现 |
|------|--------|---------|
| `@RequestParam` | `?id=1` 查询串 / 表单键值对 | 400 |
| `@PathVariable` | 路径里的 `/depts/1` | 404（路径都匹配不上） |
| `@RequestBody` | JSON 请求体 | 400 |
| 不加注解的简单类型 | 也按参数名绑定（相当于 required=false） | null / 不报错 |
| `HttpServletRequest.getParameter` | 同查询串/表单 | 返回 null，全手动 |

## 五、易错点四条

1. **形参名 ≠ 参数名**又没写 `value` → 绑定不上报 400（本项目 `deptId` vs `id` 就是实例）。
2. 普通 Spring 项目编译没加 `-parameters` 参数时形参名会丢失，必须显式写 `value`；Spring Boot 默认已加，可省略。
3. **JSON 请求体用 `@RequestParam` 接不到**，那是 `@RequestBody` 的活。
4. 同名多值（复选框）用 `@RequestParam List<String>` 或 `String[]` 接，单个形参只拿第一个。
