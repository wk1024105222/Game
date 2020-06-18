package wkai.test.game.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wkai.test.game.common.config.Audience;
import wkai.test.game.common.exception.GameException;
import wkai.test.game.common.response.ResultCode;
import wkai.test.game.entity.OrderRecord;
import wkai.test.game.service.*;
import wkai.test.game.util.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRecordService orderRecordService;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private DeliverLogService deliverLogService;

    @Autowired
    private ChatRecordService chatRecordService;

    @Autowired
    private Audience audience;

    @Autowired
    private UserInfoService userInfoService;

    public static void main(String[] args) {
        Object a = null;
        String b = (String) a;
        System.out.println(b);
    }

    @RequestMapping("/createOrder")
    public Map<String, Object> createOrder(@RequestBody Map<String, String> orderRecord, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("createOrder request msg : {}", JSON.toJSONString(orderRecord));
        Map<String, Object> rlt = new HashMap();

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

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
            price = orderRecord.containsKey("price") ? new BigDecimal(orderRecord.get("price")) : null;
            buyNum = orderRecord.containsKey("buyNum") ? Integer.parseInt(orderRecord.get("buyNum")) : null;
            totalAmount = orderRecord.containsKey("totalAmount") ? new BigDecimal(orderRecord.get("totalAmount")) : null;
            balaAmount = orderRecord.containsKey("balaAmount") ? new BigDecimal(orderRecord.get("balaAmount")) : null;
            payAmount = orderRecord.containsKey("payAmount") ? new BigDecimal(orderRecord.get("payAmount")) : null;
        } catch (NumberFormatException e) {
            logger.error("params format error");
            throw new GameException(ResultCode.PARAM_TYPE_BIND_ERROR);
        }


        if (StringUtils.isBlank(goodsId) || StringUtils.isBlank(roleName) || price == null ||
                null == buyNum || null == totalAmount || null == balaAmount ||
                null == payAmount || StringUtils.isBlank(buyerMobile)) {
            logger.error("params missing error");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        } else if (price.multiply(new BigDecimal(buyNum)).compareTo(totalAmount) != 0 || totalAmount.compareTo(balaAmount.add(payAmount)) != 0) {
            logger.error("order amount error price.multiply(new BigDecimal(buyNum)):{},totalAmount:{},balaAmount.add(payAmount):{}", price.multiply(new BigDecimal(buyNum)), totalAmount, balaAmount.add(payAmount));
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

    @RequestMapping("/getOrderStatus")
    public Map<String, Object> getOrderStatus(@RequestBody Map<String, String> orderRecord, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("getOrderStatus request msg : {}", JSON.toJSONString(orderRecord));
        Map<String, Object> rlt = new HashMap();

        String orderId = orderRecord.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            logger.error("params orderId missing");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        OrderRecord order = this.orderRecordService.getOrderRecordById(orderId);
        if (null == order) {
            logger.error("orderId:{} getOrderStatus error order not exist", orderId);
            throw new GameException(ResultCode.ORDER_NOT_EXIST);
        } else if (StringUtils.isBlank(order.getBuyerId()) || !(order.getBuyerId().equals(userId) && order.getSellerId().equals(userId))) {
            logger.error("orderId:{} getOrderStatus error cant get other's order info", orderId);
            throw new GameException(ResultCode.ORDER_NOT_SELF);
        }

        rlt.put("status", order.getStatus());
        rlt.put("rltCode", ResultCode.SUCCESS.code());
        rlt.put("rltDesc", ResultCode.SUCCESS.message());
        return rlt;
    }

    @RequestMapping("/getMyOrders")
    public Map<String, Object> getMyOrders(@RequestBody Map<String, String> condition, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("getMyOrders request msg : {}", JSON.toJSONString(condition));
        Map<String, Object> rlt = new HashMap();

        String roleType = condition.get("roleType");
        if (StringUtils.isBlank(roleType)) {
            logger.error("params roleType missing");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        String status = condition.containsKey("status") ? condition.get("condition") : null;
        String orderField = condition.containsKey("orderField") ? condition.get("orderField") : "create_time";
        String orderType = condition.containsKey("orderType") ? condition.get("orderType") : "desc";

        Integer pageNum = null;
        Integer pageSize = null;

        try {
            pageNum = condition.containsKey("pageNum") ? Integer.parseInt(condition.get("pageNum")) : 1;
            pageSize = condition.containsKey("pageSize") ? Integer.parseInt(condition.get("pageSize")) : 20;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("params format error ");
            throw new GameException(ResultCode.PARAM_TYPE_BIND_ERROR);
        }

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        List<Map<String, Object>> orderList = null;
        long totalNum = 0;
        int pageCount = 0;
        try {
            Page page = PageHelper.startPage(pageNum, pageSize);
            PageHelper.orderBy("o." + orderField + " " + orderType);
            orderList = orderList = this.orderRecordService.getSomeoneOrderListByStatus(userId, roleType, status);
            totalNum = page.getTotal();
            pageCount = page.getPages();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("orderRecordService.getSomeoneOrderListByStatus error");
            throw new GameException(ResultCode.MYSQL_SELECT_ERROR);
        }
        rlt.put("rltCode", ResultCode.SUCCESS.code());
        rlt.put("rltDesc", ResultCode.SUCCESS.message());
        rlt.put("orderList", orderList);
        rlt.put("pageNum", pageNum);
        rlt.put("pageSize", pageSize);
        rlt.put("pageCount", pageCount);
        rlt.put("totalNum", totalNum);
        return rlt;
    }

    @RequestMapping("/getOrderInfo")
    public Map<String, Object> getOrderInfo(@RequestBody Map<String, String> condition, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("getOrderInfo request msg : {}", JSON.toJSONString(condition));
        Map<String, Object> rlt = new HashMap();

        String orderId = condition.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            logger.error("params orderId missing");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        Map<String, Object> orderInfo = null;
        List<Map<String, Object>> chatHistory = null;
        List<Map<String, Object>> deliverLog = null;
        String step = "";
        try {
            step = "orderRecordService.getOrderInfoById";
            orderInfo = this.orderRecordService.getOrderInfoById(orderId);
            if (null == orderInfo) {
                logger.error("orderId:{} getOrderStatus error order not exist", orderId);
                throw new GameException(ResultCode.ORDER_NOT_EXIST);
            } else if (StringUtils.isBlank((String) orderInfo.get("buyerId")) || !(orderInfo.get("buyerId").equals(userId) || orderInfo.get("sellerId").equals(userId))) {
                logger.error("orderId:{} getOrderStatus error cant get other's order info", orderId);
                throw new GameException(ResultCode.ORDER_NOT_SELF);
            }

            step = "chatRecordService.getOrderChatHistory";
            chatHistory = chatRecordService.getOrderChatHistory(orderId, null, 20);

            step = "deliverLogService.getOrderDeliverLog";
            deliverLog = deliverLogService.getOrderDeliverLog(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{} error", step);
            throw new GameException(ResultCode.MYSQL_SELECT_ERROR);
        }

        rlt.put("orderInfo", orderInfo);
        rlt.put("chatHistory", chatHistory);
        rlt.put("deliverLog", deliverLog);
        rlt.put("rltCode", ResultCode.SUCCESS.code());
        rlt.put("rltDesc", ResultCode.SUCCESS.message());
        return rlt;
    }

    @RequestMapping("/sendGoods")
    public Map<String, Object> sendGoods(@RequestBody Map<String, String> condition, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("sendGoods request msg : {}", JSON.toJSONString(condition));
        Map<String, Object> rlt = new HashMap();

        String orderId = condition.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            logger.error("params orderId missing");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        try {
            int updateNum = this.orderRecordService.sendGoods(userId, orderId);
            if (0 == updateNum) {
                logger.error("orderId:{} orderRecordService.sendGoods error cant operate other's order", orderId);
                throw new GameException(ResultCode.ORDER_NOT_SELF);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("orderRecordService.sendGoods error");
            throw new GameException(ResultCode.MYSQL_UPDATE_ERROR);
        }

        rlt.put("rltCode", ResultCode.SUCCESS.code());
        rlt.put("rltDesc", ResultCode.SUCCESS.message());
        return rlt;
    }

    @RequestMapping("/receiveGoods")
    public Map<String, Object> receiveGoods(@RequestBody Map<String, String> condition, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("receiveGoods request msg : {}", JSON.toJSONString(condition));
        Map<String, Object> rlt = new HashMap();

        String orderId = condition.get("orderId");
        if (StringUtils.isBlank(orderId)) {
            logger.error("params orderId missing");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        try {
            int updateNum = this.orderRecordService.receiveGoods(userId, orderId);
            if (0 == updateNum) {
                logger.error("orderId:{} orderRecordService.receiveGoods error cant operate other's order", orderId);
                throw new GameException(ResultCode.ORDER_NOT_SELF);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("orderRecordService.receiveGoods error");
            throw new GameException(ResultCode.MYSQL_UPDATE_ERROR);
        }

        rlt.put("rltCode", ResultCode.SUCCESS.code());
        rlt.put("rltDesc", ResultCode.SUCCESS.message());
        return rlt;
    }

}
