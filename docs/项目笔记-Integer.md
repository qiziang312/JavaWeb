# 项目笔记：Integer（极简版）

**一句话**：`int` 的包装类——能表示 null、能进集合，Spring 接参自动转类型全靠它。

## 核心速查

| 项 | 一句话 |
|----|--------|
| 创建 | 用 `Integer.valueOf(3)`（走缓存，推荐）；`new Integer` 已废弃；要基本类型用 `parseInt("3")` |
| null 语义 | `@RequestParam Integer deptId` 能区分"没传"(null)和"传了 0"；**拆箱运算前先判空**，否则 NPE |
| 比较 | `==` 有 -128~127 缓存池陷阱，比数值一律用 `equals` |
| 转换失败 | `"abc"` 转 Integer 抛 NumberFormatException，Web 请求里被 Spring 转成 400 |
| 常用成员 | `MAX_VALUE` / `MIN_VALUE`、`intValue()`、`String.valueOf(i)`（null 安全） |

> 详细版见：《项目笔记-RequestParam.md》
