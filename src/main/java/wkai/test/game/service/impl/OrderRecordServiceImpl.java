package wkai.test.game.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wkai.test.game.common.exception.GameException;
import wkai.test.game.common.response.ResultCode;
import wkai.test.game.controller.OrderController;
import wkai.test.game.dao.GoodsInfoMapper;
import wkai.test.game.dao.OrderRecordMapper;
import wkai.test.game.dao.UserInfoMapper;
import wkai.test.game.entity.GoodsInfo;
import wkai.test.game.entity.OrderRecord;
import wkai.test.game.entity.UserInfo;
import wkai.test.game.service.OrderRecordService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderRecordServiceImpl implements OrderRecordService {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int insertOrderRecord(OrderRecord orderRecord) {
        return orderRecordMapper.insertSelective(orderRecord);
    }

    @Override
    @Transactional
    public String createOrder(String goodsId, String roleName, BigDecimal price, Integer buyNum, String buyerMobile, BigDecimal totalAmount, BigDecimal balaAmount, BigDecimal payAmount, String userId) throws Exception {
        GoodsInfo goods = goodsInfoMapper.getByIdAndStatus(goodsId, "1");

        if (goods == null) {
            logger.error("cant find on sell goodsinfo by goodsId:{}", goodsId);
            throw new GameException(ResultCode.GOODS_NOT_EXIST);
        } else if (price.compareTo(goods.getPrice()) != 0) {
            logger.error("order price:{} is different from goods price:{} ", price, goods.getPrice());
            throw new GameException(ResultCode.ORDER_PRICE_ERROR);
        } else if (buyNum > goods.getStock()) {
            logger.error("order buyNum:{} is big than goods stock:{} ", buyNum, goods.getStock());
            throw new GameException(ResultCode.GOODS_STOCKOUT);
        }

        UserInfo user = userInfoMapper.getById(userId);
        if (null == user) {
            logger.error("buyer not exist userId:{}", userId);
            throw new GameException(ResultCode.USER_NOT_EXIST);
        } else if (balaAmount.compareTo(user.getBalance())==1) {
            logger.error("order use balaAmount:{} > user balance:{}", balaAmount,user.getBalance());
            throw new GameException(ResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
        }

        String orderId = UUID.randomUUID().toString().replace("-", "");
        OrderRecord order =   new OrderRecord(orderId, goodsId, roleName, price,
                buyNum, buyerMobile, totalAmount, balaAmount,
                payAmount, "1", new Date(), userId, goods.getuserId());

        //减库存 insert订单 记账 扣减余额
        try {
            //减库存
            int updateGoodsNum = goodsInfoMapper.updateStock(goodsId,buyNum);
            if (updateGoodsNum==0) {
                throw new GameException(ResultCode.GOODS_STOCKOUT);
            }
        } catch (GameException e) {
            e.printStackTrace();
            logger.error("goodsInfoMapper.updateStock error");
            throw new GameException(ResultCode.MYSQL_UPDATE_ERROR);
        }
        try {
            //inser 订单
            int insertOrderNum =orderRecordMapper.insertSelective(order);
            if (insertOrderNum == 0) {
                logger.error("insert order orderRecordMapper.insertSelective error : {}", JSON.toJSONString(order));
                throw new GameException(ResultCode.MYSQL_INSERT_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("orderRecordMapper.insertSelective error : {}", JSON.toJSONString(order));
            throw new GameException(ResultCode.MYSQL_INSERT_ERROR);
        }
        if (balaAmount.longValue() > 0) {
            try {
                //余额抵扣
                int updateUserNum = userInfoMapper.updateBalance(userId,balaAmount);
                if (updateUserNum == 0) {
                    logger.error("reduce stock userInfoMapper.updateBalance error: userId={} balaAmount={}", userId, balaAmount);
                    throw new GameException(ResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
                }
            } catch (Exception e) {
                logger.error("reduce stock userInfoMapper.updateBalance error: userId={} balaAmount={}", userId, balaAmount);
                throw new GameException(ResultCode.MYSQL_UPDATE_ERROR);
            }
        }
        return orderId;
    }

    @Override
    public OrderRecord getOrderRecordById(String orderId) {
        return this.orderRecordMapper.getById(orderId);
    }

    @Override
    public List<Map<String, Object>> getSomeoneOrderListByStatus(String userId, String roleType, String status) {
        return orderRecordMapper.getByUserIdAndStatus(userId, roleType, status);
    }

    @Override
    public Map<String, Object> getOrderInfoById(String orderId) {
        return orderRecordMapper.getOrderInfoById(orderId);
    }

    @Override
    public int sendGoods(String userId, String orderId) {
        String status = "3";
        return this.orderRecordMapper.updateStatusBySeller(userId, orderId, status);
    }

    @Override
    public int receiveGoods(String userId, String orderId) {
        String status = "4";
        return this.orderRecordMapper.updateStatusByBuyer(userId, orderId, status);
    }
}
