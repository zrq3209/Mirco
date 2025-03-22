package cn.sju.ws.learning.auth.service;

import cn.sju.ws.learning.common.entity.dto.LoginDTO;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.entity.vo.TokenVO;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     */
    TokenVO login(LoginDTO loginDTO);

    /**
     * 用户注册
     */
    void register(UserDTO userDTO);
}