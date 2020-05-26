package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.GoodsInfoMapper;
import wkai.test.game.entity.GoodsInfo;
import wkai.test.game.service.GoodsInfoService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public int insertGoodsInfo(GoodsInfo goods) {
        return goodsInfoMapper.insertSelective(goods);
    }

    @Override
    public GoodsInfo getGoodsRecordByIdAndStatus(String goodsId, String status) {
        GoodsInfo byIdAndStatus = goodsInfoMapper.getByIdAndStatus(goodsId, status);
        return byIdAndStatus;
    }

    @Override
    public List<Map<String, Object>> getGoodsList(String gameId, String areaId, String serverId, String campId, String goodsType, String status, BigDecimal priceLimitLow, BigDecimal priceLimitHigh, Date createTimeBegin, Date createTimeEnd, String keyWord, String userId) {
        return goodsInfoMapper.selectByConditions(gameId, areaId, serverId, campId, goodsType, status, priceLimitLow, priceLimitHigh, createTimeBegin, createTimeEnd, keyWord, userId);
    }

    @Override
    public int deleteSomeOneGoods(String[] goodsIds, String userId) {
        String fromStatus = null;
        String toStatus = "0";
        return this.goodsInfoMapper.updateStatusByIdsAndUserIdAndStatus(goodsIds, userId, fromStatus, toStatus);
    }

    @Override
    public int releaseSomeOneGoods(String[] goodsIds, String userId) {
        String fromStatus = null;
        String toStatus = "2";
        return this.goodsInfoMapper.updateStatusByIdsAndUserIdAndStatus(goodsIds, userId, fromStatus, toStatus);
    }

    @Override
    public int revokeSomeOneGoods(String[] goodsIds, String userId) {
        String fromStatus = "2";
        String toStatus = "3";
        return this.goodsInfoMapper.updateStatusByIdsAndUserIdAndStatus(goodsIds, userId, fromStatus, toStatus);
    }

    @Override
    public Map<String, Object> getOnSellGoodsById(String goodsId) {
        return goodsInfoMapper.getByIdAndStatus1(goodsId, "1");
    }


}
