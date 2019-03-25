package com.lion.usermanagement.menu.resources;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuResource {

    @GetMapping
    public List list() {
        return null;
    }
}
