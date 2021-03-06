package wkai.test.farm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wkai.test.farm.dao.DeviceConfigMapper;
import wkai.test.farm.dao.UserDeviceMapper;
import wkai.test.farm.entity.DeviceConfig;
import wkai.test.farm.entity.UserDevice;
import wkai.test.farm.service.UserDeviceService;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {
    @Autowired
    private UserDeviceMapper userDeviceMapper;
    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Override
    @Transactional
    public void boundDevice(UserDevice userDevice) {
        userDeviceMapper.insertSelective(userDevice);
        //判断机器是否第一次绑定
        String owner = deviceConfigMapper.getOwnerByDeviceId(userDevice.getDeviceid());
        if (owner == null || owner.equals("")) {
            DeviceConfig conf = new DeviceConfig();
            conf.setDeviceid(userDevice.getDeviceid());
            conf.setOwner(userDevice.getUserid());
            deviceConfigMapper.insertSelective(conf);
        }
    }

    @Override
    @Transactional
    public void unboundDevice(String userId, String deviceId) {
        String owner = deviceConfigMapper.getOwnerByDeviceId(deviceId);
        //判断是否设备所有者 执行解绑操作
        if (owner.equals(userId)) {
            userDeviceMapper.deleteByDeviceId(deviceId);
            deviceConfigMapper.deleteByDeviceId(deviceId);
        } else {
            userDeviceMapper.deleteByUserIdDeviceId(userId, deviceId);
        }
    }

    @Override
    public int renameDevice(String userId, String deviceId, String name) {
        return userDeviceMapper.updateName(userId, deviceId, name);
    }
}
