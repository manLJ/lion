package com.lion.systemmgr.rbac.domain.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.systemmgr.rbac.domain.mapper.RoleMapper;
import com.lion.systemmgr.rbac.domain.mapper.UserRoleMapper;
import com.lion.systemmgr.rbac.domain.model.Role;
import com.lion.systemmgr.rbac.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * sys_用户角色关联表 服务类
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询用户角色
     *
     * @param userId 用户ID
     * @return 角色
     */
    public List<Role> loadAll(String userId) {
        return roleMapper.listUserRoles(userId);
    }

    /**
     * 用户角色关系配置
     *
     * @param userId 用户ID
     * @param roles  角色信息集合
     * @return 角色
     */
    @Transactional
    public List<Role> assignRoles(String userId, List<Role> roles) {
        Wrapper<UserRole> deleteWrapper = new QueryWrapper<UserRole>()
                .eq("USER_ID", userId);
        this.remove(deleteWrapper);
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role : roles) {
            userRoles.add(new UserRole(userId, role.getId(), 1));
        }
        if (CollectionUtils.isNotEmpty(userRoles)) this.saveBatch(userRoles);
        return roles;
    }
}
