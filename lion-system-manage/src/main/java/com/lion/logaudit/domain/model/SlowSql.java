package com.lion.logaudit.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author Lianhong
 * @since 2019/3/27
 */
@TableName("mgnt_slowsql")
public class SlowSql implements Serializable {

    @TableField("ID")
    private String id;              //ID

    /**
     * 执行时间
     */
    @TableField("OPERATETIME")
    private String operateTime;         //用户账号

    /**
     * SQL
     */
    @TableField("SLOWSQL")
    private String slowSql;

    /**
     * 单位：毫秒
     */
    @TableField("DURATION")
    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getSlowSql() {
        return slowSql;
    }

    public void setSlowSql(String slowSql) {
        this.slowSql = slowSql;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SlowSql{" +
                "id='" + id + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", slowSql='" + slowSql + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
