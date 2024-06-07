/**
 * @ AUTHOR Amit Dangi
 */

package com.sits.affiliation.approval.inspection_by_registrar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sits.affiliation.approval.consolidate_inspection_by_committee.ConsolidateInspectionByCommitteeModel;
import com.sits.commonApi.commonAPI;
import com.sits.conn.DBConnection;
import com.sits.general.General;
import com.sits.general.Logging;
import com.sits.mailUtils.SendMailDetailModel;
import com.sits.mailUtils.SendMailModel;
import com.sits.mailUtils.SendMailUtil;

public class InspectionByRegistrarManager {

	static Logger l = Logger.getLogger("exceptionlog research activity ReviewApplicationManager");

	/*Static Method to get the Application saved details if any or
	get the selected proposal details from college Registration Master as per submission by commitee head
	Return type is object Json*/
	
	public static JSONObject getApplicationDetails(InspectionByRegistrarModel raModel) {
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
	 			
	 			JSONObject jsonobj1=new JSONObject();
	 			JSONObject finalObject1=new JSONObject();
	 			finalObject1.put("tablename", "vw_user_type_details");
	 			finalObject1.put("columndesc","name");
	 			finalObject1.put("id", "id");
	 			
	 			jsonobj1= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject1);
	 			JSONArray deparr1 = (JSONArray) jsonobj1.get("commondata");
	 			System.out.println("deparr1||"+deparr1.toJSONString());
	 			
	 			conn = DBConnection.getConnection();
	 			
 			query="select distinct aff.session,concat(crm.PROP_INST_NAME,' (',crm.clg_code,')') as PROP_INST_NAME,contact,"
				+ "email,crm.AF_REG_ID,REG_NO,date_format(review_date,'%d/%m/%Y') as review_date,panel_code,review_remarks,"
				+ "aimd.isfinalsubmited as consolidate_finalsubmit,aimd.insp_remarks as consolidate_insp_remarks,"
				+ "aimd.insp_recm as consolidate_insp_recm, aimd.inspection_by as consolidate_review_by,(select panel_name "
				+ "from af_approv_pannel_mast ap where ap.panel_id=crm.panel_code ) as panel_name,imd.inspection_id as "
				+ "registrar_inspection_id,imd.inspection_by as registrar_id,imd.insp_remarks as registrar_remarks, "
				+ "imd.insp_recm as registrar_recm,imd.isfinalsubmited as registrar_finalsubmit,imd.isfinal_acknowledge ,"
				+ "imd2.inspection_id as governbody_id,imd2.insp_remarks as governbody_remarks, imd2.insp_recm as governbody_recm,"
				+ "imd2.isfinalsubmited as governbody_finalsubmit from af_inspection_member_detail aimd,af_clg_reg_mast crm,"
				+ "af_apply_for_affiliation aff left join af_inspection_member_detail imd on  aff.session=imd.session and "
				+ "imd.inspection_type='Registrar' and aff.AFF_ID=imd.af_reg_id left join af_inspection_member_detail imd2  "
				+ "on aff.session=imd2.session and imd2.inspection_type='GovernBody' and aff.AFF_ID=imd2.af_reg_id where "
				+ "crm.AF_REG_ID=aff.AFF_ID and crm.AF_REG_ID=aimd.af_reg_id and aimd.inspection_type='Inspector' and "
				+ "is_final_submit_app='Y' and aff.session='"+General.checknull(raModel.getSession_id())+"' ";
	        			
    			if(!General.checknull(raModel.getInst_name()).trim().equals("")){
    				query += "and PROP_INST_NAME like '%"+General.checknull(raModel.getInst_name())+"%' ";
				}
    			if(!General.checknull(raModel.getMobile_no()).trim().equals("")){
    				query += "and contact='"+General.checknull(raModel.getMobile_no())+"' ";
				}
    			if(!General.checknull(raModel.getEmail_id()).trim().equals("")){
	        		query += "and email='"+General.checknull(raModel.getEmail_id())+"' ";
				}
    			
