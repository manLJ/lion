package com.lion.systemmgr.rbac.resources;


import com.lion.systemmgr.auth.domain.service.HttpSessionLocal;
import com.lion.systemmgr.organization.domain.model.User;
import com.lion.systemmgr.organization.domain.service.UserService;
import com.lion.systemmgr.rbac.domain.model.Role;
import com.lion.systemmgr.rbac.domain.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_用户角色关联表 前端控制器
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Api(tags = "UserRoleRelManage", value = "用户角色关联关系管理")
@RestController
@RequestMapping("/system/organization/users")
public class UserRoleResource {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;

    /**
     * 查询用户角色
     *
     * @param userId 用户ID
     * @return 角色
     */
    @ApiOperation(value = "查询用户角色")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "path")
    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET)
    public List<Role> loadAll(@PathVariable("userId") String userId) {
        return userRoleService.loadAll(userId);
    }

    /**
     * 用户角色关系配置
     *
     * @param userId 用户ID
     * @param roles  角色信息集合
     * @return 角色
     */
    @ApiOperation(value = "用户角色关系配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "roles", value = "角色信息集合", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.POST)
    public List<Role> loadAll(@PathVariable("userId") String userId, @RequestBody List<Role> roles) {
        return userRoleService.assignRoles(userId, roles);
    }
}

