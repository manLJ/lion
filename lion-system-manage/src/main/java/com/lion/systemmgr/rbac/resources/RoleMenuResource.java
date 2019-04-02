package com.lion.systemmgr.rbac.resources;


import com.lion.systemmgr.metadata.domain.model.Menu;
import com.lion.systemmgr.rbac.domain.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_角色菜单关联表 前端控制器
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Api(tags = "RoleMenuRelManage", value = "角色菜单关联表管理 ")
@RestController
@RequestMapping("/system/rbac/roles")
public class RoleMenuResource {

    @Autowired
    private RoleMenuService privilegeService;

    /**
     * 查询指定角色菜单
     *
     * @param roleId 角色ID
     * @return 菜单集合
     */
    @ApiOperation(value = "查询指定角色菜单")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "path")
    @RequestMapping(value = "/{roleId}/menus", method = RequestMethod.GET)
    public List<Menu> loadAllPrivileges(@PathVariable("roleId") String roleId) {
        return privilegeService.loadAllPrivileges(roleId);
    }

    /**
     * 角色菜单关联关系配置
     *
     * @param roleId 角色ID
     * @param menuIds 菜单Id
     */
    @ApiOperation(value = "角色菜单关联关系配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{roleId}/menus", method = RequestMethod.POST)
    public void assignPrivileges(@PathVariable("roleId") String roleId, @RequestBody List<String> menuIds) {
        privilegeService.assignPrivileges(roleId, menuIds);
    }
}

