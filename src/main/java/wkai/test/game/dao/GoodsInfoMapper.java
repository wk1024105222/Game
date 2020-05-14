package wkai.test.game.dao;

import org.apache.ibatis.annotations.Mapper;
import wkai.test.game.entity.GoodsInfo;

@Mapper
public interface GoodsInfoMapper {
    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);
}