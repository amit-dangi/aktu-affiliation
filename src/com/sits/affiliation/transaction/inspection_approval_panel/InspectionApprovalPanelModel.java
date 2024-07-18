package com.sits.affiliation.transaction.inspection_approval_panel;

import java.util.ArrayList;
import com.sits.general.Common;

public class InspectionApprovalPanelModel extends Common{
    private String panel_id;
    private String panel_code;
    private String panel_name;
    private String is_active;
    private String member_type;
    private String member_name;
    private String department;
    private String designation;
    private String email_id;
    private String contact_no;
    private String is_convenor;
    private String iss_active;
    private String Panel_det_id;
    private String emp_type;
    
    private ArrayList<InspectionApprovalPanelModel> list;
    
    public String getPanel_det_id() {
        return this.Panel_det_id;
    }
    
    public void setPanel_det_id(final String panel_det_id) {
        this.Panel_det_id = panel_det_id;
    }
    
    public ArrayList<InspectionApprovalPanelModel> getList() {
        return this.list;
    }
    
    public void setList(final ArrayList<InspectionApprovalPanelModel> list) {
        this.list = list;
    }
    
    public String getPanel_id() {
        return this.panel_id;
    }
    
    public void setPanel_id(final String panel_id) {
        this.panel_id = panel_id;
    }
    
    public String getMember_type() {
        return this.member_type;
    }
    
    public void setMember_type(final String member_type) {
        this.member_type = member_type;
    }
    
    public String getMember_name() {
        return this.member_name;
    }
    
    public void setMember_name(final String member_name) {
        this.member_name = member_name;
    }
    
    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(final String department) {
        this.department = department;
    }
    
    public String getDesignation() {
        return this.designation;
    }
    
    public void setDesignation(final String designation) {
        this.designation = designation;
    }
    
    public String getEmail_id() {
        return this.email_id;
    }
    
    public void setEmail_id(final String email_id) {
        this.email_id = email_id;
    }
    
    public String getContact_no() {
        return this.contact_no;
    }
    
    public void setContact_no(final String contact_no) {
        this.contact_no = contact_no;
    }
    
    public String getIs_convenor() {
        return this.is_convenor;
    }
    
    public void setIs_convenor(final String is_convenor) {
        this.is_convenor = is_convenor;
    }
    
    public String getIss_active() {
        return this.iss_active;
    }
    
    public void setIss_active(final String iss_active) {
        this.iss_active = iss_active;
    }
    
    public String getPanel_code() {
        return this.panel_code;
    }
    
    public void setPanel_code(final String panel_code) {
        this.panel_code = panel_code;
    }
    
    public String getPanel_name() {
        return this.panel_name;
    }
    
    public void setPanel_name(final String panel_name) {
        this.panel_name = panel_name;
    }
    
    public String getIs_active() {
        return this.is_active;
    }
    
    public void setIs_active(final String is_active) {
        this.is_active = is_active;
        
    
    }

	public String getEmp_type() {
		return emp_type;
	}

	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}

}