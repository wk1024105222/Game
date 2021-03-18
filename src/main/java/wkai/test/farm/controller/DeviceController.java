package wkai.test.farm.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import wkai.test.farm.entity.DeviceConfig;
import wkai.test.farm.entity.UserDevice;
import wkai.test.farm.service.DeviceConfigService;
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
    @Autowired
    private DeviceConfigService deviceConfigService;

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
        DeviceConfig config = deviceConfigService.getByDeviceId(deviceId);
        rlt.put("config", config);
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
            userDeviceService.boundDevice(new UserDevice(userId, deviceId, name));
            rlt.put("rltCode", "0000");
            rlt.put("rltDesc", "绑定成功");
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
            userDeviceService.unboundDevice(userId, deviceId);
            rlt.put("rltCode", "0000");
            rlt.put("rltDesc", "解绑成功");
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

    @RequestMapping("/modifyConfig")
    @JwtIgnore
    public Map<String, Object> modifyConfig(@RequestBody Map<String, String> config, HttpServletResponse response) {
        String userId = config.get("owner");
        String deviceId = config.get("deviceid");

        ObjectMapper objectMapper = new ObjectMapper();
        DeviceConfig conf = objectMapper.convertValue(config, DeviceConfig.class);

        Map<String, Object> rlt = new HashMap();
        if (userId == null || deviceId == null || userId.equals("") || deviceId.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "缺少必填参数");
            return rlt;
        }

        try {
            int tmp = deviceConfigService.updateByUserIdAndDeviceId(conf);

            if (tmp == 1) {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "修改成功");
            } else {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "设备所有者才能修改");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "修改失败");
        }
        return rlt;
    }

    @RequestMapping("/wxLogin")
    @JwtIgnore
    public Map<String, Object> wxLogin(@RequestBody Map<String, String> config, HttpServletResponse response) {
        String code = config.get("code");

        Map<String, Object> rlt = new HashMap();
        if (code == null || code.equals("")) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "缺少必填参数");
            return rlt;
        }

        String appid = "wxd70a3ec7c8ebcd82";
        String secret = "ead766d04d7494eefffa427b52687c5c";

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            JSONObject object = JSONObject.parseObject(sessionData);
            if (!object.containsKey("errcode")) {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "请求成功");
                rlt.put("openid", (String) object.get("openid"));
                rlt.put("session_key", (String) object.get("session_key"));
            } else {
                rlt.put("rltCode", "0001");
                rlt.put("rltDesc", "请求失败");
            }
        } else {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "请求失败");
        }
        return rlt;
    }
}
