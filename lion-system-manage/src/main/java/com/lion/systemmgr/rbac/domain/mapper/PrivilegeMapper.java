package com.lion.systemmgr.rbac.domain.mapper;

import com.lion.systemmgr.rbac.domain.model.OperationPrivilege;
import com.lion.systemmgr.rbac.domain.model.VisitPrivilege;

import java.util.List;

public interface PrivilegeMapper {
    List<VisitPrivilege> findUserVisitPrivileges(String userName);

    List<OperationPrivilege> findUserOperationPrivileges(String userName);
}
