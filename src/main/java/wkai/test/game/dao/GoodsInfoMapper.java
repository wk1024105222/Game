package wkai.test.game.dao;

import org.apache.ibatis.annotations.Mapper;
import wkai.test.game.entity.GoodsInfo;

import java.util.List;

@Mapper
public interface GoodsInfoMapper {
    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo getByIdAndStatus(String goodsId, String status);

    int updateStock(String goodsId, Integer buyNum);
}