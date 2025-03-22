package cn.sju.ws.learning.course.controller;

import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.course.entity.Course;
import cn.sju.ws.learning.course.mapper.CourseMapper;
import cn.sju.ws.learning.course.vo.CourseDetailVO;
import cn.sju.ws.learning.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 内部课程控制器（供其他服务调用）
 */
@Tag(name = "内部课程服务", description = "供其他服务调用的内部接口")
@RestController
@RequestMapping("/course/internal")
@RequiredArgsConstructor
public class InternalCourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @GetMapping("/{id}")
    @Operation(summary = "获取课程详情", description = "供内部服务调用的接口")
    public Result<CourseDetailVO> getCourseDetail(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id) {
        CourseDetailVO courseDetail = courseService.getCourseDetail(id);
        return Result.success(courseDetail);
    }

    @GetMapping("/base/{id}")
    @Operation(summary = "获取课程基本信息", description = "供内部服务调用的接口")
    public Result<Course> getCourseBase(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id) {
        Course course = courseMapper.selectById(id);
        return Result.success(course);
    }

    @PutMapping("/{id}/average-score")
    @Operation(summary = "更新课程平均评分", description = "供内部服务调用的接口")
    public Result<Void> updateCourseAverageScore(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id,
            @Parameter(description = "平均评分", required = true) @RequestParam Integer averageScore) {
        Course course = courseMapper.selectById(id);
        if (course != null) {
            course.setAverageScore(averageScore);
            courseMapper.updateById(course);
        }
        return Result.success();
    }
}