package com.lion.systemmgr.metadata.domain.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.core.exception.ApplicationException;
import com.lion.systemmgr.metadata.domain.mapper.MenuMapper;
import com.lion.systemmgr.metadata.domain.mapper.MenuOperationMapper;
import com.lion.systemmgr.metadata.domain.model.Menu;
import com.lion.systemmgr.metadata.domain.model.MenuOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * sys_菜单操作管理表 服务类
 * </p>
 *
 * @author JACK
 * @since 2018-09-03
 */
@Service
public class MenuOperationService extends ServiceImpl<MenuOperationMapper, MenuOperation> {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询指定菜单操作详情
     *
     * @param menuId 菜单ID
     * @return 操作详情
     */
    public List<MenuOperation> loadItems(String menuId) {
        Wrapper<MenuOperation> queryWrapper = new QueryWrapper<MenuOperation>()
                .eq("MENU_ID", menuId);
        return this.list(queryWrapper);
    }

    /**
     * 创建操作详情
     *
     * @param menuId        菜单ID
     * @param menuOperation 操作信息
     * @return 操作详情
     */
    @Transactional
    public MenuOperation createOperations(String menuId, MenuOperation menuOperation) {
        Menu menu = menuMapper.selectById(menuId);
        if (menu == null) throw new ApplicationException("MU-0001", "菜单不存在。");

        checkCode(menuId, menuOperation.getCode());
        menuOperation.setMenuId(menuId);
        menuOperation.setDelstatus(1);
        this.save(menuOperation);
        return menuOperation;
    }

    /**
     * 修改操作详情
     *
     * @param operationId   操作ID
     * @param menuOperation 操作信息
     * @return 操作详情
     */
    @Transactional
    public MenuOperation updateOperations(String operationId, MenuOperation menuOperation) {
        checkOperation(operationId);
        checkCode(menuOperation.getMenuId(), menuOperation.getCode(), operationId);

        MenuOperation _menuOperation = this.getById(operationId);
        _menuOperation.setCode(menuOperation.getCode());
        _menuOperation.setName(menuOperation.getName());
        _menuOperation.setDelstatus(menuOperation.getDelstatus());
        this.updateById(_menuOperation);
        return _menuOperation;
    }

    private void checkOperation(String operationId) {
        MenuOperation menuOperation = this.getById(operationId);
        if (null == menuOperation) throw new ApplicationException("MU-0003", "菜单操作不存在。");
    }

    private void checkCode(String menuId, String operationCode) {
        Wrapper<MenuOperation> queryWrapper = new QueryWrapper<MenuOperation>()
                .eq("MENU_ID", menuId)
                .eq("code", operationCode);
        MenuOperation menuOperation = this.getOne(queryWrapper);
        if (menuOperation != null) throw new ApplicationException("MU-0004", "操作编码已经存在。");
    }

    private void checkCode(String menuId, String operationCode, String operationId) {
        Wrapper<MenuOperation> queryWrapper = new QueryWrapper<MenuOperation>()
                .eq("MENU_ID", menuId)
                .eq("CODE", operationCode)
                .ne("ID", operationId);
        MenuOperation menuOperation = this.getOne(queryWrapper);
        if (menuOperation != null) throw new ApplicationException("MU-0004", "操作编码已经存在。");
    }
}
