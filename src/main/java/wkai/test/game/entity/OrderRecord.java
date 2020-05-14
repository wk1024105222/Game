package wkai.test.game.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderRecord {
    private String orderId;

    private String goodsId;

    private String roleName;

    private BigDecimal price;

    private Integer buyNum;

    private String buyerMobile;

    private BigDecimal totalAmount;

    private BigDecimal balaAmount;

    private BigDecimal payAmount;

    private String status;

    private Date createTime;

    private Date updateTime;

    private Date finishTime;

    private String buyerId;

    private String sellerId;

    private String chatId;

    private String tranId;

    public OrderRecord(String orderId, String goodsId, String roleName, BigDecimal price, Integer buyNum, String buyerMobile, BigDecimal totalAmount, BigDecimal balaAmount, BigDecimal payAmount, String status, Date createTime, String buyerId, String sellerId) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.roleName = roleName;
        this.price = price;
        this.buyNum = buyNum;
        this.buyerMobile = buyerMobile;
        this.totalAmount = totalAmount;
        this.balaAmount = balaAmount;
        this.payAmount = payAmount;
        this.status = status;
        this.createTime = createTime;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile == null ? null : buyerMobile.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBalaAmount() {
        return balaAmount;
    }

    public void setBalaAmount(BigDecimal balaAmount) {
        this.balaAmount = balaAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId == null ? null : chatId.trim();
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId == null ? null : tranId.trim();
    }
}