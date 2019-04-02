package com.lion.core.identity;


/**
 * Created by JACK on 2015/9/9.
 */
public class RequestIdentity {
    private final String userName;
    private final String userToken;
    private final String remoteAddr;
    private long effectiveTimes;
    private final String userAgent;
    private String signData;
    private Object user;

    public RequestIdentity(String userName, String token, String remoteAddr, String userAgent, long effectiveTimes) {
        this.userName = userName;
        this.userToken = token;
        this.remoteAddr = remoteAddr;
        this.userAgent = userAgent;
        this.effectiveTimes = effectiveTimes;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public long getEffectiveTimes() {
        return effectiveTimes;
    }

    public void setEffectiveTimes(long effectiveTimes) {
        this.effectiveTimes = effectiveTimes;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public Object getUser() {
        return user;
    }
}
