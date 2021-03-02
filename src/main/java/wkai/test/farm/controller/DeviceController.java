package wkai.test.farm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wkai.test.farm.entity.UserDevice;
import wkai.test.farm.service.DeviceInfoHisService;
import wkai.test.farm.service.DeviceStatusService;
import wkai.test.farm.service.UserDeviceService;
import wkai.test.game.annotation.JwtIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeviceController {
    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceStatusService deviceStatusService;
    @Autowired
    private DeviceInfoHisService deviceInfoHisService;
    @Autowired
    private UserDeviceService userDeviceService;

    /**
     * userId
     *
     * @param userInfo
     * @param response
     * @return
     */
    @RequestMapping("/getDeviceStatusList")
    @JwtIgnore
    public Map<String, Object> getDeviceStatusList(@RequestBody Map<String, String> userInfo, HttpServletResponse response) {
        String userId = userInfo.get("userId");
        Map<String, Object> rlt = new HashMap();
        if (userId == null || userId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "userId为空");
            return rlt;
        }
        List<Map<String, Object>> devices = deviceStatusService.getDeviceStatusListByUserId(userId);
        rlt.put("devices", devices);
        rlt.put("rltCode", "0000");
        rlt.put("rltDesc", "查询成功");

        return rlt;
    }

    @RequestMapping("/getDeviceHisList")
    @JwtIgnore
    public Map<String, Object> getDeviceHisList(@RequestBody Map<String, String> userInfo, HttpServletResponse response) {
        String userId = userInfo.get("userId");
        String deviceId = userInfo.get("deviceId");
        String days = userInfo.get("days");
        Map<String, Object> rlt = new HashMap();
        if (userId == null || userId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "userId为空");
            return rlt;
        }
        if (deviceId == null || deviceId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "deviceId为空");
            return rlt;
        }
        if (days == null || days.equals("")) {
            days = "1";
        }

        List<Map<String, Object>> deviceHises = deviceInfoHisService.getDeviceHisListByDeviceId(userId, deviceId, new Integer(days));
        rlt.put("devices", deviceHises);
        rlt.put("rltCode", "0000");
        rlt.put("rltDesc", "查询成功");

        return rlt;
    }

    @RequestMapping("/boundDevice")
    @JwtIgnore
    public Map<String, Object> boundDevice(@RequestBody Map<String, String> userInfo, HttpServletResponse response) {
        String userId = userInfo.get("userId");
        String deviceId = userInfo.get("deviceId");
        String name = userInfo.get("name");

        Map<String, Object> rlt = new HashMap();
        if (userId == null || userId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "userId为空");
            return rlt;
        }
        if (deviceId == null || deviceId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "deviceId为空");
            return rlt;
        }

        try {
            int tmp = userDeviceService.boundDevice(new UserDevice(userId, deviceId, name));

            if (tmp == 1) {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "绑定成功");
            } else {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "绑定失败");
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "该设备已绑定");
        }

        return rlt;
    }

    @RequestMapping("/unboundDevice")
    @JwtIgnore
    public Map<String, Object> unboundDevice(@RequestBody Map<String, String> userInfo, HttpServletResponse response) {
        String userId = userInfo.get("userId");
        String deviceId = userInfo.get("deviceId");

        Map<String, Object> rlt = new HashMap();
        if (userId == null || userId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "userId为空");
            return rlt;
        }
        if (deviceId == null || deviceId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "deviceId为空");
            return rlt;
        }

        try {
            int tmp = userDeviceService.unboundDevice(userId, deviceId);

            if (tmp == 1) {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "解绑成功");
            } else {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "尚未绑定");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "解绑失败");
        }

        return rlt;
    }

    @RequestMapping("/renameDevice")
    @JwtIgnore
    public Map<String, Object> renameDevice(@RequestBody Map<String, String> userInfo, HttpServletResponse response) {
        String userId = userInfo.get("userId");
        String deviceId = userInfo.get("deviceId");
        String name = userInfo.get("name");

        Map<String, Object> rlt = new HashMap();
        if (userId == null || deviceId == null || name == null ||
                userId.equals("") || deviceId.equals("") || name.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "缺少必填参数");
            return rlt;
        }

        try {
            int tmp = userDeviceService.renameDevice(userId, deviceId, name);

            if (tmp == 1) {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "重命名成功");
            } else {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "尚未绑定");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "重命名失败");
        }

        return rlt;
    }
}
