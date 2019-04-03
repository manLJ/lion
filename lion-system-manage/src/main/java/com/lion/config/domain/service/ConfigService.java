package com.lion.config.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.config.domain.mapper.ConfigMapper;
import com.lion.config.domain.model.Config;
import com.lion.config.domain.model.ConfigItem;
import com.lion.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * conf_配置表 服务类
 * </p>
 *
 * @author zlzhou
 * @since 2019-01-02
 */
@Service
public class ConfigService extends ServiceImpl<ConfigMapper, Config> {
    @Resource
    private ConfigItemService configItemService;

    public List findItemsNameByName(String name) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<Config>()
                .eq("name", name);
        return configItemService.findNameByConfigId(this.getOne(queryWrapper).getId());
    }

    /**
     * 查询配置列表
     */
    public List<Config> list() {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<Config>().orderByDesc("CREATE_DATE");
        return this.list(queryWrapper);
    }

    /**
     * 新增配置信息
     *
     * @param config 配置信息
     * @return 配置信息
     */
    public Config create(Config config) {
        if (StringUtils.isBlank(config.getName())) {
            throw new ApplicationException("createConfig-002", "请提供配置名称。");
        }
        if (!CollectionUtils.isEmpty(this.list(new QueryWrapper<Config>().eq("name", config.getName())))) {
            throw new ApplicationException("createConfig-003", "配置名称已存在。");
        }
        config.setStatus("1");
        this.save(config);
        return config;
    }

    /**
     * 修改配置信息
     *
     * @param id           配置ID
     * @param updateConfig 配置信息
     * @return 配置信息
     */
    public Config updateConfig(String id, Config updateConfig) {
        if (StringUtils.isBlank(updateConfig.getName())) {
            throw new ApplicationException("updateConfig-002", "请提供配置名称。");
        }
        if (StringUtils.isBlank(updateConfig.getStatus())) {
            throw new ApplicationException("updateConfig-003", "请提供配置状态。");
        }
        if (!CollectionUtils.isEmpty(this.list(new QueryWrapper<Config>().eq("name", updateConfig.getName()).ne("id", id)))) {
            throw new ApplicationException("updateConfig-004", "配置名称已存在。");
        }
        Config config = this.getById(id);
        config.applyFor(updateConfig);
        this.updateById(config);
        return config;
    }

    public List<ConfigItem> findItemListByParentName(String type, String name) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<Config>()
                .eq("name", type);
        String config_id = this.getOne(queryWrapper).getId();
        QueryWrapper<ConfigItem> configItemQueryWrapper = new QueryWrapper<ConfigItem>()
                .eq("config_id", config_id)
                .isNull("parent_id")
                .eq(StringUtils.isNotBlank(name), "name", name);
        ConfigItem configItem = configItemService.getOne(configItemQueryWrapper);
        return configItemService.findNameSonByParentId(configItem.getId());
    }

    //查询系列列表
    public List findSeriesList(String name) {
        QueryWrapper<Config> queryWrapper = new QueryWrapper<Config>()
                .eq("name", name);
        return configItemService.findSeriesByConfigId(this.getOne(queryWrapper).getId());
    }
}