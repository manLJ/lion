package com.lion.systemmgr.auth.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.systemmgr.auth.domain.model.UserAccountErrorLogin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserAccountErrorLoginMapper extends BaseMapper<UserAccountErrorLogin> {


    List<Map> queryAllLockUsers(@Param("account") String account, @Param("chinaName") String chinaName);
}