package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.DeliverLogMapper;
import wkai.test.game.service.DeliverLogService;

import java.util.List;
import java.util.Map;

@Service
public class DeliverLogServiceImpl implements DeliverLogService {

    @Autowired
    private DeliverLogMapper deliverLogMapper;

    @Override
    public List<Map<String, Object>> getOrderDeliverLog(String orderId) {
        return deliverLogMapper.getByOrderId(orderId);
    }
}
