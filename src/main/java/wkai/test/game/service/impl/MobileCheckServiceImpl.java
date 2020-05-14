package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.LoginRecordMapper;
import wkai.test.game.dao.MobileCheckRecordMapper;
import wkai.test.game.entity.LoginRecord;
import wkai.test.game.entity.MobileCheckRecord;
import wkai.test.game.service.LoginRecordService;
import wkai.test.game.service.MobileCheckService;

@Service
public class MobileCheckServiceImpl implements MobileCheckService {

    @Autowired
    private MobileCheckRecordMapper mobileCheckRecordMapper;

    @Override
    public int insertMobileCheckRecord(MobileCheckRecord mobileCheckRecord) {
        return mobileCheckRecordMapper.insertSelective(mobileCheckRecord);
    }
}
