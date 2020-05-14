package wkai.test.game.entity;

import java.util.Date;

public class LoginRecord {
    private String sessionid;

    private Date loginTime;

    private String userId;

    private String status;

    private Date logoutTime;

    public LoginRecord(String sessionid, Date loginTime, String userId, String status) {
        this.sessionid = sessionid;
        this.loginTime = loginTime;
        this.userId = userId;
        this.status = status;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid == null ? null : sessionid.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

}