package com.lion.config.domain.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * conf_配置项表
 * </p>
 *
 * @author zlzhou
 * @since 2019-01-03
 */
@TableName("conf_config_item")
public class ConfigItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableField("id")
    private String id;

    /**
     * 配置标识
     */
    @TableField("config_id")
    private String configId;

    /**
     * 父类
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 配置项名称
     */
    @TableField("name")
    private String name;

    /**
     * 配置项别名
     */
    @TableField("alias")
    private String alias;

    /**
     * 配置项值
     */
    @TableField("value")
    private String value;

    /**
     * 状态：1，启用；0，禁用
     */
    @TableField("status")
    private String status;

    /**
     * 排序号
     */
    @TableField("sort")
    private BigDecimal sort;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createDate;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String modifier;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateDate;

    /**
     * 子项列表
     */
    @TableField(exist = false)
    private List childrenList;

    /**
     * 课程数量
     */
    @TableField(exist = false)
    private Integer courseNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
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

    public List getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List childrenList) {
        this.childrenList = childrenList;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public String toString() {
        return "ConfigItem{" +
                ", id=" + id +
                ", configId=" + configId +
                ", parentId=" + parentId +
                ", name=" + name +
                ", alias=" + alias +
                ", value=" + value +
                ", status=" + status +
                ", sort=" + sort +
                ", creator=" + creator +
                ", createDate=" + createDate +
                ", modifier=" + modifier +
                ", updateDate=" + updateDate +
                "}";
    }

    public void applyFor(ConfigItem editConfigItem) {
        this.parentId = editConfigItem.getParentId();
        this.name = editConfigItem.getName();
        this.alias = editConfigItem.getAlias();
        this.value = editConfigItem.getValue();
        this.status = editConfigItem.getStatus();
        this.sort = editConfigItem.getSort();
    }
}
