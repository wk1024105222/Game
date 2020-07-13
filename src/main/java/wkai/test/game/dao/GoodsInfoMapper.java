package wkai.test.game.dao;

import org.apache.ibatis.annotations.Mapper;
import wkai.test.game.entity.GoodsInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsInfoMapper {
    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo getByIdAndStatus(String goodsId, String status);

    Map<String, Object> getByIdAndStatus1(String goodsId, String status);

    int updateStock(String goodsId, Integer buyNum);

    List<Map<String, Object>> selectByConditions(String gameId, String areaId, String serverId, String campId, String goodsType, String status, BigDecimal priceLimitLow, BigDecimal priceLimitHigh, Date createTimeBegin, Date createTimeEnd, String keyWord, String userId);

    int updateStatusByIdsAndUserIdAndStatus(String[] goodsIds, String userId, String fromStatus, String toStatus);

    Integer getCountByUserIdAndStatus(String userId, String status);
}