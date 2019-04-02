package com.lion.systemmgr.auth.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.joda.time.DateTime;

import java.io.Serializable;

@TableName("sys_user_account_error_login")
public class UserAccountErrorLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ID")
    private String id;

    /**
     * 账户名
     */
    @TableField("ACCOUNT")
    private String account;

    /**
     * 最后一次登录的IP
     */
    @TableField("IP")
    private String ip;

    /**
     * 上一次登录错误的时间
     */
    @TableField("ERROR_DATE")
    private String errorDate;

    /**
     * 错误次数
     */
    @TableField("ERROR_TIMES")
    private Integer errorTimes;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(String errorDate) {
        this.errorDate = errorDate;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
    }

    public void applyFor(String userName, String ip, int errorTimes) {
        this.account = userName;
        this.errorTimes = errorTimes;
        this.ip = ip;
        this.errorDate = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }

}
