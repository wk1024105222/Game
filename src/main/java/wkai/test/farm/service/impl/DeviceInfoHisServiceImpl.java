package wkai.test.farm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.farm.dao.DeviceInfoHisMapper;
import wkai.test.farm.entity.DeviceStatus;
import wkai.test.farm.service.DeviceInfoHisService;

import java.util.List;
import java.util.Map;

@Service
public class DeviceInfoHisServiceImpl implements DeviceInfoHisService {
    @Autowired
    private DeviceInfoHisMapper deviceInfoHisMapper;

    @Override
    public int insert(DeviceStatus deviceStatus) {
        return this.deviceInfoHisMapper.insertSelective(deviceStatus);
    }

    @Override
    public List<Map<String, Object>> getDeviceHisListByDeviceId(String userId, String deviceId, Integer days) {
        return this.deviceInfoHisMapper.selectByDeviceId(userId, deviceId, days);
    }

}
