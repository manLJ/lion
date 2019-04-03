package com.lion.config.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.config.domain.mapper.ConfigItemMapper;
import com.lion.config.domain.model.ConfigItem;
import com.lion.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * conf_配置项表 服务类
 * </p>
 *
 * @author zlzhou
 * @since 2019-01-02
 */
@Service
public class ConfigItemService extends ServiceImpl<ConfigItemMapper, ConfigItem> {
    @Autowired
    private ConfigItemMapper itemMapper;

    public List<ConfigItem> findNameByConfigId(String config_id) {
        QueryWrapper<ConfigItem> Wrapper = new QueryWrapper<ConfigItem>()
                .select("id", "name")
                .eq(StringUtils.isNotBlank(config_id), "config_id", config_id)
                .eq("status", 1)
                .isNull("parent_id")
                .orderByAsc("sort");
        return this.list(Wrapper);
    }

    /**
     * 查询指定配置项第一节点子类
     */
    public IPage<ConfigItem> findAllByConfigIdAndParentIdIsNull(Page<ConfigItem> page, String configId) {
        QueryWrapper<ConfigItem> queryWrapper = new QueryWrapper<ConfigItem>()
                .eq("config_id", configId)
                .isNull("parent_id")
                .orderByDesc("sort");
        return this.page(page, queryWrapper);
    }

    /**
     * 查询指定配置项第二节点子类
     *
     * @param page
     * @param configId 配置项Id
     * @param parentId 父类Id
     * @return 配置项第二节点子类
     */
    public IPage<ConfigItem> findAllByConfigIdAndParentIdIsExists(Page<ConfigItem> page, String configId, String parentId) {
        QueryWrapper<ConfigItem> queryWrapper = new QueryWrapper<ConfigItem>()
                .eq("config_id", configId)
                .eq("parent_id", parentId)
                .orderByDesc("sort");
        return this.page(page, queryWrapper);
    }

    /**
     * 新增子类项
     *
     * @param configId   配置项Id
     * @param parentId   父类Id
     * @param configItem 子类项
     * @return 子类项
     */
    public ConfigItem createConfigItem(String configId, String parentId, ConfigItem configItem) {
        if (StringUtils.isBlank(configItem.getName())) {
            throw new ApplicationException("createConfigItem-003", "请提供配置名称。");
        }
        if (!CollectionUtils.isEmpty(this.list(new QueryWrapper<ConfigItem>().eq("name", configItem.getName()).eq("config_id", configId).eq(StringUtils.isNotBlank(parentId) && !"null".equals(parentId), "parent_id", parentId)))) {
            throw new ApplicationException("createConfigItem-004", "配置名称已存在。");
        }
        configItem.setConfigId(configId);
        if (StringUtils.isNotBlank(parentId) && !"null".equals(parentId)) configItem.setParentId(parentId);
        configItem.setStatus("1");
        this.save(configItem);
        return configItem;
    }


    /**
     * 修改子类项
     *
     * @param id             子类项Id
     * @param editConfigItem 子类项
     * @return 子类项
     */
    public ConfigItem updateConfigItem(String id, ConfigItem editConfigItem) {
        if (StringUtils.isBlank(editConfigItem.getName())) {
            throw new ApplicationException("updateConfigItem-003", "请提供配置名称。");
        }
        if (!CollectionUtils.isEmpty(this.list(new QueryWrapper<ConfigItem>().eq("name", editConfigItem.getName()).eq("config_id", editConfigItem.getConfigId()).eq(StringUtils.isNotBlank(editConfigItem.getParentId()), "parent_id", editConfigItem.getParentId()).ne("id", id)))) {
            throw new ApplicationException("updateConfigItem-004", "配置名称已存在。");
        }
        ConfigItem configItem = this.getById(id);
        configItem.applyFor(editConfigItem);
        this.updateById(configItem);
        return configItem;
    }

    public List<ConfigItem> findNameSonByParentId(String id) {
        QueryWrapper<ConfigItem> queryWrapper = new QueryWrapper<ConfigItem>()
                .select("id", "name")
                .eq("status", 1)
                .eq("parent_id", id)
                .orderByAsc("sort");
        return this.list(queryWrapper);
    }

    /**
     * 根据父节点名，查询子节点
     *
     * @param name 名
     * @return List
     */
    public List<ConfigItem> findItemsByParentName(String name) {
        return itemMapper.findItemsByParentName(name);
    }


    public List<ConfigItem> findSeriesByConfigId(String config_id) {
        QueryWrapper<ConfigItem> Wrapper = new QueryWrapper<ConfigItem>()
                .select("id", "name")
                .eq(StringUtils.isNotBlank(config_id), "config_id", config_id)
                .eq("status", 1)
                .isNotNull("parent_id")
                .orderByAsc("sort");
        return this.list(Wrapper);
    }
}
