package com.lion.logaudit.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lion.core.page.PageWrapper;
import com.lion.logaudit.domain.mapper.SlowSqlMapper;
import com.lion.logaudit.domain.model.SlowSql;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Lianhong
 * @since 2019/3/27
 */
@Service
public class SlowSqlService extends ServiceImpl<SlowSqlMapper, SlowSql> {

    @Resource
    private SlowSqlMapper slowSqlMapper;

    public IPage list(Page<SlowSql> page, String beginTime, String endTime) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        return PageWrapper.wrapper(page, new PageInfo<>(slowSqlMapper.listBy(beginTime, endTime)));
    }
}
