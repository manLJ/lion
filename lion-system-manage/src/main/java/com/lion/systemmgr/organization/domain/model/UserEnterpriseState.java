package com.lion.systemmgr.organization.domain.model;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * 企业状态:0,禁用;1,启用
 */
public enum UserEnterpriseState implements IEnum {

    FORBIDDEN("0", "禁用"),
    ENABLED("1", "启用状态");

    private final String code;
    private final String dec;

    UserEnterpriseState(String code, String dec) {
        this.code = code;
        this.dec = dec;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public Serializable getValue() {
        return this.code;
    }

    public static UserEnterpriseState statusOf(String code) {
        if (UserEnterpriseState.ENABLED.getValue().equals(code)) return UserEnterpriseState.ENABLED;
        if (UserEnterpriseState.FORBIDDEN.getValue().equals(code)) return UserEnterpriseState.ENABLED;
        return valueOf(code);
    }

}
