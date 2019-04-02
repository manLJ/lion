package com.lion.systemmgr.organization.resources;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lion.systemmgr.auth.domain.service.HttpSessionLocal;
import com.lion.systemmgr.organization.domain.model.User;
import com.lion.systemmgr.organization.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_用户表 前端控制器
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Api(tags = "UserManage", value = "用户管理")
@RestController
@RequestMapping("/system/organization/users")
public class UserResource {
    @Autowired
    private UserService userService;

    /**
     * 查询用户清单
     *
     * @return 用户列表
     */
    @ApiOperation(value = "查询用户清单")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> loadAll() {
        return userService.loadAll();
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @ApiOperation(value = "新增用户")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public User create(@RequestBody User user) {
        return userService.create(user, "MANAGEMENT");
    }

    /**
     * 修改用户信息
     *
     * @param userId 用户ID
     * @param user   修改信息
     * @return 用户
     */
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户信息", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public User update(@PathVariable("userId") String userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @return 用户
     */
    @ApiOperation(value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户信息", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{userId}/resetpwd", method = RequestMethod.PUT)
    public User resetPwd(@PathVariable("userId") String userId) {
        return userService.resetPwd(userId);
    }

    /**
     * 修改密码
     *
     * @param userData oldToken 旧密码；newToken 新密码
     * @return 用户
     */
    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "userData", value = "秘钥", required = true, paramType = "body")
    @RequestMapping(value = "/changepwd", method = RequestMethod.PUT)
    public User changePwd(@RequestBody JSONObject userData) {
        return userService.changePwd(HttpSessionLocal.getCurrentUser().getAccount(), userData);
    }

    /**
     * 查询指定用户
     *
     * @param userName 用户登录名
     * @return 用户
     */
    @ApiOperation(value = "查询指定用户")
    @ApiImplicitParam(name = "userName", value = "用户登录名", required = true, paramType = "path")
    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public User loadUserPrivileges(@PathVariable("userName") String userName) {
        return userService.findUserByAccount(userName);
    }

    /**
     * 查询封禁用户列表
     *
     * @param account
     * @param chinaName
     * @param page
     * @return
     */
    @ApiOperation(value = "查询封禁用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户登录名", paramType = "query"),
            @ApiImplicitParam(name = "chinaName", value = "中文名", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "分页", required = true, paramType = "body")
    })
    @GetMapping(value = "/lockUsers")
    public IPage unlockUser(@RequestParam(value = "account", required = false) String account,
                            @RequestParam(value = "chinaName", required = false) String chinaName,
                            Page page) {
        return userService.findAllLockUsers(account, chinaName, page);
    }

    /**
     * 解锁用户
     *
     * @return
     */
    @ApiOperation(value = "解锁用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path")
    @RequestMapping(value = "/lockUsers/{id}", method = RequestMethod.PUT)
    public JSONObject unlockUser(@PathVariable("id") String id) {
        userService.unlockUser(id);
        return new JSONObject();
    }

}

