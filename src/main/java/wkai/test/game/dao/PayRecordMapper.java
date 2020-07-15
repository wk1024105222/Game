package wkai.test.game.dao;

import wkai.test.game.entity.PayRecord;

public interface PayRecordMapper {
    int insert(PayRecord record);

    int insertSelective(PayRecord record);
}