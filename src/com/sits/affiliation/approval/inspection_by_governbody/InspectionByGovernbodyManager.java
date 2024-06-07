/**
 * @ AUTHOR Amit Dangi
 */

package com.sits.affiliation.approval.inspection_by_governbody;

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

public class InspectionByGovernbodyManager {

	static Logger l = Logger.getLogger("exceptionlog research activity ReviewApplicationManager");

	/*Static Method to get the Application saved details if any or
	get the selected proposal details from college Registration Master as per submission by comitee head
	Return type is object Json*/
	
	public static JSONObject getApplicationDetails(InspectionByGovernbodyModel raModel) {
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
	 			
 			query="select distinct aff.session,PROP_INST_NAME,contact,email,crm.AF_REG_ID,REG_NO,date_format(review_date,'%d/%m/%Y') as review_date,"
 					+ "panel_code,review_remarks,crm.isfinalsubmited as consolidate_finalsubmit,consolidate_insp_remarks,consolidate_insp_recm,consolidate_review_by,"
 					+ "(select panel_name from af_approv_pannel_mast ap where ap.panel_id=crm.panel_code ) as panel_name,imd.inspection_id as registrar_inspection_id,"
 					+ "imd.inspection_by as registrar_id,imd.insp_remarks "
 					+ "as registrar_remarks, imd.insp_recm as registrar_recm,imd.isfinalsubmited as registrar_finalsubmit "
 					+ ",imd2.inspection_id as governbody_inspection_id,imd2.inspection_by as governbody_id,imd2.insp_remarks as governbody_remarks, imd2.insp_recm as governbody_recm,imd2.isfinalsubmited as governbody_finalsubmit "
 					+ "from af_clg_reg_mast crm,af_apply_for_affiliation "
 					+ "aff left join af_inspection_member_detail imd on aff.session=imd.session and imd.inspection_type='Registrar' and aff.AFF_ID=imd.af_reg_id "
 					+ "left join af_inspection_member_detail imd2 on aff.session=imd2.session and imd2.inspection_type='GovernBody' and aff.AFF_ID=imd2.af_reg_id "
 					+ "where crm.AF_REG_ID=aff.AFF_ID and is_final_submit_app='Y' and imd.isfinalsubmited='Y' and aff.session='"+General.checknull(raModel.getSession_id())+"' ";
 		        	 			
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
	 			System.out.println("getApplicationDetails governbody psmt||"+psmt);
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
	 				
	 				for(int i=0; i<deparr1.size(); i++){
						JSONObject jsn1=	(JSONObject) deparr1.get(i);
						if(jsn1.get("id").equals(rst.getString("consolidate_review_by")))
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
					 
					 json.put("registrar_inspection_id",General.checknull(rst.getString("registrar_inspection_id")));
					 json.put("registrar_remarks",General.checknull(rst.getString("registrar_remarks")));
					 json.put("registrar_recm",General.checknull(rst.getString("registrar_recm")));
					 json.put("registrar_finalsubmit",General.checknull(rst.getString("registrar_finalsubmit")));
					 
					 //Government body saved fields
					 for(int i=0; i<deparr1.size(); i++){
							JSONObject jsn1=	(JSONObject) deparr1.get(i);
							if(jsn1.get("id").equals(rst.getString("governbody_id")))
							{
								 json.put("governbody_id",General.checknull(jsn1.get("desc").toString()));
							}
						  }
					 json.put("governbody_inspection_id",General.checknull(rst.getString("governbody_inspection_id")));
					 json.put("governbody_remarks",General.checknull(rst.getString("governbody_remarks")));
					 json.put("governbody_recm",General.checknull(rst.getString("governbody_recm")));
					 json.put("governbody_finalsubmit",General.checknull(rst.getString("governbody_finalsubmit")));
					 
					 jsonArray.add(json);
	        	 }
	        	 
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[InspectionByGovernbodyManager] MethodName=[getApplicationDetails()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[InspectionByGovernbodyManager] MethodName=[getApplicationDetails()] :", e.getMessage().toString()));
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
	            	  l.fatal(Logging.logException("FileName=[InspectionByGovernbodyManager],MethodName=[getUtilizationCertificateList()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }
	

	
	// save Data
	
	public static String save(InspectionByGovernbodyModel formModel, String user_id, String machine) {
		l = Logger.getLogger("exceptionlog");
		Connection conn = null;
		PreparedStatement psmt = null;
		String query = "", msg="0";
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			if(!formModel.getInspection_id().equals("")){
				query="update af_inspection_member_detail set isfinalsubmited=?,insp_remarks=?,insp_recm=?,"
						+ "UPDATED_BY=?,UPDATE_DATE=now(),"
						+ "UPDATE_MACHINE=? where session=? and inspection_id=? and inspection_type=? ";
					psmt = conn.prepareStatement(query);
					psmt.setString(1, General.checknull(formModel.getActiontype()));
					psmt.setString(2, General.checknull(formModel.getRemarks()));
					psmt.setString(3, General.checknull(formModel.getRecommendation()));
					psmt.setString(4, user_id);
					psmt.setString(5, General.checknull(formModel.getIp()));
					psmt.setString(6, General.checknull(formModel.getSession_id()));
					psmt.setString(7, General.checknull(formModel.getInspection_id()));
					psmt.setString(8, General.checknull(formModel.getInspection_type()));
					
					System.out.println("InspectionByGovernbodyManager update af_inspection_member_detail ||"+psmt);
					count = psmt.executeUpdate();
					
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
					
					System.out.println("InspectionByGovernbodyManager insert af_inspection_member_detail ||"+psmt);
					count = psmt.executeUpdate();
					
			}
			if (count > 0){
				msg="1";
				conn.commit();
			} else {
				msg="0";
				conn.rollback();
			}
		} catch (Exception e) {
			msg="0";
			System.out.println("Exception in InspectionByGovernbodyManager[SAVE]" + " " + e.getMessage());
			l.fatal(Logging.logException("InspectionByGovernbodyManager[SAVE]", e.toString()));
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
	
}
