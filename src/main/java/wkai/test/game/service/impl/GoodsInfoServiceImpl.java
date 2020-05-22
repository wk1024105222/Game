package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.GoodsInfoMapper;
import wkai.test.game.entity.GoodsInfo;
import wkai.test.game.service.GoodsInfoService;

import java.math.BigDecimal;
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
        return goodsInfoMapper.getByIdAndStatus(goodsId, status);
    }

    @Override
    public List<Map<String, Object>> getGoodsList(String gameId, String areaId, String serverId, String campId, String goodsType, String status, BigDecimal priceLimitLow, BigDecimal priceLimitHigh) {
        return goodsInfoMapper.selectByConditions(gameId,areaId,serverId,campId,goodsType,status,priceLimitLow,priceLimitHigh);
    }


}
