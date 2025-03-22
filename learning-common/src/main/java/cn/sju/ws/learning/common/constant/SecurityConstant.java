package cn.sju.ws.learning.common.constant;

/**
 * 安全相关常量
 */
public interface SecurityConstant {
    /**
     * JWT密钥
     * 注意：实际生产环境中，应该从配置中心获取或使用更复杂的密钥管理方式
     */
    String JWT_SECRET = "learning_jwt_secret_key_please_change_in_production";

    /**
     * Token过期时间（毫秒）- 24小时
     */
    long TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;

    /**
     * Token请求头名称
     */
    String TOKEN_HEADER = "Authorization";

    /**
     * Token前缀
     */
    String TOKEN_PREFIX = "Bearer ";
}