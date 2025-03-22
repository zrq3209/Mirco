package cn.sju.ws.learning.auth.controller;

import cn.sju.ws.learning.auth.service.AuthService;
import cn.sju.ws.learning.common.entity.dto.LoginDTO;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.entity.vo.TokenVO;
import cn.sju.ws.learning.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 */
@Tag(name = "认证服务", description = "包括登录、注册等功能")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户名密码登录")
    public Result<TokenVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        TokenVO tokenVO = authService.login(loginDTO);
        return Result.success("登录成功", tokenVO);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册")
    public Result<String> register(@Valid @RequestBody UserDTO userDTO) {
        authService.register(userDTO);
        return Result.success("注册成功");
    }
}