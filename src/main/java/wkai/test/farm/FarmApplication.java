package wkai.test.farm;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import wkai.test.farm.mqtt.MqttPushClient;
import wkai.test.farm.mqtt.PushCallback;


@SpringBootApplication
@MapperScan("wkai.test.farm.dao")
public class FarmApplication {
    public static void main(String[] args) throws MqttException {
        SpringApplication application = new SpringApplication(FarmApplication.class);
        ConfigurableApplicationContext context = application.run(args);
        MqttPushClient client = context.getBean(MqttPushClient.class);
        client.connect();
        client.subscribe(PushCallback.STATUS_TOPIC);
        client.publish(PushCallback.CONTROL_TOPIC, "close");
    }

}