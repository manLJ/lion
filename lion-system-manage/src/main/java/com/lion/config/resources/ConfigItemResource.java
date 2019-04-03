package com.lion.config.resources;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.config.domain.model.ConfigItem;
import com.lion.config.domain.service.ConfigItemService;
import com.lion.config.domain.service.ConfigService;
import com.lion.core.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * conf_配置项表 前端控制器
 * </p>
 *
 * @author zlzhou
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/system/config/configItem")
public class ConfigItemResource {

    @Autowired
    private ConfigItemService itemService;

    @Resource
    private ConfigService configService;

    /**
     * 查询指定配置项第一节点子类
     *
     * @param configId 配置项Id
     * @return 配置项第一节点子类
     */
    @GetMapping("/{configId}")
    public IPage<ConfigItem> list(Page<ConfigItem> page, @PathVariable("configId") String configId) {
        return itemService.findAllByConfigIdAndParentIdIsNull(page, configId);
    }

    /**
     * 查询指定配置项第二节点子类
     *
     * @param configId 配置项Id
     * @param parentId 父类Id
     * @return 配置项第二节点子类
     */
    @GetMapping("/{configId}/{parentId}")
    public IPage<ConfigItem> listSecond(Page<ConfigItem> page, @PathVariable("configId") String configId, @PathVariable("parentId") String parentId) {
        return itemService.findAllByConfigIdAndParentIdIsExists(page, configId, parentId);
    }

    /**
     * 新增子类项
     *
     * @param configId 配置项Id
     * @param parentId 父类Id
     * @param request  子类项
     * @return 子类项
     */
    @PostMapping("/{configId}/{parentId}")
    public ConfigItem createConfigItem(@PathVariable("configId") String configId, @PathVariable("parentId") String parentId, @RequestBody ConfigItem request) {
        if (null == request) {
            throw new ApplicationException("createConfigItem-001", "请提供配置子类信息。");
        }
        if (null == configService.getById(configId)) {
            throw new ApplicationException("createConfigItem-002", "配置大项不存在。");
        }
        return itemService.createConfigItem(configId, parentId, request);
    }

    /**
     * 修改子类项
     *
     * @param id      子类项Id
     * @param request 子类项
     * @return 子类项
     */
    @PutMapping("/{id}")
    public ConfigItem updateConfigItem(@PathVariable("id") String id, @RequestBody ConfigItem request) {
        if (null == request) {
            throw new ApplicationException("updateConfigItem-001", "请提供配置子类信息。");
        }
        if (null == this.itemService.getById(id)) {
            throw new ApplicationException("updateConfigItem-002", "配置子类不存在。");
        }
        return itemService.updateConfigItem(id, request);
    }

    /**
     * 查询
     *
     * @param parentId id
     * @return list
     */
    @GetMapping("/searchByParent/{parentId}")
    public List<ConfigItem> listSecond(@PathVariable("parentId") String parentId) {
        return itemService.findNameSonByParentId(parentId);
    }

}

