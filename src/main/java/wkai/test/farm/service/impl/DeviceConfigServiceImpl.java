package wkai.test.farm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.farm.dao.DeviceConfigMapper;
import wkai.test.farm.entity.DeviceConfig;
import wkai.test.farm.service.DeviceConfigService;

@Service
public class DeviceConfigServiceImpl implements DeviceConfigService {
    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Override
    public int add(DeviceConfig deviceConfig) {
        return 0;
    }

    @Override
    public DeviceConfig getByDeviceId(String deviceId) {
        return deviceConfigMapper.selectByDeviceId(deviceId);
    }

    @Override
    public int updateByUserIdAndDeviceId(DeviceConfig conf) {
        return deviceConfigMapper.updateByUserIdAndDeviceId(conf);
    }
}
