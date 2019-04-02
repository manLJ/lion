package com.lion.systemmgr.metadata.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * sys_菜单操作管理表
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@TableName("sys_menu_operation")
public class MenuOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 操作编码
     */
    private String code;

    /**
     * 菜单ID
     */
    @TableField("MENU_ID")
    private String menuId;

    /**
     * 操作名称
     */
    private String name;

    /**
     * 禁用标识
     */
    private Integer delstatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    @Override
    public String toString() {
        return "MenuOperation{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", menuId='" + menuId + '\'' +
                ", name='" + name + '\'' +
                ", delstatus=" + delstatus +
                '}';
    }
}
