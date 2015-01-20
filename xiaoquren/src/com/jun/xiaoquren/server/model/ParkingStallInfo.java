package com.jun.xiaoquren.server.model;

import java.util.Date;

public class ParkingStallInfo {
    private Integer id;

    private Long xiaoquId;

    private String supplyDemandType;

    private String yourIdentity;

    private Double price;

    private Double areaMeasure;

    private String address;

    private String owner;

    private String title;

    private String content;

    private String phone;

    private String nickname;

    private Long readCount;

    private Long sortOrder;

    private Long creatorId;

    private Long updatorId;

    private Boolean deleted;

    private Long version;

    private Date createDate;
    
    private String createTime;

    private Date updateDate;

    private Date publishDate;

    private Date expireDate;

    private String priceUnit;

    public Integer getId() {
        return id;
    }
    
    public String getStringId() {
    	return String.valueOf(id);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getXiaoquId() {
        return xiaoquId;
    }

    public void setXiaoquId(Long xiaoquId) {
        this.xiaoquId = xiaoquId;
    }

    public String getSupplyDemandType() {
        return supplyDemandType;
    }

    public void setSupplyDemandType(String supplyDemandType) {
        this.supplyDemandType = supplyDemandType == null ? null : supplyDemandType.trim();
    }

    public String getYourIdentity() {
        return yourIdentity;
    }

    public void setYourIdentity(String yourIdentity) {
        this.yourIdentity = yourIdentity == null ? null : yourIdentity.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAreaMeasure() {
        return areaMeasure;
    }

    public void setAreaMeasure(Double areaMeasure) {
        this.areaMeasure = areaMeasure;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit == null ? null : priceUnit.trim();
    }

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}