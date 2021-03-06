package wkai.test.game.service;

import wkai.test.game.entity.GoodsInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GoodsInfoService {
    int insertGoodsInfo(GoodsInfo goods);

    GoodsInfo getGoodsRecordByIdAndStatus(String goodsId, String status);

    //List<Map<String, Object>> getGoodsList(String gameId, String areaId, String serverId, String campId, String goodsType, String status, BigDecimal priceLimitLow, BigDecimal priceLimitHigh);

    Map<String, Object> getOnSellGoodsById(String goodsId);

    List<Map<String, Object>> getGoodsList(String gameId, String areaId, String serverId, String campId, String goodsType, String status, BigDecimal priceLimitLow, BigDecimal priceLimitHigh, Date createTimeBegin, Date createTimeEnd, String keyWord, String userId);

    int deleteSomeOneGoods(String[] goods_arr, String userId);

    int releaseSomeOneGoods(String[] goods_arr, String userId);

    int revokeSomeOneGoods(String[] goods_arr, String userId);
}


