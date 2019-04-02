package com.lion.systemmgr.organization.domain.model;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum PositionEm implements IEnum {
    Primary("10001","初级"),
    Intermediate("10002","中级"),
    Advanced("10003","高级");

    private final String code;
    private final String desc;

    PositionEm(String code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Serializable getValue() {
        return this.code;
    }


    public String toString() {
        return this.code;
    }
}
