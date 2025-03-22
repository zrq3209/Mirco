package cn.sju.ws.learning.user.controller;

import cn.sju.ws.learning.common.entity.Role;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.entity.vo.UserVO;
import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.user.dto.UserQueryDTO;
import cn.sju.ws.learning.user.dto.UserUpdateDTO;
import cn.sju.ws.learning.user.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@Tag(name = "用户服务", description = "用户管理相关接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户详细信息")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public Result<UserVO> getUserById(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        UserVO userVO = userService.getUserById(id);
        return Result.success(userVO);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询用户", description = "分页查询用户列表")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<UserVO>> getUserPage(@Valid UserQueryDTO queryDTO) {
        IPage<UserVO> page = userService.getUserPage(queryDTO);
        return Result.success(page);
    }

    @PostMapping
    @Operation(summary = "创建用户", description = "创建新用户")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> createUser(@Valid @RequestBody UserDTO userDTO) {
        Long id = userService.createUser(userDTO);
        return Result.success("创建成功", id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户信息", description = "更新用户基本信息")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public Result<String> updateUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO updateDTO) {
        userService.updateUser(id, updateDTO);
        return Result.success("更新成功");
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "修改用户角色", description = "修改用户角色")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> updateUserRole(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id,
            @Parameter(description = "角色ID", required = true) @RequestParam Integer roleId) {
        userService.updateUserRole(id, roleId);
        return Result.success("修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "删除用户")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "修改密码", description = "修改用户密码")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public Result<String> updatePassword(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id,
            @Parameter(description = "旧密码", required = true) @RequestParam String oldPassword,
            @Parameter(description = "新密码", required = true) @RequestParam String newPassword) {
        userService.updatePassword(id, oldPassword, newPassword);
        return Result.success("修改成功");
    }

    @GetMapping("/roles")
    @Operation(summary = "获取所有角色", description = "获取系统中所有角色列表")
    public Result<List<Role>> getAllRoles() {
        List<Role> roles = userService.getAllRoles();
        return Result.success(roles);
    }
}