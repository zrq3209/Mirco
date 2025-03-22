package cn.sju.ws.learning.course.entity;

import cn.sju.ws.learning.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 课程实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("course")
public class Course extends BaseEntity {

    /**
     * 课程ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程价格
     */
    private BigDecimal price;

    /**
     * 课程简介
     */
    private String description;

    /**
     * 创建者用户名
     */
    private String userName;

    /**
     * 平均评分
     */
    private Integer averageScore;

    /**
     * 封面图片
     */
    private String coverPicture;

    /**
     * 是否审核通过
     */
    private Integer approved;
}