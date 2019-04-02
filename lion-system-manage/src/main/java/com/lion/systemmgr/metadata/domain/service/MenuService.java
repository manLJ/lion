package com.lion.systemmgr.metadata.domain.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.core.exception.ApplicationException;
import com.lion.systemmgr.metadata.domain.mapper.MenuMapper;
import com.lion.systemmgr.metadata.domain.model.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * sys_菜单表 服务类
 * </p>
 *
 * @author JACK
 * @since 2018-09-03
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    public List<Menu> loadDisplayMenus() {
        Wrapper<Menu> queryRootWrapper = new QueryWrapper<Menu>()
                .isNull("PARENT_ID")
                .eq("delstatus", 1)
                .orderByAsc("SORT_INDEX");
        List<Menu> rootMenus = this.list(queryRootWrapper);
        for (Menu menu : rootMenus) {
            Wrapper<Menu> queryChildWrapper = new QueryWrapper<Menu>()
                    .eq("PARENT_ID", menu.getId())
                    .eq("delstatus", 1)
                    .orderByAsc("SORT_INDEX");
            menu.setItems(this.list(queryChildWrapper));
        }
        return rootMenus;
    }

    /**
     * 菜单创建
     *
     * @param menu 菜单信息
     * @return 菜单
     */
    @Transactional
    public Menu create(Menu menu) {
        if (StringUtils.isBlank(menu.getCode())) {
            throw new ApplicationException("createMenu-001", "菜单编码不能为空。");
        }
        if (StringUtils.isBlank(menu.getName())) {
            throw new ApplicationException("createMenu-001", "菜单名称不能为空。");
        }

        checkCode(menu.getCode());
        menu.setDelstatus(1);
        this.save(menu);
        return menu;
    }

    /**
     * 菜单修改
     *
     * @param menuId 菜单ID
     * @param menu   修改信息
     * @return 菜单
     */
    @Transactional
    public Menu update(String menuId, Menu menu) {
        if (StringUtils.isBlank(menu.getCode())) {
            throw new ApplicationException("createMenu-001", "菜单编码不能为空。");
        }
        if (StringUtils.isBlank(menu.getName())) {
            throw new ApplicationException("createMenu-001", "菜单名称不能为空。");
        }

        checkExists(menuId);
        checkCode(menuId, menu.getCode());

        Menu _menu = this.getById(menuId);
        _menu.setCode(menu.getCode());
        _menu.setName(menu.getName());
        _menu.setDelstatus(menu.getDelstatus());
        _menu.setSortIndex(menu.getSortIndex());

        this.updateById(_menu);
        return _menu;
    }

    private void checkExists(String menuId) {
        Menu _menu = this.getById(menuId);
        if (_menu == null) throw new ApplicationException("MU-0001", "菜单不存在。");
    }

    private void checkCode(String code) {
        Wrapper<Menu> queryWrapper = new QueryWrapper<Menu>()
                .eq("CODE", code);
        Menu menu = this.getOne(queryWrapper);
        if (menu != null) throw new ApplicationException("MU-0002", "菜单编码已经存在。");
    }

    private void checkCode(String menuId, String code) {
        Wrapper<Menu> queryWrapper = new QueryWrapper<Menu>()
                .ne("ID", menuId)
                .eq("CODE", code);
        Menu menu = this.getOne(queryWrapper);
        if (menu != null) throw new ApplicationException("MU-0002", "菜单编码已经存在。");
    }
}
