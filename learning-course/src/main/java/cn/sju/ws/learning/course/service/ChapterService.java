package cn.sju.ws.learning.course.service;

import cn.sju.ws.learning.course.dto.ChapterDTO;
import cn.sju.ws.learning.course.vo.ChapterVO;

import java.util.List;

/**
 * 章节服务接口
 */
public interface ChapterService {

    /**
     * 创建章节
     */
    Long createChapter(ChapterDTO chapterDTO, String userName);

    /**
     * 更新章节
     */
    void updateChapter(Long id, ChapterDTO chapterDTO, String userName);

    /**
     * 获取章节详情
     */
    ChapterVO getChapterDetail(Long id);

    /**
     * 获取课程的所有章节
     */
    List<ChapterVO> getChaptersByCourseId(Long courseId);

    /**
     * 删除章节
     */
    void deleteChapter(Long id, String userName);
}