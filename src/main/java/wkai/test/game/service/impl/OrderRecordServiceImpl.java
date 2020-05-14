package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.OrderRecordMapper;
import wkai.test.game.entity.OrderRecord;
import wkai.test.game.service.OrderRecordService;

@Service
public class OrderRecordServiceImpl implements OrderRecordService {
    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Override
    public int insertOrderRecord(OrderRecord orderRecord) {
        return orderRecordMapper.insertSelective(orderRecord);
    }
}
