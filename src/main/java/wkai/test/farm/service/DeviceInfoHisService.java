package wkai.test.farm.service;

import wkai.test.farm.entity.DeviceStatus;

import java.util.List;
import java.util.Map;

public interface DeviceInfoHisService {

    int insert(DeviceStatus deviceStatus);

    List<Map<String, Object>> getDeviceHisListByDeviceId(String userId, String deviceId, Integer days);
}
