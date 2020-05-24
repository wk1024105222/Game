package wkai.test.game.service;

import java.util.List;
import java.util.Map;

public interface DeliverLogService {

    List<Map<String, Object>> getOrderDeliverLog(String orderId);
}
