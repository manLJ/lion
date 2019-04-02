package com.lion.logaudit.resources;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.logaudit.domain.model.SlowSql;
import com.lion.logaudit.domain.service.SlowSqlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Lianhong
 * @since 2019/3/27
 */
@RestController
@RequestMapping("/system/slowSql")
public class SlowSqlResource {

    @Resource
    private SlowSqlService slowSqlService;

    @GetMapping
    public IPage list(Page<SlowSql> page,
                      @RequestParam(value = "beginTime", required = false) String beginTime,
                      @RequestParam(value = "endTime", required = false) String endTime) {
        return slowSqlService.list(page, beginTime, endTime);
    }
}
