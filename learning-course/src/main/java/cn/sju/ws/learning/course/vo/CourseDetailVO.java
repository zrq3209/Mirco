package cn.sju.ws.learning.course.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 课程详情视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseDetailVO extends CourseVO {

    /**
     * 章节列表
     */
    private List<ChapterVO> chapters;
}