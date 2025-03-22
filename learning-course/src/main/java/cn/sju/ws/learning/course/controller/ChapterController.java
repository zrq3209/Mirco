package cn.sju.ws.learning.course.controller;

import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.course.dto.ChapterDTO;
import cn.sju.ws.learning.course.service.ChapterService;
import cn.sju.ws.learning.course.vo.ChapterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 章节控制器
 */
@Tag(name = "章节管理", description = "章节管理相关接口")
@RestController
@RequestMapping("/api/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    @Operation(summary = "创建章节", description = "创建新章节")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Result<Long> createChapter(@Valid @RequestBody ChapterDTO chapterDTO) {
        String userName = getCurrentUserName();
        Long id = chapterService.createChapter(chapterDTO, userName);
        return Result.success("创建成功", id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新章节", description = "更新章节信息")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Result<String> updateChapter(
            @Parameter(description = "章节ID", required = true) @PathVariable Long id,
            @Valid @RequestBody ChapterDTO chapterDTO) {
        String userName = getCurrentUserName();
        chapterService.updateChapter(id, chapterDTO, userName);
        return Result.success("更新成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取章节详情", description = "获取章节详细信息")
    public Result<ChapterVO> getChapterDetail(
            @Parameter(description = "章节ID", required = true) @PathVariable Long id) {
        ChapterVO chapterDetail = chapterService.getChapterDetail(id);
        return Result.success(chapterDetail);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "获取课程的章节列表", description = "获取指定课程的所有章节")
    public Result<List<ChapterVO>> getChaptersByCourseId(
            @Parameter(description = "课程ID", required = true) @PathVariable Long courseId) {
        List<ChapterVO> chapters = chapterService.getChaptersByCourseId(courseId);
        return Result.success(chapters);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除章节", description = "删除章节")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public Result<String> deleteChapter(
            @Parameter(description = "章节ID", required = true) @PathVariable Long id) {
        String userName = getCurrentUserName();
        chapterService.deleteChapter(id, userName);
        return Result.success("删除成功");
    }

    /**
     * 获取当前登录用户名
     */
    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}