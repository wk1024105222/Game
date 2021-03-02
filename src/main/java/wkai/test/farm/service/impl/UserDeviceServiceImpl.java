package wkai.test.farm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.farm.dao.UserDeviceMapper;
import wkai.test.farm.entity.UserDevice;
import wkai.test.farm.service.UserDeviceService;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {
    @Autowired
    private UserDeviceMapper userDeviceMapper;

    @Override
    public int boundDevice(UserDevice userDevice) {
        return userDeviceMapper.insertSelective(userDevice);
    }

    @Override
    public int unboundDevice(String userId, String deviceId) {
        return userDeviceMapper.deleteByUserIdDeviceId(userId, deviceId);
    }

    @Override
    public int renameDevice(String userId, String deviceId, String name) {
        return userDeviceMapper.updateName(userId, deviceId, name);
    }
}
