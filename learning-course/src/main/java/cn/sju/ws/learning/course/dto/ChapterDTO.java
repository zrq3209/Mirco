package cn.sju.ws.learning.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 章节创建/更新DTO
 */
@Data
public class ChapterDTO {

    /**
     * 章节标题
     */
    @NotBlank(message = "章节标题不能为空")
    @Size(max = 100, message = "章节标题长度不能超过100")
    private String title;

    /**
     * 内容类型：video-视频，text-文字
     */
    @NotBlank(message = "内容类型不能为空")
    private String type;

    /**
     * 视频链接
     */
    private String videoUrl;

    /**
     * 视频时长
     */
    private String videoTime;

    /**
     * 文字内容
     */
    private String textContent;

    /**
     * 所属课程ID
     */
    @NotNull(message = "课程ID不能为空")
    private Long courseId;
}