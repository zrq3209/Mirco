package cn.sju.ws.learning.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    CLOSED(2, "已关闭");

    private final Integer code;
    private final String desc;

    /**
     * 根据code获取订单状态枚举
     */
    public static OrderStatusEnum getByCode(Integer code) {
        for (OrderStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}