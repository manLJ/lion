package com.lion.systemmgr.rbac.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.systemmgr.metadata.domain.model.Menu;
import com.lion.systemmgr.rbac.domain.model.RoleMenu;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Menu> loadAllPrivileges(String roleId);
}
