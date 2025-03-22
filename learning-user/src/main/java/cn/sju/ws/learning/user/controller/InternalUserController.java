package cn.sju.ws.learning.user.controller;

import cn.sju.ws.learning.common.entity.User;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 内部用户控制器（供其他服务调用）
 */
@Tag(name = "内部用户服务", description = "供其他服务调用的内部接口")
@RestController
@RequestMapping("/user/internal")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @GetMapping("/username/{username}")
    @Operation(summary = "根据用户名获取用户", description = "供内部服务调用的接口")
    public Result<User> getUserByUsername(
            @Parameter(description = "用户名", required = true) @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return Result.success(user);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "供认证服务调用的注册接口")
    public Result<Long> registerUser(@Valid @RequestBody UserDTO userDTO) {
        Long id = userService.createUser(userDTO);
        return Result.success("注册成功", id);
    }
}