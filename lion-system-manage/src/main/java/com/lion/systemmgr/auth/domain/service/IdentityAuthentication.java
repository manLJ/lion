package com.lion.systemmgr.auth.domain.service;

import com.lion.core.exception.ApplicationException;
import com.lion.core.helper.EncryptionHelper;
import com.lion.core.identity.RequestIdentity;
import com.lion.systemmgr.auth.domain.model.VerifyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Create By JACK  2018/5/1
 */
@Service
public class IdentityAuthentication {
    @Value("${spring.session.timeout:3600}")
    private int validityPeriod;//默认1小时

    private UserProvider userProvider;

    @Autowired
    public void setUserProvider(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Resource
    private UserAccountErrorLoginService userAccountErrorLoginService;

    public VerifyResult verifyUserAccount(String userName) {
        if (!userProvider.existsUser(userName)) throw new ApplicationException("UNKNOWN_USER", "用户不存在。");
        if (!userProvider.userAvailable(userName)) throw new ApplicationException("DISABLED_USER", "用户已被禁用。");
        return new VerifyResult(true);
    }

    private RequestIdentity bindTokenToUser(String userName, String accessToken, HttpServletRequest request) {
        RequestIdentity requestIdentity = new RequestIdentity(userName, accessToken, request.getRemoteAddr(), request.getHeader("user-agent"), System.currentTimeMillis());
        //记录登录用户身份
        HttpSessionLocal.setAttribute(accessToken, requestIdentity);
        return requestIdentity;
    }

    public String generateToken(String userName, HttpServletRequest request) {
        String signData = String.valueOf(System.currentTimeMillis());
        String secretKey = userName + request.getHeader("user-agent") + request.getRemoteAddr();
        String token = EncryptionHelper.signDataWithToken(signData, secretKey);
        bindTokenToUser(userName, token, request);
        return token;
    }


    private boolean validateRequestStatus(HttpServletRequest request) {
        RequestIdentity requestIdentity = getRequestIdentity(request);
        if (requestIdentity != null) {
            long maxAllowedInterval = this.validityPeriod * 1000L;//默认1小时
            long currentInterval = System.currentTimeMillis() - requestIdentity.getEffectiveTimes();//最近一次请求的时间间隔
            if (currentInterval < maxAllowedInterval && requestIdentity.getUserAgent().equals(request.getHeader("user-agent")))
                return true;
        }
        return false;
    }

    /**
     * 创建登录用户加密密钥
     *
     * @param userName
     * @param request
     * @return
     */
    public String generateEncryptKey(String userName, HttpServletRequest request) {
        String agentToken = userName + request.getHeader("user-agent") + request.getRemoteAddr();
        String secretKey = EncryptionHelper.signDataWithToken(String.valueOf(System.currentTimeMillis()), agentToken);
        HttpSessionLocal.setAttribute(secretKey, userName);
        return secretKey;
    }

    private boolean validateUserToken(String token, String userName, String clientSignData) {
        //获取密钥
        /*String token = getRequestIdentity(request).getUserToken();
        //读取服务端待签名数据
        String signData = this.userProvider.getUserToken(userName);
        //使用密钥签名数据
        String signedData = EncryptionHelper.signDataWithToken(signData, token);
        //判断服务端签名数据与客户端签名数据是否相等
        if (userAccountErrorLoginService.verifyAccountErrors(userName))
            throw new ApplicationException("validateUserToken-001", "该用户密码错误达到5次，已被封锁，请联系管理员。");

        if (signedData != null && !signedData.equals(clientSignData))
            userAccountErrorLoginService.errorRecord(request, userName);

        if (signedData != null && signedData.equals(clientSignData))
            userAccountErrorLoginService.removeByAccount(userName);

        return signedData == null ? false : signedData.equals(clientSignData);*/

        //读取服务端待签名数据
        String signData = this.userProvider.getUserToken(userName);
        //使用密钥签名数据
        String signedData = EncryptionHelper.signDataWithToken(signData, token);
        //判断服务端签名数据与客户端签名数据是否相等
        if (userAccountErrorLoginService.verifyAccountErrors(userName))
            throw new ApplicationException("validateUserToken-001", "该用户密码错误达到5次，已被封锁，请联系管理员。");
        //判断服务端签名数据与客户端签名数据是否相等
        return signedData == null ? false : signedData.equals(clientSignData);

    }

    public VerifyResult verifyUserToken(String token, String signData, HttpServletRequest request) {

       /* String userName = String.valueOf(HttpSessionLocal.getAttribute(token));

        if (!validateRequestStatus(request))
            throw new ApplicationException("SESSION_FAILURE", "会话失效，请重新登陆。");
        if (!validateUserToken(userName, request, signData))
            throw new ApplicationException("PASSWORD_ERROR", "用户密码不正确。");

        RequestIdentity identity = getRequestIdentity(request);
        identity.setSignData(signData);
        identity.setUser(userProvider.getUserInfo(userName));
        return new VerifyResult(true);
*/
        String userName = String.valueOf(HttpSessionLocal.getAttribute(token));
        if (!validateUserToken(token, userName, signData))
            throw new ApplicationException("AU-0004", "用户或密码不正确。");
        String accessToken = generateEncryptKey(userName, request);
        RequestIdentity identity = bindTokenToUser(userName, accessToken, request);
        identity.setSignData(signData);
        identity.setUser(userProvider.getUserInfo(userName));

        VerifyResult result = new VerifyResult(true);
        result.setToken(accessToken);
        return result;
    }

    public VerifyResult logout(HttpServletRequest request) {
        HttpSessionLocal.removeAttribute(request.getHeader("X-Auth-Token"));
        return new VerifyResult(true);
    }

    private RequestIdentity getRequestIdentity(HttpServletRequest request) {
        Object identity = HttpSessionLocal.getAttribute(request.getHeader("X-Auth-Token"));
        if (identity == null) return null;
        return (RequestIdentity) identity;
    }

    public void validate(String token, HttpServletRequest request) throws ApplicationException {
        RequestIdentity requestIdentity = getRequestIdentity(request);
        if (requestIdentity == null) throw new ApplicationException("用户未登录。");

        if (!validateRequestStatus(request)) throw new ApplicationException("会话失效，请重新登陆。");
        if (!validateUserToken(token, request)) throw new ApplicationException("非法用户");
    }

    private boolean validateUserToken(String token, HttpServletRequest request) {
        return (!StringUtils.isEmpty(token) && token.equals(getRequestIdentity(request).getUserToken()));
    }

    public Object getCurrentUser(HttpServletRequest request) {
        return getRequestIdentity(request).getUser();
    }


    public void setEffectiveTimes(HttpServletRequest request) {
        RequestIdentity requestIdentity = getRequestIdentity(request);
        if (requestIdentity != null) {
            requestIdentity.setEffectiveTimes(System.currentTimeMillis());  //最近一次请求的时间间隔
        }
    }
}
