package cn.sju.ws.learning.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Bean拷贝工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanCopyUtil {

    // ------------------------- 新增方法：直接复制到已存在的目标对象 -------------------------
    /**
     * 复制属性到已存在的目标对象（源对象 → 目标对象）
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanUtils.copyProperties(source, target);
    }

    // ------------------------- 原有方法保持不变 -------------------------
    /**
     * 单个对象属性拷贝（通过Class创建目标对象）
     */
    public static <T, R> R copyProperties(T source, Class<R> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            R target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Bean拷贝异常", e);
        }
    }

    /**
     * 单个对象属性拷贝（通过Supplier创建目标对象）
     */
    public static <T, R> R copyProperties(T source, Supplier<R> targetSupplier) {
        if (source == null) {
            return null;
        }
        R target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 列表对象属性拷贝（通过Class创建目标对象）
     */
    public static <T, R> List<R> copyListProperties(List<T> sourceList, Class<R> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }
        List<R> targetList = new ArrayList<>(sourceList.size());
        for (T source : sourceList) {
            targetList.add(copyProperties(source, targetClass));
        }
        return targetList;
    }

    /**
     * 列表对象属性拷贝（通过Supplier创建目标对象）
     */
    public static <T, R> List<R> copyListProperties(List<T> sourceList, Supplier<R> targetSupplier) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }
        List<R> targetList = new ArrayList<>(sourceList.size());
        for (T source : sourceList) {
            targetList.add(copyProperties(source, targetSupplier));
        }
        return targetList;
    }
}