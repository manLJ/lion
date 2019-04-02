package com.lion.systemmgr.auth.domain.service;

import com.lion.systemmgr.organization.domain.model.User;

/**
 * Created by JACK on 2015/9/9.
 */
public interface UserProvider {
    boolean existsUser(String userName);

    boolean userAvailable(String userName);

    String getUserToken(String userName);

    User getUserInfo(String userName);
}
