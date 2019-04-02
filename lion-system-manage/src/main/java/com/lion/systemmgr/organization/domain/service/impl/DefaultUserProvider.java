package com.lion.systemmgr.organization.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lion.systemmgr.auth.domain.service.UserProvider;
import com.lion.systemmgr.organization.domain.mapper.UserMapper;
import com.lion.systemmgr.organization.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by JACK on 2015/1/21.
 */
@Service
public class DefaultUserProvider implements UserProvider {
    @Autowired
    private UserMapper userMapper;

    public boolean existsUser(String userName) {
        User user = getUserInfo(userName);
        return user != null;
    }

    public boolean userAvailable(String userName) {
        User user = getUserInfo(userName);
        return user != null && user.getDelstatus() == 1;
    }

    public String getUserToken(String userName) {
        User user = getUserInfo(userName);
        return user.getToken();
    }

    public User getUserInfo(String userName) {
        Wrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("account", userName);
        return this.userMapper.selectOne(queryWrapper);
    }


}
