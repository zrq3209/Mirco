package cn.sju.ws.learning.user.mapper;

import cn.sju.ws.learning.common.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户信息
     */
    IPage<User> selectUserPage(Page<User> page, @Param("username") String username,
                               @Param("fullName") String fullName, @Param("roleId") Integer roleId);
}