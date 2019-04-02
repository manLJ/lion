package com.lion.logaudit.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.logaudit.domain.mapper.UserAccountLockRecordMapper;
import com.lion.logaudit.domain.model.UserAccountLockRecord;
import com.lion.systemmgr.auth.domain.service.HttpSessionLocal;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class UserAccountLockRecordService extends ServiceImpl<UserAccountLockRecordMapper, UserAccountLockRecord> {

    /**
     * 分页查询所有锁定明细
     *
     * @param page
     * @param userName
     * @param ip
     * @param startTime
     * @param endTime
     * @return 锁定明细
     */
    public IPage<UserAccountLockRecord> findAll(Page<UserAccountLockRecord> page, String userName, String ip, String startTime, String endTime) {
        QueryWrapper<UserAccountLockRecord> queryWrapper = new QueryWrapper<UserAccountLockRecord>()
                .like(StringUtils.isNotBlank(userName), "ACCOUNT", userName)
                .like(StringUtils.isNotBlank(ip), "LOGIN_IP", ip)
                .ge(StringUtils.isNotBlank(startTime), "LOCK_TIME", startTime)
                .le(StringUtils.isNotBlank(endTime), "LOCK_TIME", endTime)
                .orderByDesc("LOCK_TIME");
        return this.page(page, queryWrapper);
    }

    /**
     * 帐号连续登录失败次数超过 maxErrorTimes 次 ,记录日志
     *
     * @param userName      账号
     * @param requestRealIp IP
     * @param maxErrorTimes 错误次数
     */
    public void saveLock(String userName, String requestRealIp, int maxErrorTimes) {
        UserAccountLockRecord lockRecord = new UserAccountLockRecord();
        lockRecord.setAccount(userName);
        lockRecord.setLockTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        lockRecord.setLoginIp(requestRealIp);
        lockRecord.setLocker("system");
        lockRecord.setLockReason(String.format("帐号 %s 连续登录失败次数超过 %s 次，帐号锁定。", userName, maxErrorTimes));
        this.save(lockRecord);
    }

    /**
     * 解锁日志记录
     *
     * @param lockRecord
     */
    public void updateLockUser(UserAccountLockRecord lockRecord) {
        if (null == lockRecord) return;
        lockRecord.setUnLocker(HttpSessionLocal.getCurrentUser().getChinaName());
        lockRecord.setUnLockTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        this.updateById(lockRecord);
    }

}
