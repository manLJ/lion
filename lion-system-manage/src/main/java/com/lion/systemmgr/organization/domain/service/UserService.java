package com.lion.systemmgr.organization.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lion.core.exception.ApplicationException;
import com.lion.core.helper.EncryptionHelper;
import com.lion.core.page.PageWrapper;
import com.lion.logaudit.domain.model.UserAccountLockRecord;
import com.lion.logaudit.domain.service.UserAccountLockRecordService;
import com.lion.systemmgr.auth.domain.mapper.UserAccountErrorLoginMapper;
import com.lion.systemmgr.auth.domain.model.UserAccountErrorLogin;
import com.lion.systemmgr.organization.domain.mapper.UserMapper;
import com.lion.systemmgr.organization.domain.model.User;
import com.lion.systemmgr.rbac.domain.mapper.PrivilegeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * sys_用户表 服务类
 * </p>
 *
 * @author JACK
 * @since 2018-08-22
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Resource
    private UserAccountErrorLoginMapper userAccountErrorLoginMapper;

    @Resource
    private UserAccountLockRecordService userAccountLockRecordService;

    @Value("${identity.defaultToken}")
    private String defaultToken;

    /**
     * 查询用户清单
     *
     * @return 用户列表
     */
    public List<User> loadAll() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("CREATE_DATE");
        return this.list(queryWrapper);
    }

    /**
     * 新增用户
     * "MANAGEMENT"
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @Transactional
    public User create(User user, String type) {
        checkUserAccount(user.getAccount());
        user.setToken(EncryptionHelper.signDataWithToken(user.getAccount(), defaultToken));
//        user.setDelstatus(1);
        this.save(user);
        user.setToken(defaultToken);
        return user;
    }

    private void checkUserAccount(String account) {
        Wrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("account", account);
        User user = this.getOne(queryWrapper);
        if (user != null) throw new ApplicationException("US-0001", "账号已经存在。");
    }

    /**
     * 修改用户信息
     *
     * @param userId 用户ID
     * @param user   修改信息
     * @return 用户
     */
    @Transactional
    public User updateUser(String userId, User user) {
        if (StringUtils.isBlank(user.getAccount()))
            throw new ApplicationException("updateUser-0001", "登录名不能为空。");
        if (StringUtils.isBlank(user.getChinaName()))
            throw new ApplicationException("updateUser-0002", "中文名不能为空。");
        if (null == user.getDelstatus())
            throw new ApplicationException("updateUser-0003", "状态不能为空。");

        checkUserAccount(user.getAccount(), userId);

        User _user = this.getById(userId);
        if (_user == null) throw new ApplicationException("US-0002", "用户不存在。");
        _user.applyForUpdate(user.getAccount(), user.getChinaName(), user.getPhone(), user.getDelstatus());

        this.updateById(_user);

        return _user;
    }

    private void checkUserAccount(String account, String userId) {
        Wrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("account", account)
                .ne("id", userId);
        User user = this.getOne(queryWrapper);
        if (user != null) throw new ApplicationException("US-0001", "账号已经存在。");
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Transactional
    public User resetPwd(String userId) {
        User _user = this.getById(userId);
        if (_user == null) throw new ApplicationException("US-0002", "用户不存在。");

        _user.setToken(EncryptionHelper.signDataWithToken(_user.getAccount(), defaultToken));
        this.updateById(_user);

        _user.setToken(defaultToken);
        return _user;
    }

    /**
     * 查询指定用户
     *
     * @param account 用户登录名
     * @return 用户
     */
    public User findUserByAccount(String account) {
        Wrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("account", account);
        User user = this.getOne(queryWrapper);
        if (user == null) throw new ApplicationException("US-0002", "用户不存在。");

        user.setVisitPrivileges(privilegeMapper.findUserVisitPrivileges(account));
        user.setOperationPrivileges(privilegeMapper.findUserOperationPrivileges(account));
        return user;
    }

    /**
     * 修改密码
     *
     * @param userData oldToken 旧密码；newToken 新密码
     * @return 用户
     */
    @Transactional
    public User changePwd(String account, JSONObject userData) {
        if (StringUtils.isBlank(userData.getString("oldToken")))
            throw new ApplicationException("US-0004", "原始密码不能为空。");
        if (StringUtils.isBlank(userData.getString("newToken")))
            throw new ApplicationException("US-0004", "新密码不能为空。");
        if (StringUtils.isBlank(userData.getString("ensureToken")))
            throw new ApplicationException("US-0004", "确认密码不能为空。");

        Wrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("account", account);
        User user = this.getOne(queryWrapper);
        if (user == null) throw new ApplicationException("US-0002", "用户不存在。");

        String oldToken = EncryptionHelper.signDataWithToken(account, userData.getString("oldToken"));
        if (!user.getToken().equals(oldToken)) throw new ApplicationException("US-0003", "原始密码错误。");

        if (!userData.getString("newToken").equals(userData.getString("ensureToken"))) {
            throw new ApplicationException("US-0003", "新密码和确认密码不相同。");
        }

        user.setToken(EncryptionHelper.signDataWithToken(account, userData.getString("newToken")));
        this.updateById(user);
        user.setToken(userData.getString("newToken"));
        return user;
    }

    /**
     * 查询所有封禁用户
     *
     * @param account
     * @param chinaName
     * @param page
     * @return
     */
    public IPage findAllLockUsers(String account, String chinaName, Page page) {
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        return PageWrapper.wrapper(page, new PageInfo(userAccountErrorLoginMapper.queryAllLockUsers(account, chinaName)));
    }

    /**
     * 解锁用户
     *
     * @param id
     */
    @Transactional
    public void unlockUser(String id) {
        UserAccountErrorLogin errorLogin = userAccountErrorLoginMapper.selectById(id);

        QueryWrapper<UserAccountLockRecord> queryWrapper = new QueryWrapper<UserAccountLockRecord>()
                .eq("ACCOUNT", errorLogin.getAccount())
                .isNull("UN_LOCKER");
        UserAccountLockRecord lockRecord = userAccountLockRecordService.getOne(queryWrapper);
        userAccountLockRecordService.updateLockUser(lockRecord);

        userAccountErrorLoginMapper.deleteById(id);

    }
}
