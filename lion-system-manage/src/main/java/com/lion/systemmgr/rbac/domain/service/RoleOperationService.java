package com.lion.systemmgr.rbac.domain.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.systemmgr.metadata.domain.model.MenuOperation;
import com.lion.systemmgr.rbac.domain.mapper.RoleOperationMapper;
import com.lion.systemmgr.rbac.domain.model.RoleOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * sys_角色操作关联表 服务类
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Service
public class RoleOperationService extends ServiceImpl<RoleOperationMapper, RoleOperation> {
    @Autowired
    private RoleOperationMapper operationMapper;

    /**
     * 查询指定角色菜单
     *
     * @param roleId 角色ID
     * @return 菜单操作
     */
    public List<MenuOperation> loadAllOperations(String roleId) {
        return operationMapper.loadAllOperations(roleId);
    }

    /**
     * 角色菜单关联关系配置
     *
     * @param roleId          角色ID
     * @param _menuOperations 角色菜单关联关系
     */
    @Transactional
    public void assignOperations(String roleId, List<MenuOperation> _menuOperations) {
        if (_menuOperations == null || _menuOperations.isEmpty()) return;

        List<RoleOperation> rolePrivileges = new ArrayList<>();
        for (MenuOperation operation : _menuOperations) {
            rolePrivileges.add(new RoleOperation(roleId, operation.getMenuId(), operation.getId()));
        }
        this.saveBatch(rolePrivileges);
    }
}
