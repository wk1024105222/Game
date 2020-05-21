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
import wkai.test.game.common.response.Result;
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
    private GoodsInfoService goodsInfoService;

    @Autowired
    private Audience audience;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/createOrder")
    public Map<String, String> createOrder(@RequestBody Map<String, String> orderRecord, HttpServletRequest request, HttpServletResponse response ) throws Exception {
        Map<String, String> rlt = new HashMap();
        rlt.put("rltCode", "9999");
        rlt.put("rltDesc", "操作失败");

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7),audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        String goodsId = orderRecord.get("goodsId");
        String roleName = orderRecord.get("roleName");
        String buyerMobile = orderRecord.get("buyerMobile");
        BigDecimal price = null;
        Integer buyNum = null;
        BigDecimal totalAmount = null;
        BigDecimal balaAmount = null;
        BigDecimal payAmount = null;
        try {
            price = orderRecord.containsKey("price")?new BigDecimal(orderRecord.get("price")):null;
            buyNum = orderRecord.containsKey("buyNum")?Integer.parseInt(orderRecord.get("buyNum")):null;
            totalAmount = orderRecord.containsKey("totalAmount")?new BigDecimal(orderRecord.get("totalAmount")):null;
            balaAmount = orderRecord.containsKey("balaAmount")?new BigDecimal(orderRecord.get("balaAmount")):null;
            payAmount = orderRecord.containsKey("payAmount")?new BigDecimal(orderRecord.get("payAmount")):null;
        } catch (NumberFormatException e) {
            rlt.put("rltCode", "9998");
            rlt.put("rltDesc", "请求参数异常");
            return rlt;
        }

        if (StringUtils.isBlank(goodsId) || StringUtils.isBlank(roleName) || price==null ||
                null==buyNum || null==totalAmount || null==balaAmount ||
                null==payAmount || StringUtils.isBlank(buyerMobile) ){
            rlt.put("rltCode", "9998");
            rlt.put("rltDesc", "请求参数异常");
            return rlt;
        } else if (price.doubleValue() * buyNum != totalAmount.doubleValue() || totalAmount.doubleValue()!=balaAmount.doubleValue()+payAmount.doubleValue()) {
            rlt.put("rltCode", "0008");
            rlt.put("rltDesc", "订单总金额异常,请重新提交");
            return rlt;
        }

        Result r = orderRecordService.createOrder(goodsId, roleName, price,
                buyNum, buyerMobile, totalAmount, balaAmount,
                payAmount, userId);
        rlt.put("rltCode", r.getRltCode());
        rlt.put("rltDesc", r.getRltDesc());


//        else {
//
//
//            String  code =
//
//            try {
//                int tmp = orderRecordService.insertOrderRecord(or);
//                if (tmp == 1) {
//                    rlt.put("orderId", orderId);
//                    rlt.put("rltCode", "0000");
//                    rlt.put("rltDesc", "成功");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("orderRecordService.insertOrderRecord error: {}", JSON.toJSONString(or));
//            }
//        }

        return rlt;

    }
}
