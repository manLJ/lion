package com.lion.systemmgr.rbac.resources;


import com.lion.systemmgr.metadata.domain.model.MenuOperation;
import com.lion.systemmgr.rbac.domain.service.RoleOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_角色操作关联表 前端控制器
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Api(tags = "RoleOperationRelManage", value = "角色操作关联关系管理")
@RestController
@RequestMapping("/system/rbac/roles")
public class RoleOperationResource {

    @Autowired
    private RoleOperationService privilegeService;

    /**
     * 查询指定角色操作
     *
     * @param roleId 角色ID
     * @return 菜单操作
     */
    @ApiOperation(value = "查询指定角色操作")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "path")
    @RequestMapping(value = "/{roleId}/operations", method = RequestMethod.GET)
    public List<MenuOperation> loadAllOperations(@PathVariable("roleId") String roleId) {
        return privilegeService.loadAllOperations(roleId);
    }

    /**
     * 角色操作关联关系配置
     *
     * @param roleId     角色ID
     * @param operations 角色操作关联关系
     */
    @ApiOperation(value = "角色操作关联关系配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "operations", value = "角色操作关联关系", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{roleId}/operations", method = RequestMethod.POST)
    public void assignOperations(@PathVariable("roleId") String roleId, @RequestBody List<MenuOperation> operations) {
        privilegeService.assignOperations(roleId, operations);
    }
}

