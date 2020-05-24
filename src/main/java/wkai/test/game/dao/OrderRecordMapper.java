package wkai.test.game.dao;

import wkai.test.game.entity.OrderRecord;

import java.util.List;
import java.util.Map;

public interface OrderRecordMapper {
    int insert(OrderRecord record);

    int insertSelective(OrderRecord record);

    OrderRecord getById(String orderId);

    List<Map<String, Object>> getByUserIdAndStatus(String userId, String roleType, String status);

    Map<String, Object> getOrderInfoById(String orderId);

    int updateStatusBySeller(String userId, String orderId, String status);

    int updateStatusByBuyer(String userId, String orderId, String status);
}