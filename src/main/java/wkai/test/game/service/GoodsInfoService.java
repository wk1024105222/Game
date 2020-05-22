package wkai.test.game.service;

import wkai.test.game.entity.GoodsInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GoodsInfoService {
    int insertGoodsInfo(GoodsInfo goods);

    GoodsInfo getGoodsRecordByIdAndStatus(String goodsId, String status);

    List<Map<String, Object>> getGoodsList(String gameId, String areaId, String serverId, String campId, String goodsType, String status, BigDecimal priceLimitLow, BigDecimal priceLimitHigh);
}
