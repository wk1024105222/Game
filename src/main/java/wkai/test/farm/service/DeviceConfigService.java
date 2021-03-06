package wkai.test.farm.service;

import wkai.test.farm.entity.DeviceConfig;

public interface DeviceConfigService {

    int add(DeviceConfig deviceConfig);

    DeviceConfig getByDeviceId(String deviceId);

    int updateByUserIdAndDeviceId(DeviceConfig conf);
}
