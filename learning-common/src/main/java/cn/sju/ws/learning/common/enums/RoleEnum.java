package cn.sju.ws.learning.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    STUDENT(1, "学生"),
    TEACHER(2, "教师"),
    ADMIN(3, "管理员");

    private final Integer id;
    private final String name;

    /**
     * 根据ID获取角色枚举
     */
    public static RoleEnum getById(Integer id) {
        for (RoleEnum role : values()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        return null;
    }

    /**
     * 根据名称获取角色枚举
     */
    public static RoleEnum getByName(String name) {
        for (RoleEnum role : values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }
}