package com.lion.config.domain.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * conf_配置表
 * </p>
 *
 * @author zlzhou
 * @since 2019-01-03
 */
@TableName("conf_config")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableField("id")
    private String id;

    /**
     * 配置名称
     */
    @TableField("name")
    private String name;

    /**
     * 配置别名
     */
    @TableField("alias")
    private String alias;

    /**
     * 配置描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态：1，启用；0，禁用
     */
    @TableField("status")
    private String status;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private String createDate;

    /**
     * 修改人
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Config{" +
                ", id=" + id +
                ", name=" + name +
                ", alias=" + alias +
                ", description=" + description +
                ", status=" + status +
                ", creator=" + creator +
                ", createDate=" + createDate +
                ", modifier=" + modifier +
                ", updateDate=" + updateDate +
                "}";
    }

    public void applyFor(Config updateConfig) {
        this.status = updateConfig.getStatus();
        this.name = updateConfig.getName();
        this.alias = updateConfig.getAlias();
        this.description = updateConfig.getDescription();
    }
}
