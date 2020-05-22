package wkai.test.game.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserInfo {
    private String userId;

    private String loginPwd;

    private String payPwd;

    private String userName;

    private Date createTime;

    private String idno;

    private BigDecimal balance;

    private String status;

    private BigDecimal buyCredit;

    private Integer buySucess;

    private Integer buyCancel;

    private BigDecimal sellCredit;

    private Integer sellSuccess;

    private Integer sellCancel;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd == null ? null : payPwd.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno == null ? null : idno.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getBuyCredit() {
        return buyCredit;
    }

    public void setBuyCredit(BigDecimal buyCredit) {
        this.buyCredit = buyCredit;
    }

    public Integer getBuySucess() {
        return buySucess;
    }

    public void setBuySucess(Integer buySucess) {
        this.buySucess = buySucess;
    }

    public Integer getBuyCancel() {
        return buyCancel;
    }

    public void setBuyCancel(Integer buyCancel) {
        this.buyCancel = buyCancel;
    }

    public BigDecimal getSellCredit() {
        return sellCredit;
    }

    public void setSellCredit(BigDecimal sellCredit) {
        this.sellCredit = sellCredit;
    }

    public Integer getSellSuccess() {
        return sellSuccess;
    }

    public void setSellSuccess(Integer sellSuccess) {
        this.sellSuccess = sellSuccess;
    }

    public Integer getSellCancel() {
        return sellCancel;
    }

    public void setSellCancel(Integer sellCancel) {
        this.sellCancel = sellCancel;
    }

    public UserInfo(String userId, String loginPwd, String payPwd, String userName, Date createTime, String idno, BigDecimal balance, String status, BigDecimal buyCredit, Integer buySucess, Integer buyCancel, BigDecimal sellCredit, Integer sellSuccess, Integer sellCancel) {
        this.userId = userId;
        this.loginPwd = loginPwd;
        this.payPwd = payPwd;
        this.userName = userName;
        this.createTime = createTime;
        this.idno = idno;
        this.balance = balance;
        this.status = status;
        this.buyCredit = buyCredit;
        this.buySucess = buySucess;
        this.buyCancel = buyCancel;
        this.sellCredit = sellCredit;
        this.sellSuccess = sellSuccess;
        this.sellCancel = sellCancel;
    }
}