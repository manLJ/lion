package com.lion.config.resources;


import com.lion.config.domain.model.Config;
import com.lion.config.domain.service.ConfigService;
import com.lion.core.exception.ApplicationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * conf_配置表 前端控制器
 * </p>
 *
 * @author zlzhou
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/system/config/config")
public class SystemConfigResource {
    @Resource
    private ConfigService configService;

    /**
     * 查询配置列表
     */
    @GetMapping("")
    public List<Config> list() {
        return configService.list();
    }

    /**
     * 新增配置信息
     *
     * @param config 配置信息
     * @return 配置信息
     */
    @PostMapping("")
    public Config create(@RequestBody Config config) {
        if (null == config) {
            throw new ApplicationException("createConfig-001", "请提供配置信息。");
        }
        return configService.create(config);
    }

    /**
     * 修改配置信息
     *
     * @param id     配置ID
     * @param config 配置信息
     * @return 配置信息
     */
    @PutMapping("/{id}")
    public Config update(@PathVariable("id") String id, @RequestBody Config config) {
        if (null == config) {
            throw new ApplicationException("updateConfig-001", "请提供配置信息。");
        }
        return configService.updateConfig(id, config);
    }

    @GetMapping("/findConfigItemList")
    public List findConfigItemList(@RequestParam("configName") String configName) {
        return configService.findItemsNameByName(configName);
    }
}

