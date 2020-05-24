package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.ChatRecordMapper;
import wkai.test.game.service.ChatRecordService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    @Autowired
    private ChatRecordMapper chatRecordMapper;

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }

    @Override
    public List<Map<String, Object>> getOrderChatHistory(String orderId, Date limitTime, Integer size) {
        return chatRecordMapper.getByOrderId(orderId, limitTime, size);
    }
}
