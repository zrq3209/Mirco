package cn.sju.ws.learning.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户更新参数
 */
@Data
public class UserUpdateDTO {

    /**
     * 姓名
     */
    @Size(max = 20, message = "姓名长度不能超过20个字符")
    private String fullName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 邮箱地址
     */
    @Email(message = "邮箱格式不正确")
    private String emailAddress;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 头像链接
     */
    private String profilePicture;
}