/**
 * @ AUTHOR Amit Dangi
 */


package com.sits.affiliation.approval.inspection_by_registrar;

import com.sits.general.Common;

public class InspectionByRegistrarModel extends Common{

	private String session_id;
	private String inst_name;
	private String mobile_no;
	private String email_id;
	private String XTODATE;
	private String XFROMDATE;
	private String applicationId;
	private String panel_id;
	private String remarks;
	private String recommendation;
	private String actiontype;
	private String panel_member;
	private String inspection_type;
	private String inspection_by;
	private String inspection_id;
	private String reg_no;
	private String reg_name;
	
	
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getInst_name() {
		return inst_name;
	}
	public void setInst_name(String inst_name) {
		this.inst_name = inst_name;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getXTODATE() {
		return XTODATE;
	}
	public void setXTODATE(String xTODATE) {
		XTODATE = xTODATE;
	}
	public String getXFROMDATE() {
		return XFROMDATE;
	}
	public void setXFROMDATE(String xFROMDATE) {
		XFROMDATE = xFROMDATE;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getPanel_id() {
		return panel_id;
	}
	public void setPanel_id(String panel_id) {
		this.panel_id = panel_id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getActiontype() {
		return actiontype;
	}
	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
	public String getPanel_member() {
		return panel_member;
	}
	public void setPanel_member(String panel_member) {
		this.panel_member = panel_member;
	}
	public String getInspection_type() {
		return inspection_type;
	}
	public void setInspection_type(String inspection_type) {
		this.inspection_type = inspection_type;
	}
	public String getInspection_id() {
		return inspection_id;
	}
	public void setInspection_id(String inspection_id) {
		this.inspection_id = inspection_id;
	}
	public String getInspection_by() {
		return inspection_by;
	}
	public void setInspection_by(String inspection_by) {
		this.inspection_by = inspection_by;
	}
	
	public String getReg_no() {
		return reg_no;
	}
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}
	
	@Override
	public String toString() {
		return "InspectionByRegistrarModel [session_id=" + session_id + ", inst_name=" + inst_name + ", mobile_no="
				+ mobile_no + ", email_id=" + email_id + ", XTODATE=" + XTODATE + ", XFROMDATE=" + XFROMDATE
				+ ", applicationId=" + applicationId + ", panel_id=" + panel_id + ", remarks=" + remarks
				+ ", recommendation=" + recommendation + ", actiontype=" + actiontype + ", panel_member=" + panel_member
				+ ", inspection_type=" + inspection_type + ", inspection_by=" + inspection_by + ", inspection_id="
				+ inspection_id + "]";
	}
	
	
	
}
