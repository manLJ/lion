package com.lion.systemmgr.auth.resources;

import com.lion.systemmgr.auth.domain.model.VerifyResult;
import com.lion.systemmgr.auth.domain.service.HttpSessionLocal;
import com.lion.systemmgr.auth.domain.service.VerificationCodeGenerator;
import com.lion.systemmgr.organization.domain.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Create By JACK  2018/5/1
 */

@RestController
@RequestMapping(value = "/system/verification/code")
@Api(tags = "verifyCodeManage", value = "验证码管理")
public class VerificationCodeResource {
    private Logger logger = LoggerFactory.getLogger(VerificationCodeResource.class);

    /**
     * 获取验证码图片
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "获取验证码图片")
    @RequestMapping(value = "/generator", method = RequestMethod.GET)
    public void generatorVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //验证码不能缓存
        response.setHeader("Expires", "-1");
        response.setHeader("cache-control", "no-cahce");
        response.setHeader("pragma", "no-cache");

        VerificationCodeGenerator vcg = new VerificationCodeGenerator();
        //将验证码保存到session域对象
        HttpSessionLocal.setAttribute("DRAGON-DEEPOOL-VERIFYCODE", vcg.verifyCode());
        //获取验证码图片
        BufferedImage vcodeImage = vcg.generatorImage();
        //输出验证码图片
        ImageIO.write(vcodeImage, "gif", response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param verifyCode
     * @param request
     * @return
     */
    @ApiOperation(value = "校验验证码")
    @ApiImplicitParam(name = "verifyCode", value = "校验码", required = true, paramType = "path", dataType = "String")
    @RequestMapping(value = "/verify/{verifyCode}", method = RequestMethod.PUT)
    public VerifyResult checkVerifyCode(@PathVariable("verifyCode") String verifyCode, HttpServletRequest request) {
        String sessionVerifyCode = (String) HttpSessionLocal.getAttribute("DRAGON-DEEPOOL-VERIFYCODE");
        //判断验证码是否相同
        if (StringUtils.isEmpty(verifyCode) || StringUtils.isEmpty(sessionVerifyCode)) return new VerifyResult(false);
        else if (verifyCode.equalsIgnoreCase(sessionVerifyCode)) return new VerifyResult(true);
        else return new VerifyResult(false);
    }

}
