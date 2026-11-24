//分页查询返回结果实体类
package com.qza.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T>
{
    private Long total;
    private List<T> rows;
}
