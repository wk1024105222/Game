package wkai.test.game.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wkai.test.game.annotation.JwtIgnore;
import wkai.test.game.entity.*;
import wkai.test.game.service.*;
import wkai.test.game.common.config.Audience;
import wkai.test.game.util.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRecordService orderRecordService;

    @Autowired
    private Audience audience;

    @RequestMapping("/createOrder")
    public Map<String, String> createOrder(@RequestBody Map<String, String> orderRecord, HttpServletRequest request, HttpServletResponse response ) {
        Map<String, String> rlt = new HashMap();
        rlt.put("rltCode", "9999");
        rlt.put("rltDesc", "操作失败");

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7),audience.getBase64Secret());

        String orderId = UUID.randomUUID().toString().replace("-", "");
        String goodsId = orderRecord.get("goodsId");
        String roleName = orderRecord.get("roleName");
        String price = orderRecord.get("price");
        String buyNum = orderRecord.get("buyNum");
        String totalAmount = orderRecord.get("totalAmount");
        String balaAmount = orderRecord.get("balaAmount");
        String payAmount = orderRecord.get("payAmount");
        String buyerMobile = orderRecord.get("buyerMobile");

        if (StringUtils.isBlank(goodsId) || StringUtils.isBlank(roleName) || StringUtils.isBlank(price) ||
                StringUtils.isBlank(buyNum) || StringUtils.isBlank(totalAmount) || StringUtils.isBlank(balaAmount) ||
                StringUtils.isBlank(payAmount) || StringUtils.isBlank(buyerMobile) ){
            rlt.put("rltCode", "9998");
            rlt.put("rltDesc", "请求参数异常");
        } else {

            OrderRecord or = new OrderRecord(orderId, goodsId, roleName, new BigDecimal(price),
                    Integer.parseInt(buyNum), buyerMobile, new BigDecimal(totalAmount), new BigDecimal(balaAmount),
                    new BigDecimal(payAmount), "1", new Date(), userId, userId) ;

            try {
                int tmp = orderRecordService.insertOrderRecord(or);
                if (tmp == 1) {
                    rlt.put("orderId",orderId);
                    rlt.put("rltCode", "0000");
                    rlt.put("rltDesc", "成功");
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("orderRecordService.insertOrderRecord error: {}", JSON.toJSONString(or));
            }

        }
        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        return rlt;

    }
}
