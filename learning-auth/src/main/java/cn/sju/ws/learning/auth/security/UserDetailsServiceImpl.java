package cn.sju.ws.learning.auth.security;

import cn.sju.ws.learning.auth.feign.UserFeignClient;
import cn.sju.ws.learning.common.entity.User;
import cn.sju.ws.learning.common.enums.RoleEnum;
import cn.sju.ws.learning.common.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 用户详情服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Result<User> result = userFeignClient.getUserByUsername(username);
            if (result != null && result.getCode() == 200 && result.getData() != null) {
                User user = result.getData();

                // 获取角色
                RoleEnum role = RoleEnum.getById(user.getRoleId());
                String roleName = role != null ? "ROLE_" + role.getName() : "ROLE_USER";

                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority(roleName)))
                        .build();
            } else {
                log.error("用户不存在: {}", username);
                throw new UsernameNotFoundException("用户不存在");
            }
        } catch (Exception e) {
            log.error("获取用户信息异常: {}", e.getMessage());
            throw new UsernameNotFoundException("获取用户信息异常");
        }
    }
}