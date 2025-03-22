package cn.sju.ws.learning.course.service.impl;

import cn.sju.ws.learning.common.exception.BusinessException;
import cn.sju.ws.learning.common.util.BeanCopyUtil;
import cn.sju.ws.learning.course.entity.Category;
import cn.sju.ws.learning.course.entity.CategoryCourse;
import cn.sju.ws.learning.course.mapper.CategoryCourseMapper;
import cn.sju.ws.learning.course.mapper.CategoryMapper;
import cn.sju.ws.learning.course.service.CategoryService;
import cn.sju.ws.learning.course.vo.CategoryVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分类服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryCourseMapper categoryCourseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createCategory(Category category) {
        // 检查父级分类是否存在
        if (category.getParentId() != 0) {
            Category parent = categoryMapper.selectById(category.getParentId());
            if (parent == null) {
                throw new BusinessException("父级分类不存在");
            }
        }

        // 创建分类
        categoryMapper.insert(category);
        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Integer id, Category category) {
        // 查询分类
        Category existCategory = categoryMapper.selectById(id);
        if (existCategory == null) {
            throw new BusinessException("分类不存在");
        }

        // 检查父级分类是否存在
        if (category.getParentId() != 0) {
            Category parent = categoryMapper.selectById(category.getParentId());
            if (parent == null) {
                throw new BusinessException("父级分类不存在");
            }

            // 不能将自己设为自己的子分类
            if (category.getParentId().equals(id)) {
                throw new BusinessException("不能将自己设为自己的子分类");
            }

            // 不能将自己设为自己子分类的子分类
            List<Integer> childrenIds = getChildrenIds(id);
            if (childrenIds.contains(category.getParentId())) {
                throw new BusinessException("不能将自己设为自己子分类的子分类");
            }
        }

        // 更新分类
        category.setId(id);
        categoryMapper.updateById(category);
    }

    @Override
    public CategoryVO getCategoryDetail(Integer id) {
        // 查询分类
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }

        // 转换为VO
        return BeanCopyUtil.copyProperties(category, CategoryVO.class);
    }

    @Override
    public List<CategoryVO> getCategoryTree() {
        // 查询所有分类
        List<Category> allCategories = categoryMapper.selectList(null);

        // 转换为VO
        List<CategoryVO> categoryVOs = BeanCopyUtil.copyListProperties(allCategories, CategoryVO.class);

        // 构建树形结构
        Map<Integer, List<CategoryVO>> parentMap = categoryVOs.stream()
                .collect(Collectors.groupingBy(CategoryVO::getParentId));

        // 设置子分类
        categoryVOs.forEach(categoryVO ->
                categoryVO.setChildren(parentMap.getOrDefault(categoryVO.getId(), new ArrayList<>())));

        // 只返回顶级分类
        return categoryVOs.stream()
                .filter(categoryVO -> categoryVO.getParentId() == 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        // 查询所有分类
        List<Category> allCategories = categoryMapper.selectList(null);

        // 转换为VO
        return BeanCopyUtil.copyListProperties(allCategories, CategoryVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Integer id) {
        // 查询分类
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }

        // 检查是否有子分类
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, id);
        long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException("该分类下有子分类，无法删除");
        }

        // 检查是否有关联的课程
        LambdaQueryWrapper<CategoryCourse> ccQueryWrapper = new LambdaQueryWrapper<>();
        ccQueryWrapper.eq(CategoryCourse::getCategoryId, id);
        long courseCount = categoryCourseMapper.selectCount(ccQueryWrapper);
        if (courseCount > 0) {
            throw new BusinessException("该分类下有关联的课程，无法删除");
        }

        // 删除分类
        categoryMapper.deleteById(id);
    }

    @Override
    public List<CategoryVO> getCategoriesByCourseId(Long courseId) {
        // 查询课程关联的分类ID
        LambdaQueryWrapper<CategoryCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryCourse::getCourseId, courseId);
        List<CategoryCourse> categoryCourses = categoryCourseMapper.selectList(queryWrapper);

        if (categoryCourses.isEmpty()) {
            return new ArrayList<>();
        }

        // 查询分类信息
        List<Integer> categoryIds = categoryCourses.stream()
                .map(CategoryCourse::getCategoryId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
        categoryQueryWrapper.in(Category::getId, categoryIds);
        List<Category> categories = categoryMapper.selectList(categoryQueryWrapper);

        // 转换为VO
        return BeanCopyUtil.copyListProperties(categories, CategoryVO.class);
    }

    /**
     * 获取指定分类的所有子分类ID（包括子分类的子分类）
     */
    private List<Integer> getChildrenIds(Integer categoryId) {
        List<Integer> result = new ArrayList<>();

        // 查询直接子分类
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, categoryId);
        List<Category> children = categoryMapper.selectList(queryWrapper);

        for (Category child : children) {
            result.add(child.getId());
            // 递归查询子分类的子分类
            result.addAll(getChildrenIds(child.getId()));
        }

        return result;
    }
}