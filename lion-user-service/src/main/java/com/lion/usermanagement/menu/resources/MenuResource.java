package com.lion.usermanagement.menu.resources;


import com.lion.usermanagement.menu.domian.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuResource {

    @Resource
    private MenuService menuService;

    @GetMapping
    public List list() {
        return null;
    }
}
