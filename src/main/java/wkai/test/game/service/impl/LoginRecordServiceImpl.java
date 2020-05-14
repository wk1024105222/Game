package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.LoginRecordMapper;
import wkai.test.game.dao.UserInfoMapper;
import wkai.test.game.entity.LoginRecord;
import wkai.test.game.entity.UserInfo;
import wkai.test.game.service.LoginRecordService;
import wkai.test.game.service.UserInfoService;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public int insertLoginRecord(LoginRecord loginRecord) {
        return loginRecordMapper.insertSelective(loginRecord);
    }
}
