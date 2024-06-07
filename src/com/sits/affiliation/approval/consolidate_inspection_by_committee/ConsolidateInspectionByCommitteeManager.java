/**
 * @ AUTHOR Ashwani Kumar
 */

package com.sits.affiliation.approval.consolidate_inspection_by_committee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sits.commonApi.commonAPI;
import com.sits.conn.DBConnection;
import com.sits.general.General;
import com.sits.general.Logging;

public class ConsolidateInspectionByCommitteeManager {

	static Logger l = Logger.getLogger("exceptionlog research activity ReviewApplicationManager");

	/*Static Method to get the Application saved details if any or
	get the selected proposal details from Project Proposal Submission form as per proposal id
	Return type is object Json*/
	
	public static JSONObject getApplicationDetails(ConsolidateInspectionByCommitteeModel raModel) {
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
	 			
	 			conn = DBConnection.getConnection();
	        	query="select distinct session,PROP_INST_NAME,contact,email,AF_REG_ID,REG_NO,date_format(review_date,'%d/%m/%Y') as review_date,panel_code,review_remarks,isfinalsubmited as consolidate_finalsubmit,"
	        			+ "consolidate_insp_remarks,consolidate_insp_recm,( select panel_name from af_approv_pannel_mast ap where ap.panel_id=crm.panel_code ) as panel_name,"
	        			+ "(select isfinalsubmited from af_inspection_member_detail where af_reg_id=crm.AF_REG_ID and session=aff.session and inspection_type='Inspector' order by CREATED_DATE desc limit 1) as isfinalsubmited,"
	        			+ "coalesce((select (case when member_type is not null then 'Y' else 'N' END) from af_approv_pannel_detail where panel_id=crm.panel_code and member_type='"+General.checknull(raModel.getPanel_member())+"' "
    					+ "and issActive='Y' and is_convenor='Y'LIMIT 1),'N') as is_convinor from af_clg_reg_mast crm,af_apply_for_affiliation aff "
	        			+ "where crm.AF_REG_ID=aff.AFF_ID and is_final_submit_app='Y' and session='"+General.checknull(raModel.getSession_id())+"' ";
	        			
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
					 json.put("isfinalsubmited_byinspector",General.checknull(rst.getString("isfinalsubmited")));
					 json.put("is_convinor", General.checknull(rst.getString("is_convinor")));
					 jsonArray.add(json);
	        	 }
	        	 
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[ConsolidateInspectionByCommitteeManager] MethodName=[getTargetAchievementDetail()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[ConsolidateInspectionByCommitteeManager] MethodName=[getTargetAchievementDetail()] :", e.getMessage().toString()));
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
	            	  l.fatal(Logging.logException("FileName=[ConsolidateInspectionByCommitteeManager],MethodName=[getUtilizationCertificateList()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }
	
	public static JSONObject getPanelDetails(ConsolidateInspectionByCommitteeModel raModel) {
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
	 			finalObject1.put("tablename", "employee_mast");
	 			finalObject1.put("columndesc","employeeName");
	 			finalObject1.put("id", "employeeId");
	 			
	 			jsonobj1= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject1);
	 			JSONArray deparr1 = (JSONArray) jsonobj1.get("commondata");
	 			
	 			conn = DBConnection.getConnection();
	        	query="select inspection_by,session,insp_remarks,insp_recm,panel_id from "
	        			+ "af_inspection_member_detail imd,af_approv_pannel_detail apd "
	        			+ " where imd.inspection_by=apd.member_type and imd.inspection_type='Inspector' ";
	        	
			        	if(!General.checknull(raModel.getApplicationId()).trim().equals("")){
			        		query += "and af_reg_id='"+General.checknull(raModel.getApplicationId())+"' ";
						}
	        			if(!General.checknull(raModel.getPanel_id()).trim().equals("")){
			        		query += "and panel_id='"+General.checknull(raModel.getPanel_id())+"' ";
						}
	        			
	 			psmt = conn.prepareStatement(query);
	 		   //System.out.println("getPanelDetails psmt||"+psmt);
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
	 				//System.out.println("deparr1||"+deparr1.toJSONString());
	 				for(int i=0; i<deparr1.size(); i++){
						JSONObject jsn1=	(JSONObject) deparr1.get(i);
						if(jsn1.get("id").equals(rst.getString("inspection_by")))
						{
							 json.put("inspection_by",General.checknull(jsn1.get("desc").toString()));
						}
					  }
	 				
					 json.put("remarks",General.checknull(rst.getString("insp_remarks")));
					 json.put("insp_recm",General.checknull(rst.getString("insp_recm")));
					 json.put("inspection_by_id",General.checknull(rst.getString("inspection_by")));
					 jsonArray.add(json);
	        	 }
	        	 //System.out.println("jsonArray||"+jsonArray.toJSONString());
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[ConsolidateInspectionByCommitteeManager] MethodName=[getTargetAchievementDetail()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[ConsolidateInspectionByCommitteeManager] MethodName=[getTargetAchievementDetail()] :", e.getMessage().toString()));
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
	            	  l.fatal(Logging.logException("FileName=[ConsolidateInspectionByCommitteeManager],MethodName=[getUtilizationCertificateList()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }

	
	// save Data
	
	public static String save(ConsolidateInspectionByCommitteeModel model, String user_id, String machine) {
		l = Logger.getLogger("exceptionlog");
		Connection conn = null;
		PreparedStatement psmt = null;
		String qry = "",query="", msg="0";
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			/*qry="update af_clg_reg_mast set isfinalsubmited=?,isfinalsubmited_dt=now() ,consolidate_insp_remarks=?,"
					+ " consolidate_insp_recm=?,consolidate_review_by=?"
					+ ",review_date=now(),is_final_submit_app=? where AF_REG_ID=?";
			psmt = conn.prepareStatement(qry);	
	
			psmt.setString(1, General.checknull(model.getActiontype()));
			psmt.setString(2, General.checknull(model.getRemarks()));
			psmt.setString(3, General.checknull(model.getRecommendation()));
			psmt.setString(4, General.checknull(model.getInspection_by()));
			psmt.setString(5, General.checknull(model.getActiontype()));
			psmt.setString(6, General.checknull(model.getApplicationId()));
			//System.out.println("psmt update||"+psmt);
			int cnt = psmt.executeUpdate();
			if (cnt > 0){*/
				query="insert into af_inspection_member_detail (session,af_reg_id,inspection_by,"
						+ "insp_remarks,insp_recm,CREATED_BY,CREATED_DATE,CREATED_MACHINE,inspection_type,isfinalsubmited) "
						+ "values(?,?,?,?,?,?,now(),?,?,?)";
				psmt = conn.prepareStatement(query);
				psmt.setString(1, General.checknull(model.getSession_id()));
				psmt.setString(2, General.checknull(model.getApplicationId()));
				psmt.setString(3, General.checknull(model.getInspection_by()));
				psmt.setString(4, General.checknull(model.getRemarks()));
				psmt.setString(5, General.checknull(model.getRecommendation()));
				psmt.setString(6, user_id);
				psmt.setString(7, General.checknull(model.getIp()));
				psmt.setString(8, General.checknull(model.getInspection_type()));
				psmt.setString(9, General.checknull(model.getActiontype().equals("N")?"RO":model.getActiontype()));
				
				System.out.println("ConsolidateInspectionByCommitteeManager insert af_inspection_member_detail ||"+psmt);
				int count = psmt.executeUpdate();
				if(count>0){
				msg="1";
				conn.commit();
				/*}*/
			} else {
				msg="0";
				conn.rollback();
			}
		} catch (Exception e) {
			msg="0";
			System.out.println("Exception in ConsolidateInspectionByCommitteeManager[SAVE]" + " " + e.getMessage());
			l.fatal(Logging.logException("ConsolidateInspectionByCommitteeManagers[SAVE]", e.toString()));
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
	
	public static JSONObject getReportDetails(ConsolidateInspectionByCommitteeModel raModel) {
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
	 			
	 			conn = DBConnection.getConnection();
	        	query="select distinct session,REG_FOR_NAME,PROP_INST_NAME,contact,email,AF_REG_ID,REG_NO,date_format(review_date,'%d/%m/%Y') as review_date,"
        			+ "panel_code,review_remarks,case when isfinalsubmited='Y' THEN 'Granted' when isfinalsubmited='N' then 'ReOpen' else 'Pending' end as consolidate_status,"
        			+ "date_format(isfinalsubmited_dt,'%d/%m/%Y') as isfinalsubmited_dt,consolidate_insp_remarks,consolidate_insp_recm,consolidate_review_by,(select panel_name "
        			+ "from af_approv_pannel_mast ap where ap.panel_id=crm.panel_code ) as panel_name,(select isfinalsubmited from af_inspection_member_detail where af_reg_id=crm.AF_REG_ID "
        			+ "and session=aff.session and inspection_type='Inspector' order by CREATED_DATE desc limit 1) as isfinalsubmited,coalesce((select (case when member_type is not null then 'Y' else 'N' END) "
        			+ "from af_approv_pannel_detail where panel_id=crm.panel_code and member_type='"+General.checknull(raModel.getPanel_member())+"' and issActive='Y' and is_convenor='Y'LIMIT 1),'N') "
					+ "as is_convinor from af_clg_reg_mast crm,af_apply_for_affiliation aff where crm.AF_REG_ID=aff.AFF_ID and is_final_submit_app='Y' and session='"+General.checknull(raModel.getSession_id())+"' ";
	        			
	        			if(!General.checknull(raModel.getInst_name()).trim().equals("")){
	        				query += "and upper(PROP_INST_NAME) like '%"+General.checknull(raModel.getInst_name())+"%' ";
						}
	        			if(!General.checknull(raModel.getApplicationId()).trim().equals("")){
	        				query += "and REG_NO='"+General.checknull(raModel.getApplicationId())+"' ";
						}
	        			if(!General.checknull(raModel.getActiontype()).trim().equals("")){
			        		query += "and (case when isfinalsubmited='Y' THEN 'Granted' when isfinalsubmited='N' then 'ReOpen' else 'Pending' end)='"+General.checknull(raModel.getActiontype())+"' ";
						}
	        			/*
	        			if(!(General.checknull(raModel.getXFROMDATE()).trim().equals("") && General.checknull(raModel.getXTODATE()).trim().equals("")) ){
			        		query += "and date_format(review_date,'%d/%m/%Y') between '"+General.checknull(raModel.getXTODATE())+"' and '"+General.checknull(raModel.getXFROMDATE())+"' ";
						}
	        			*/ 
	 			psmt = conn.prepareStatement(query);
	 			System.out.println("getReportDetails psmt||"+psmt);
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
	 				 json.put("REG_NO",General.checknull(rst.getString("REG_NO")));
					 json.put("REG_FOR_NAME",General.checknull(rst.getString("REG_FOR_NAME")));
					 json.put("PROP_INST_NAME",General.checknull(rst.getString("PROP_INST_NAME")));
					 json.put("contact",General.checknull(rst.getString("contact")));
					 json.put("email",General.checknull(rst.getString("email")));
					 json.put("AF_REG_ID",General.checknull(rst.getString("AF_REG_ID")));
					 
					 json.put("panel_code",General.checknull(rst.getString("panel_code")));
					 json.put("remarks",General.checknull(rst.getString("review_remarks")));
					 json.put("panel_name",General.checknull(rst.getString("panel_name")));
					 json.put("cons_insp_recm",General.checknull(rst.getString("consolidate_insp_recm")));
					 json.put("cons_insp_remarks",General.checknull(rst.getString("consolidate_insp_remarks")));
					 json.put("consolidate_status",General.checknull(rst.getString("consolidate_status")));
					 json.put("is_convinor", General.checknull(rst.getString("is_convinor")));
					 json.put("isfinalsubmited_dt", General.checknull(rst.getString("isfinalsubmited_dt")));
					 jsonArray.add(json);
	        	 }
	        	 
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[ConsolidateInspectionByCommitteeManager] MethodName=[getReportDetails()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[ConsolidateInspectionByCommitteeManager] MethodName=[getReportDetails()] :", e.getMessage().toString()));
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
	            	  l.fatal(Logging.logException("FileName=[ConsolidateInspectionByCommitteeManager],MethodName=[getReportDetails()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }
	
}
