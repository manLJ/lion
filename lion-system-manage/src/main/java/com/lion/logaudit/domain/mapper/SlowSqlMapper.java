package com.lion.logaudit.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.logaudit.domain.model.SlowSql;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lianhong
 * @since 2019/3/27
 */
public interface SlowSqlMapper extends BaseMapper<SlowSql> {

    List<SlowSql> listBy(@Param("beginTime") String beginTime,
                         @Param("endTime") String endTime);
}
