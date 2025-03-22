package cn.sju.ws.learning.course.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程创建/更新DTO
 */
@Data
public class CourseDTO {

    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空")
    @Size(max = 100, message = "课程名称长度不能超过100")
    private String name;

    /**
     * 课程价格
     */
    @DecimalMin(value = "0.00", message = "课程价格不能为负数")
    private BigDecimal price;

    /**
     * 课程简介
     */
    @NotBlank(message = "课程简介不能为空")
    @Size(max = 200, message = "课程简介长度不能超过200")
    private String description;

    /**
     * 封面图片
     */
    @NotBlank(message = "封面图片不能为空")
    private String coverPicture;

    /**
     * 分类ID列表
     */
    @NotEmpty(message = "至少选择一个分类")
    private List<Integer> categoryIds;
}