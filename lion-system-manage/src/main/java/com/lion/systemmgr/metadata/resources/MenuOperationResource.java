package com.lion.systemmgr.metadata.resources;


import com.lion.systemmgr.metadata.domain.model.MenuOperation;
import com.lion.systemmgr.metadata.domain.service.MenuOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_菜单操作管理表 前端控制器
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Api(tags = "MenusOperationManage", value = "菜单操作管理")
@RestController
@RequestMapping("/system/metadata/menus")
public class MenuOperationResource {
    @Autowired
    private MenuOperationService menuOperationService;

    /**
     * 查询指定菜单操作详情
     *
     * @param menuId 菜单ID
     * @return 操作详情
     */
    @ApiOperation(value = "查询指定菜单操作详情")
    @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "path")
    @RequestMapping(value = "/{menuId}/operations", method = RequestMethod.GET)
    public List<MenuOperation> createItems(@PathVariable("menuId") String menuId) {
        return menuOperationService.loadItems(menuId);
    }

    /**
     * 创建操作详情
     *
     * @param menuId        菜单ID
     * @param menuOperation 操作信息
     * @return 操作详情
     */
    @ApiOperation(value = "查询指定菜单操作详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "menuOperation", value = "操作信息", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{menuId}/operations", method = RequestMethod.POST)
    public MenuOperation createItems(@PathVariable("menuId") String menuId, @RequestBody MenuOperation menuOperation) {
        return menuOperationService.createOperations(menuId, menuOperation);
    }

    @ApiOperation(value = "删除指定菜单操作详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "path"),
    })
    @DeleteMapping("/{menuId}/operations")
    public void deleteOperation(@PathVariable("menuId") String menuId) {
        menuOperationService.removeById(menuId);
    }

    /**
     * 修改操作详情
     *
     * @param operationId   操作ID
     * @param menuOperation 操作信息
     * @return 操作详情
     */
    @ApiOperation(value = "查询指定菜单操作详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "operationId", value = "操作ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "menuOperation", value = "操作信息", required = true, paramType = "body")
    })
    @RequestMapping(value = "/{menuId}/operations/{operationId}", method = RequestMethod.PUT)
    public MenuOperation updateItem(@PathVariable String operationId, @RequestBody MenuOperation menuOperation) {
        return menuOperationService.updateOperations(operationId, menuOperation);
    }
}

