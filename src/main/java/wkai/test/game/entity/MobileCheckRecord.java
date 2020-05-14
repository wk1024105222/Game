package wkai.test.game.entity;

import java.util.Date;

public class MobileCheckRecord {
    private String traceId;

    private String mobile;

    private Date reqTime;

    private String checkCode;

    private String purpose;

    private String resultCode;

    private Date respTime;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId == null ? null : traceId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode == null ? null : checkCode.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public MobileCheckRecord(String traceId, String mobile, String purpose, String checkCode, String resultCode, Date reqTime ,Date respTime) {
        this.traceId = traceId;
        this.mobile = mobile;
        this.reqTime = reqTime;
        this.checkCode = checkCode;
        this.purpose = purpose;
        this.resultCode = resultCode;
        this.respTime = respTime;
    }
}