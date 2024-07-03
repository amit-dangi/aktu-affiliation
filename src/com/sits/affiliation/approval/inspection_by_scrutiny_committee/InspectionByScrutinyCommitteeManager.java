/**
 * @ AUTHOR Amit Dangi
 */

package com.sits.affiliation.approval.inspection_by_scrutiny_committee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sits.commonApi.commonAPI;
import com.sits.conn.DBConnection;
import com.sits.general.General;
import com.sits.general.Logging;
import com.sits.mailUtils.SendMailDetailModel;
import com.sits.mailUtils.SendMailModel;
import com.sits.mailUtils.SendMailUtil;
import com.sits.general.ReadProps;
import com.sits.common.ZipUtils;
import java.io.File;

public class InspectionByScrutinyCommitteeManager {
	static Logger l = Logger.getLogger("exceptionlog research activity ReviewApplicationManager");

	/*
	 * Static Method to get the Application saved details if any or get the
	 * selected proposal details from college Registration Master as per
	 * submission by comitee head Return type is object Json
	 */

	public static JSONObject getApplicationDetails(InspectionByScrutinyCommitteeModel raModel) {
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rst = null;
		String query = "";
		JSONObject objectJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			JSONObject jsonobj = new JSONObject();
			JSONObject finalObject = new JSONObject();
			finalObject.put("tablename", "academic_session_master");
			finalObject.put("columndesc", "session");
			finalObject.put("id", "id");

			jsonobj = commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
			JSONArray deparr = (JSONArray) jsonobj.get("commondata");

			conn = DBConnection.getConnection();

			query = "select distinct date_format(is_final_submit_app_dt,'%d-%M-%Y %r') as is_final_submit_app_date,aff.session,concat(crm.PROP_INST_NAME,' (',crm.clg_code,')') as PROP_INST_NAME, contact, "
					+ "crm.clg_code,email, crm.AF_REG_ID, REG_NO,crm.is_final_submit_app,(select case when (isfinalsubmited='RO' and crm.is_final_submit_app='Y') then "
					+ "'' ELSE isfinalsubmited end from af_inspection_member_detail where af_reg_id=crm.AF_REG_ID order by "
					+ "CREATED_DATE desc limit 1) status, (select insp_remarks from af_inspection_member_detail where af_reg_id=crm.AF_REG_ID order by "
					+ "CREATED_DATE desc limit 1) insp_remarks,(select if(DATE_ADD(CREATED_DATE, INTERVAL 3 DAY)>now(),'Y','N') as is_finalsubmit_open "
					+ "from af_inspection_member_detail where inspection_type='Scrutiny_head' and af_reg_id=crm.AF_REG_ID "
					+ "order by CREATED_DATE desc limit 1) as is_finalsubmit_open from af_clg_reg_mast crm, af_apply_for_affiliation aff where crm.AF_REG_ID=aff.AFF_ID and " 
					+ "(is_final_submit_app in ('Y') OR (crm.AF_REG_ID in (select af_reg_id from af_inspection_member_detail where session='90' and isfinalsubmited='RO')) ) and "
					+ "aff.session='"+ General.checknull(raModel.getSession_id()) + "' ";
			if (!General.checknull(raModel.getInst_name()).trim().equals("")) {
				query += "and PROP_INST_NAME like '%" + General.checknull(raModel.getInst_name().trim()) + "%' ";
			}
			if (!General.checknull(raModel.getMobile_no()).trim().equals("")) {
				query += "and contact='" + General.checknull(raModel.getMobile_no().trim()) + "' ";
			}
			if (!General.checknull(raModel.getEmail_id()).trim().equals("")) {
				query += "and email='" + General.checknull(raModel.getEmail_id().trim()) + "' ";
			}
			if (!(General.checknull(raModel.getXFROMDATE()).trim().equals("") && General.checknull(raModel.getXTODATE()).trim().equals(""))) {
				query += "and date_format(review_date,'%d/%m/%Y') between '" + General.checknull(raModel.getXTODATE())+ "' and '" + General.checknull(raModel.getXFROMDATE()) + "' ";
			}
			query += "order by is_final_submit_app_dt asc";
			
			psmt = conn.prepareStatement(query);
			System.out.println("psmt||"+psmt);
			rst = psmt.executeQuery();
			while (rst.next()) {
				JSONObject json = new JSONObject();
				for (int i = 0; i < deparr.size(); i++) {
					JSONObject jsn = (JSONObject) deparr.get(i);
					if (jsn.get("id").equals(rst.getString("session"))) {
						json.put("session", General.checknull(jsn.get("desc").toString()));
					}
				}
				
				json.put("clg_code",General.checknull(rst.getString("clg_code")));
				json.put("is_final_submit_app_dt",General.checknull(rst.getString("is_final_submit_app_date")));
				json.put("REG_FOR_NAME", General.checknull(rst.getString("PROP_INST_NAME")));
				json.put("contact", General.checknull(rst.getString("contact")));
				json.put("email", General.checknull(rst.getString("email")));
				json.put("AF_REG_ID", General.checknull(rst.getString("AF_REG_ID")));
				json.put("REG_NO", General.checknull(rst.getString("REG_NO")));
				json.put("STATUS", General.checknull(rst.getString("status")));
				json.put("REMARKS", General.checknull(rst.getString("insp_remarks")));
				json.put("is_finalsubmit_open", General.checknull(rst.getString("is_finalsubmit_open")));
				jsonArray.add(json);
			}

			objectJson.put("Applicationlist", jsonArray);

		} catch (Exception e) {
			System.out.println("FileName=[InspectionByScrutinyCommitteeManager] MethodName=[getApplicationDetails()] :"+ e.getMessage().toString());
			l.fatal(Logging.logException("FileName=[InspectionByScrutinyCommitteeManager] MethodName=[getApplicationDetails()] :", e.getMessage().toString()));
		} finally {
			try {
				if (rst != null) {
					rst.close();
					rst = null;
				}
				if (psmt != null) {
					psmt.close();
					psmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (final Exception e) {
				l.fatal(Logging.logException("FileName=[InspectionByScrutinyCommitteeManager],MethodName=[getUtilizationCertificateList()]",e.getMessage().toString()));
			}
		}
		return objectJson;
	}

	public static String save(InspectionByScrutinyCommitteeModel formModel) {
		l = Logger.getLogger("exceptionlog");
		Connection conn = null;
		PreparedStatement psmt = null;
		String query = "", msg = "0";
		int cnt = 0, count = 0;
		try {
			conn = DBConnection.getConnection();

			query = "insert into af_inspection_member_detail (session,af_reg_id,inspection_by,insp_remarks,"
					+ "CREATED_BY,CREATED_DATE,CREATED_MACHINE,inspection_type,isfinalsubmited) values(?,?,?,?,?,now(),?,?,?)";
			psmt = conn.prepareStatement(query);
			psmt.setString(1, General.checknull(formModel.getSession_id()));
			psmt.setString(2, General.checknull(formModel.getInspection_id()));
			psmt.setString(3, General.checknull(formModel.getInspection_by()));
			psmt.setString(4, General.checknull(formModel.getRemarks()));
			psmt.setString(5, General.checknull(formModel.getUpdatedBy()));
			psmt.setString(6, General.checknull(formModel.getIp()));
			psmt.setString(7, General.checknull(formModel.getInspection_type()));
			psmt.setString(8, General.checknull(formModel.getStatus()));
			count = psmt.executeUpdate();

			if (count > 0) {
				if (General.checknull(formModel.getStatus()).equals("RO")) {
					query = "";psmt = null;
					query = "UPDATE af_clg_reg_mast SET is_final_submit_app='N', is_final_submit_app_dt=now() WHERE AF_REG_ID=?";
					psmt = conn.prepareStatement(query);
					psmt.setString(1, General.checknull(formModel.getInspection_id()));
					cnt = psmt.executeUpdate();
				}
				
				if (General.checknull(formModel.getStatus()).equals("Y")) {
					query = "";psmt = null;
					query = "UPDATE af_clg_reg_mast SET is_final_submit_app='Y' WHERE AF_REG_ID=?";
					psmt = conn.prepareStatement(query);
					psmt.setString(1, General.checknull(formModel.getInspection_id()));
					cnt = psmt.executeUpdate();
				}

				if ((General.checknull(formModel.getStatus()).equals("RO")) || (General.checknull(formModel.getStatus()).equals("N"))) {
					String status = "";
					if (General.checknull(formModel.getStatus()).equals("RO")) {
						status = "Re-Open";
					}
					if (General.checknull(formModel.getStatus()).equals("N")) {
						status = "Rejected";
					}
					String reg_no = "", reg_id = "", inst_name = "", inst_email = "", inst_mob = "",scrutiny_remarks="";
					query = "";
					psmt = null;
					query = "select REG_NO, AF_REG_ID, PROP_INST_NAME, EMAIL, CONTACT,"
							+ "(select insp_remarks from af_inspection_member_detail imd where inspection_type='Scrutiny_head' "
							+ "and imd.inspection_type='Scrutiny_head' and crm.AF_REG_ID=imd.af_reg_id "
							+ "order by imd.CREATED_DATE desc limit 1) as scrutiny_remarks from af_clg_reg_mast crm where AF_REG_ID=?";
					psmt = conn.prepareStatement(query);
					psmt.setString(1, General.checknull(formModel.getInspection_id()));
					ResultSet rst = psmt.executeQuery();
					if (rst.next()) {
						reg_no = General.checknull(rst.getString("REG_NO"));
						reg_id = General.checknull(rst.getString("AF_REG_ID"));
						inst_name = General.checknull(rst.getString("PROP_INST_NAME"));
						inst_email = General.checknull(rst.getString("EMAIL"));
						inst_mob = General.checknull(rst.getString("CONTACT"));
						scrutiny_remarks=General.checknull(rst.getString("scrutiny_remarks"));
					}

					SendMail(status, reg_no, reg_id, inst_name, inst_email, inst_mob,scrutiny_remarks, formModel.getInspection_by(), formModel.getIp());
				}
				msg = "1";
			} else {
				msg = "0";
			}
		} catch (Exception e) {
			msg = "0";
			System.out.println("Exception in InspectionByScrutinyCommitteeManager[SAVE]" + " " + e.getMessage());
			l.fatal(Logging.logException("InspectionByScrutinyCommitteeManager[SAVE]", e.toString()));
		} finally {
			try {
				conn.close();
				psmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return msg;
	}

	public static String SendMail(String app_status, String reg_no, String reg_id, String inst_name, String inst_email, String inst_mob,String scrutiny_remarks, String insp_by, String ip) {
		String status = "acknowledgement from Scrutiny Committee mail sent to college";
		String sndrCode = new SendMailUtil().getCparamData("AFILIATION", "MAIL", "SENDER");
		String subject = "Affiliation Form " + app_status + " with Dr. A.P.J. Abdul Kalam Technical University";
		ArrayList<SendMailDetailModel> list = new ArrayList<SendMailDetailModel>();
		String bodyText = "";
		bodyText = mailContent(reg_no, inst_name, app_status,scrutiny_remarks);

		SendMailDetailModel mdl = new SendMailDetailModel();
		mdl.setEmail(General.checknull(inst_email).trim());
		mdl.setTyp("t");
		list.add(mdl);

		SendMailModel mailParameters = new SendMailModel();
		mailParameters.setSenderCode(sndrCode);
		mailParameters.setSubject(subject);
		mailParameters.setBodyText(bodyText);
		mailParameters.setMailType("S");
		mailParameters.setModule("AFFILIATION MANAGEMENT");
		mailParameters.setSubModule("Scrutiny Committee");
		mailParameters.setDescp1(reg_no + "#Inspection by Scrutiny Committee");
		mailParameters.setDescp2(reg_id);
		mailParameters.setDescp3(insp_by);
		mailParameters.setCreatedBy(insp_by);
		mailParameters.setIp(ip);
		mailParameters.setList(list);
		mailParameters.setMobile(inst_mob);

		new SendMailUtil().saveTempMailSender(mailParameters, "temp");

		return status;
	}

	public static String mailContent(String Reg_no, String inst_name, String status,String scrutiny_remarks) {
		StringBuffer mailcontent = new StringBuffer();
		String MAIL_CONTENT_1 = "";

		if(status.equals("Re-Open")){
			MAIL_CONTENT_1 = "<b>Dear " + inst_name + ",</b><br><br>"
				+ "<b>Greeting from Dr. A.P.J. Abdul Kalam Technical University</b><br></br>"
				+ "Your Affiliation Form (" + Reg_no + ") has been Re-Opened, You are requested to update the information "
				+ "within 3 Days (As per University Guidline).<br><br>"
				+ "<b>Inspection Remarks :</b> " + scrutiny_remarks + "<br><br><br><br>"
				+ "<b>Note :</b> After Update the Details, Please do Final Submit<br><br><br><br>"
				+ "<b>Warm Regards,</b><br>" 
				+ "Dr. A.P.J. Abdul Kalam Technical University!";
		}else{
			MAIL_CONTENT_1 = "<b>Dear " + inst_name + ",</b><br><br>"
					+ "Your Affiliation Form (" + Reg_no + ") has been Rejected, for more details, Please contact with University. <br><br><br><br>"
					+ "<b>Warm Regards,</b><br>" 
					+ "Dr. A.P.J. Abdul Kalam Technical University!";	
		}
		mailcontent.append(MAIL_CONTENT_1);
		//System.out.println("mailcontent>>>" + mailcontent.toString());
		return mailcontent.toString();
	}
	
	// Added by ashwani kumar use for Write Zip File Date 31-May-2024
	public static String downloadZipFile(String cand_id, String appNo,String clg_code) {
		String OUTPUT_ZIP_FILE = ReadProps.getkeyValue("document.path", "sitsResource") + clg_code+"_"+appNo + ".zip";
		String SOURCE_FOLDER = ReadProps.getkeyValue("document.path", "sitsResource") +"AFFILIATION_TEMP/"+ appNo; // SourceFolder
		try {  
				ZipUtils zu = new ZipUtils();
				zu.generateFileList(new File(SOURCE_FOLDER), SOURCE_FOLDER);
				zu.zipIt(OUTPUT_ZIP_FILE, SOURCE_FOLDER);
				// Delete the download folder after zip
		} catch (Exception e) {
			System.out.println("Error in InspectionByScrutinyCommitteeManager[downloadZipFile] : " + e.getMessage());
		}  
		return OUTPUT_ZIP_FILE;
	}

}