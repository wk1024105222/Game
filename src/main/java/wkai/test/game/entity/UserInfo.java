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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

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

    public UserInfo(String userId, String loginPwd, String payPwd, String userName, Date createTime, BigDecimal balance) {
        this.userId = userId;
        this.loginPwd = loginPwd;
        this.payPwd = payPwd;
        this.userName = userName;
        this.createTime = createTime;
        this.balance = balance;
    }
}