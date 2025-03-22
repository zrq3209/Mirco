package cn.sju.ws.learning.user.dto;

import cn.sju.ws.learning.common.entity.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends PageParam {

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 角色ID
     */
    private Integer roleId;
}