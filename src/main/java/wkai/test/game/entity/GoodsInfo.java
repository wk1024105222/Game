package wkai.test.game.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsInfo {
    private String goodsId;

    private String title;

    private String gameId;

    private String areaId;

    private String serverId;

    private String campId;

    private String goodsType;

    private String traceType;

    private Integer containNum;

    private BigDecimal price;

    private Integer stock;

    private String roleName;

    private String mobile;

    private String tranHourBegin;

    private String tranHourEnd;

    private Integer expireDays;

    private Date expireTime;

    private String recommendRank;

    private String status;

    private Date createTime;

    private String userId;

    public GoodsInfo(String goodsId, String title, String gameId, String areaId, String serverId, String campId, String goodsType, String traceType, Integer containNum, BigDecimal price, Integer stock, String roleName, String mobile, String tranHourBegin, String tranHourEnd, Integer expireDays, Date expireTime, String recommendRank, String status, Date createTime, String userId
    ) {
        this.goodsId = goodsId;
        this.title = title;
        this.gameId = gameId;
        this.areaId = areaId;
        this.serverId = serverId;
        this.campId = campId;
        this.goodsType = goodsType;
        this.traceType = traceType;
        this.containNum = containNum;
        this.price = price;
        this.stock = stock;
        this.roleName = roleName;
        this.mobile = mobile;
        this.tranHourBegin = tranHourBegin;
        this.tranHourEnd = tranHourEnd;
        this.expireDays = expireDays;
        this.expireTime = expireTime;
        this.recommendRank = recommendRank;
        this.status = status;
        this.createTime = createTime;
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId == null ? null : gameId.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId == null ? null : serverId.trim();
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId == null ? null : campId.trim();
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType == null ? null : goodsType.trim();
    }

    public String getTraceType() {
        return traceType;
    }

    public void setTraceType(String traceType) {
        this.traceType = traceType == null ? null : traceType.trim();
    }

    public Integer getContainNum() {
        return containNum;
    }

    public void setContainNum(Integer containNum) {
        this.containNum = containNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTranHourBegin() {
        return tranHourBegin;
    }

    public void setTranHourBegin(String tranHourBegin) {
        this.tranHourBegin = tranHourBegin == null ? null : tranHourBegin.trim();
    }

    public String getTranHourEnd() {
        return tranHourEnd;
    }

    public void setTranHourEnd(String tranHourEnd) {
        this.tranHourEnd = tranHourEnd == null ? null : tranHourEnd.trim();
    }

    public Integer getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(Integer expireDays) {
        this.expireDays = expireDays;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getRecommendRank() {
        return recommendRank;
    }

    public void setRecommendRank(String recommendRank) {
        this.recommendRank = recommendRank == null ? null : recommendRank.trim();
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

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }
}