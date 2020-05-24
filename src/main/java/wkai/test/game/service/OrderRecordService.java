package wkai.test.game.service;

import wkai.test.game.entity.OrderRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderRecordService {
    int insertOrderRecord(OrderRecord or);

    String createOrder(String goodsId, String roleName, BigDecimal price, Integer buyNum,
                       String buyerMobile, BigDecimal totalAmount, BigDecimal balaAmount, BigDecimal payAmount,
                       String userId) throws Exception;

    OrderRecord getOrderRecordById(String orderId);

    List<Map<String, Object>> getSomeoneOrderListByStatus(String userId, String roleType, String status);

    Map<String, Object> getOrderInfoById(String orderId);

    int sendGoods(String userId, String orderId);

    int receiveGoods(String userId, String orderId);
}
