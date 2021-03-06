package wkai.test.farm.dao;

import wkai.test.farm.entity.DeviceConfig;

public interface DeviceConfigMapper {
    int insert(DeviceConfig record);

    int insertSelective(DeviceConfig record);

    String getOwnerByDeviceId(String deviceId);

    void deleteByDeviceId(String deviceId);

    DeviceConfig selectByDeviceId(String deviceId);

    int updateByUserIdAndDeviceId(DeviceConfig conf);
}