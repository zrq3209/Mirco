package cn.sju.ws.learning.auth.feign;

import cn.sju.ws.learning.common.entity.User;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务Feign客户端
 */
@FeignClient(name = "learning-user", path = "/user/internal")
public interface UserFeignClient {

    /**
     * 根据用户名获取用户信息
     */
    @GetMapping("/username/{username}")
    Result<User> getUserByUsername(@PathVariable("username") String username);

    /**
     * 注册用户
     */
    @PostMapping("/register")
    Result<Long> registerUser(@RequestBody UserDTO userDTO);
}