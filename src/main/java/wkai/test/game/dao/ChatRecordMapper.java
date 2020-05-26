package wkai.test.game.dao;

import wkai.test.game.entity.ChatRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ChatRecordMapper {
    int insert(ChatRecord record);

    int insertSelective(ChatRecord record);

    List<Map<String, Object>> getByOrderId(String orderId, Date limitTime, Integer size);

    int updateStatus(String msgId, String status);
}