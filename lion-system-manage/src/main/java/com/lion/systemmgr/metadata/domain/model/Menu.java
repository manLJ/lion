package com.lion.systemmgr.metadata.domain.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * sys_菜单表
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@TableName("sys_menu")
public class Menu implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 父级ID
     */
    @TableField("PARENT_ID")
    private String parentId;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 层级
     */
    private String level;

    /**
     * 禁用标识
     */
    private Integer delstatus;


    @TableField("SORT_INDEX")
    private Integer sortIndex;

    /**
     * 创建人名称
     */
    @TableField(value = "CREATOR",fill = FieldFill.INSERT)
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

    @TableField(exist = false)
    private List<Menu> items;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
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
        return "Menu{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", delstatus=" + delstatus +
                ", sortIndex=" + sortIndex +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifier='" + modifier + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", items=" + items +
                '}';
    }

    public void setItems(List<Menu> items) {
        this.items = items;
    }

    public List<Menu> getItems() {
        return items;
    }
}
