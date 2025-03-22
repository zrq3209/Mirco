package cn.sju.ws.learning.course.entity;

import cn.sju.ws.learning.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 章节实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("chapter")
public class Chapter extends BaseEntity {

    /**
     * 章节ID
     */
    @TableId(type = IdType.AUTO)
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
}