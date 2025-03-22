package cn.sju.ws.learning.course.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程视图对象
 */
@Data
public class CourseVO {

    /**
     * 课程ID
     */
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 分类列表
     */
    private List<CategoryVO> categories;
}