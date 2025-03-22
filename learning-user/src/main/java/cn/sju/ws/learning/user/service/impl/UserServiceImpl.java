package cn.sju.ws.learning.user.service.impl;

import cn.sju.ws.learning.common.entity.Role;
import cn.sju.ws.learning.common.entity.User;
import cn.sju.ws.learning.common.entity.dto.UserDTO;
import cn.sju.ws.learning.common.entity.vo.UserVO;
import cn.sju.ws.learning.common.enums.RoleEnum;
import cn.sju.ws.learning.common.exception.BusinessException;
import cn.sju.ws.learning.common.util.AssertUtil;
import cn.sju.ws.learning.common.util.BeanCopyUtil;
import cn.sju.ws.learning.common.util.PasswordUtil;
import cn.sju.ws.learning.user.dto.UserQueryDTO;
import cn.sju.ws.learning.user.dto.UserUpdateDTO;
import cn.sju.ws.learning.user.mapper.RoleMapper;
import cn.sju.ws.learning.user.mapper.UserMapper;
import cn.sju.ws.learning.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public UserVO getUserById(Long id) {
        User user = userMapper.selectById(id);
        AssertUtil.notNull(user, "用户不存在");

        UserVO userVO = BeanCopyUtil.copyProperties(user, UserVO.class);

        // 设置角色名称
        RoleEnum role = RoleEnum.getById(user.getRoleId());
        if (role != null) {
            userVO.setRoleName(role.getName());
        }

        return userVO;
    }

    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<UserVO> getUserPage(UserQueryDTO queryDTO) {
        Page<User> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<User> userPage = userMapper.selectUserPage(page, queryDTO.getUsername(),
                queryDTO.getFullName(), queryDTO.getRoleId());

        // 转换为VO
        return userPage.convert(user -> {
            UserVO userVO = BeanCopyUtil.copyProperties(user, UserVO.class);

            // 设置角色名称
            RoleEnum role = RoleEnum.getById(user.getRoleId());
            if (role != null) {
                userVO.setRoleName(role.getName());
            }

            return userVO;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserDTO userDTO) {
        // 检查用户名是否已存在
        User existUser = getUserByUsername(userDTO.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        // 检查角色ID是否存在
        Role role = roleMapper.selectById(userDTO.getRoleId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 创建用户
        User user = BeanCopyUtil.copyProperties(userDTO, User.class);

        // 密码加密
        user.setPassword(PasswordUtil.encrypt(userDTO.getPassword()));

        // 设置默认头像
        if (user.getProfilePicture() == null) {
            user.setProfilePicture("http://localhost:8080/dfs/profile-pictures/default.jpg");
        }

        userMapper.insert(user);
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = userMapper.selectById(id);
        AssertUtil.notNull(user, "用户不存在");

        // 更新用户信息
        if (updateDTO.getFullName() != null) {
            user.setFullName(updateDTO.getFullName());
        }
        if (updateDTO.getGender() != null) {
            user.setGender(updateDTO.getGender());
        }
        if (updateDTO.getEmailAddress() != null) {
            user.setEmailAddress(updateDTO.getEmailAddress());
        }
        if (updateDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(updateDTO.getPhoneNumber());
        }
        if (updateDTO.getProfilePicture() != null) {
            user.setProfilePicture(updateDTO.getProfilePicture());
        }

        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRole(Long id, Integer roleId) {
        User user = userMapper.selectById(id);
        AssertUtil.notNull(user, "用户不存在");

        // 检查角色ID是否存在
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 更新角色
        user.setRoleId(roleId);
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        User user = userMapper.selectById(id);
        AssertUtil.notNull(user, "用户不存在");

        userMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userMapper.selectById(id);
        AssertUtil.notNull(user, "用户不存在");

        // 验证旧密码
        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("旧密码不正确");
        }

        // 更新密码
        user.setPassword(PasswordUtil.encrypt(newPassword));
        userMapper.updateById(user);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectList(null);
    }
}