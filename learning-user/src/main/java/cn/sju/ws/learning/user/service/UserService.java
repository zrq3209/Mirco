package cn.sju.ws.learning.user.service;

import cn.sju.ws.learning.common.entity.Role;
import cn.sju.ws.learning.common.entity.User;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.entity.vo.UserVO;
import cn.sju.ws.learning.user.dto.UserQueryDTO;
import cn.sju.ws.learning.user.dto.UserUpdateDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 根据ID获取用户信息
     */
    UserVO getUserById(Long id);

    /**
     * 根据用户名获取用户信息
     */
    User getUserByUsername(String username);

    /**
     * 分页查询用户列表
     */
    IPage<UserVO> getUserPage(UserQueryDTO queryDTO);

    /**
     * 创建用户
     */
    Long createUser(UserDTO userDTO);

    /**
     * 更新用户信息
     */
    void updateUser(Long id, UserUpdateDTO updateDTO);

    /**
     * 修改用户角色
     */
    void updateUserRole(Long id, Integer roleId);

    /**
     * 删除用户
     */
    void deleteUser(Long id);

    /**
     * 修改密码
     */
    void updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 获取所有角色列表
     */
    List<Role> getAllRoles();
}