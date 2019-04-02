package com.lion.systemmgr.rbac.domain.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.systemmgr.metadata.domain.model.Menu;
import com.lion.systemmgr.rbac.domain.mapper.RoleMenuMapper;
import com.lion.systemmgr.rbac.domain.mapper.RoleOperationMapper;
import com.lion.systemmgr.rbac.domain.model.RoleMenu;
import com.lion.systemmgr.rbac.domain.model.RoleOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * sys_角色菜单关联表 服务类
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleOperationMapper operationMapper;

    /**
     * 查询指定角色菜单
     *
     * @param roleId 角色ID
     * @return 菜单集合
     */
    public List<Menu> loadAllPrivileges(String roleId) {
        return roleMenuMapper.loadAllPrivileges(roleId);
    }

    /**
     * 角色菜单关联关系配置
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID
     */
    @Transactional
    public void assignPrivileges(String roleId, List<String> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) return;

        clearPrivileges(roleId);
        List<RoleMenu> rolePrivileges = new ArrayList<>();
        for (String menuId : menuIds) {
            rolePrivileges.add(new RoleMenu(roleId, menuId));
        }
        this.saveBatch(rolePrivileges);
    }

    private void clearPrivileges(String roleId) {
        Wrapper<RoleOperation> operationQueryWrapper = new QueryWrapper<RoleOperation>()
                .eq("ROLE_ID", roleId);
        operationMapper.delete(operationQueryWrapper);

        Wrapper<RoleMenu> menuQueryWrapper = new QueryWrapper<RoleMenu>()
                .eq("ROLE_ID", roleId);
        roleMenuMapper.delete(menuQueryWrapper);
    }
}
