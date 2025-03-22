package cn.sju.ws.learning.auth.service.impl;

import cn.sju.ws.learning.auth.feign.UserFeignClient;
import cn.sju.ws.learning.auth.service.AuthService;
import cn.sju.ws.learning.common.constant.SecurityConstant;
import cn.sju.ws.learning.common.entity.User;
import cn.sju.ws.learning.common.entity.dto.LoginDTO;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.entity.vo.TokenVO;
import cn.sju.ws.learning.common.enums.RoleEnum;
import cn.sju.ws.learning.common.exception.BusinessException;
import cn.sju.ws.learning.common.result.Result;
import cn.sju.ws.learning.common.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserFeignClient userFeignClient;

    @Override
    public TokenVO login(LoginDTO loginDTO) {
        // 进行认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取用户信息
        Result<User> userResult = userFeignClient.getUserByUsername(loginDTO.getUsername());
        if (userResult == null || userResult.getCode() != 200 || userResult.getData() == null) {
            throw new BusinessException("用户不存在");
        }

        User user = userResult.getData();
        RoleEnum role = RoleEnum.getById(user.getRoleId());
        String roleName = role != null ? role.getName() : "未知角色";

        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("roleId", user.getRoleId());
        claims.put("roleName", roleName);

        String token = jwtTokenProvider.generateToken(user.getUsername(), claims);
        Date expiration = jwtTokenProvider.getClaimsFromToken(token).getExpiration();

        // 返回结果
        return TokenVO.builder()
                .token(token)
                .username(user.getUsername())
                .roleName(roleName)
                .expireTime(expiration.getTime())
                .build();
    }

    @Override
    public void register(UserDTO userDTO) {
        // 检查用户名是否已存在
        Result<User> userResult = userFeignClient.getUserByUsername(userDTO.getUsername());
        if (userResult != null && userResult.getCode() == 200 && userResult.getData() != null) {
            throw new BusinessException("用户名已存在");
        }

        // 调用用户服务注册用户
        Result<Long> result = userFeignClient.registerUser(userDTO);
        if (result == null || result.getCode() != 200) {
            String errorMsg = result != null ? result.getMessage() : "注册失败";
            throw new BusinessException(errorMsg);
        }
    }
}