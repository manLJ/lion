package com.lion.systemmgr.rbac.domain.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * sys_角色表
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 禁用标识
     */
    private Integer delstatus;

    /**
     * 角色类型：企业：ENTERPRISE，运维管理：MANAGEMENT
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人名称
     */
    @TableField(value = "CREATOR", fill = FieldFill.INSERT)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private String createDate;

    /**
     * 修改人名称
     */
    @TableField(fill = FieldFill.UPDATE)
    private String modifier;

    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_DATE", fill = FieldFill.UPDATE)
    private String updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", delstatus=" + delstatus +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifier='" + modifier + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
