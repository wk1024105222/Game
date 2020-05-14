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
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private MobileCheckService mobileCheckService;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private OrderRecordService orderRecordService;

    @Autowired
    private Audience audience;

    @Autowired
    private SmsService smsService;

    private ConcurrentHashMap<String,String> mobileCheckCodeMap = new  ConcurrentHashMap<String,String>();

    @RequestMapping("/login")
    @JwtIgnore
    public Map<String, String> login(@RequestBody Map<String, String> userInfo, HttpServletResponse response) {
        String userId = userInfo.get("userId");
        String loginPwd = userInfo.get("loginPwd");

        UserInfo user = userInfoService.selectUserInfoByLoginPwd(userId, loginPwd);

        Map<String, String> rlt = new HashMap();
        if (null == user) {
            rlt.put("rltCode", "0001");
            rlt.put("rltDesc", "用户名或密码异常");

        } else {
            String sessionId = UUID.randomUUID().toString().replace("-", "");

            int tmp = loginRecordService.insertLoginRecord(new LoginRecord(sessionId, new Date(), userId, "1"));

            if (tmp == 1) {
//                logger.info("### 登录成功, token={} ###", token);
//                rlt.put("token", token);
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "成功");
            } else {
                rlt.put("rltCode", "9999");
                rlt.put("rltDesc", "失败");
            }

            String token = JwtTokenUtil.createJWT(userId, "user", audience);
            response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        }
        return rlt;
    }

    @RequestMapping("/changeLoginPwd")
    @JwtIgnore
    public Map<String, String> changeLoginPwd(@RequestBody Map<String, String> request) {
        Map<String, String> rlt = new HashMap();

        String userId = request.get("userId");
        String oldLoginPwd = request.get("oldLoginPwd");
        String newLoginPwd = request.get("newLoginPwd");
        String oldPayPwd = request.get("oldPayPwd");
        String newPayPwd = request.get("newPayPwd");
        String changeType = request.get("changeType");

        UserInfo user = null;

        if (changeType.equals("2")) {
            user = userInfoService.selectUserInfoBy2Pwd(userId, oldLoginPwd, oldPayPwd);
        } else if (changeType.equals("0")) {
            user = userInfoService.selectUserInfoByLoginPwd(userId, oldLoginPwd);
        } else if (changeType.equals("1")) {
            user = userInfoService.selectUserInfoByPayPwd(userId, oldPayPwd);
        } else {
            rlt.put("rltCode", "9998");
            rlt.put("rltDesc", "请求参数异常");
            return rlt;
        }

        if (null == user) {
            rlt.put("rltCode", "0002");
            rlt.put("rltDesc", "原密码异常");
            return rlt;

        } else {
            int changRlt = -1;
            if (changeType.equals("2")) {
                changRlt = userInfoService.change2Pwd(userId, newLoginPwd, newPayPwd);
            } else if (changeType.equals("0")) {
                changRlt = userInfoService.changeLoginPwd(userId, newLoginPwd);
            } else if (changeType.equals("1")) {
                changRlt = userInfoService.changePayPwd(userId, newPayPwd);
            }

            if (changRlt == 1) {
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "成功");
                return rlt;
            } else {
                rlt.put("rltCode", "9999");
                rlt.put("rltDesc", "操作失败");
                return rlt;
            }
        }
    }

    @RequestMapping("/sendMobileCheckCode")
    @JwtIgnore
    public Map<String, String> getMobileCheckCode(@RequestBody Map<String, String> request) {
        Map<String, String> rlt = new HashMap();
        String mobile = request.get("mobile");
        String purpose = request.get("purpose");

        int code = (new Date().getTime() + mobile).hashCode() % 1000000;
        String checkCode = String.valueOf(code >= 0 ? code : code * -1);

        //redis.ince('checkCode_YYYYMMDD',1);
        String traceId = UUID.randomUUID().toString().replace("-", "");

        String resultCode = smsService.sendSmsCheckcode(mobile, checkCode);

        if ( resultCode.equals("00000")) {
            //redis.set("checkCode_"+traceid,code,15*3600)
            mobileCheckCodeMap.put("checkCode_"+traceId,checkCode);
            rlt.put("rltCode", "0000");
            rlt.put("rltDesc", "成功");
            rlt.put("traceId",traceId);
        } else {
            rlt.put("rltCode", "9999");
            rlt.put("rltDesc", "操作失败");
        }

        MobileCheckRecord tmp = new MobileCheckRecord(traceId,
                mobile, purpose, checkCode, resultCode, new Date() ,new Date());
        try {
            mobileCheckService.insertMobileCheckRecord(tmp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("mobileCheckService.insertMobileCheckRecord error: {}",JSON.toJSONString(tmp));
        }
        return rlt;
    }

    @RequestMapping("/register")
    @JwtIgnore
    public Map<String, String> register(@RequestBody Map<String, String> userInfo) {
        Map<String, String> rlt = new HashMap();
        rlt.put("rltCode", "9999");
        rlt.put("rltDesc", "操作失败");

        String userId = userInfo.get("userId");
        String loginPwd = userInfo.get("loginPwd");
        String payPwd = userInfo.get("payPwd");
        String checkCode = userInfo.get("checkCode");
        String traceId = userInfo.get("traceId");
        String userName = userInfo.get("userName");

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(loginPwd) || StringUtils.isBlank(payPwd) ||
                StringUtils.isBlank(checkCode) || StringUtils.isBlank(userId) || StringUtils.isBlank(userName)) {
            rlt.put("rltCode", "9998");
            rlt.put("rltDesc", "请求参数异常");

        } else {
            String codeInMem = mobileCheckCodeMap.get("checkCode_" + traceId);
            if (codeInMem != null && checkCode.equals(codeInMem)) {
                UserInfo user = new UserInfo(userId, loginPwd, payPwd, userName, new Date());
                try {
                    int tmp = userInfoService.insertUserInfo(user);
                    if (tmp == 1) {
                        rlt.put("rltCode", "0000");
                        rlt.put("rltDesc", "成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("userInfoService.insertUserInfo error: {}", JSON.toJSONString(user));
                }
            } else {
                rlt.put("rltCode", "0003");
                rlt.put("rltDesc", "验证码不正确");
            }
        }
        return rlt;
    }

    @RequestMapping("/createGoods")
    public Map<String, String> createGoods(@RequestBody Map<String, String> goodsInfo, HttpServletRequest request, HttpServletResponse response ) {
        Map<String, String> rlt = new HashMap();
        rlt.put("rltCode", "9999");
        rlt.put("rltDesc", "操作失败");

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7),audience.getBase64Secret());

        String gameId = goodsInfo.get("gameId");
        String areaId = goodsInfo.get("areaId");
        String serverId = goodsInfo.get("serverId");
        String campId = goodsInfo.get("campId");
        String goodsType = goodsInfo.get("goodsType");
        String traceType = goodsInfo.get("traceType");
        String containNum = goodsInfo.get("containNum");
        String price = goodsInfo.get("price");
        String stock = goodsInfo.get("stock");
        String roleName = goodsInfo.get("roleName");
        String mobile = goodsInfo.get("mobile");
        String tranHourBegin = goodsInfo.get("tranHourBegin");
        String tranHourEnd = goodsInfo.get("tranHourEnd");
        String expireDays = goodsInfo.get("expireDays");

        if (StringUtils.isBlank(gameId) || StringUtils.isBlank(areaId) || StringUtils.isBlank(serverId) ||
                StringUtils.isBlank(campId) || StringUtils.isBlank(goodsType) || StringUtils.isBlank(traceType) ||
                StringUtils.isBlank(containNum) || StringUtils.isBlank(price) || StringUtils.isBlank(stock) ||
                StringUtils.isBlank(roleName) || StringUtils.isBlank(mobile) || StringUtils.isBlank(tranHourBegin) ||
                StringUtils.isBlank(tranHourEnd) || StringUtils.isBlank(expireDays)) {
            rlt.put("rltCode", "9998");
            rlt.put("rltDesc", "请求参数异常");

        } else {
            String goodsId = UUID.randomUUID().toString().replace("-", "");
            String title = price+"元="+containNum+"金";
            String status = "0";
            String recommendRank = "0";
            Date createTime = new Date();
            Calendar ca = Calendar.getInstance();
            ca.setTime(createTime);
            ca.add(Calendar.DATE, Integer.parseInt(expireDays));
            Date expireTime =  ca.getTime();

            GoodsInfo goods = new GoodsInfo(goodsId, title, gameId, areaId,
                    serverId, campId, goodsType, traceType,
                    Integer.parseInt(containNum), new BigDecimal(price), Integer.parseInt(stock), roleName,
                    mobile, tranHourBegin, tranHourEnd, Integer.parseInt(expireDays),
                    expireTime, recommendRank, status, createTime,userId) ;

            try {
                int tmp = goodsInfoService.insertGoodsInfo(goods);
                if (tmp == 1) {
                    rlt.put("goodsId",goodsId);
                    rlt.put("rltCode", "0000");
                    rlt.put("rltDesc", "成功");
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("goodsInfoService.insertGoodsInfo error: {}", JSON.toJSONString(goods));
            }
        }

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        return rlt;
    }

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
