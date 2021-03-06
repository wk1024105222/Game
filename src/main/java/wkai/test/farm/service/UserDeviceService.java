package wkai.test.farm.service;

import wkai.test.farm.entity.UserDevice;

public interface UserDeviceService {
    void boundDevice(UserDevice userDevice);

    void unboundDevice(String userId, String deviceId);

    int renameDevice(String userId, String deviceId, String name);
}
