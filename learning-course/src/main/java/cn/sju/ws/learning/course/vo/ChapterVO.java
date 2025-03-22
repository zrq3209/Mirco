package cn.sju.ws.learning.course.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 章节视图对象
 */
@Data
public class ChapterVO {

    /**
     * 章节ID
     */
    private Long id;

    /**
     * 章节标题
     */
    private String title;

    /**
     * 内容类型：video-视频，text-文字
     */
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
    private Long courseId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}