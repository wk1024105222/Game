package wkai.test.game.entity;

public class ChatRecord {
    private String msgId;

    private String fromUserId;

    private String toUserId;

    private String msgType;

    private String msgText;

    private String picUrl;

    private Long createTime;

    private String status;

    private String orderId;

    public ChatRecord(String msgId, String fromUserId, String toUserId,
                      String msgType, String msgText, String picUrl,
                      Long createTime, String status, String orderId) {
        this.msgId = msgId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.msgType = msgType;
        this.msgText = msgText;
        this.picUrl = picUrl;
        this.createTime = createTime;
        this.status = status;
        this.orderId = orderId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId == null ? null : fromUserId.trim();
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId == null ? null : toUserId.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText == null ? null : msgText.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }
}