    			if(!(General.checknull(raModel.getXFROMDATE()).trim().equals("") && General.checknull(raModel.getXTODATE()).trim().equals("")) ){
	        		query += "and date_format(review_date,'%d/%m/%Y') between '"+General.checknull(raModel.getXTODATE())+"' and '"+General.checknull(raModel.getXFROMDATE())+"' ";
				}
    			query += " order by is_final_submit_app_dt asc";		 
	 			psmt = conn.prepareStatement(query);
	 			System.out.println("getApplicationDetails registrar psmt||"+psmt);
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
	 				String consolidate_review_by=rst.getString("consolidate_review_by");
	 				json.put("consolidate_review_by_id",consolidate_review_by);
 				
 					for(int i=0; i<deparr1.size(); i++){
						JSONObject jsn1=	(JSONObject) deparr1.get(i);
						if(jsn1.get("id").equals(consolidate_review_by))
						{
							 json.put("consolidate_review_by",General.checknull(jsn1.get("desc").toString()));
						}
					  }
	 				
					 json.put("REG_FOR_NAME",General.checknull(rst.getString("PROP_INST_NAME")));
					 json.put("contact",General.checknull(rst.getString("contact")));
					 json.put("email",General.checknull(rst.getString("email")));
					 json.put("AF_REG_ID",General.checknull(rst.getString("AF_REG_ID")));
					 json.put("REG_NO",General.checknull(rst.getString("REG_NO")));
					 json.put("panel_code",General.checknull(rst.getString("panel_code")));
					 json.put("remarks",General.checknull(rst.getString("review_remarks")));
					 json.put("panel_name",General.checknull(rst.getString("panel_name")));
					 json.put("consolidate_finalsubmit",General.checknull(rst.getString("consolidate_finalsubmit")));
					 json.put("cons_insp_recm",General.checknull(rst.getString("consolidate_insp_recm")));
					 json.put("cons_insp_remarks",General.checknull(rst.getString("consolidate_insp_remarks")));
					 
					 //20 May New fields added
					 for(int i=0; i<deparr1.size(); i++){
							JSONObject jsn1=	(JSONObject) deparr1.get(i);
							if(jsn1.get("id").equals(rst.getString("registrar_id")))
							{
								 json.put("registrar_id",General.checknull(jsn1.get("desc").toString()));
							}
						  }
					 
