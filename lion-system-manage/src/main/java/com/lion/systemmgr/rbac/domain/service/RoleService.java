package com.lion.systemmgr.rbac.domain.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lion.core.exception.ApplicationException;
import com.lion.systemmgr.rbac.domain.mapper.RoleMapper;
import com.lion.systemmgr.rbac.domain.model.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * sys_角色表 服务类
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    /**
     * 查询角色清单
     *
     * @param type 角色类型：企业：ENTERPRISE，运维管理：MANAGEMENT
     * @return 角色列表
     */
    public List<Role> loadAll(String type) {
        return this.list(new QueryWrapper<Role>().eq("TYPE", type).orderByDesc("CREATE_DATE"));
    }

    /**
     * 创建角色
     *
     * @param role 角色信息
     * @return 角色
     */
    @Transactional
    public Role create(Role role) {
        checkRoleName(role.getRoleName());
        role.setType("MANAGEMENT");
        role.setDelstatus(1);
        this.save(role);
        return role;
    }

    public void checkRoleName(String name) {
        Wrapper<Role> queryWrapper = new QueryWrapper<Role>()
                .eq("ROLE_NAME", name);
        Role role = this.getOne(queryWrapper);
        if (role != null) throw new ApplicationException("RO-0001", "角色名称已经存在。");
    }

    public void checkRoleName(String name, String roleId) {
        Wrapper<Role> queryWrapper = new QueryWrapper<Role>()
                .eq("ROLE_NAME", name)
                .ne("ID", roleId);
        Role role = this.getOne(queryWrapper);
        if (role != null) throw new ApplicationException("RO-0001", "角色名称已经存在。");
    }

    /**
     * 修改角色
     *
     * @param roleId 角色ID
     * @param role   修改信息
     * @return 角色
     */
    @Transactional
    public Role updateRole(String roleId, Role role) {
        if (StringUtils.isBlank(role.getRoleName())) {
            throw new ApplicationException("updateRole-001", "角色名称不能为空。");
        }
        if (null == role.getDelstatus()) {
            throw new ApplicationException("updateRole-002", "角色状态不能为空。");
        }

        checkRoleName(role.getRoleName(), roleId);
        checkRoleExists(roleId);

        Role _role = this.getById(roleId);
        _role.setRoleName(role.getRoleName());
        _role.setDescription(role.getDescription());
        _role.setDelstatus(role.getDelstatus());

        this.updateById(_role);

        return _role;
    }

    public void checkRoleExists(String roleId) {
        Role _role = this.getById(roleId);
        if (_role == null) throw new ApplicationException("RO-0002", "角色不存在。");
    }
}
