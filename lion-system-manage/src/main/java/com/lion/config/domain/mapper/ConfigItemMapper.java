package com.lion.config.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.config.domain.model.ConfigItem;

import java.util.List;

/**
 * <p>
 * conf_配置项表 Mapper 接口
 * </p>
 *
 * @author zlzhou
 * @since 2019-01-02
 */
public interface ConfigItemMapper extends BaseMapper<ConfigItem> {

    List<ConfigItem> findItemsByParentName(String name);
}
