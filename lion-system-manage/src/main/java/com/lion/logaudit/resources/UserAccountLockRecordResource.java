package com.lion.logaudit.resources;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.logaudit.domain.model.UserAccountLockRecord;
import com.lion.logaudit.domain.service.UserAccountLockRecordService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * sys_用户锁定明细
 */
@Api(tags = "UserAccountLockManage", value = "用户锁定明细管理")
@RestController
@RequestMapping("/log/userAccountLock")
public class UserAccountLockRecordResource {

    @Resource
    private UserAccountLockRecordService lockRecordService;

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
    @GetMapping("")
    public IPage<UserAccountLockRecord> list(Page<UserAccountLockRecord> page,
                                             @RequestParam("userName") String userName,
                                             @RequestParam("ip") String ip,
                                             @RequestParam("startTime") String startTime,
                                             @RequestParam("endTime") String endTime) {
        return lockRecordService.findAll(page, userName, ip, startTime, endTime);
    }

}
