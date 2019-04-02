package com.lion.systemmgr.auth.resources;

import com.alibaba.fastjson.JSONObject;
import com.lion.systemmgr.auth.domain.model.VerifyResult;
import com.lion.systemmgr.auth.domain.service.IdentityAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证管理 Create By JACK  2018/5/1
 */

@RestController
@Api(tags = "AuthorityManage", value = "登录验证管理")
@RequestMapping(value = "/system/auth/authority")
public class AuthenticationResource {
    @Autowired
    private IdentityAuthentication authentication;

    /**
     * 第一次登陆请求，
     * 获取密钥
     *
     * @param userName
     * @param request
     * @return
     */
    @ApiOperation(value = "获取密钥凭证")
    @ApiImplicitParam(name = "userName", value = "用户登录名", required = true, paramType = "path")
    @RequestMapping(value = "/token/{userName}", method = RequestMethod.GET)
    public VerifyResult takeToken(@PathVariable("userName") String userName, HttpServletRequest request) {
        //用户名和其它信息的验证扩展接口
        VerifyResult result = authentication.verifyUserAccount(userName);
        if (result.isPassed()) {
            //生成密钥
            result.setToken(authentication.generateToken(userName, request));
        }
        return result;
    }

    /**
     * 第二次登陆请求，根据
     * 密钥、
     * 绑定密钥的key、
     * 客户端的签名数据（该密码是由：用户名和源密码经HmacSHA256算法加密，然后经Base64编码，结合密钥再次经HmacSHA256加密，再次经Base64编码得到）
     * 界定是否允许登陆
     * 验证通过后：
     * 服务端会保存用户的密钥和签名数据
     * 客户端数据的生命周期必须随浏览器的关闭而销毁
     *
     * @param params
     * @param request
     * @return
     */
    @ApiOperation(value = "根据密钥凭证进行身份认证")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userName", value = "用户登录名", required = true, paramType = "form"),
            @ApiImplicitParam(name = "signData", value = "客户端的签名数据", required = true, paramType = "form")
    })
    @RequestMapping(value = "/verify", method = RequestMethod.PUT)
    public VerifyResult verifyUserToken(@RequestBody JSONObject params, HttpServletRequest request) {
        return authentication.verifyUserToken(params.getString("userName"), params.getString("signData"), params.getString("code"), request);
    }

    /**
     * 注销登陆
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "注销登陆")
    @ApiImplicitParam(name = "userName", value = "用户登录名", required = true, paramType = "path")
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public VerifyResult logout(HttpServletRequest request) {
        return authentication.logout(request);
    }


}
