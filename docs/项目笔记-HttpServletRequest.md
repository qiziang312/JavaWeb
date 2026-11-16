# 项目笔记：HttpServletRequest（精简版）

> 配套阅读：《项目笔记-SpringBoot分层框架.md》《项目笔记-注解讲解与调用关系.md》

## 一、一句话认识它

Tomcat 把浏览器发来的 HTTP 请求报文解析好、封装成的对象，用方法直接取，不用自己拆字节。

## 二、这是谁的包（jakarta.servlet）

| 层面 | 是谁的 |
|------|--------|
| **标准/规范** | **Jakarta EE**（Eclipse 基金会维护）制定的 Servlet 规范，`HttpServletRequest` 只是规范里定义的**接口** |
| **历史前身** | 最早是 Sun 公司 Java EE（J2EE）里的 `javax.servlet`；2017 年 Java EE 捐给 Eclipse 基金会后，因商标限制从 Jakarta EE 9 起改名 `jakarta.*` |
| **真正提供实现类的 jar** | **Servlet 容器（Tomcat）**。本项目是 `spring-boot-starter-web` 带进来的内嵌 Tomcat（`tomcat-embed-core`），接口方法都是 Tomcat 实现的 |

版本对应（记这一条就够）：

- Spring Boot **2.x** → `javax.servlet.*`（Servlet 4.0）
- Spring Boot **3.x** → `jakarta.servlet.*`（Servlet 5.0/6.0）

本项目是 Spring Boot 3.3.5，所以 import 写 `jakarta.servlet.http.HttpServletRequest`；老教程里的 `javax.servlet` 是 Boot 2.x 时代写法，照抄会报"程序包不存在"。

> 一句话总结：规范是 Eclipse 基金会 Jakarta EE 的，代码（实现）是 Tomcat 的，javax→jakarta 只是改名换东家。

## 三、取参方法一张表

| 方法 | 一句话 |
|---|---|
| `getMethod()` | 取请求方式（GET/POST） |
| `getRequestURI()` / `getRequestURL()` | 取路径 / 取完整 URL |
| `getHeader(name)` | 取单个请求头 |
| `getCookies()` | 取所有 Cookie，**没带返回 null，用前判空** |
| `getParameter(name)` | 取单个参数，GET 查询串和 POST 表单体统一取；取不到返回 null |
| `getParameterValues(name)` | 取同名多值（复选框） |
| `getInputStream()` / `getReader()` | 取请求体原始流（上传/JSON），二者互斥、与 getParameter 互斥 |
| `getRemoteAddr()` | 取客户端 IP（有代理时不可信） |
| `setAttribute()` / `getAttribute()` | 请求域存/取值，仅一次请求内有效 |

## 四、易错点四条

1. `getParameter`、`getCookies` 取不到是 **null**，用前判空。
2. POST 中文乱码：第一次取参**前** `setCharacterEncoding("UTF-8")`。
3. 转发（forward）共享请求域，重定向是两次请求、不共享。
4. 请求结束对象即销毁，请求域生命周期 = 一次请求。
