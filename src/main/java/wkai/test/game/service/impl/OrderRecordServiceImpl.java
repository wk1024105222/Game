package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wkai.test.game.common.response.Result;
import wkai.test.game.dao.GoodsInfoMapper;
import wkai.test.game.dao.OrderRecordMapper;
import wkai.test.game.dao.UserInfoMapper;
import wkai.test.game.entity.GoodsInfo;
import wkai.test.game.entity.OrderRecord;
import wkai.test.game.entity.UserInfo;
import wkai.test.game.service.GoodsInfoService;
import wkai.test.game.service.OrderRecordService;
import wkai.test.game.service.UserInfoService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderRecordServiceImpl implements OrderRecordService {
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
    public Result createOrder(String goodsId, String roleName, BigDecimal price, Integer buyNum, String buyerMobile, BigDecimal totalAmount, BigDecimal balaAmount, BigDecimal payAmount, String userId) throws Exception {
        GoodsInfo goods = goodsInfoMapper.getByIdAndStatus(goodsId, "1");

        if (goods == null ) {
            return new Result("0004","商品已经下架");
        } else if (price.doubleValue() != goods.getPrice().doubleValue()) {
            return new Result("0006","商品价格变化，请重新提交");
        } else if (buyNum > goods.getStock()) {
            return new Result("0007","商品库存不足，请重新提交");
        }

        UserInfo user = userInfoMapper.getById(userId);
        if (null == user) {
            return new Result("0009","用户信息异常，请联系客户人员");
        } else if (balaAmount.doubleValue()>user.getBalance().doubleValue()) {
            return new Result("0010","余额不足");
        }

        String orderId = UUID.randomUUID().toString().replace("-", "");
        OrderRecord order =   new OrderRecord(orderId, goodsId, roleName, price,
                buyNum, buyerMobile, totalAmount, balaAmount,
                payAmount, "1", new Date(), userId, goods.getuserId());

        //减库存 insert订单 记账 扣减余额
        int updateGoodsNum = goodsInfoMapper.updateStock(goodsId,buyNum);
        if (updateGoodsNum==0) {
            //return new Result("0007","商品库存不足，请重新提交");
            throw new Exception();
        }
        int insertOrderNum =orderRecordMapper.insertSelective(order);
        if (insertOrderNum == 0) {
//            return new Result("0011","订单创建失败");
            throw new Exception();
        }
        if (balaAmount.longValue() > 0) {
            int updateUserNum = userInfoMapper.updateBalance(userId,balaAmount);
            if (updateUserNum == 0) {
//                return new Result("0010","余额不足");
                throw new Exception();
            }
        }
        return new Result("0000","操作成功");
    }
}
