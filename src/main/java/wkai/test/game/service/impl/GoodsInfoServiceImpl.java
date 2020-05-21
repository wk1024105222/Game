package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.GoodsInfoMapper;
import wkai.test.game.entity.GoodsInfo;
import wkai.test.game.service.GoodsInfoService;

import java.util.List;

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
    public int reduceStock(Integer buyNum) {

        return 0;
    }


}
