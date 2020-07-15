package wkai.test.game.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PayRecord {
    private Long id;

    private String orderId;

    private String payAccount;

    private String payAccountType;

    private BigDecimal payAmount;

    private String payUserid;

    private String payName;

    private String payPhone;

    private String recAccount;

    private String recAccountType;

    private String recUserid;

    private Date createTime;

    private String status;

    public PayRecord(Long id,
                     String orderId,
                     String payAccount,
                     String payAccountType,
                     BigDecimal payAmount,
                     String payUserid,
                     String payName,
                     String payPhone,
                     String recAccount,
                     String recAccountType,
                     String recUserid,
                     Date createTime,
                     String status) {
        this.id = id;
        this.orderId = orderId;
        this.payAccount = payAccount;
        this.payAccountType = payAccountType;
        this.payAmount = payAmount;
        this.payUserid = payUserid;
        this.payName = payName;
        this.payPhone = payPhone;
        this.recAccount = recAccount;
        this.recAccountType = recAccountType;
        this.recUserid = recUserid;
        this.createTime = createTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getPayAccountType() {
        return payAccountType;
    }

    public void setPayAccountType(String payAccountType) {
        this.payAccountType = payAccountType == null ? null : payAccountType.trim();
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayUserid() {
        return payUserid;
    }

    public void setPayUserid(String payUserid) {
        this.payUserid = payUserid == null ? null : payUserid.trim();
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    public String getPayPhone() {
        return payPhone;
    }

    public void setPayPhone(String payPhone) {
        this.payPhone = payPhone == null ? null : payPhone.trim();
    }

    public String getRecAccount() {
        return recAccount;
    }

    public void setRecAccount(String recAccount) {
        this.recAccount = recAccount == null ? null : recAccount.trim();
    }

    public String getRecAccountType() {
        return recAccountType;
    }

    public void setRecAccountType(String recAccountType) {
        this.recAccountType = recAccountType == null ? null : recAccountType.trim();
    }

    public String getRecUserid() {
        return recUserid;
    }

    public void setRecUserid(String recUserid) {
        this.recUserid = recUserid == null ? null : recUserid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}