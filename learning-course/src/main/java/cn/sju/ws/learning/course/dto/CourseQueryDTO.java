package cn.sju.ws.learning.course.dto;

import cn.sju.ws.learning.common.entity.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseQueryDTO extends PageParam {

    /**
     * 课程名称，模糊查询
     */
    private String name;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 创建者用户名
     */
    private String userName;

    /**
     * 是否审核通过
     */
    private Integer approved;
}