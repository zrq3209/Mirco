package cn.sju.ws.learning.common.entity;

import cn.sju.ws.learning.common.constant.CommonConstant;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 分页请求参数
 */
@Data
public class PageParam {
    /**
     * 页码
     */
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum = CommonConstant.DEFAULT_PAGE_NUMBER;

    /**
     * 每页数量
     */
    @Min(value = 1, message = "每页条数不能小于1")
    @Max(value = CommonConstant.MAX_PAGE_SIZE, message = "每页条数不能超过" + CommonConstant.MAX_PAGE_SIZE)
    private Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
}