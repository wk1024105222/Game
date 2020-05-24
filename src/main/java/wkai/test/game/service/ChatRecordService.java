package wkai.test.game.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ChatRecordService {

    List<Map<String, Object>> getOrderChatHistory(String orderId, Date limitTime, Integer size);
}
