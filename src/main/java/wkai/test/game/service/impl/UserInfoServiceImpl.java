package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.UserInfoMapper;
import wkai.test.game.entity.UserInfo;
import wkai.test.game.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectUserInfoBy2Pwd(String userId, String oldLoginPwd, String oldPayPwd) {
        return userInfoMapper.selectUserInfoBy2Pwd(userId, oldLoginPwd, oldPayPwd);
    }

    @Override
    public UserInfo selectUserInfoByLoginPwd(String userId, String loginPwd) {
        return userInfoMapper.selectUserInfoByLoginPwd(userId,loginPwd);
    }

    @Override
    public UserInfo selectUserInfoByPayPwd(String userId, String payPwd) {
        return userInfoMapper.selectUserInfoByPayPwd(userId,payPwd);
    }

    @Override
    public int change2Pwd(String userId, String newLoginPwd, String newPayPwd) {
        return userInfoMapper.change2Pwd(userId, newLoginPwd, newPayPwd);
    }

    @Override
    public int changeLoginPwd(String userId, String newLoginPwd) {
        return userInfoMapper.changeLoginPwd(userId,newLoginPwd);
    }

    @Override
    public int changePayPwd(String userId, String newPayPwd) {
        return userInfoMapper.changePayPwd(userId,newPayPwd);
    }

    @Override
    public int insertUserInfo(UserInfo userInfo) {
        return userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public UserInfo getUserInfoById(String userId) {
        return userInfoMapper.getById(userId);
    }

    @Override
    public boolean exists(String mobile) {
        UserInfo u = userInfoMapper.getById(mobile);
        return null==u?false:true;
    }
}
