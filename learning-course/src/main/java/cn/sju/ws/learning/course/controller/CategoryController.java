package cn.sju.ws.learning.course.controller;

import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.course.entity.Category;
import cn.sju.ws.learning.course.service.CategoryService;
import cn.sju.ws.learning.course.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 */
@Tag(name = "分类管理", description = "分类管理相关接口")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "创建分类", description = "创建新分类")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Integer> createCategory(@Valid @RequestBody Category category) {
        Integer id = categoryService.createCategory(category);
        return Result.success("创建成功", id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新分类", description = "更新分类信息")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> updateCategory(
            @Parameter(description = "分类ID", required = true) @PathVariable Integer id,
            @Valid @RequestBody Category category) {
        categoryService.updateCategory(id, category);
        return Result.success("更新成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取分类详情", description = "获取分类详细信息")
    public Result<CategoryVO> getCategoryDetail(
            @Parameter(description = "分类ID", required = true) @PathVariable Integer id) {
        CategoryVO categoryDetail = categoryService.getCategoryDetail(id);
        return Result.success(categoryDetail);
    }

    @GetMapping("/tree")
    @Operation(summary = "获取分类树", description = "获取所有分类的树形结构")
    public Result<List<CategoryVO>> getCategoryTree() {
        List<CategoryVO> categoryTree = categoryService.getCategoryTree();
        return Result.success(categoryTree);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有分类", description = "获取所有分类（平铺结构）")
    public Result<List<CategoryVO>> getAllCategories() {
        List<CategoryVO> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类", description = "删除分类")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteCategory(
            @Parameter(description = "分类ID", required = true) @PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return Result.success("删除成功");
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "获取课程的分类列表", description = "获取指定课程的所有分类")
    public Result<List<CategoryVO>> getCategoriesByCourseId(
            @Parameter(description = "课程ID", required = true) @PathVariable Long courseId) {
        List<CategoryVO> categories = categoryService.getCategoriesByCourseId(courseId);
        return Result.success(categories);
    }
}