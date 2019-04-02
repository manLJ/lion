package com.lion.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;

public class PageWrapper {
    public static IPage wrapper (IPage page, PageInfo pageInfo) {
        return page.setRecords(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }
}
