package wkai.test.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wkai.test.game.dao.GoodsInfoMapper;
import wkai.test.game.dao.OrderRecordMapper;
import wkai.test.game.dao.UserInfoMapper;
import wkai.test.game.entity.UserInfo;
import wkai.test.game.service.UserInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public UserInfo selectUserInfoBy2Pwd(String userId, String oldLoginPwd, String oldPayPwd) {
        return userInfoMapper.selectUserInfoBy2Pwd(userId, oldLoginPwd, oldPayPwd);
    }

    @Override
    public UserInfo selectUserInfoByLoginPwd(String userId, String loginPwd) {
        return userInfoMapper.selectUserInfoByLoginPwd(userId, loginPwd);
    }

    @Override
    public UserInfo selectUserInfoByPayPwd(String userId, String payPwd) {
        return userInfoMapper.selectUserInfoByPayPwd(userId,payPwd);
    }

    @Override
    public int change2Pwd(String userId, String newLoginPwd, String newPayPwd) {
        return userInfoMapper.change2Pwd(userId, newLoginPwd, newPayPwd);
    }

    @Override
    public int changeLoginPwd(String userId, String newLoginPwd) {
        return userInfoMapper.changeLoginPwd(userId,newLoginPwd);
    }

    @Override
    public int changePayPwd(String userId, String newPayPwd) {
        return userInfoMapper.changePayPwd(userId,newPayPwd);
    }

    @Override
    public int insertUserInfo(UserInfo userInfo) {
        return userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public UserInfo getUserInfoById(String userId) {
        return userInfoMapper.getById(userId);
    }

    @Override
    public boolean exists(String mobile) {
        UserInfo u = userInfoMapper.getById(mobile);
        return null == u ? false : true;
    }

    @Override
    public Map<String, Object> getSellerInfoById(String sellerId) {
        return userInfoMapper.getSellerInfoById(sellerId);
    }

    @Override
    public Map<String, Object> getUserCenterInfo(String userId) {
        List<Map<String, Object>> buyerInfoList = this.orderRecordMapper.getBuyerInfo(userId);
        List<Map<String, Object>> sellerInfoList = this.orderRecordMapper.getSellerInfo(userId);
        UserInfo user = this.userInfoMapper.getById(userId);
        Integer goodsNum = this.goodsInfoMapper.getCountByUserIdAndStatus(userId, "2");

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("mobile", userId);
        userInfo.put("balance", user.getBalance());

        Map<String, Object> buyerInfo = new HashMap<>();
        buyerInfo.put("bToPayNum", 0);
        buyerInfo.put("bToSendNum", 0);
        buyerInfo.put("bToRecNum", 0);
        buyerInfo.put("bSuccessNum", 0);
        buyerInfo.put("bCancelNum", 0);
        for (Map<String, Object> tmp : buyerInfoList) {
            String status = (String) tmp.get("status");
            if ("1".equals(status)) {
                buyerInfo.put("bToPayNum", tmp.get("num"));
            } else if ("2".equals(status)) {
                buyerInfo.put("bToSendNum", tmp.get("num"));
            } else if ("3".equals(status)) {
                buyerInfo.put("bToRecNum", tmp.get("num"));
            } else if ("4".equals(status)) {
                buyerInfo.put("bSuccessNum", tmp.get("num"));
            } else if ("5".equals(status)) {
                buyerInfo.put("bCancelNum", tmp.get("num"));
            }
        }
        buyerInfo.put("buyerCredit", user.getBuyCredit());

        HashMap<String, Object> sellerInfo = new HashMap<>();
        sellerInfo.put("sToPayNum", 0);
        sellerInfo.put("sToSendNum", 0);
        sellerInfo.put("sToRecNum", 0);
        sellerInfo.put("sSuccessNum", 0);
        sellerInfo.put("sCancelNum", 0);
        for (Map<String, Object> tmp : sellerInfoList) {
            String status = (String) tmp.get("status");
            if ("1".equals(status)) {
                sellerInfo.put("sToPayNum", tmp.get("num"));
            } else if ("2".equals(status)) {
                sellerInfo.put("sToSendNum", tmp.get("num"));
            } else if ("3".equals(status)) {
                sellerInfo.put("sToRecNum", tmp.get("num"));
            } else if ("4".equals(status)) {
                sellerInfo.put("sSuccessNum", tmp.get("num"));
            } else if ("5".equals(status)) {
                sellerInfo.put("sCancelNum", tmp.get("num"));
            }
        }
        sellerInfo.put("sellerCredit", user.getSellCredit());
        sellerInfo.put("sellGoodsNum", goodsNum);

        Map<String, Object> rlt = new HashMap<>();
        rlt.put("userInfo", userInfo);
        rlt.put("buyerInfo", buyerInfo);
        rlt.put("sellerInfo", sellerInfo);

        return rlt;

    }
}
