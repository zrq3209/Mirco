package cn.sju.ws.learning.common.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Token视图对象
 */
@Data
@Builder
public class TokenVO {

    private String token;

    private String username;

    private String roleName;

    private Long expireTime;
}