package com.lion.systemmgr.auth.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.core.exception.ApplicationException;
import com.lion.core.iputil.RequestRealIpUtils;
import com.lion.logaudit.domain.service.UserAccountLockRecordService;
import com.lion.systemmgr.auth.domain.mapper.UserAccountErrorLoginMapper;
import com.lion.systemmgr.auth.domain.model.UserAccountErrorLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAccountErrorLoginService extends ServiceImpl<UserAccountErrorLoginMapper, UserAccountErrorLogin> {

    @Resource
    private RequestRealIpUtils requestRealIpUtils;

    @Resource
    private UserAccountLockRecordService userAccountLockRecordService;

    @Value("${login.maxErrorTimes}")
    private int maxErrorTimes;

    /**
     * 用户密码错误次数记录
     *
     * @param request
     * @param userName 用户账号
     */
    public void errorRecord(HttpServletRequest request, String userName) {
        QueryWrapper<UserAccountErrorLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ACCOUNT", userName);
        UserAccountErrorLogin userAccount = this.getOne(queryWrapper);

        if (null == userAccount) {
            UserAccountErrorLogin userAccountErrorLogin = new UserAccountErrorLogin();
            userAccountErrorLogin.applyFor(userName, requestRealIpUtils.getRequestRealIp(request), 1);
            this.save(userAccountErrorLogin);
        } else {
            int errorCount = userAccount.getErrorTimes();
            if (errorCount < maxErrorTimes) {
                errorCount++;
                userAccount.applyFor(userName, requestRealIpUtils.getRequestRealIp(request), errorCount);
                this.saveOrUpdate(userAccount);
                if (errorCount == maxErrorTimes) {
                    userAccountLockRecordService.saveLock(userName, requestRealIpUtils.getRequestRealIp(request), maxErrorTimes);
                    throw new ApplicationException("validateUserToken-001", "密码错误达到5次，该用户已被封锁，请联系管理员！");
                }
            }
        }
    }

    /**
     * 验证登录用户错误次数
     *
     * @param userName
     * @return boolean
     */
    public Boolean verifyAccountErrors(String userName) {
        QueryWrapper<UserAccountErrorLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ACCOUNT", userName);
        UserAccountErrorLogin userAccount = this.getOne(queryWrapper);
        if (userAccount == null) {
            return false;
        } else if (userAccount.getErrorTimes() == maxErrorTimes) {
            return true;
        }
        return false;
    }

    /**
     * 根据账号，清空登录错误记录
     *
     * @param userName 账号
     */
    public void removeByAccount(String userName) {
        Map map = new HashMap();
        map.put("ACCOUNT", userName);
        this.removeByMap(map);
    }
}
