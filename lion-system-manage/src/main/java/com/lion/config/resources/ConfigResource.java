package com.lion.config.resources;


import com.lion.config.domain.service.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/config")
public class ConfigResource {
    @Resource
    private ConfigService configService;

    @GetMapping("/findConfigItemList")
    public List findConfigItemList(@RequestParam("configName") String configName) {
        return configService.findItemsNameByName(configName);
    }
}

