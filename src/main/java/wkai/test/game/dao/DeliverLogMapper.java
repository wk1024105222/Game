package wkai.test.game.dao;

import wkai.test.game.entity.deliverLog;

import java.util.List;
import java.util.Map;

public interface DeliverLogMapper {
    int insert(deliverLog record);

    int insertSelective(deliverLog record);

    List<Map<String, Object>> getByOrderId(String orderId);
}