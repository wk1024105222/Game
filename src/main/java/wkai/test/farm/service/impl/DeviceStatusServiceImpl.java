package wkai.test.farm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.farm.dao.DeviceStatusMapper;
import wkai.test.farm.entity.DeviceStatus;
import wkai.test.farm.service.DeviceStatusService;

import java.util.List;
import java.util.Map;

@Service
public class DeviceStatusServiceImpl implements DeviceStatusService {
    @Autowired
    private DeviceStatusMapper deviceStatusMapper;

    @Override
    public int add(DeviceStatus ds) {
        return deviceStatusMapper.insert(ds);
    }

    @Override
    public int selectById(String deviceid) {
        return deviceStatusMapper.selectCount(deviceid);
    }

    @Override
    public int updateById(DeviceStatus ds) {
        return deviceStatusMapper.updateById(ds);
    }

    @Override
    public List<Map<String, Object>> getDeviceStatusListByUserId(String userId) {
        return deviceStatusMapper.selectByUserId(userId);
    }
}
