package wkai.test.game.service;

import wkai.test.game.entity.ChatRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ChatRecordService {

    List<Map<String, Object>> getOrderChatHistory(String orderId, Date limitTime, Integer size);

    int saveChatRecord(ChatRecord chatRecord);

    int chatBeenRead(String msgId);
}
