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
import wkai.test.game.common.exception.GameException;
import wkai.test.game.common.response.Result;
import wkai.test.game.common.response.ResultCode;
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
    public Map<String, Object> createOrder(@RequestBody Map<String, String> orderRecord, HttpServletRequest request, HttpServletResponse response ) throws Exception {
        logger.info("createOrder request msg : {}", JSON.toJSONString(orderRecord));
        Map<String, Object> rlt = new HashMap();

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
            logger.error("params format error");
            throw new GameException(ResultCode.PARAM_TYPE_BIND_ERROR);
        }


        if (StringUtils.isBlank(goodsId) || StringUtils.isBlank(roleName) || price==null ||
                null==buyNum || null==totalAmount || null==balaAmount ||
                null==payAmount || StringUtils.isBlank(buyerMobile) ){
            logger.error("params missing error");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        } else if (price.multiply(new BigDecimal(buyNum)).compareTo(totalAmount) != 0 || totalAmount.compareTo(balaAmount.add(payAmount))!=0) {
            logger.error("order amount error price.multiply(new BigDecimal(buyNum)):{},totalAmount:{},balaAmount.add(payAmount):{}",price.multiply(new BigDecimal(buyNum)) , totalAmount ,balaAmount.add(payAmount));
            throw new GameException(ResultCode.ORDER_AMOUNT_ERROR);
        }

        String orderId = orderRecordService.createOrder(goodsId, roleName, price,
                buyNum, buyerMobile, totalAmount, balaAmount,
                payAmount, userId);
        rlt.put("rltCode", "0000");
        rlt.put("rltDesc", "操作成功");
        rlt.put("orderId", orderId);
        return rlt;

    }
}
