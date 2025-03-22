package cn.sju.ws.learning.course.controller;

import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.course.dto.CourseQueryDTO;
import cn.sju.ws.learning.course.service.CategoryService;
import cn.sju.ws.learning.course.service.CourseService;
import cn.sju.ws.learning.course.vo.CategoryVO;
import cn.sju.ws.learning.course.vo.CourseDetailVO;
import cn.sju.ws.learning.course.vo.CourseVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公开课程控制器
 */
@Tag(name = "公开课程接口", description = "公开的课程相关接口，无需鉴权")
@RestController
@RequestMapping("/api/course/public")
@RequiredArgsConstructor
public class PublicCourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    @Operation(summary = "获取课程详情", description = "获取课程详细信息")
    public Result<CourseDetailVO> getCourseDetail(
            @Parameter(description = "课程ID", required = true) @PathVariable Long id) {
        CourseDetailVO courseDetail = courseService.getCourseDetail(id);
        return Result.success(courseDetail);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询课程", description = "分页查询已审核通过的课程列表")
    public Result<IPage<CourseVO>> getCoursePage(@Valid CourseQueryDTO queryDTO) {
        // 只查询已审核通过的课程
        queryDTO.setApproved(1);
        IPage<CourseVO> page = courseService.getCoursePage(queryDTO);
        return Result.success(page);
    }

    @GetMapping("/category/tree")
    @Operation(summary = "获取分类树", description = "获取所有分类的树形结构")
    public Result<List<CategoryVO>> getCategoryTree() {
        List<CategoryVO> categoryTree = categoryService.getCategoryTree();
        return Result.success(categoryTree);
    }

    @GetMapping("/category/all")
    @Operation(summary = "获取所有分类", description = "获取所有分类（平铺结构）")
    public Result<List<CategoryVO>> getAllCategories() {
        List<CategoryVO> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }
}