package com.lion.systemmgr.rbac.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * sys_角色操作关联表
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@TableName("sys_role_operation")
public class RoleOperation implements Serializable {

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
     * 菜单操作ID
     */
    @TableField("OPERATION_ID")
    private String operationId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 禁用标识
     */
    private Integer delstatus;

    public RoleOperation() {
    }

    public RoleOperation(String roleId, String menuId, String operationId) {
        this.roleId = roleId;
        this.menuId=menuId;
        this.operationId=operationId;
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

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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
        return "RoleOperation{" +
        ", id=" + id +
        ", menuId=" + menuId +
        ", operationId=" + operationId +
        ", roleId=" + roleId +
        ", delstatus=" + delstatus +
        "}";
    }
}
