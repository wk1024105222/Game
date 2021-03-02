package wkai.test.farm.dao;

import wkai.test.farm.entity.UserDevice;

public interface UserDeviceMapper {
    int insert(UserDevice record);

    int insertSelective(UserDevice record);

    int deleteByUserIdDeviceId(String userId, String deviceId);

    int updateName(String userId, String deviceId, String name);
}