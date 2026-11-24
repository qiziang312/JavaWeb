//-----------------------------------------------------部门实体类---------------------------------------------------------
// id：主键
// name：部门名称
// createTime：创建时间
// updateTime：更新时间
//----------------------------------------------------------------------------------------------------------------------
package com.qza.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}