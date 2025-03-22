package cn.sju.ws.learning.common.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户视图对象
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private String fullName;

    private String gender;

    private Integer roleId;

    private String roleName;

    private String emailAddress;

    private String phoneNumber;

    private String profilePicture;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}