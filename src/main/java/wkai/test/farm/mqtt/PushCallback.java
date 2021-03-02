package wkai.test.farm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wkai.test.farm.entity.DeviceStatus;
import wkai.test.farm.service.DeviceInfoHisService;
import wkai.test.farm.service.DeviceStatusService;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class PushCallback implements MqttCallback {
    public static final String STATUS_TOPIC = "status";
    public static final String CONTROL_TOPIC = "control";
    public static final String SPLIT_CHAR = "\\|";
    private static final Logger LOGGER = LoggerFactory.getLogger(PushCallback.class);
    @Autowired
    private DeviceStatusService deviceStatusService;

    @Autowired
    private DeviceInfoHisService deviceInfoHisService;

    @Autowired
    private MqttPushClient mqttPushClient;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("连接断开");
        try {
            mqttPushClient.connect();
            mqttPushClient.subscribe(PushCallback.STATUS_TOPIC);
            mqttPushClient.publish(PushCallback.CONTROL_TOPIC, "reconnect");
            System.out.println("重新连接成功");
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("重新连接失败");
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String msg = new String(message.getPayload());
        System.out.printf("主题:%s\tQos:%s\t内容:%s\n", topic, message.getQos(), msg);
        if (topic.equals(PushCallback.STATUS_TOPIC)) {
            DeviceStatus ds = this.getDeviceStatusFromMsg(msg);
            if (ds == null) {
                LOGGER.info("error msg：" + msg);
            } else {
                try {
                    int tmp = deviceInfoHisService.insert(ds);
                    if (tmp == 1) {
                        LOGGER.info(ds.getDeviceid() + "his insert ok");
                    } else {
                        LOGGER.info(ds.getDeviceid() + "his insert error");
                    }

                    int count = deviceStatusService.selectById(ds.getDeviceid());
                    if (count == 0) {
                        tmp = deviceStatusService.add(ds);
                    } else {
                        tmp = deviceStatusService.updateById(ds);
                    }

                    if (tmp == 1) {
                        LOGGER.info(ds.getDeviceid() + "status update ok");
                    } else {
                        LOGGER.info(ds.getDeviceid() + "status update error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(token);
        try {
            System.out.println(token.getMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public DeviceStatus getDeviceStatusFromMsg(String msg) {
        DeviceStatus ds = null;
        String[] arr = msg.split(PushCallback.SPLIT_CHAR);
        if (arr.length == 6) {
            ds = new DeviceStatus(arr[0], new BigDecimal(arr[1]), new BigDecimal(arr[2]), new BigDecimal(arr[3]), new BigDecimal(arr[4]), new BigDecimal(arr[5]), new Date());
        } else {

        }
        return ds;
    }

}