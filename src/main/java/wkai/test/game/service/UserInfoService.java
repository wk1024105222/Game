package wkai.test.game.service;

import wkai.test.game.entity.UserInfo;

public interface UserInfoService {

    UserInfo selectUserInfoBy2Pwd(String userId, String oldLoginPwd, String oldPayPwd);

    UserInfo selectUserInfoByLoginPwd(String userId, String loginPwd);

    UserInfo selectUserInfoByPayPwd(String userId, String payPwd);

    int change2Pwd(String userId, String newLoginPwd, String newPayPwd);

    int changeLoginPwd(String userId, String newLoginPwd);

    int changePayPwd(String userId, String newPayPwd);

    int insertUserInfo(UserInfo userInfo);
}