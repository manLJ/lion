package com.lion.logaudit.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("sys_user_account_lock_record")
public class UserAccountLockRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("ID")
    private String id;              //ID

    /**
     * 用户账号
     */
    @TableField("ACCOUNT")
    private String account;         //用户账号

    /**
     * 登录IP
     */
    @TableField("LOGIN_IP")
    private String loginIp;

    /**
     * 锁定时间
     */
    @TableField("LOCK_TIME")
    private String lockTime;

    /**
     * 锁定原因
     */
    @TableField("LOCK_REASON")
    private String lockReason;

    /**
     * 解锁时间
     */
    @TableField("UN_LOCK_TIME")
    private String unLockTime;

    /**
     * 解锁原因
     */
    @TableField("UN_LOCK_REASON")
    private String unLockReason;

    /**
     * 解锁人
     */
    @TableField("UN_LOCKER")
    private String unLocker;

    /**
     * 锁定人
     */
    @TableField("LOCKER")
    private String locker;


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

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public String getUnLockTime() {
        return unLockTime;
    }

    public void setUnLockTime(String unLockTime) {
        this.unLockTime = unLockTime;
    }

    public String getUnLockReason() {
        return unLockReason;
    }

    public void setUnLockReason(String unLockReason) {
        this.unLockReason = unLockReason;
    }

    public String getUnLocker() {
        return unLocker;
    }

    public void setUnLocker(String unLocker) {
        this.unLocker = unLocker;
    }

    public String getLocker() {
        return locker;
    }

    public void setLocker(String locker) {
        this.locker = locker;
    }
}
