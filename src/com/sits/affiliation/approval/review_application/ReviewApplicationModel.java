package com.sits.affiliation.approval.review_application;

public class ReviewApplicationModel {

	private String session_id;
	private String inst_name;
	private String mobile_no;
	private String email_id;
	private String XTODATE;
	private String XFROMDATE;
	private String applicationId;
	private String panel_id;
	private String remarks;
	
	//View payment details model
	private String id;
	private String af_id;
	private String req_id;
	private String req_name;
	private String sub_req_id;
	private String sub_req_name;
	private String app_date;
	private String amt;
	private String pay_status;
	 
	private String nirf_rank;
	private String QS_World;
	private String NBA_acc;
	private String NAAC;
	private String AICTE;
	private String consecutively;
	private String is_Noc;
	private String course_name;
	private String course_Id;
	private String degree;
	private String session;
	
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
	@Override
	public String toString() {
		return "ReviewApplicationModel [session_id=" + session_id + ", inst_name=" + inst_name + ", mobile_no="
				+ mobile_no + ", email_id=" + email_id + ", XTODATE=" + XTODATE + ", XFROMDATE=" + XFROMDATE
				+ ", applicationId=" + applicationId + ", panel_id=" + panel_id + ", remarks=" + remarks + "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}
	public String getSub_req_id() {
		return sub_req_id;
	}
	public void setSub_req_id(String sub_req_id) {
		this.sub_req_id = sub_req_id;
	}
	public String getApp_date() {
		return app_date;
	}
	public void setApp_date(String app_date) {
		this.app_date = app_date;
	}
	public String getAf_id() {
		return af_id;
	}
	public void setAf_id(String af_id) {
		this.af_id = af_id;
	}
	public String getReq_name() {
		return req_name;
	}
	public void setReq_name(String req_name) {
		this.req_name = req_name;
	}
	public String getSub_req_name() {
		return sub_req_name;
	}
	public void setSub_req_name(String sub_req_name) {
		this.sub_req_name = sub_req_name;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	 
	public String getNirf_rank() {
		return nirf_rank;
	}
	public void setNirf_rank(String nirf_rank) {
		this.nirf_rank = nirf_rank;
	}
	public String getQS_World() {
		return QS_World;
	}
	public void setQS_World(String QS_World) {
		this.QS_World = QS_World;
	}
	public String getNBA_acc() {
		return NBA_acc;
	}
	public void setNBA_acc(String NBA_acc) {
		this.NBA_acc = NBA_acc;
	}
	public String getNAAC() {
		return NAAC;
	}
	public void setNAAC(String NAAC) {
		this.NAAC = NAAC;
	}
	public String getAICTE() {
		return AICTE;
	}
	public void setAICTE(String AICTE) {
		this.AICTE = AICTE;
	}
	public String getConsecutively() {
		return consecutively;
	}
	public void setConsecutively(String consecutively) {
		this.consecutively = consecutively;
	}
	public String getIs_Noc() {
		return is_Noc;
	}
	public void setIs_Noc(String is_Noc) {
		this.is_Noc = is_Noc;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	public String getCourse_Id() {
		return course_Id;
	}
	public void setCourse_Id(String course_Id) {
		this.course_Id = course_Id;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	
}