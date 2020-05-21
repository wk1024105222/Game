package wkai.test.game.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wkai.test.game.entity.UserInfo;

import java.math.BigDecimal;

@Mapper
public interface UserInfoMapper {
    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectUserInfoBy2Pwd(String userId, String oldLoginPwd, String oldPayPwd);

    UserInfo selectUserInfoByLoginPwd(String userId, String loginPwd);

    UserInfo selectUserInfoByPayPwd(String userId, String payPwd);

    int change2Pwd(String userId, String newLoginPwd, String newPayPwd);

    int changeLoginPwd(String userId, String newLoginPwd);

    int changePayPwd(String userId, String newPayPwd);

    UserInfo getById(String userId);

    int updateBalance(String userId, BigDecimal balaAmount);
}