package cn.sju.ws.learning.common.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户数据传输对象
 */
@Data
public class UserDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码长度不能小于8位")
    private String password;

    @NotBlank(message = "姓名不能为空")
    @Size(max = 20, message = "姓名长度不能超过20个字符")
    private String fullName;

    private String gender;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String emailAddress;

    private String phoneNumber;

    private Integer roleId = 1; // 默认为学生角色
}