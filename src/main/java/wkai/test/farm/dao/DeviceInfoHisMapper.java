package wkai.test.farm.dao;

import wkai.test.farm.entity.DeviceInfoHis;
import wkai.test.farm.entity.DeviceStatus;

import java.util.List;
import java.util.Map;

public interface DeviceInfoHisMapper {
    int insert(DeviceInfoHis record);

    int insertSelective(DeviceStatus record);

    List<Map<String, Object>> selectByDeviceId(String userId, String deviceId, Integer days);
}