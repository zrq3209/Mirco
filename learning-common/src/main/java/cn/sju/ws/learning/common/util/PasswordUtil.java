package cn.sju.ws.learning.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordUtil {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 密码加密
     */
    public static String encrypt(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    /**
     * 密码匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }
}