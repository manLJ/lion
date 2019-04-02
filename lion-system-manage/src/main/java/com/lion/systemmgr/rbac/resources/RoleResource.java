package com.lion.systemmgr.rbac.resources;


import com.lion.systemmgr.rbac.domain.model.Role;
import com.lion.systemmgr.rbac.domain.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_角色表 前端控制器
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Api(tags = "RoleManage", value = "角色管理")
@RestController
@RequestMapping("/system/rbac/roles")
public class RoleResource {
    @Autowired
    private RoleService roleService;

    /**
     * 查询角色清单
     *
     * @param type 角色类型：企业：ENTERPRISE，运维管理：MANAGEMENT
     * @return 角色列表
     */
    @ApiOperation(value = "查询角色清单")
    @ApiImplicitParam(name = "type", value = "角色类型", required = true, paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Role> loadAll(@RequestParam("type") String type) {
        return roleService.loadAll(type);
    }

    /**
     * 创建角色
     *
     * @param role 角色信息
     * @return 角色
     */
    @ApiOperation(value = "创建角色")
    @ApiImplicitParam(name = "role", value = "角色信息", required = true, paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Role create(@RequestBody Role role) {
        return roleService.create(role);
    }

    /**
     * 修改角色
     *
     * @param roleId 角色ID
     * @param role   修改信息
     * @return 角色
     */
    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "role", value = "角色修改信息", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT)
    public Role update(@PathVariable("roleId") String roleId, @RequestBody Role role) {
        return roleService.updateRole(roleId, role);
    }
}

