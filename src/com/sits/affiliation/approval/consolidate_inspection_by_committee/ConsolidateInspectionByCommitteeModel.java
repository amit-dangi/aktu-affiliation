/**
 * @ AUTHOR Ashwani Kumar
 */


package com.sits.affiliation.approval.consolidate_inspection_by_committee;

import com.sits.general.Common;

public class ConsolidateInspectionByCommitteeModel extends Common {

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
	private String Inspection_type;
	private String Inspection_by;
	
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
		return Inspection_type;
	}
	public void setInspection_type(String inspection_type) {
		Inspection_type = inspection_type;
	}
	public String getInspection_by() {
		return Inspection_by;
	}
	public void setInspection_by(String inspection_by) {
		Inspection_by = inspection_by;
	}
	
	
	
}
