package cn.sju.ws.learning.course.service.impl;

import cn.sju.ws.learning.common.exception.BusinessException;
import cn.sju.ws.learning.common.util.AssertUtil;
import cn.sju.ws.learning.common.util.BeanCopyUtil;
import cn.sju.ws.learning.course.dto.CourseDTO;
import cn.sju.ws.learning.course.dto.CourseQueryDTO;
import cn.sju.ws.learning.course.entity.CategoryCourse;
import cn.sju.ws.learning.course.entity.Course;
import cn.sju.ws.learning.course.mapper.CategoryCourseMapper;
import cn.sju.ws.learning.course.mapper.CourseMapper;
import cn.sju.ws.learning.course.service.CategoryService;
import cn.sju.ws.learning.course.service.ChapterService;
import cn.sju.ws.learning.course.service.CourseService;
import cn.sju.ws.learning.course.vo.CategoryVO;
import cn.sju.ws.learning.course.vo.ChapterVO;
import cn.sju.ws.learning.course.vo.CourseDetailVO;
import cn.sju.ws.learning.course.vo.CourseVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final CategoryCourseMapper categoryCourseMapper;
    private final CategoryService categoryService;
    private final ChapterService chapterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCourse(CourseDTO courseDTO, String userName) {
        // 创建课程
        Course course = BeanCopyUtil.copyProperties(courseDTO, Course.class);
        course.setUserName(userName);
        course.setAverageScore(0); // 初始评分为0
        course.setApproved(0); // 默认未审核

        courseMapper.insert(course);

        // 关联分类
        saveCourseCategories(course.getId(), courseDTO.getCategoryIds());

        return course.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCourse(Long id, CourseDTO courseDTO, String userName) {
        // 查询课程
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        // 检查权限
        checkCourseOwner(course, userName);

        // 更新课程信息
        BeanCopyUtil.copyProperties(courseDTO, course);
        courseMapper.updateById(course);

        // 更新分类关联
        LambdaQueryWrapper<CategoryCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryCourse::getCourseId, id);
        categoryCourseMapper.delete(queryWrapper);

        saveCourseCategories(id, courseDTO.getCategoryIds());
    }

    @Override
    public CourseDetailVO getCourseDetail(Long id) {
        // 查询课程
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        // 转换为VO
        CourseDetailVO courseDetailVO = BeanCopyUtil.copyProperties(course, CourseDetailVO.class);

        // 获取分类
        List<CategoryVO> categories = categoryService.getCategoriesByCourseId(id);
        courseDetailVO.setCategories(categories);

        // 获取章节
        List<ChapterVO> chapters = chapterService.getChaptersByCourseId(id);
        courseDetailVO.setChapters(chapters);

        return courseDetailVO;
    }

    @Override
    public IPage<CourseVO> getCoursePage(CourseQueryDTO queryDTO) {
        Page<Course> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<Course> coursePage = courseMapper.selectCoursePage(
                page,
                queryDTO.getName(),
                queryDTO.getCategoryId(),
                queryDTO.getUserName(),
                queryDTO.getApproved()
        );

        // 转换为VO
        return coursePage.convert(course -> {
            CourseVO courseVO = BeanCopyUtil.copyProperties(course, CourseVO.class);
            List<CategoryVO> categories = categoryService.getCategoriesByCourseId(course.getId());
            courseVO.setCategories(categories);
            return courseVO;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCourse(Long id, String userName) {
        // 查询课程
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        // 检查权限
        checkCourseOwner(course, userName);

        // 删除课程
        courseMapper.deleteById(id);

        // 删除分类关联
        LambdaQueryWrapper<CategoryCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryCourse::getCourseId, id);
        categoryCourseMapper.delete(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveCourse(Long id, Integer approved) {
        // 查询课程
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        // 更新审核状态
        course.setApproved(approved);
        courseMapper.updateById(course);
    }

    @Override
    public IPage<CourseVO> getTeacherCoursePage(CourseQueryDTO queryDTO, String userName) {
        // 设置用户名，只查询教师创建的课程
        queryDTO.setUserName(userName);
        return getCoursePage(queryDTO);
    }

    /**
     * 保存课程和分类的关联关系
     */
    private void saveCourseCategories(Long courseId, List<Integer> categoryIds) {
        for (Integer categoryId : categoryIds) {
            CategoryCourse categoryCourse = new CategoryCourse();
            categoryCourse.setCategoryId(categoryId);
            categoryCourse.setCourseId(courseId);
            categoryCourseMapper.insert(categoryCourse);
        }
    }

    /**
     * 检查用户是否是课程创建者
     */
    private void checkCourseOwner(Course course, String userName) {
        if (!course.getUserName().equals(userName)) {
            throw new BusinessException("您不是该课程的创建者，无法操作");
        }
    }
}