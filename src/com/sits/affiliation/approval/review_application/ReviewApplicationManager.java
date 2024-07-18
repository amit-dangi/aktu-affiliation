/**
 * @ AUTHOR Amit Dangi
 */
package com.sits.affiliation.approval.review_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sits.common.ZipUtils;
import com.sits.commonApi.commonAPI;
import com.sits.conn.DBConnection;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;
import java.io.File;
import org.apache.commons.fileupload.FileItem;
import com.sits.general.ReadProps;

public class ReviewApplicationManager {
	static Logger l = Logger.getLogger("exceptionlog research activity ReviewApplicationManager");

	/*Static Method to get the Application saved details if any or
	get the selected proposal details from Project Proposal Submission form as per proposal id
	Return type is object Json*/
	public static JSONObject getApplicationDetails(ReviewApplicationModel raModel) {
        PreparedStatement psmt = null;
        Connection conn=null;
        ResultSet rst = null;
        String query="";
        JSONObject objectJson=new JSONObject();        
        JSONArray jsonArray = new JSONArray();
	         try {
				 
	        	JSONObject jsonobj=new JSONObject();
	 			JSONObject finalObject=new JSONObject();
	 			finalObject.put("tablename", "academic_session_master");
	 			finalObject.put("columndesc","session");
	 			finalObject.put("id", "id");
	 			jsonobj= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
	 			JSONArray deparr = (JSONArray) jsonobj.get("commondata");
	 			//System.out.println("sessionarray||"+deparr);
	 			
	 			conn = DBConnection.getConnection();
	        	query="select distinct date_format(is_final_submit_app_dt,'%d-%M-%Y %r') as is_final_submit_app_date,aff.session,concat(PROP_INST_NAME,'(',clg_code,')') as PROP_INST_NAME,clg_code,contact,email,crm.AF_REG_ID,REG_NO,date_format(Payment_Date,'%d/%m/%Y') "
        			+ "as Payment_Date,panel_code,review_remarks from af_clg_reg_mast crm,af_apply_for_affiliation aff,af_inspection_member_detail imd "
        			+ "where crm.AF_REG_ID=aff.AFF_ID and is_final_submit_app='Y' and crm.AF_REG_ID=imd.af_reg_id and imd.isfinalsubmited='Y' "
        			+ "and imd.inspection_type='Scrutiny_head' and aff.session='"+General.checknull(raModel.getSession_id())+"' ";
	        			
	        			if(!General.checknull(raModel.getInst_name()).trim().equals("")){
	        				query += "and PROP_INST_NAME like '%"+General.checknull(raModel.getInst_name())+"%' ";
						}
	        			if(!General.checknull(raModel.getMobile_no()).trim().equals("")){
	        				query += "and contact='"+General.checknull(raModel.getEmail_id())+"' ";
						}
	        			if(!General.checknull(raModel.getEmail_id()).trim().equals("")){
			        		query += "and email='"+General.checknull(raModel.getEmail_id())+"' ";
						}
	        			if(!General.checknull(raModel.getDistrict()).trim().equals("")){
	                		query += "and DISTRICT='"+General.checknull(raModel.getDistrict())+"' ";
	        			}
	        			if(!General.checknull(raModel.getRequestType()).trim().equals("")){
	                		query += "and request_id='"+General.checknull(raModel.getRequestType())+"' ";
	        			}
	        			if(!General.checknull(raModel.getXTODATE()).trim().equals("")){
			        		query += "and Payment_Date='"+General.checknull(raModel.getXTODATE())+"' ";
						}
	        			if(!General.checknull(raModel.getXFROMDATE()).trim().equals("")){
			        		query += "and Payment_Date='"+General.checknull(raModel.getXFROMDATE())+"' ";
						}
	        			query += " order by is_final_submit_app_dt asc";
	 			psmt = conn.prepareStatement(query);
	 			System.out.println("getApplicationDetails psmt||"+psmt);
	 			rst = psmt.executeQuery();
	 			
	 			while (rst.next()) {
	 				JSONObject json= new JSONObject();
	 				for(int i=0; i<deparr.size(); i++){
						JSONObject jsn=	(JSONObject) deparr.get(i);
						if(jsn.get("id").equals(rst.getString("session")))
						{
							 json.put("session",General.checknull(jsn.get("desc").toString()));
						}
					}
	 				 json.put("is_final_submit_app_dt", General.checknull(rst.getString("is_final_submit_app_date")));
	 				 json.put("clg_code",General.checknull(rst.getString("clg_code")));
					 json.put("REG_FOR_NAME",General.checknull(rst.getString("PROP_INST_NAME")));
					 json.put("contact",General.checknull(rst.getString("contact")));
					 json.put("email",General.checknull(rst.getString("email")));
					 json.put("AF_REG_ID",General.checknull(rst.getString("AF_REG_ID")));
					 json.put("REG_NO",General.checknull(rst.getString("REG_NO")));
					 json.put("Payment_Date",General.checknull(rst.getString("Payment_Date")));
					 json.put("panel_code",General.checknull(rst.getString("panel_code")));
					 json.put("remarks",General.checknull(rst.getString("review_remarks")));
					 jsonArray.add(json);
	        	 }
	        	 
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[ReviewApplicationManager] MethodName=[getApplicationDetails()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[ReviewApplicationManager] MethodName=[getApplicationDetails()] :", e.getMessage().toString()));
	          }finally {
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
	            	  l.fatal(Logging.logException("FileName=[ReviewApplicationManager],MethodName=[getUtilizationCertificateList()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }
	
	public static String save(ReviewApplicationModel model, String user_id, String machine) {
		l = Logger.getLogger("exceptionlog");
		Connection conn = null;
		PreparedStatement psmt = null;
		String qry = "", msg="0";
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			qry="update af_clg_reg_mast set panel_code=?, review_remarks=?,review_by=?"
					+ ",review_date=now() where AF_REG_ID=?";
			psmt = conn.prepareStatement(qry);	
	
			psmt.setString(1, General.checknull(model.getPanel_id()));
			psmt.setString(2, General.checknull(model.getRemarks()));
			psmt.setString(3, General.checknull(user_id));
			psmt.setString(4, General.checknull(model.getApplicationId()));
			//System.out.println("psmt update||"+psmt);
			int cnt = psmt.executeUpdate();
			if (cnt > 0){
				msg="1";
				conn.commit();
			} else {
				msg="0";
				conn.rollback();
			}
		} catch (Exception e) {
			msg="0";
			System.out.println("Exception in ReviewApplicationManager[SAVE]" + " " + e.getMessage());
			l.fatal(Logging.logException("ReviewApplicationManager[SAVE]", e.toString()));
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
	
	

public static ArrayList<ReviewApplicationModel> getSavedData(String id, String login_id, String typ){ 
	PreparedStatement psmt = null;
	ResultSet rst = null;
	Connection conn = null;
	String qry = "";
	ArrayList<ReviewApplicationModel> model = new ArrayList<ReviewApplicationModel>();
	try{
		conn=DBConnection.getConnection();

		qry="select mrt.session ssn, (select if(count(*)=0, 'Y', 'N') val from af_reuest_form_payment afp where is_submitted='Y' and "
				+ "payment_status='paid' and cand_id=? and session=mrt.session and request_id=mrt.request_id and sub_request_id=mrt.sub_request_id"
				+ ") Payment_Status, mrt.id, mrt.AFF_ID, mrt.session, mrt.request_id, rt.req_desc, mrt.sub_request_id, st.sub_request_id "
				+ "sub_request_name, date_format(mrt.applied_on, '%d/%m/%Y') date, fc.amount, fc.additional_cost, "
				+ "mrt.Payment_Status P, st.is_noc as noc, mrt.course_name "
				+ "from af_apply_for_affiliation mrt, af_request_type rt, af_manage_request_type_sub_type st, af_manage_fee_configration fc "
				+ "where mrt.request_id=rt.req_id and mrt.sub_request_id=st.MRT_ID and mrt.request_id=fc.request_type "
				+ "and mrt.sub_request_id=fc.sub_request_type and fc. man_fee_config_id=mrt.fee_config_id and "
				+ "fc.req_type='AC' and mrt.AFF_ID=? ";
		if(General.checknull(typ).equals("2")){
			qry+=" group by st.sub_request_id";
		}
		psmt = conn.prepareStatement(qry);
		psmt.setString(1, id);
		psmt.setString(2, id);
		rst= psmt.executeQuery();
		//System.out.println("0000000000000000"+psmt);
		if(rst!=null){
			while(rst.next()){
				ReviewApplicationModel afm = new ReviewApplicationModel();
				afm.setId(General.checknull(rst.getString("id")));
				afm.setAf_id(General.checknull(rst.getString("AFF_ID")));
				afm.setReq_id(General.checknull(rst.getString("request_id")));
				afm.setReq_name(General.checknull(rst.getString("req_desc")));
				afm.setSub_req_id(General.checknull(rst.getString("sub_request_id")));
				afm.setSub_req_name(General.checknull(rst.getString("sub_request_name")));
				afm.setApp_date(General.checknull(rst.getString("date")));
				int amount=Integer.parseInt(General.checknull(rst.getString("amount")))+Integer.parseInt(General.checknull(rst.getString("additional_cost")));
				afm.setAmt(String.valueOf(amount));
				afm.setPay_status(General.checknull(rst.getString("Payment_Status")));
				afm.setIs_Noc(General.checknull(rst.getString("noc")));
				afm.setCourse_Id(General.checknull(rst.getString("course_name")));
				afm.setCourse_name(General.checknull(rst.getString("course_name")));
				afm.setSession(General.checknull(rst.getString("ssn")));
				model.add(afm);
			}
		}
	}catch(Exception e){
		System.out.println("EXCEPTION IS CAUSED BY: ReviewApplicationManager [getSavedData]" + " " + e.getMessage().trim().toUpperCase());
		l.fatal(Logging.logException("ReviewApplicationManager [getSavedData]", e.toString()));
		return model;
	}finally {
		try {
			conn.close();
			psmt.close();
			rst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	return model;
}

public static String getDetailForDiffrenceAmtPaid(String id, String req_id, String Sub_req_id, String amt){
	PreparedStatement pst = null;
	Connection conn = null;
	ResultSet rst = null;
	String query = "", is_submit="N";
	query = "select is_submitted from af_reuest_form_payment where CAND_ID=? and request_id=? and "
			+ "sub_request_id=? and payment_status='paid' and is_submitted='Y' and payment_amount=?";
	try {
		conn = DBConnection.getConnection();
		pst = conn.prepareStatement(query);
		pst.setString(1, General.checknull(id));
		pst.setString(2, General.checknull(req_id));
		pst.setString(3, General.checknull(Sub_req_id));
		pst.setString(4, General.checknull(amt));
		//System.out.println("pst::::"+pst);
		rst = pst.executeQuery();
		if (rst.next()) {
			is_submit = General.checknull(rst.getString("is_submitted"));
		}
	} catch (Exception e) {
		System.out.println("FileName=[ApplyAffiliationManager],MethodName=[getDetailForDiffrenceAmtPaid()]" + e.getMessage().toString());
		l.fatal(Logging.logException("FileName=[ApplyAffiliationManager],MethodName=[getDetailForDiffrenceAmtPaid()]", e.getMessage().toString()));
	} finally {
		try {
			if (rst != null) {
				rst.close();
				rst = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (final Exception e) {
			l.fatal(Logging.logException("FileName=[ApplyAffiliationManager],MethodName=[getDetailForDiffrenceAmtPaid()]", e.getMessage().toString()));
		}
	}
	return is_submit;
}



//Added by ashwani kumar use for Write Zip File Date 27-May-2024
public static String downloadZipFile(String cand_id, String appNo,String clg_code) {
	String OUTPUT_ZIP_FILE = ReadProps.getkeyValue("document.path", "sitsResource") + clg_code+"_"+appNo + ".zip";
	String SOURCE_FOLDER = ReadProps.getkeyValue("document.path", "sitsResource") +"AFFILIATION_TEMP/"+ appNo; // SourceFolder
	try {  
			ZipUtils zu = new ZipUtils();
			zu.generateFileList(new File(SOURCE_FOLDER), SOURCE_FOLDER);
			zu.zipIt(OUTPUT_ZIP_FILE, SOURCE_FOLDER);
			// Delete the download folder after zip
	} catch (Exception e) {
		System.out.println("Error in ReviewApplicationManager[downloadZipFile] : " + e.getMessage());
	}  
	return OUTPUT_ZIP_FILE;
}
	
	
}