package wkai.test.game.service;

import wkai.test.game.entity.GoodsInfo;

import java.util.List;

public interface GoodsInfoService {
    int insertGoodsInfo(GoodsInfo goods);

    GoodsInfo getGoodsRecordByIdAndStatus(String goodsId, String status);

    int reduceStock(Integer buyNum);
}
