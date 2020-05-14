package wkai.test.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.common.config.SmsConfig;
import wkai.test.game.service.SmsService;
import wkai.test.game.util.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SmsServiceImpl implements SmsService {
    private  final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private SmsConfig smsConfig;

    @Override
    public String sendSmsCheckcode(String mobile, String code) {
        String rlt = "00000";

        String host = smsConfig.getHost();
        String path = smsConfig.getPath();
        String method = smsConfig.getMethod();
        String appcode = smsConfig.getAppcode();
        String templateId = smsConfig.getTemplateId();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        querys.put("param", "code:" + code);
        querys.put("tpl_id", templateId);
        Map<String, String> bodys = new HashMap<String, String>();
        logger.info("sendMobileCheckCodeRequest:{}",querys.toString());

//        try {
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//
//            String req_str = EntityUtils.toString(response.getEntity());
//            logger.info("sendMobileCheckCodeResponse:{}",req_str);
//
//            JSONObject jsonObject = JSON.parseObject(req_str);
//            rlt = jsonObject.getString("return_code");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return rlt;
    }


}
