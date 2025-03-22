package cn.sju.ws.learning.course.service.impl;

import cn.sju.ws.learning.common.exception.BusinessException;
import cn.sju.ws.learning.common.util.AssertUtil;
import cn.sju.ws.learning.common.util.BeanCopyUtil;
import cn.sju.ws.learning.course.dto.ChapterDTO;
import cn.sju.ws.learning.course.entity.Chapter;
import cn.sju.ws.learning.course.entity.Course;
import cn.sju.ws.learning.course.mapper.ChapterMapper;
import cn.sju.ws.learning.course.mapper.CourseMapper;
import cn.sju.ws.learning.course.service.ChapterService;
import cn.sju.ws.learning.course.vo.ChapterVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 章节服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterMapper chapterMapper;
    private final CourseMapper courseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createChapter(ChapterDTO chapterDTO, String userName) {
        // 校验课程是否存在，以及当前用户是否有权限
        checkCourseOwner(chapterDTO.getCourseId(), userName);

        // 创建章节
        Chapter chapter = BeanCopyUtil.copyProperties(chapterDTO, Chapter.class);
        chapterMapper.insert(chapter);

        return chapter.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateChapter(Long id, ChapterDTO chapterDTO, String userName) {
        // 查询章节
        Chapter chapter = chapterMapper.selectById(id);
        if (chapter == null) {
            throw new BusinessException("章节不存在");
        }

        // 校验课程是否存在，以及当前用户是否有权限
        checkCourseOwner(chapter.getCourseId(), userName);

        // 更新章节信息
        BeanCopyUtil.copyProperties(chapterDTO, chapter);
        chapterMapper.updateById(chapter);
    }

    @Override
    public ChapterVO getChapterDetail(Long id) {
        // 查询章节
        Chapter chapter = chapterMapper.selectById(id);
        if (chapter == null) {
            throw new BusinessException("章节不存在");
        }

        // 转换为VO
        return BeanCopyUtil.copyProperties(chapter, ChapterVO.class);
    }

    @Override
    public List<ChapterVO> getChaptersByCourseId(Long courseId) {
        // 查询课程下的所有章节
        LambdaQueryWrapper<Chapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Chapter::getCourseId, courseId)
                .orderByAsc(Chapter::getCreateTime);

        List<Chapter> chapters = chapterMapper.selectList(queryWrapper);

        // 转换为VO
        return BeanCopyUtil.copyListProperties(chapters, ChapterVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChapter(Long id, String userName) {
        // 查询章节
        Chapter chapter = chapterMapper.selectById(id);
        if (chapter == null) {
            throw new BusinessException("章节不存在");
        }

        // 校验当前用户是否有权限
        checkCourseOwner(chapter.getCourseId(), userName);

        // 删除章节
        chapterMapper.deleteById(id);
    }

    /**
     * 检查用户是否是课程创建者
     */
    private void checkCourseOwner(Long courseId, String userName) {
        Course course = courseMapper.selectById(courseId);
        AssertUtil.notNull(course, "课程不存在");

        if (!course.getUserName().equals(userName)) {
            throw new BusinessException("您不是该课程的创建者，无法操作");
        }
    }
}