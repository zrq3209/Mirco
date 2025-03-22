package cn.sju.ws.learning.course.mapper;

import cn.sju.ws.learning.course.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 课程Mapper接口
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 分页查询课程
     */
    IPage<Course> selectCoursePage(Page<Course> page,
                                   @Param("name") String name,
                                   @Param("categoryId") Integer categoryId,
                                   @Param("userName") String userName,
                                   @Param("approved") Integer approved);
}