package com.lion.systemmgr.rbac.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * sys_用户角色关联表
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@TableName("sys_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 禁用标识
     */
    private Integer delstatus;

    public UserRole() {
    }

    public UserRole(String userId, String roleId, Integer delstatus) {
        this.userId = userId;
        this.roleId=roleId;
        this.delstatus = delstatus;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    @Override
    public String toString() {
        return "UserRole{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", userId=" + userId +
        ", delstatus=" + delstatus +
        "}";
    }
}