					 //json.put("registrar_id",General.checknull(rst.getString("registrar_id")));
					 json.put("registrar_inspection_id",General.checknull(rst.getString("registrar_inspection_id")));
					 json.put("registrar_remarks",General.checknull(rst.getString("registrar_remarks")));
					 json.put("registrar_recm",General.checknull(rst.getString("registrar_recm")));
					 json.put("registrar_finalsubmit",General.checknull(rst.getString("registrar_finalsubmit")));
					 
					 
					/* for(int i=0; i<deparr1.size(); i++){
							JSONObject jsn1=	(JSONObject) deparr1.get(i);
							if(jsn1.get("id").equals(rst.getString("governbody_id")))
							{
								 json.put("governbody_id",General.checknull(jsn1.get("desc").toString()));
							}
						  }*/
					 json.put("governbody_remarks",General.checknull(rst.getString("governbody_remarks")));
					 json.put("governbody_recm",General.checknull(rst.getString("governbody_recm")));
					 json.put("governbody_finalsubmit",General.checknull(rst.getString("governbody_finalsubmit")));
					 json.put("isfinal_acknowledge",General.checknull(rst.getString("isfinal_acknowledge")));
					 jsonArray.add(json);
	        	 }
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[InspectionByRegistrarManager] MethodName=[getApplicationDetails()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[InspectionByRegistrarManager] MethodName=[getApplicationDetails()] :", e.getMessage().toString()));
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
	            	  l.fatal(Logging.logException("FileName=[InspectionByRegistrarManager],MethodName=[getApplicationDetails()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }
	

	
	// save Data
	
	public static String save(InspectionByRegistrarModel formModel, String user_id, String machine) {
		l = Logger.getLogger("exceptionlog");
		Connection conn = null;
		PreparedStatement psmt = null;
		String query = "", msg="0";
		int count = 0; Boolean ismail=false;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			if(!formModel.getInspection_id().equals("")){
				query="update af_inspection_member_detail set isfinal_acknowledge=?,"
						+ "UPDATED_BY=?,UPDATE_DATE=now(),"
						+ "UPDATE_MACHINE=? where session=? and inspection_id=? and inspection_type=? ";
					psmt = conn.prepareStatement(query);
					psmt.setString(1, General.checknull(formModel.getActiontype()));
					psmt.setString(2, user_id);
					psmt.setString(3, General.checknull(formModel.getIp()));
					psmt.setString(4, General.checknull(formModel.getSession_id()));
					psmt.setString(5, General.checknull(formModel.getInspection_id()));
					psmt.setString(6, General.checknull(formModel.getInspection_type()));
					
					System.out.println("InspectorInspectionManager update af_inspection_member_detail ||"+psmt);
					count = psmt.executeUpdate();
					ismail=true;
			}else{
					query="insert into af_inspection_member_detail (session,af_reg_id,inspection_by,"
							+ "insp_remarks,insp_recm,CREATED_BY,CREATED_DATE,CREATED_MACHINE,inspection_type,isfinalsubmited) "
							+ "values(?,?,?,?,?,?,now(),?,?,?)";
					psmt = conn.prepareStatement(query);
					psmt.setString(1, General.checknull(formModel.getSession_id()));
					psmt.setString(2, General.checknull(formModel.getApplicationId()));
					psmt.setString(3, General.checknull(formModel.getInspection_by()));
					psmt.setString(4, General.checknull(formModel.getRemarks()));
					psmt.setString(5, General.checknull(formModel.getRecommendation()));
					psmt.setString(6, user_id);
					psmt.setString(7, General.checknull(formModel.getIp()));
					psmt.setString(8, General.checknull(formModel.getInspection_type()));
					psmt.setString(9, General.checknull(formModel.getActiontype()));
					
					System.out.println("InspectorInspectionManager insert af_inspection_member_detail ||"+psmt);
					count = psmt.executeUpdate();
					
			}
			if (count > 0){
				msg="1";
				conn.commit();
				if(ismail){
				SendMail(formModel);
				}
			} else {
				msg="0";
				conn.rollback();
			}
		} catch (Exception e) {
			msg="0";
			System.out.println("Exception in InspectionByRegistrarManager[SAVE]" + " " + e.getMessage());
			l.fatal(Logging.logException("InspectionByRegistrarManagers[SAVE]", e.toString()));
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
	
	
	
public static String SendMail(InspectionByRegistrarModel formModel) {
		
		String status="Final acknowledgement from registrar mail sent to college";
//		model.setValidate setIsValidate(true);
		String sndrCode = new SendMailUtil().getCparamData("AFILIATION", "MAIL", "SENDER");
		String subject="Confirmation of Successful Affiliation Granted with Dr. A.P.J. Abdul Kalam Technical University";
		ArrayList<SendMailDetailModel> list = new ArrayList<SendMailDetailModel>();
		String bodyText ="";
		bodyText = mailContent(formModel.getReg_no(),formModel.getReg_name());

		SendMailDetailModel mdl = new SendMailDetailModel();
		mdl.setEmail(General.checknull(formModel.getEmail_id()).trim());
		mdl.setTyp("t");
		list.add(mdl);
		
		SendMailModel mailParameters = new SendMailModel();
		mailParameters.setSenderCode(sndrCode);
		mailParameters.setSubject(subject);
		mailParameters.setBodyText(bodyText);
		mailParameters.setMailType("S");
		mailParameters.setModule("AFFILIATION MANAGEMENT");
		mailParameters.setSubModule("Transaction & Approval Process");
		mailParameters.setDescp1(formModel.getReg_no()+"#Inspection by Registrar for Granted Applications");
		
		mailParameters.setDescp2(formModel.getReg_name());
		mailParameters.setCreatedBy(formModel.getInspection_by());
		mailParameters.setIp(formModel.getIp());
		mailParameters.setList(list);
		mailParameters.setMobile(formModel.getMobile_no());
		mailParameters.setDescp3(formModel.getApplicationId());
		
		new SendMailUtil().saveTempMailSender(mailParameters, "temp");
	
		return status;
	}

	public static String mailContent( String Reg_no,String inst_name ) {
		StringBuffer mailcontent = new StringBuffer();
		String MAIL_CONTENT_1 = "";

		MAIL_CONTENT_1 = "<b>Dear "+inst_name+", </b><br><br>"
			+ "<b>Greeting from Dr. A.P.J. Abdul Kalam Technical University</b><br>"
			+ " </br>"
			+ " We are happy to inform that your Affiliation with (Dr. A.P.J. Abdul Kalam Technical University) is completed successfully and Registration Number is "+Reg_no+".<br>"
			+ " </br>"
			+" For further any query related to After Affiliation, feel free to connect with us."
			+ "<br><br><br><b>Warm Regards,</b><br>"
			+ "Dr. A.P.J. Abdul Kalam Technical University!";
	
		mailcontent.append(MAIL_CONTENT_1);
		System.out.println("mailcontent>>>"+mailcontent.toString());
		return mailcontent.toString();
	}
	
	public static JSONObject getReportDetails(InspectionByRegistrarModel raModel) {
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
	 			
/*	 			JSONObject jsonobj1=new JSONObject();
	 			JSONObject finalObject1=new JSONObject();
	 			finalObject1.put("tablename", "employee_mast");
	 			finalObject1.put("columndesc","employeeName");
	 			finalObject1.put("id", "employeeId");
	 			
	 			jsonobj1= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject1);
	 			JSONArray deparr1 = (JSONArray) jsonobj1.get("commondata");*/
	 			
	 			
 			conn = DBConnection.getConnection();
 			query="select * from (";
        	query+="select distinct date_format(is_final_submit_app_dt,'%d-%M-%Y %r') as is_final_submit_app_date,is_final_submit_app_dt,aff.session,PROP_INST_NAME,REG_FOR_NAME,contact,email,crm.AF_REG_ID,REG_NO,date_format(review_date,'%d/%m/%Y') as "
    			+ "review_date,panel_code,review_remarks,crm.isfinalsubmited as consolidate_finalsubmit,consolidate_insp_remarks,consolidate_insp_recm, "
				+"consolidate_review_by,(select panel_name from af_approv_pannel_mast ap where ap.panel_id=crm.panel_code ) as "
				+"panel_name,imd.inspection_id as registrar_inspection_id,imd.inspection_by as registrar_id,imd.insp_remarks as registrar_remarks, "
				+"imd.insp_recm as registrar_recm,imd.isfinalsubmited as registrar_finalsubmit,imd.isfinal_acknowledge ,imd2.inspection_id as "
				+"governbody_id,imd4.insp_remarks as scrutiny_remarks, imd4.insp_recm as scrutiny_recm,imd2.isfinalsubmited as "
				+"governbody_finalsubmit,imd4.isfinalsubmited as scrutinystatus ,"
				+"case when (imd4.isfinalsubmited is null ) THEN 'Pending at Scrutiny Head for Action' " 
				+"when (imd4.isfinalsubmited='Y' and imd3.isfinalsubmited is null ) THEN 'Pending at Inspection Convenor for Action' "
				+"when (imd3.isfinalsubmited='Y' and imd.isfinalsubmited is null ) THEN 'Pending at Registrar for Action' "
				+"when (imd4.isfinalsubmited='Y' and imd3.isfinalsubmited='Y' and imd.isfinalsubmited='Y' and imd2.isfinalsubmited is null) THEN 'Pending at Govenmentbody for Action' "
				+"when (imd.isfinalsubmited='Y' and imd2.isfinalsubmited='Y' and imd.isfinal_acknowledge is null) THEN 'Pending at Registrar for acknowledgement final submit' "
				+"when imd.isfinal_acknowledge='Y' THEN 'Final Submitted Application' else 'Rejected Application' end as registrar_status,date_format(imd.UPDATE_DATE,'%d/%m/%Y') as "
				+"actiondate from af_clg_reg_mast crm,af_apply_for_affiliation aff left join af_inspection_member_detail imd on aff.session=imd.session "
				+"and imd.inspection_type='Registrar' and aff.AFF_ID=imd.af_reg_id left join af_inspection_member_detail imd2 on aff.session=imd2.session "
				+"and imd2.inspection_type='GovernBody' and aff.AFF_ID=imd2.af_reg_id left join af_inspection_member_detail imd3 on aff.session=imd3.session "
				+"and imd3.inspection_type='Inspector' and aff.AFF_ID=imd3.af_reg_id left join (select * from af_inspection_member_detail where concat(af_reg_id,CREATED_DATE) in "
				+"(select concat(af_reg_id,max(CREATED_DATE)) from af_inspection_member_detail where inspection_type='Scrutiny_head' " 
				+"group by af_reg_id) ) as imd4 on aff.session=imd4.session "
				+"and imd4.inspection_type='Scrutiny_head' and aff.AFF_ID=imd4.af_reg_id where crm.AF_REG_ID=aff.AFF_ID and " 
				+"is_final_submit_app='Y' and aff.session='"+General.checknull(raModel.getSession_id())+"' ";
    			
	    			if(!General.checknull(raModel.getInst_name()).trim().equals("")){
	    				query += "and upper(PROP_INST_NAME) like '%"+General.checknull(raModel.getInst_name())+"%' ";
					}
	    			if(!General.checknull(raModel.getApplicationId()).trim().equals("")){
	    				query += "and REG_NO='"+General.checknull(raModel.getApplicationId())+"' ";
					}
	    			/*if(!General.checknull(raModel.getActiontype()).trim().equals("")){
		        		query += "and (case when isfinalsubmited='Y' THEN 'Granted' when isfinalsubmited='N' then 'ReOpen' else 'Pending' end)='"+General.checknull(raModel.getActiontype())+"' ";
					}*/
    			
    			query += " ) as details where upper(registrar_status) like '%"+General.checknull(raModel.getActiontype())+"%'";
				query += " order by is_final_submit_app_dt asc";
	 			psmt = conn.prepareStatement(query);
	 			System.out.println("getInspection by Registrar ReportDetails psmt||"+psmt);
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
	 				
	 				/*for(int i=0; i<deparr1.size(); i++){
						JSONObject jsn1=	(JSONObject) deparr1.get(i);
						if(jsn1.get("id").equals(rst.getString("consolidate_review_by")))
						{
							 json.put("consolidate_review_by",General.checknull(jsn1.get("desc").toString()));
						}
					  }*/
	 				
	 				 json.put("is_final_submit_app_dt",General.checknull(rst.getString("is_final_submit_app_date")));
	 				 json.put("PROP_INST_NAME",General.checknull(rst.getString("PROP_INST_NAME")));
					 json.put("REG_FOR_NAME",General.checknull(rst.getString("REG_FOR_NAME")));
					 json.put("contact",General.checknull(rst.getString("contact")));
					 json.put("email",General.checknull(rst.getString("email")));
					 json.put("AF_REG_ID",General.checknull(rst.getString("AF_REG_ID")));
					 json.put("REG_NO",General.checknull(rst.getString("REG_NO")));
					 json.put("panel_code",General.checknull(rst.getString("panel_code")));
					 json.put("remarks",General.checknull(rst.getString("review_remarks")));
					 json.put("panel_name",General.checknull(rst.getString("panel_name")));
					 
					 //20 May New fields added
					/* for(int i=0; i<deparr1.size(); i++){
							JSONObject jsn1=	(JSONObject) deparr1.get(i);
							if(jsn1.get("id").equals(rst.getString("registrar_id")))
							{
								 json.put("registrar_id",General.checknull(jsn1.get("desc").toString()));
							}
						  }*/
					 
					 //json.put("registrar_id",General.checknull(rst.getString("registrar_id")));
					 json.put("registrar_inspection_id",General.checknull(rst.getString("registrar_inspection_id")));
					 json.put("registrar_remarks",General.checknull(rst.getString("registrar_remarks")));
					 json.put("registrar_recm",General.checknull(rst.getString("registrar_recm")));
					 json.put("registrar_status",General.checknull(rst.getString("registrar_status")));
					 json.put("actiondate",General.checknull(rst.getString("actiondate")));
					 jsonArray.add(json);
	        	 }
	        	 
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[InspectionByRegistrarManagers] MethodName=[getReportDetails()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[InspectionByRegistrarManagers] MethodName=[getReportDetails()] :", e.getMessage().toString()));
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
	            	  l.fatal(Logging.logException("FileName=[InspectionByRegistrarManagers],MethodName=[getReportDetails()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }
	
	
}