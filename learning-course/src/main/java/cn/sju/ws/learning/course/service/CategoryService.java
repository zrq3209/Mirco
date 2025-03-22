package cn.sju.ws.learning.course.service;

import cn.sju.ws.learning.course.entity.Category;
import cn.sju.ws.learning.course.vo.CategoryVO;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {

    /**
     * 创建分类
     */
    Integer createCategory(Category category);

    /**
     * 更新分类
     */
    void updateCategory(Integer id, Category category);

    /**
     * 获取分类详情
     */
    CategoryVO getCategoryDetail(Integer id);

    /**
     * 获取所有分类（树形结构）
     */
    List<CategoryVO> getCategoryTree();

    /**
     * 获取所有分类（平铺结构）
     */
    List<CategoryVO> getAllCategories();

    /**
     * 删除分类
     */
    void deleteCategory(Integer id);

    /**
     * 获取课程的分类列表
     */
    List<CategoryVO> getCategoriesByCourseId(Long courseId);
}