package cn.sju.ws.learning.course.service;

import cn.sju.ws.learning.course.dto.CourseDTO;
import cn.sju.ws.learning.course.dto.CourseQueryDTO;
import cn.sju.ws.learning.course.vo.CourseDetailVO;
import cn.sju.ws.learning.course.vo.CourseVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 课程服务接口
 */
public interface CourseService {

    /**
     * 创建课程
     */
    Long createCourse(CourseDTO courseDTO, String userName);

    /**
     * 更新课程
     */
    void updateCourse(Long id, CourseDTO courseDTO, String userName);

    /**
     * 获取课程详情
     */
    CourseDetailVO getCourseDetail(Long id);

    /**
     * 分页查询课程
     */
    IPage<CourseVO> getCoursePage(CourseQueryDTO queryDTO);

    /**
     * 删除课程
     */
    void deleteCourse(Long id, String userName);

    /**
     * 审核课程
     */
    void approveCourse(Long id, Integer approved);

    /**
     * 获取教师创建的课程列表
     */
    IPage<CourseVO> getTeacherCoursePage(CourseQueryDTO queryDTO, String userName);
}