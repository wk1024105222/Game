package wkai.test.game.dao;

import wkai.test.game.entity.MobileCheckRecord;

public interface MobileCheckRecordMapper {
    int insert(MobileCheckRecord record);

    int insertSelective(MobileCheckRecord record);
}