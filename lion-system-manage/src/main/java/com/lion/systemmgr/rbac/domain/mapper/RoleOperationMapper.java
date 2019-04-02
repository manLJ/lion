package com.lion.systemmgr.rbac.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.systemmgr.metadata.domain.model.MenuOperation;
import com.lion.systemmgr.rbac.domain.model.RoleOperation;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
public interface RoleOperationMapper extends BaseMapper<RoleOperation> {

    List<MenuOperation> loadAllOperations(String roleId);
}
