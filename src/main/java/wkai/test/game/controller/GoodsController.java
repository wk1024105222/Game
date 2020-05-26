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
import wkai.test.game.annotation.JwtIgnore;
import wkai.test.game.common.config.Audience;
import wkai.test.game.common.exception.GameException;
import wkai.test.game.common.response.ResultCode;
import wkai.test.game.entity.GoodsInfo;
import wkai.test.game.service.GoodsInfoService;
import wkai.test.game.service.UserInfoService;
import wkai.test.game.util.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class GoodsController {
    private final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private Audience audience;

    @RequestMapping("/createGoods")
    public Map<String, Object> createGoods(@RequestBody Map<String, String> goodsInfo, HttpServletRequest request, HttpServletResponse response) {
        logger.info("createGoods request msg : {}", JSON.toJSONString(goodsInfo));
        Map<String, Object> rlt = new HashMap();

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

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
            logger.error("params missing error");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        Integer containNum_i = null;
        BigDecimal price_d = null;
        Integer stock_i = null;
        Integer expireDays_i = null;

        try {
            containNum_i = Integer.parseInt(containNum);
            price_d = new BigDecimal(price);
            stock_i = Integer.parseInt(stock);
            expireDays_i = Integer.parseInt(expireDays);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("params format error");
            throw new GameException(ResultCode.PARAM_TYPE_BIND_ERROR);
        }

        String goodsId = UUID.randomUUID().toString().replace("-", "");
        String title = price + "元=" + containNum + "金";
        String status = "0";
        String recommendRank = "0";
        Date createTime = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(createTime);
        ca.add(Calendar.DATE, Integer.parseInt(expireDays));
        Date expireTime = ca.getTime();

        GoodsInfo goods = new GoodsInfo(goodsId, title, gameId, areaId,
                serverId, campId, goodsType, traceType,
                containNum_i, price_d, stock_i, roleName,
                mobile, tranHourBegin, tranHourEnd, expireDays_i,
                expireTime, recommendRank, status, createTime, userId);

        try {
            int tmp = goodsInfoService.insertGoodsInfo(goods);
            if (tmp == 1) {
                rlt.put("goodsId", goodsId);
                rlt.put("rltCode", "0000");
                rlt.put("rltDesc", "成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("goodsInfoService.insertGoodsInfo error");
            throw new GameException(ResultCode.GOODS_CREATE_ERROR);
        }
        return rlt;
    }

    @RequestMapping("/listGoods")
    @JwtIgnore
    public Map<String, Object> listGoods(@RequestBody Map<String, String> condition, HttpServletRequest request, HttpServletResponse response) {
        logger.info("listGoods request msg : {}", JSON.toJSONString(condition));
        Map<String, Object> rlt = new HashMap();

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        String gameId = condition.get("gameId");
        String areaId = condition.get("areaId");
        String serverId = condition.get("serverId");
        String campId = condition.get("campId");
        String goodsType = condition.get("goodsType");
        String orderField = condition.containsKey("orderField") ? condition.get("orderField") : "create_time";
        String orderType = condition.containsKey("orderType") ? condition.get("orderType") : "desc";

        if (StringUtils.isBlank(gameId)) {
            logger.error("params missing error");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        BigDecimal priceLimitLow = null;
        BigDecimal priceLimitHigh = null;
        Integer pageNum = null;
        Integer pageSize = null;

        try {
            priceLimitLow = condition.containsKey("priceLimitLow") ? new BigDecimal(condition.get("priceLimitLow")) : null;
            priceLimitHigh = condition.containsKey("priceLimitHigh") ? new BigDecimal(condition.get("priceLimitHigh")) : null;
            pageNum = condition.containsKey("pageNum") ? Integer.parseInt(condition.get("pageNum")) : 1;
            pageSize = condition.containsKey("pageSize") ? Integer.parseInt(condition.get("pageSize")) : 20;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("params format error");
            throw new GameException(ResultCode.PARAM_TYPE_BIND_ERROR);
        }

        String status = "1";
        List<Map<String, Object>> goodses = null;
        long totalNum = 0;
        int pageCount = 0;
        try {
            Page page = PageHelper.startPage(pageNum, pageSize);
            PageHelper.orderBy("g." + orderField + " " + orderType);
            goodses = goodsInfoService.getGoodsList(gameId, areaId, serverId, campId, goodsType,
                    status, priceLimitLow, priceLimitHigh, null, null, null, null);
            totalNum = page.getTotal();
            pageCount = page.getPages();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("goodsInfoService.getGoodsList error");
            throw new GameException(ResultCode.GOODS_LIST_ERROR);
        }

        rlt.put("rltCode", "0000");
        rlt.put("rltDesc", "操作成功");
        rlt.put("goodsList", goodses);
        rlt.put("pageNum", pageNum);
        rlt.put("pageSize", pageSize);
        rlt.put("pageCount", pageCount);
        rlt.put("totalNum", totalNum);
        return rlt;
    }

    @RequestMapping("/getGoodsDetail")
    @JwtIgnore
    public Map<String, Object> getGoodsDetail(@RequestBody Map<String, String> condition, HttpServletRequest request, HttpServletResponse response) {
        logger.info("getGoodsDetail request msg : {}", JSON.toJSONString(condition));
        Map<String, Object> rlt = new HashMap();

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        String goodsId = condition.get("goodsId");

        if (StringUtils.isBlank(goodsId)) {
            logger.error("params missing error");
            throw new GameException(ResultCode.PARAM_NOT_COMPLETE);
        }

        Map<String, Object> goodsInfo = null;
        try {
            goodsInfo  = this.goodsInfoService.getOnSellGoodsById(goodsId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("goodsInfoService.getOnSellGoodsById error");
            throw new GameException(ResultCode.GOODS_NOT_EXIST);
        }

        Map<String, Object> sellerInfo = null;
        try {
            sellerInfo  = this.userInfoService.getSellerInfoById((String)goodsInfo.get("sellerId"));
            BigDecimal suc = new BigDecimal((Integer) sellerInfo.get("sellSuccess"));
            BigDecimal can = new BigDecimal((Integer) sellerInfo.get("sellCancel"));
            if (null != sellerInfo) {
                if (suc.add(can).intValue() == 0) {
                    sellerInfo.put("tranRate","0%");
                } else {
                    double rate = suc.divide(suc.add(can),2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    sellerInfo.put("tranRate",rate+"%");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("userInfoService.getSellerInfoById error");
            throw new GameException(ResultCode.SELLER_NOT_EXIST);
        }
        rlt.put("rltCode", "0000");
        rlt.put("rltDesc", "操作成功");
        rlt.put("goodsInfo", goodsInfo);
        rlt.put("sellerInfo", sellerInfo);
        return rlt;
    }

    @RequestMapping("/listMyGoods")
    @JwtIgnore
    public Map<String, Object> listMyGoods(@RequestBody Map<String, String> condition, HttpServletRequest request, HttpServletResponse response) {
        logger.info("listMyGoods request msg : {}", JSON.toJSONString(condition));
        Map<String, Object> rlt = new HashMap();

        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String userId = JwtTokenUtil.getUserId(authHeader.substring(7), audience.getBase64Secret());

        String token = JwtTokenUtil.createJWT(userId, "user", audience);
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);

        String gameId = condition.get("gameId");
        String areaId = condition.get("areaId");
        String serverId = condition.get("serverId");
        String campId = condition.get("campId");
        String goodsType = condition.get("goodsType");
        String orderField = condition.containsKey("orderField") ? condition.get("orderField") : "create_time";
        String orderType = condition.containsKey("orderType") ? condition.get("orderType") : "desc";


        String keyWord = condition.get("keyWord");
        String status = condition.get("status");


        Date createTimeBegin = null;
        Date createTimeEnd = null;
        Integer pageNum = null;
        Integer pageSize = null;

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

        try {
            createTimeBegin = condition.get("createTimeBegin") == null ? null : sdf.parse((String) condition.get("createTimeBegin"));
            createTimeEnd = condition.get("createTimeEnd") == null ? null : sdf.parse((String) condition.get("createTimeEnd"));
            pageNum = condition.containsKey("pageNum") ? Integer.parseInt(condition.get("pageNum")) : 1;
            pageSize = condition.containsKey("pageSize") ? Integer.parseInt(condition.get("pageSize")) : 20;
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
            logger.error("params format error");
            throw new GameException(ResultCode.PARAM_TYPE_BIND_ERROR);
        }

        List<Map<String, Object>> goodses = null;
        long totalNum = 0;
        int pageCount = 0;
        try {
            Page page = PageHelper.startPage(pageNum, pageSize);
            PageHelper.orderBy("g." + orderField + " " + orderType);
            goodses = goodsInfoService.getGoodsList(gameId, areaId, serverId, campId, goodsType,
                    status, null, null, createTimeBegin, createTimeEnd, keyWord, userId);
            totalNum = page.getTotal();
            pageCount = page.getPages();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("goodsInfoService.getGoodsList error");
            throw new GameException(ResultCode.MYSQL_SELECT_ERROR);
        }

        rlt.put("rltCode", "0000");
        rlt.put("rltDesc", "操作成功");
        rlt.put("goodsList", goodses);
        rlt.put("pageNum", pageNum);
        rlt.put("pageSize", pageSize);
        rlt.put("pageCount", pageCount);
        rlt.put("totalNum", totalNum);
        return rlt;
    }
}
