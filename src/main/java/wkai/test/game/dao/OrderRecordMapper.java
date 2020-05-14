package wkai.test.game.dao;

import wkai.test.game.entity.OrderRecord;

public interface OrderRecordMapper {
    int insert(OrderRecord record);

    int insertSelective(OrderRecord record);
}