package com.lion.systemmgr.organization.domain.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lion.systemmgr.rbac.domain.model.OperationPrivilege;
import com.lion.systemmgr.rbac.domain.model.VisitPrivilege;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * sys_用户表
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    /**
     * 登录账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 中文名称
     */
    @TableField("china_name")
    private String chinaName;

    /**
     * 中文名称
     */
    @TableField("head_photo")
    private String headPhoto;

    /**
     * 密钥
     */
    private String token;

    /**
     * 禁用标识:0,禁用;1,启用
     */
    private Integer delstatus;

    /**
     * 最后一次登录的IP
     */
    private String ip;

    /**
     * 最后一次登录的时间
     */
    @TableField(value = "login_time")
    private String loginTime;

    /**
     * 手机号码
     */
    private String phone;

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

    @TableField(exist = false)
    private List<VisitPrivilege> visitPrivileges;
    @TableField(exist = false)
    private List<OperationPrivilege> operationPrivileges;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setVisitPrivileges(List<VisitPrivilege> visitPrivileges) {
        this.visitPrivileges = visitPrivileges;
    }

    public List<VisitPrivilege> getVisitPrivileges() {
        return visitPrivileges;
    }

    public void setOperationPrivileges(List<OperationPrivilege> operationPrivileges) {
        this.operationPrivileges = operationPrivileges;
    }

    public List<OperationPrivilege> getOperationPrivileges() {
        return operationPrivileges;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", chinaName='" + chinaName + '\'' +
                ", headPhoto='" + headPhoto + '\'' +
                ", token='" + token + '\'' +
                ", delstatus=" + delstatus +
                ", ip='" + ip + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", phone='" + phone + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifier='" + modifier + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", visitPrivileges=" + visitPrivileges +
                ", operationPrivileges=" + operationPrivileges +
                '}';
    }

    public void applyFor(String socialCreditCode, String enterpriseName) {
        this.account = socialCreditCode.substring(socialCreditCode.length() - 8, socialCreditCode.length());
        this.chinaName = enterpriseName;
        this.delstatus = 0;
    }

    public void applyForUpdate(String account, String chinaName, String phone, Integer delstatus) {
        this.account = account;
        this.chinaName = chinaName;
        this.phone = phone;
        this.delstatus = delstatus;

    }
}
