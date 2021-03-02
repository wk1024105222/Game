package wkai.test.farm.dao;

import wkai.test.farm.entity.DeviceStatus;

import java.util.List;
import java.util.Map;

public interface DeviceStatusMapper {
    int insert(DeviceStatus record);

    int insertSelective(DeviceStatus record);

    int selectCount(String deviceid);

    int updateById(DeviceStatus ds);

    List<Map<String, Object>> selectByUserId(String userId);
}