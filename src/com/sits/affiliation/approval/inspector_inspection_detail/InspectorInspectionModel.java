package com.sits.affiliation.approval.inspector_inspection_detail;

import org.json.simple.JSONArray;

import com.sits.affiliation.approval.inspection_by_scrutiny_committee.InspectionByScrutinyCommitteeModel;
import com.sits.general.Common;


public class InspectorInspectionModel extends Common {

	private String session_id;
	private String inst_name;
	private String mobile_no;
	private String email_id;
	private String XTODATE;
	private String XFROMDATE;
	private String applicationId;
	private String panel_id;
	private String remarks;
	private String isfinalsubmited;
	private String is_attach_req;
	private String inspection_id;
	private String course_name;
	private String branch_name;
	private String department;
	private String is_config_avbl_crpt;
	private String course_Id;
	private String PROP_INST_NAME;
	private String old_course_name;
	private String new_course_name;
	private String old_degree;
	private String degree;
	private String s_district;
	private String is_convinor;
	private String Inspection_type;
	private String loginId;
	
	public String getPROP_INST_NAME() {
		return PROP_INST_NAME;
	}
	public void setPROP_INST_NAME(String pROP_INST_NAME) {
		PROP_INST_NAME = pROP_INST_NAME;
	}
	public String getOld_course_name() {
		return old_course_name;
	}
	public void setOld_course_name(String old_course_name) {
		this.old_course_name = old_course_name;
	}
	public String getNew_course_name() {
		return new_course_name;
	}
	public void setNew_course_name(String new_course_name) {
		this.new_course_name = new_course_name;
	}
	public String getOld_degree() {
		return old_degree;
	}
	public void setOld_degree(String old_degree) {
		this.old_degree = old_degree;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCourse_Id() {
		return course_Id;
	}
	public void setCourse_Id(String course_Id) {
		this.course_Id = course_Id;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}

	private String session;
	
	public String getApplicationId() {
		return applicationId;
	}
	public String getInspection_id() {
		return inspection_id;
	}
	public void setInspection_id(String inspection_id) {
		this.inspection_id = inspection_id;
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
	
	public String getIsfinalsubmited() {
		return isfinalsubmited;
	}
	public void setIsfinalsubmited(String isfinalsubmited) {
		this.isfinalsubmited = isfinalsubmited;
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
	//Computer Peripheral & Facilities Details
	private String Inst_Id;
	private String FAC_ID;
	private String fac_type;
	private String fac_name;
	private String avbl_room;
	private String avbl_room_inspection;
	private String inspection_status;
	private String gender;
	private String doj;

	public String getFAC_ID() {
		return FAC_ID;
	}
	public void setFAC_ID(String fAC_ID) {
		FAC_ID = fAC_ID;
	}
	public String getFac_type() {
		return fac_type;
	}
	public void setFac_type(String fac_type) {
		this.fac_type = fac_type;
	}
	public String getFac_name() {
		return fac_name;
	}
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	public String getAvbl_room() {
		return avbl_room;
	}
	public void setAvbl_room(String avbl_room) {
		this.avbl_room = avbl_room;
	}
	public String getAvbl_room_inspection() {
		return avbl_room_inspection;
	}
	public void setAvbl_room_inspection(String avbl_room_inspection) {
		this.avbl_room_inspection = avbl_room_inspection;
	}
	public String getInst_Id() {
		return Inst_Id;
	}
	public void setInst_Id(String inst_Id) {
		Inst_Id = inst_Id;
	}
	public String getInspection_status() {
		return inspection_status;
	}
	public void setInspection_status(String inspection_status) {
		this.inspection_status = inspection_status;
	}
	
	
	
	
	// Inspector purpose
	
	private String insp_recm;
	private String insp_remarks;
	private JSONArray computerDetails;
	private JSONArray facilityDetails;

	public String getInsp_recm() {
		return insp_recm;
	}
	public void setInsp_recm(String insp_recm) {
		this.insp_recm = insp_recm;
	}
	public String getInsp_remarks() {
		return insp_remarks;
	}
	public void setInsp_remarks(String insp_remarks) {
		this.insp_remarks = insp_remarks;
	}
	public JSONArray getComputerDetails() {
		return computerDetails;
	}
	public void setComputerDetails(JSONArray computerDetails) {
		this.computerDetails = computerDetails;
	}
	public JSONArray getFacilityDetails() {
		return facilityDetails;
	}
	public void setFacilityDetails(JSONArray facilityDetails) {
		this.facilityDetails = facilityDetails;
	}
	
	//administrative & Amenities Details Details Model
	private JSONArray administrativeDetails;
	private JSONArray amenitiesDetails;
	private String bd_id;
	private String administrative_typ;
	private String detail_typ;
	private String avbl_carpet;
	private String avbl_carpet_inspection;

	public String getBd_id() {
		return bd_id;
	}
	public void setBd_id(String bd_id) {
		this.bd_id = bd_id;
	}
	public String getAdministrative_typ() {
		return administrative_typ;
	}
	public void setAdministrative_typ(String administrative_typ) {
		this.administrative_typ = administrative_typ;
	}
	public String getDetail_typ() {
		return detail_typ;
	}
	public void setDetail_typ(String detail_typ) {
		this.detail_typ = detail_typ;
	}
	public String getAvbl_carpet() {
		return avbl_carpet;
	}
	public void setAvbl_carpet(String avbl_carpet) {
		this.avbl_carpet = avbl_carpet;
	}
	public String getAvbl_carpet_inspection() {
		return avbl_carpet_inspection;
	}
	public void setAvbl_carpet_inspection(String avbl_carpet_inspection) {
		this.avbl_carpet_inspection = avbl_carpet_inspection;
	}
	public JSONArray getAdministrativeDetails() {
		return administrativeDetails;
	}
	public void setAdministrativeDetails(JSONArray administrativeDetails) {
		this.administrativeDetails = administrativeDetails;
	}
	public JSONArray getAmenitiesDetails() {
		return amenitiesDetails;
	}
	public void setAmenitiesDetails(JSONArray amenitiesDetails) {
		this.amenitiesDetails = amenitiesDetails;
	}

	//Infrastructure Information Model
	private JSONArray InfraDetails;
	private String TYPE_OF_LAND;
	private String AVBL_LAND;
	private String AVBL_CONST_AREA;
	
	public JSONArray getInfraDetails() {
		return InfraDetails;
	}
	public void setInfraDetails(JSONArray infraDetails) {
		InfraDetails = infraDetails;
	}
	
	public String getTYPE_OF_LAND() {
		return TYPE_OF_LAND;
	}

	public void setTYPE_OF_LAND(String tYPE_OF_LAND) {
		TYPE_OF_LAND = tYPE_OF_LAND;
	}

	public String getAVBL_LAND() {
		return AVBL_LAND;
	}

	public void setAVBL_LAND(String aVBL_LAND) {
		AVBL_LAND = aVBL_LAND;
	}

	public String getAVBL_CONST_AREA() {
		return AVBL_CONST_AREA;
	}

	public void setAVBL_CONST_AREA(String aVBL_CONST_AREA) {
		AVBL_CONST_AREA = aVBL_CONST_AREA;
	}
	
	//Faculty information
	
	private String aadharno;
	private String panno;
	private String father_name;
	private String qualification;
	private String designation;
	private String is_director;
	private JSONArray facultyDetails;
	
	public String getAadharno() {
		return aadharno;
	}
	public void setAadharno(String aadharno) {
		this.aadharno = aadharno;
	}
	public String getPanno() {
		return panno;
	}
	public void setPanno(String panno) {
		this.panno = panno;
	}
	public String getFather_name() {
		return father_name;
	}
	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getIs_director() {
		return is_director;
	}
	public void setIs_director(String is_director) {
		this.is_director = is_director;
	}
	public JSONArray getFacultyDetails() {
		return facultyDetails;
	}
	public void setFacultyDetails(JSONArray facultyDetails) {
		this.facultyDetails = facultyDetails;
	}
	
	//questionnairesDetails Model
	
	private JSONArray questionnairesDetails;
	private String ques_id;
	private String ques_desc;
	private String isActive;
	private String field_1;
	private String field_2;
	private String field_3;
	private String inspection_by;
	private String file_name;
	private String ques_type;

	public JSONArray getQuestionnairesDetails() {
		return questionnairesDetails;
	}
	public void setQuestionnairesDetails(JSONArray questionnairesDetails) {
		this.questionnairesDetails = questionnairesDetails;
	}
	public String getQues_id() {
		return ques_id;
	}
	public void setQues_id(String ques_id) {
		this.ques_id = ques_id;
	}
	public String getQues_desc() {
		return ques_desc;
	}
	public void setQues_desc(String ques_desc) {
		this.ques_desc = ques_desc;
	}
	public String getField_1() {
		return field_1;
	}
	public void setField_1(String field_1) {
		this.field_1 = field_1;
	}
	public String getField_2() {
		return field_2;
	}
	public void setField_2(String field_2) {
		this.field_2 = field_2;
	}
	public String getField_3() {
		return field_3;
	}
	public void setField_3(String field_3) {
		this.field_3 = field_3;
	}
	public String getInspection_by() {
		return inspection_by;
	}
	public void setInspection_by(String inspection_by) {
		this.inspection_by = inspection_by;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
	//Affiliation Request Getters and Setters
	
	private String id;
	private String af_id;
	private String req_id;
	private String req_name;
	private String sub_req_id;
	private String sub_req_name;
	private String app_date;
	private String amt;
	private String pay_status;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAf_id() {
		return af_id;
	}
	public void setAf_id(String af_id) {
		this.af_id = af_id;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}
	public String getReq_name() {
		return req_name;
	}
	public void setReq_name(String req_name) {
		this.req_name = req_name;
	}
	public String getSub_req_id() {
		return sub_req_id;
	}
	public void setSub_req_id(String sub_req_id) {
		this.sub_req_id = sub_req_id;
	}
	public String getSub_req_name() {
		return sub_req_name;
	}
	public void setSub_req_name(String sub_req_name) {
		this.sub_req_name = sub_req_name;
	}
	public String getApp_date() {
		return app_date;
	}
	public void setApp_date(String app_date) {
		this.app_date = app_date;
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
	
	//GetAffIntakeDetail Getters and Setters
	private String prog_id;
	private String prog_type;
	private String shift;
	private String app_in_AICTE;
	private String app_in_COPCI;
	private String app_in_univ;

	public String getProg_id() {
		return prog_id;
	}
	public void setProg_id(String prog_id) {
		this.prog_id = prog_id;
	}
	public String getProg_type() {
		return prog_type;
	}
	public void setProg_type(String prog_type) {
		this.prog_type = prog_type;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getApp_in_AICTE() {
		return app_in_AICTE;
	}
	public void setApp_in_AICTE(String app_in_AICTE) {
		this.app_in_AICTE = app_in_AICTE;
	}
	public String getApp_in_COPCI() {
		return app_in_COPCI;
	}
	public void setApp_in_COPCI(String app_in_COPCI) {
		this.app_in_COPCI = app_in_COPCI;
	}
	public String getApp_in_univ() {
		return app_in_univ;
	}
	public void setApp_in_univ(String app_in_univ) {
		this.app_in_univ = app_in_univ;
	}
	public String getIs_attach_req() {
		return is_attach_req;
	}
	public void setIs_attach_req(String is_attach_req) {
		this.is_attach_req = is_attach_req;
	}
	public String getQues_type() {
		return ques_type;
	}
	public void setQues_type(String ques_type) {
		this.ques_type = ques_type;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getIs_config_avbl_crpt() {
		return is_config_avbl_crpt;
	}
	public void setIs_config_avbl_crpt(String is_config_avbl_crpt) {
		this.is_config_avbl_crpt = is_config_avbl_crpt;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getS_district() {
		return s_district;
	}
	public void setS_district(String s_district) {
		this.s_district = s_district;
	}
	
	//MemberDetails for new requirement add in model
	private JSONArray membersDetails;
	public JSONArray getMembersDetails() {
		return membersDetails;
	}
	public void setMembersDetails(JSONArray membersDetails) {
		this.membersDetails = membersDetails;
	}
	public String getIs_convinor() {
		return is_convinor;
	}
	public void setIs_convinor(String is_convinor) {
		this.is_convinor = is_convinor;
	}
	public String getInspection_type() {
		return Inspection_type;
	}
	public void setInspection_type(String inspection_type) {
		Inspection_type = inspection_type;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	
}