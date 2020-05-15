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
public class GoodsController {
    private final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private Audience audience;

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
}
