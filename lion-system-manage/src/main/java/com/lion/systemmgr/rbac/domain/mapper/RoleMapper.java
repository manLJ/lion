package com.lion.systemmgr.rbac.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.systemmgr.rbac.domain.model.Role;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> listUserRoles(String userId);
}
