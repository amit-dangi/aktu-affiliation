// 
// Decompiled by Procyon v0.5.36
// 

package com.sits.general;

public class Common
{
    private String createDate;
    private String updateDate;
    private String status;
    private String mode;
    private String type;
    private String category;
    private String fstatus;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
    private String ip;
    private String errMsg;
    public boolean valid;
    private String district;
    private String requestType;
    
    
    public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(final String createDate) {
        this.createDate = createDate;
    }
    
    public String getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(final String updateDate) {
        this.updateDate = updateDate;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public String getMode() {
        return this.mode;
    }
    
    public void setMode(final String mode) {
        this.mode = mode;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    public String getFstatus() {
        return this.fstatus;
    }
    
    public void setFstatus(final String fstatus) {
        this.fstatus = fstatus;
    }
    
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public String getDeletedBy() {
        return this.deletedBy;
    }
    
    public void setDeletedBy(final String deletedBy) {
        this.deletedBy = deletedBy;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    public String getErrMsg() {
        return this.errMsg;
    }
    
    public void setErrMsg(final String errMsg) {
        this.errMsg = errMsg;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public void setValid(final boolean valid) {
        this.valid = valid;
    }
}
