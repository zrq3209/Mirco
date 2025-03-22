package cn.sju.ws.learning.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 课程分类关联实体
 */
@Data
@TableName("category_course")
public class CategoryCourse {

    /**
     * 关联ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 课程ID
     */
    private Long courseId;
}