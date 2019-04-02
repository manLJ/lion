package com.lion.systemmgr.rbac.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * sys_角色菜单关联表
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 菜单ID
     */
    @TableField("MENU_ID")
    private String menuId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 禁用标识
     */
    private Integer delstatus;

    public RoleMenu() {
    }

    public RoleMenu(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
                ", id=" + id +
                ", menuId=" + menuId +
                ", roleId=" + roleId +
                ", delstatus=" + delstatus +
                "}";
    }
}
