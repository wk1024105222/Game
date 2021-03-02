package wkai.test.farm.service;

import wkai.test.farm.entity.DeviceStatus;

import java.util.List;
import java.util.Map;

public interface DeviceStatusService {
    int add(DeviceStatus ds);

    int selectById(String deviceid);

    int updateById(DeviceStatus ds);

    List<Map<String, Object>> getDeviceStatusListByUserId(String userId);
}
