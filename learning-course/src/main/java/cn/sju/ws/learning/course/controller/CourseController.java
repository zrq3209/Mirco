package cn.sju.ws.learning.course.controller;

import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.course.dto.CourseDTO;
import cn.sju.ws.learning.course.dto.CourseQueryDTO;
import cn.sju.ws.learning.course.service.CourseService;
import cn.sju.ws.learning.course.vo.CourseDetailVO;
import cn.sju.ws.learning.course.vo.CourseVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 课程控制器
 */
@Tag(name = "课程管理", description = "课程管理相关接口")
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(summary = "创建课程", description = "创建新课程")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Result<Long> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        String userName = getCurrentUserName();
        Long id = courseService.createCourse(courseDTO, userName);
        return Result.success("创建成功", id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新课程", description = "更新课程信息")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Result<String> updateCourse(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id,
            @Valid @RequestBody CourseDTO courseDTO) {
        String userName = getCurrentUserName();
        courseService.updateCourse(id, courseDTO, userName);
        return Result.success("更新成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程详情", description = "获取课程详细信息")
    public Result<CourseDetailVO> getCourseDetail(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id) {
        CourseDetailVO courseDetail = courseService.getCourseDetail(id);
        return Result.success(courseDetail);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询课程", description = "分页查询课程列表")
    public Result<IPage<CourseVO>> getCoursePage(@Valid CourseQueryDTO queryDTO) {
        // 默认只查询已审核通过的课程
        if (queryDTO.getApproved() == null) {
            queryDTO.setApproved(1);
        }
        IPage<CourseVO> page = courseService.getCoursePage(queryDTO);
        return Result.success(page);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除课程", description = "删除课程")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Result<String> deleteCourse(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id) {
        String userName = getCurrentUserName();
        courseService.deleteCourse(id, userName);
        return Result.success("删除成功");
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "审核课程", description = "审核课程")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> approveCourse(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id,
            @Parameter(description = "审核状态：1-通过，0-不通过", required = true) @RequestParam Integer approved) {
        courseService.approveCourse(id, approved);
        return Result.success("审核成功");
    }

    @GetMapping("/teacher/page")
    @Operation(summary = "获取教师创建的课程", description = "分页查询当前教师创建的课程列表")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Result<IPage<CourseVO>> getTeacherCoursePage(@Valid CourseQueryDTO queryDTO) {
        String userName = getCurrentUserName();
        IPage<CourseVO> page = courseService.getTeacherCoursePage(queryDTO, userName);
        return Result.success(page);
    }

    /**
     * 获取当前登录用户名
     */
    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}