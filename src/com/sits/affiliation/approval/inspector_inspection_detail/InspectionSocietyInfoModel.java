package com.sits.affiliation.approval.inspector_inspection_detail;

import java.io.Reader;
import java.util.ArrayList;

import com.sits.general.Common;

public class InspectionSocietyInfoModel extends Common{

	private String mem_name;	
	private String mem_aadhar;
	private String member_dob;
	private String mem_mob_no;
	private String mem_email;
	private String soc_id;
	private String soc_did;
	private String trust_detail;
	private String trust_address;
	private String state_name;
	private String district_name;
	private String tahsil_name;
	private String pin_code;
	private String phone_no;
	private String email_id;
	private String fax;
	private String mem_photo;
	private String mem_sign;
	private String mem_memo;
	
	private ArrayList<InspectionSocietyInfoModel> list;
	private String mem_desg;
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_aadhar() {
		return mem_aadhar;
	}
	public void setMem_aadhar(String mem_aadhar) {
		this.mem_aadhar = mem_aadhar;
	}
	public String getMember_dob() {
		return member_dob;
	}
	public void setMember_dob(String member_dob) {
		this.member_dob = member_dob;
	}
	public String getMem_mob_no() {
		return mem_mob_no;
	}
	public void setMem_mob_no(String mem_mob_no) {
		this.mem_mob_no = mem_mob_no;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_desg() {
		return mem_desg;
	}
	public void setMem_desg(String mem_desg) {
		this.mem_desg = mem_desg;
	}
	public Reader getObjEmp() {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<InspectionSocietyInfoModel> getList() {
		// TODO Auto-generated method stub
		return list;
	}	
	public void setList(ArrayList<InspectionSocietyInfoModel> list) {
		this.list = list;
	}
	public String getSoc_did() {
		return soc_did;
	}
	public void setSoc_did(String soc_did) {
		this.soc_did = soc_did;
	}
	public String getSoc_id() {
		return soc_id;
	}
	public void setSoc_id(String soc_id) {
		this.soc_id = soc_id;
	}
	public String getTrust_detail() {
		return trust_detail;
	}
	public void setTrust_detail(String trust_detail) {
		this.trust_detail = trust_detail;
	}
	public String getTrust_address() {
		return trust_address;
	}
	public void setTrust_address(String trust_address) {
		this.trust_address = trust_address;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	public String getTahsil_name() {
		return tahsil_name;
	}
	public void setTahsil_name(String tahsil_name) {
		this.tahsil_name = tahsil_name;
	}
	public String getPin_code() {
		return pin_code;
	}
	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	public String getMem_sign() {
		return mem_sign;
	}
	public void setMem_sign(String mem_sign) {
		this.mem_sign = mem_sign;
	}
	public String getMem_memo() {
		return mem_memo;
	}
	public void setMem_memo(String mem_memo) {
		this.mem_memo = mem_memo;
	}

}
