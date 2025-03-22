package cn.sju.ws.learning.common.util;

import cn.sju.ws.learning.common.exception.BusinessException;
import cn.sju.ws.learning.common.result.ResultCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 断言工具类
 */
public class AssertUtil {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(boolean expression, ResultCode resultCode) {
        if (!expression) {
            throw new BusinessException(resultCode);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new BusinessException(message);
        }
    }

    public static void isFalse(boolean expression, ResultCode resultCode) {
        if (expression) {
            throw new BusinessException(resultCode);
        }
    }

    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(Object object, ResultCode resultCode) {
        if (Objects.isNull(object)) {
            throw new BusinessException(resultCode);
        }
    }

    public static void notEmpty(String str, String message) {
        if (!StringUtils.hasText(str)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(String str, ResultCode resultCode) {
        if (!StringUtils.hasText(str)) {
            throw new BusinessException(resultCode);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, ResultCode resultCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(resultCode);
        }
    }
}