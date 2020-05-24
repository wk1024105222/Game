package wkai.test.game.service;

import wkai.test.game.common.response.Result;
import wkai.test.game.entity.OrderRecord;

import java.math.BigDecimal;

public interface OrderRecordService {
    int insertOrderRecord(OrderRecord or);

    String createOrder(String goodsId, String roleName, BigDecimal price, Integer buyNum,
                       String buyerMobile, BigDecimal totalAmount, BigDecimal balaAmount, BigDecimal payAmount,
                       String userId) throws Exception;
}
