package com.bookingsystem.enums;

/**
 * 预约状态枚举
 * 对应数据库中的status字段
 */
public enum ReservationStatusEnums {

    /**
     * 待审核 - 预约已提交但尚未审核
     */
    PENDING("pending", "待审核"),

    /**
     * 已批准 - 预约申请已通过审核
     */
    APPROVED("approved", "已批准"),

    /**
     * 已拒绝 - 预约申请被拒绝
     */
    REJECTED("rejected", "已拒绝"),

    /**
     * 已取消 - 用户取消了预约
     */
    CANCELLED("cancelled", "已取消"),

    /**
     * 已完成 - 预约已使用完毕
     */
    COMPLETED("completed", "已完成"),

    /**
     * 已占用 - 预约因超时未签到被占用
     */
    OCCUPIED("occupied", "已占用");

    /**
     * 状态编码，对应数据库中的值
     */
    private final String code;

    /**
     * 状态描述，用于前端显示
     */
    private final String description;

    /**
     * 构造函数
     *
     * @param code 状态编码
     * @param description 状态描述
     */
    ReservationStatusEnums(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取状态编码
     *
     * @return 状态编码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 获取状态描述
     *
     * @return 状态描述
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 根据状态编码获取对应的枚举值
     *
     * @param code 状态编码
     * @return 对应的枚举值，如果没有匹配则返回null
     */
    public static ReservationStatusEnums fromCode(String code) {
        for (ReservationStatusEnums status : ReservationStatusEnums.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断当前状态是否可以取消
     * 只有待审核和已批准的预约可以取消
     *
     * @return 是否可以取消
     */
    public boolean canCancel() {
        return this == PENDING || this == APPROVED;
    }

    /**
     * 判断当前状态是否为最终状态
     * 已拒绝、已取消和已完成为最终状态
     *
     * @return 是否为最终状态
     */
    public boolean isFinalStatus() {
        return this == REJECTED || this == CANCELLED || this == COMPLETED;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
