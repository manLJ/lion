package com.lion.systemmgr.metadata.resources;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lion.systemmgr.metadata.domain.model.Menu;
import com.lion.systemmgr.metadata.domain.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_菜单表 前端控制器
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Api(tags = "MenusManage", value = "菜单管理")
@RestController
@RequestMapping("/system/metadata/menus")
public class MenuResource {
    @Autowired
    private MenuService menuService;

    /**
     * 菜单清单
     *
     * @return 菜单列表
     */
    @ApiOperation(value = "菜单清单")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Menu> loadAll() {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.isNull("parent_id");
        List<Menu> menus = menuService.list(qw);
        for (Menu menu : menus) {
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", menu.getId());
            List<Menu> childMenu = menuService.list(wrapper);
            for (Menu m : childMenu) {
                QueryWrapper<Menu> w = new QueryWrapper<>();
                w.eq("parent_id", m.getId());
                List<Menu> cm = menuService.list(w);
                m.setItems(cm);
            }
            menu.setItems(childMenu);
        }
        return menus;
    }

    /**
     * 菜单创建
     *
     * @param menu 菜单信息
     * @return 菜单
     */
    @ApiOperation(value = "菜单创建")
    @ApiImplicitParam(name = "menu", value = "菜单信息", required = true, paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Menu create(@RequestBody Menu menu) {
        return menuService.create(menu);
    }

    /**
     * 菜单修改
     *
     * @param menuId 菜单ID
     * @param menu   修改信息
     * @return 菜单
     */
    @ApiOperation(value = "菜单修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "menu", value = "修改信息", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{menuId}", method = RequestMethod.PUT)
    public Menu update(@PathVariable("menuId") String menuId, @RequestBody Menu menu) {
        return menuService.update(menuId, menu);
    }

    /**
     * 菜单删除
     *
     * @param menuId 菜单ID
     */
    @Transactional
    @ApiOperation(value = "菜单删除")
    @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "path")
    @RequestMapping(value = "/{menuId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("menuId") String menuId) {
        menuService.removeById(menuId);
    }

    /**
     * 菜单联级查询
     *
     * @return 菜单列表 一级菜单+二级菜单
     */
    @ApiOperation(value = "菜单联级查询")
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public List<Menu> loadDisplayMenus() {
        return menuService.loadDisplayMenus();
    }
}

