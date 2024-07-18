/**
 * @ AUTHOR Amit Dangi
 */
package com.sits.affiliation.approval.inspector_inspection_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sits.commonApi.commonAPI;
import com.sits.conn.DBConnection;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;
import java.io.File;
import org.apache.commons.fileupload.FileItem;
import com.sits.general.ReadProps;

public class InspectorInspectionManager {
	static Logger l = Logger.getLogger("exceptionlog activity InspectorInspectionManager");

	
	/*Static ArrayList<InspectorInspectionModel> Method to get the computer Peripheral Facilities
	 *  saved Details if any as per proposal Inst_Id
	Return type is Model list*/
	public static ArrayList<InspectorInspectionModel> computerPeripheralFacilitiesDetails(String Inst_Id,String inspection_by) {
		String cSql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<InspectorInspectionModel> list = new ArrayList<>();
		JSONObject finalObjectPtype = new JSONObject();
		JSONObject jsonobjPtype = new JSONObject();
		jsonobjPtype.put("id", "");
		finalObjectPtype = commonAPI.getDropDownByWebService("rest/apiServices/getDisciplineName-A", jsonobjPtype);
		JSONArray projtypearr = (JSONArray) finalObjectPtype.get("ProgDD");
		
		try {
			conn = DBConnection.getConnection();
			cSql = "select fd.FAC_ID,fd.AF_FAC_ID,fd.pgm_type,fd.fac_type,if(is_other_chk='Y', concat('Others -',fac_name), (select administrative_type from af_administrative_configuration_details where ADMC_detail_id=fac_name) ) as fac_name,"
				+ "fd.avbl_room,fdi.avbl_room_inspection,fdi.inspection_status from af_facility_detail fd left join af_facility_detail_inspection fdi "
				+ "on fd.FAC_ID=fdi.FAC_ID and fdi.inspection_by='"+inspection_by+"' where fd.AF_FAC_ID='"+Inst_Id+"' ";
			pstmt = conn.prepareStatement(cSql);
			rst = pstmt.executeQuery();
			//System.out.println("InspectorInspectionManager computerPeripheralFacilitiesDetails Search Details"+pstmt);
			while (rst.next()) {
				InspectorInspectionModel sm = new InspectorInspectionModel();
					sm.setFAC_ID(General.checknull(rst.getString("FAC_ID")));
					sm.setFac_type(General.checknull(rst.getString("fac_type")));
					for (int i = 0; i < projtypearr.size(); i++) {
						JSONObject jsn = (JSONObject) projtypearr.get(i);
						if (jsn.get("id").equals(rst.getString("pgm_type"))) {
							sm.setProg_type(General.checknull(jsn.get("name").toString()));
						}
					}
					//sm.setProg_type(General.checknull(rst.getString("pgm_type")));
					sm.setFac_name(General.checknull(rst.getString("fac_name")));
					sm.setAvbl_room(General.checknull(rst.getString("avbl_room")));
					sm.setAvbl_room_inspection(General.checknull(rst.getString("avbl_room_inspection")));
					sm.setInspection_status(General.checknull(rst.getString("inspection_status")));
					list.add(sm);
				} ;
		} catch (Exception e) {
			System.out.println("Error in InspectorInspectionManager[computerPeripheralFacilitiesDetails] : " + e.getMessage());
			l.fatal(Logging.logException("InspectorInspectionManager[computerPeripheralFacilitiesDetails]", e.getMessage().toString()));
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rst != null) rst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	/*Static ArrayList<InspectorInspectionModel> Method to get administrative & Amenities Details
	 *  saved Details if any as per proposal Inst_Id
	Return type is Model list*/
	public static ArrayList<InspectorInspectionModel> administrative_AmenitiesDetails(String Inst_Id,String inspection_by) {
		String cSql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<InspectorInspectionModel> list = new ArrayList<>();
		JSONObject finalObjectPtype = new JSONObject();
		JSONObject jsonobjPtype = new JSONObject();
		jsonobjPtype.put("id", "");
		finalObjectPtype = commonAPI.getDropDownByWebService("rest/apiServices/degree_by_degreetype-A", jsonobjPtype);
		JSONArray projtypearr = (JSONArray) finalObjectPtype.get("ProgDD");
		try {
			conn = DBConnection.getConnection();
/*			cSql = "select BD_ID,AF_BD_ID,ADMNSTRATIVE_TYP,DETAIL_TYP,AVBL_CARPET,avbl_carpet_inspection,AVBL_ROOM,avbl_room_inspection,inspection_status "
					+ "from af_block_details where AF_BD_ID='"+Inst_Id+"'";*/
			
			cSql="select bd.BD_ID,bd.AF_BD_ID,bd.pgm_type,if(is_other_chk='Y', bd.ADMNSTRATIVE_TYP,(select administrative_type "
				+"from af_administrative_configuration_details where ADMC_detail_id=bd.ADMNSTRATIVE_TYP) ) as ADMNSTRATIVE_TYP,"
				+"bd.DETAIL_TYP,bd.AVBL_CARPET,bd.AVBL_ROOM,bdi.avbl_carpet_inspection,"
				+"bdi.avbl_room_inspection,bdi.inspection_status,bd.AVBL_CARPET<(select available_carpet from af_administrative_configuration_details "
				+"acd where acd.ADMC_detail_id=bd.ADMNSTRATIVE_TYP limit 1) as config_avbl_crpt from af_block_details bd left join af_block_details_inspection bdi "
				+"on bd.BD_ID=bdi.BD_ID and bdi.inspection_by='"+inspection_by+"' where AF_BD_ID='"+Inst_Id+"'";
			pstmt = conn.prepareStatement(cSql);
			rst = pstmt.executeQuery();
			//System.out.println("InspectorInspectionManager administrative AmenitiesSearch Details"+pstmt);
			while (rst.next()) {
				InspectorInspectionModel sm = new InspectorInspectionModel();
					sm.setBd_id(General.checknull(rst.getString("BD_ID")));
					for (int i = 0; i < projtypearr.size(); i++) {
						JSONObject jsn = (JSONObject) projtypearr.get(i);
						if (jsn.get("id").equals(rst.getString("pgm_type"))) {
							sm.setProg_type(General.checknull(jsn.get("name").toString()));
						}
					}
					sm.setAdministrative_typ(General.checknull(rst.getString("ADMNSTRATIVE_TYP")));
					sm.setDetail_typ(General.checknull(rst.getString("DETAIL_TYP")));
					sm.setAvbl_carpet(General.checknull(rst.getString("AVBL_CARPET")));
					sm.setAvbl_carpet_inspection(General.checknull(rst.getString("avbl_carpet_inspection")));
					sm.setAvbl_room(General.checknull(rst.getString("AVBL_ROOM")));
					sm.setAvbl_room_inspection(General.checknull(rst.getString("avbl_room_inspection")));
					sm.setInspection_status(General.checknull(rst.getString("inspection_status")));
					sm.setIs_config_avbl_crpt(General.checknull(rst.getString("config_avbl_crpt")));
					
					list.add(sm);
				} ;
		} catch (Exception e) {
			System.out.println("Error in InspectorInspectionManager[administrative_AmenitiesDetailsDetails] : " + e.getMessage());
			l.fatal(Logging.logException("InspectorInspectionManager[administrative_AmenitiesDetailsDetails]", e.getMessage().toString()));
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rst != null) rst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//Static ArrayList<InspectorInspectionModel> Method to get Infrastructure Information
	public static ArrayList<InspectorInspectionModel> InfrastructureDetails(String Inst_Id, String inspection_by) {
		String cSql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<InspectorInspectionModel> list = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			/*JSONObject jsonobj = new JSONObject();
			JSONObject finalObject = new JSONObject();
			finalObject.put("tablename", "academic_degree_master a, academic_faculty_master b");
			finalObject.put("columndesc", "concat(a.degree_name_description , ' (',b.faculty,')')");
			finalObject.put("id", "a.id");
			finalObject.put("cond", "a.faculty_name=b.id and a.IS_ACTIVE='Y' ");
			jsonobj = commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
			JSONArray deparr = (JSONArray) jsonobj.get("commondata");*/
			
			JSONObject jsonobj = new JSONObject();
			JSONObject finalObject = new JSONObject();
			finalObject.put("id", ""); // use getProgramName
			jsonobj = commonAPI.getDropDownByWebService("rest/apiServices/getDisciplineName-A", finalObject);
			JSONArray deparr = (JSONArray) jsonobj.get("ProgDD");
			
			cSql="select afd.INFRA_ID,AF_INFRA_ID,afd.PROGRAM,"
				+ "if(is_other_chk='Y', COMP_PERI_NAME, (select administrative_type from af_administrative_configuration_details where ADMC_detail_id=COMP_PERI_NAME) ) as fac_name,"
				+ "AVLB_ROOM as avbl_room,afdi.avbl_room_inspection,afdi.inspection_status,AVLB_AREA,TYPE_OF_LAND,AVBL_LAND,AVBL_CONST_AREA  "
				+ "from af_infrastructure_detail afd left join af_infrastructure_detail_inspection afdi "
				+ "on afd.INFRA_ID=afdi.INFRA_ID and afdi.inspection_by='"+inspection_by+"' where afd.AF_INFRA_ID='"+Inst_Id+"' ";
			//System.out.println("InspectorInspectionManager InfrastructureDetails Search Details||"+cSql);
			pstmt = conn.prepareStatement(cSql);
			rst = pstmt.executeQuery();
			while (rst.next()) {
				InspectorInspectionModel sm = new InspectorInspectionModel();
					sm.setFAC_ID(General.checknull(rst.getString("INFRA_ID")));
					for (int i = 0; i < deparr.size(); i++) {
						JSONObject jsn = (JSONObject) deparr.get(i);
						if (jsn.get("id").equals(rst.getString("PROGRAM"))) {
							sm.setFac_type(General.checknull(jsn.get("name").toString()));
						}
					}
					//sm.setFac_type(General.checknull(rst.getString("fac_type")));
					sm.setFac_name(General.checknull(rst.getString("fac_name")));
					sm.setAvbl_room(General.checknull(rst.getString("avbl_room")));
					sm.setAvbl_room_inspection(General.checknull(rst.getString("avbl_room_inspection")));
					sm.setInspection_status(General.checknull(rst.getString("inspection_status")));
					
					sm.setTYPE_OF_LAND(General.checknull(rst.getString("TYPE_OF_LAND")));
					sm.setAVBL_LAND(General.checknull(rst.getString("AVBL_LAND")));
					sm.setAVBL_CONST_AREA(General.checknull(rst.getString("AVBL_CONST_AREA")));
					list.add(sm);
				} ;
		} catch (Exception e) {
			System.out.println("Error in InspectorInspectionManager[InfrastructureDetails] : " + e.getMessage());
			l.fatal(Logging.logException("InspectorInspectionManager[InfrastructureDetails]", e.getMessage().toString()));
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rst != null) rst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/*Static ArrayList<InspectorInspectionModel> Method to get the get faculty Details
	 *  saved Details if any as per proposal Inst_Id
	Return type is Model list*/
	public static ArrayList<InspectorInspectionModel> getfacultyDetails(String Inst_Id,String inspection_by) {
		String cSql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<InspectorInspectionModel> list = new ArrayList<>();
		try {
			conn = DBConnection.getConnection();
			/*cSql = "select FD_ID, AF_FD_ID, fac_name, aadhar, pan, father_name, qualification, "
					+ "designation,is_director,inspection_status from af_faculty_detail where AF_FD_ID='"+Inst_Id+"'";*/
			cSql ="select fd.FD_ID, AF_FD_ID, fac_id,fac_name,(case when gender='M' then 'Male' when gender='F' "
				+ "then 'Female' else 'Other' end) as gender,date_format(doj,'%d/%m/%Y') as doj, aadhar, pan, father_name, qualification, designation,"
				+ "is_director,fdi.inspection_status,department,course_name,branch_name,shift,"
				+ "( select  concat(reference_id,'_', file_name ) from file_attachment where reference_id=fd.FD_ID and trim(file_type)='emp_doc_detail' "
				+ " order by CREATED desc limit 1 )  as file_name from af_faculty_detail fd left join af_faculty_detail_inspection fdi "
				+ "on fd.FD_ID=fdi.FD_ID and fdi.inspection_by='"+inspection_by+"' where fd.AF_FD_ID='"+Inst_Id+"'";
			
			System.out.println("InspectorInspectionManager getfacultyDetails Search Details"+cSql);
			pstmt = conn.prepareStatement(cSql);
			rst = pstmt.executeQuery();
			while (rst.next()) {
				InspectorInspectionModel sm = new InspectorInspectionModel();
					sm.setFAC_ID(General.checknull(rst.getString("FD_ID")));
					sm.setFac_name(General.checknull(rst.getString("fac_name")));
					sm.setGender(General.checknull(rst.getString("gender")));
					sm.setDoj(General.checknull(rst.getString("doj")));
					sm.setAadharno(General.checknull(rst.getString("fac_id")));
					sm.setPanno(General.checknull(rst.getString("pan")));
					sm.setFather_name(General.checknull(rst.getString("father_name")));
					sm.setDepartment(General.checknull(rst.getString("department")));
					sm.setQualification(General.checknull(rst.getString("qualification")));
					sm.setDesignation(General.checknull(rst.getString("designation")));
					sm.setIs_director(General.checknull(rst.getString("is_director")));
					sm.setInspection_status(General.checknull(rst.getString("inspection_status")));
					sm.setCourse_name(General.checknull(rst.getString("course_name")));
					sm.setBranch_name(General.checknull(rst.getString("branch_name")));
					sm.setShift(General.checknull(rst.getString("shift")));
					sm.setFile_name(General.checknull(rst.getString("file_name")));
					list.add(sm);
				} ;
		} catch (Exception e) {
			System.out.println("Error in InspectorInspectionManager[getfacultyDetails] : " + e.getMessage());
			l.fatal(Logging.logException("InspectorInspectionManager[getfacultyDetails]", e.getMessage().toString()));
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rst != null) rst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/*Static ArrayList<InspectorInspectionModel> Method to get the get Questionnaires Details
	 *  saved Details if any as per proposal Inst_Id
	Return type is Model list*/
	public static ArrayList<InspectorInspectionModel> getquestionnairesDetails(String Inst_Id,String inspection_by) {
		String cSql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<InspectorInspectionModel> list = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			/*New union added for inspection questionare in this af_questionnaire_param_mast only table will used*/
			/*cSql="select QUES_TYP,file_name, id,a.QUES_ID as QUES_ID,a.QUES_ID as QUES_ID, a.FEEDBACK_DESC as QUES_DESC, a.isActive as isActive, a.subtyp, "
					+ "a.optVal, a.status, a.IS_ATTACH_REQ,inspection_status from ("
					+ "select QUES_TYP,(select concat(file_attachment_id,'_',file_name) from file_attachment where file_type='QUESTIONNAIRE_DOC' and "
					+ "table_name='AF_QUESTIONNAIRE_DETAILS' and reference_id=qd.id) file_name, qd.id id,m.QUES_ID, FEEDBACK_DESC, qd.isActive, "
					+ "concat(qd.field_1,',',qd.field_2,',',qd.field_3) subtyp, "
					+ "if(IS_SUB_QUES='Y', (select group_concat(OPT_VAL) from af_questionnaire_param_detail d where d.QUES_ID=m.QUES_ID), group_concat('','','')) optVal, 'U' status, IS_ATTACH_REQ,"
					+ "(select inspection_status from af_questionnaire_details_inspection qdi where qdi.id=qd.id and inspection_by='"+inspection_by+"' limit 1) as inspection_status "
					+ "from af_questionnaire_param_mast m, "
					+ " af_questionnaire_details qd where m.IS_ACTIVE='Y' and m.QUES_TYP='A' "
					+ "and m.QUES_ID=qd.QUES_ID and qd.AF_QUES_ID='"+Inst_Id+"' group by m.QUES_ID "
					+ "union " 
					+ "select QUES_TYP,'' file_name,aqd.id, qpm.QUES_ID, FEEDBACK_DESC,'N', '' subtyp,'', 'N' status,'N' IS_ATTACH_REQ ,"
					+ "(select inspection_status from af_questionnaire_details_inspection qdi where qdi.id=aqd.id and inspection_by='"+inspection_by+"' limit 1) as inspection_status "
					+ "from af_questionnaire_param_mast qpm left join af_questionnaire_details aqd on aqd.AF_QUES_ID='"+Inst_Id+"' and aqd.QUES_ID=qpm.QUES_ID where "
					+ "IS_ACTIVE='Y' and QUES_TYP='I'  ) a where a.QUES_ID is not null group by a.QUES_ID";
			*/
			cSql="select QUES_TYP,file_name, id,a.QUES_ID as QUES_ID, QUES_DESC, a.isActive as isActive, a.subtyp, a.optVal,"
				+ " a.status, a.IS_ATTACH_REQ,inspection_status from ( select QUES_TYP,file_name, id,a.QUES_ID as QUES_ID,"
				+ " a.FEEDBACK_DESC as QUES_DESC, a.isActive as isActive, a.subtyp, a.optVal, a.status, a.IS_ATTACH_REQ,"
				+ "inspection_status from (select QUES_TYP,qd.id id,m.QUES_ID, FEEDBACK_DESC, qd.isActive,"
				+ "concat(qd.field_1,',',qd.field_2,',',qd.field_3) subtyp, if(IS_SUB_QUES='Y',"
				+ "(select group_concat(OPT_VAL) from af_questionnaire_param_detail d where d.QUES_ID=m.QUES_ID), group_concat('','','')) optVal,"
				+ " 'U' status, IS_ATTACH_REQ, (select inspection_status from af_questionnaire_details_inspection qdi where qdi.id=qd.id and inspection_by='"+inspection_by+"' limit 1) as inspection_status "
				+ "from af_questionnaire_param_mast m, af_questionnaire_details qd  where m.IS_ACTIVE='Y' and m.QUES_TYP='A' and  m.QUES_ID=qd.QUES_ID and qd.AF_QUES_ID='"+Inst_Id+"' group by m.QUES_ID) as a "
				+ "left join (select concat(fa.file_attachment_id,'_',fa.file_name) as file_name,fa.reference_id,file_type from file_attachment fa order by fa.CREATED desc ) as filetable  on filetable.reference_id=a.id "
				+ "and filetable.file_type='QUESTIONNAIRE_DOC' where a.QUES_ID is not null group by a.QUES_ID  union  select QUES_TYP,'' file_name,aqd.id, qpm.QUES_ID,  FEEDBACK_DESC,'N', '' subtyp,'', 'N' status,'N' IS_ATTACH_REQ ,"
				+ "(select inspection_status from  af_questionnaire_details_inspection qdi where qdi.id=aqd.id and inspection_by='"+inspection_by+"' limit 1) as inspection_status from  af_questionnaire_param_mast qpm"
				+ " left join af_questionnaire_details aqd on aqd.AF_QUES_ID='"+Inst_Id+"' and  aqd.QUES_ID=qpm.QUES_ID where IS_ACTIVE='Y' and QUES_TYP='I' ) a where a.QUES_ID is not null group by a.QUES_ID";
			pstmt = conn.prepareStatement(cSql);
			System.out.println("InspectorInspectionManager getquestionnairesDetails Search Details"+pstmt);
			rst = pstmt.executeQuery();
			while (rst.next()) {
				InspectorInspectionModel sm = new InspectorInspectionModel();
				
					sm.setQues_id(General.checknull(rst.getString("QUES_ID")));
					sm.setQues_desc(General.checknull(rst.getString("QUES_DESC")));
					sm.setIsActive(General.checknull(rst.getString("isActive")));
					sm.setField_1(General.checknull(rst.getString("optVal")));
					sm.setField_2(General.checknull(rst.getString("subtyp")));
					sm.setField_3(General.checknull(rst.getString("status")));
					sm.setIs_attach_req(General.checknull(rst.getString("IS_ATTACH_REQ")));
					sm.setId(General.checknull(rst.getString("id")));
					sm.setFile_name(General.checknull(rst.getString("file_name")));
					//Question type will manage form jsp
					sm.setQues_type(General.checknull(rst.getString("QUES_TYP")));
					sm.setInspection_status(General.checknull(rst.getString("inspection_status")));
					
					list.add(sm);
				} ;
		} catch (Exception e) {
			System.out.println("Error in InspectorInspectionManager[getquestionnairesDetails] : " + e.getMessage());
			l.fatal(Logging.logException("InspectorInspectionManager[getquestionnairesDetails]", e.getMessage().toString()));
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rst != null) rst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/*Static Method to get the Application saved details if any or
	get the selected proposal details from Project Proposal Submission form as per proposal id
	Return type is object Json*/
	public static JSONObject getApplicationDetails(InspectorInspectionModel raModel) {
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
	        	query="select distinct request_id,(select isfinalsubmited from af_inspection_member_detail where af_reg_id=crm.AF_REG_ID and session=aff.session and inspection_type='Inspector' and inspection_by in (select member_type from af_approv_pannel_detail where panel_id=crm.panel_code and issActive='Y' and is_convenor='Y') order by CREATED_DATE desc limit 1) as isfinalsubmited,"
	        			+ "session,PROP_INST_NAME,contact,email,AF_REG_ID,REG_NO,date_format(Payment_Date,'%d/%m/%Y') as Payment_Date,panel_code,review_remarks,"
	        			+ "is_final_submit_app,coalesce((select (case when member_type is not null then 'Y' else 'N' END) from af_approv_pannel_detail where panel_id=crm.panel_code and member_type='"+General.checknull(raModel.getInspection_by())+"' and issActive='Y' LIMIT 1),'N') "
	        			+ "as is_pannel_member,coalesce((select (case when member_type is not null then 'Y' else 'N' END) from af_approv_pannel_detail where panel_id=crm.panel_code and member_type='"+General.checknull(raModel.getInspection_by())+"' and issActive='Y' and is_convenor='Y' LIMIT 1),'N') as is_convinor, "
    					+ "(select member_type from af_approv_pannel_detail where panel_id=crm.panel_code  and issActive='Y' and is_convenor='Y' LIMIT 1) as convinor_id,DISTRICT from af_clg_reg_mast crm,af_apply_for_affiliation aff "
	        			+ "where crm.AF_REG_ID=aff.AFF_ID and is_final_submit_app='Y' and session='"+General.checknull(raModel.getSession_id())+"' ";
	        			if(raModel.getLoginId().toUpperCase().contains("DM")){
	        				query += " and DISTRICT='"+General.checknull(raModel.getS_district())+"' and aff.request_id='RT0001' ";
	        			}
	        			if(!General.checknull(raModel.getInst_name()).trim().equals("")){
	        				query += "and PROP_INST_NAME like '%"+General.checknull(raModel.getInst_name())+"%' ";
						}
	        			if(!General.checknull(raModel.getMobile_no()).trim().equals("")){
	        				query += "and contact='"+General.checknull(raModel.getEmail_id())+"' ";
						}
	        			if(!General.checknull(raModel.getEmail_id()).trim().equals("")){
			        		query += "and email='"+General.checknull(raModel.getEmail_id())+"' ";
						}
	        			if(!(General.checknull(raModel.getXFROMDATE()).trim().equals("") && General.checknull(raModel.getXTODATE()).trim().equals("")) ){
			        		query += "and date_format(review_date,'%d/%m/%Y') between '"+General.checknull(raModel.getXTODATE())+"' and '"+General.checknull(raModel.getXFROMDATE())+"' ";
						}
	        			query += "and crm.panel_code is not null and AF_REG_ID in (select af_reg_id from af_inspection_member_detail where isfinalsubmited='Y') order by is_final_submit_app_dt asc";
	 			psmt = conn.prepareStatement(query);
	 			System.out.println("InspectorInspectionManager getApplicationDetails psmt||"+psmt);
	 			rst = psmt.executeQuery();
	 			
	 			while (rst.next()) {
	 				JSONObject json= new JSONObject();
	 				//Getting applications needed detail
	 				for(int i=0; i<deparr.size(); i++){
						JSONObject jsn=	(JSONObject) deparr.get(i);
						if(jsn.get("id").equals(rst.getString("session")))
						{
							 json.put("session",General.checknull(jsn.get("desc").toString()));
						}
	 				}
	 				
	 				 json.put("request_id",General.checknull(rst.getString("request_id")));
	        		 json.put("session_id",General.checknull(rst.getString("session")));
					 json.put("REG_FOR_NAME",General.checknull(rst.getString("PROP_INST_NAME")));
					 json.put("contact",General.checknull(rst.getString("contact")));
					 json.put("email",General.checknull(rst.getString("email")));
					 json.put("AF_REG_ID",General.checknull(rst.getString("AF_REG_ID")));
					 json.put("REG_NO",General.checknull(rst.getString("REG_NO")));
					 json.put("Payment_Date",General.checknull(rst.getString("Payment_Date")));
					 json.put("panel_code",General.checknull(rst.getString("panel_code")));
					 json.put("remarks",General.checknull(rst.getString("review_remarks")));
					 json.put("isfinalsubmited",General.checknull(rst.getString("isfinalsubmited")));
					 if(General.checknull(rst.getString("is_final_submit_app")).equals("Y") && General.checknull(rst.getString("is_pannel_member")).equals("Y"))
					 {json.put("is_pannel_member","Y");}
					 else{json.put("is_pannel_member","N");}
					 json.put("is_convinor",General.checknull(rst.getString("is_convinor")));
					 json.put("convinor_id",General.checknull(rst.getString("convinor_id")));
					 jsonArray.add(json);
	        	 }
	        	 
	        	 objectJson.put("Applicationlist", jsonArray);
	        	 
	          } catch (Exception e) {
	           System.out.println("FileName=[InspectorInspectionManager] MethodName=[getApplicationDetails()] :"+ e.getMessage().toString());
	           l.fatal(Logging.logException("FileName=[InspectorInspectionManager] MethodName=[getApplicationDetails()] :", e.getMessage().toString()));
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
	            	  l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getApplicationDetails()]", e.getMessage().toString()));
	              }
	          }
	         return objectJson;
	    }
	
	public static JSONObject save(InspectorInspectionModel formModel, String machine, String user_id,List<FileItem> items) {
		Connection conn = null;
		ResultSet rst = null;
		PreparedStatement psmt = null;		
		String query = "" ,inspection_by=formModel.getInspection_by();
		int count = 0,i=0;
		int delcount=0;
		int cnt[]=null;
		JSONObject jSonDataFinalObj = new JSONObject();
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			String inspection_id=formModel.getInspection_id();
			String is_convinor=formModel.getIs_convinor();
			/*if(formModel.getIsfinalsubmited().equals("Y")){
				query="update af_inspection_member_detail set isfinalsubmited=?,UPDATED_BY=?,UPDATE_DATE=now(),"
						+ "UPDATE_MACHINE=? where session=? and af_reg_id=? and inspection_by='"+inspection_by+"' ";
				psmt = conn.prepareStatement(query);
				psmt.setString(++i, General.checknull(formModel.getIsfinalsubmited()));
				psmt.setString(++i, user_id);
				psmt.setString(++i, machine);
				psmt.setString(++i, General.checknull(formModel.getSession_id()));
				psmt.setString(++i, General.checknull(formModel.getInst_Id()));
				//psmt.setString(++i, inspection_by);
				
				System.out.println("InspectorInspectionManager update finalsubmit ||"+psmt);
				count = psmt.executeUpdate();
				
				if (count > 0){ 
				conn.commit();
				jSonDataFinalObj.put("status", "Inspection Details Submitted Successfully");
				jSonDataFinalObj.put("flag", "Y");
				}else {
				conn.rollback();				
				jSonDataFinalObj.put("status", ApplicationConstants.EXCEPTION_MESSAGE);
				jSonDataFinalObj.put("flag", "N");
				}	
			}else{*/
				/*if(!inspection_id.equals("")){
					System.out.println("in if");	
					query="update af_inspection_member_detail set isfinalsubmited=?,insp_remarks=?,insp_recm=?,"
						+ "UPDATED_BY=?,UPDATE_DATE=now(),"
						+ "UPDATE_MACHINE=? where session=? and inspection_id=? and inspection_by='"+inspection_by+"' ";
					psmt = conn.prepareStatement(query);
					psmt.setString(1, General.checknull(formModel.getIsfinalsubmited()));
					psmt.setString(2, General.checknull(formModel.getInsp_remarks()));
					psmt.setString(3, General.checknull(formModel.getInsp_recm()));
					psmt.setString(4, user_id);
					psmt.setString(5, General.checknull(formModel.getIp()));
					psmt.setString(6, General.checknull(formModel.getSession_id()));
					psmt.setString(7, inspection_id);
					
					//System.out.println("InspectorInspectionManager update af_inspection_member_detail ||"+psmt);
					count = psmt.executeUpdate();
					
				}else {*/
				System.out.println("in else");
				String inst_id=General.checknull(formModel.getInst_Id());
			String delqry = "DELETE FROM af_inspection_member_detail where inspection_by='"+inspection_by+"'"
					+ " and session='"+formModel.getSession_id()+"' and af_reg_id='"+inst_id+"' and inspection_type in ('Inspector','Member')";
			PreparedStatement psmtdel= conn.prepareStatement(delqry);
			delcount=psmtdel.executeUpdate();
	query="insert into af_inspection_member_detail (session,af_reg_id,inspection_by,insp_remarks,insp_recm,"
			+ "emp_type,member_name,department,designation,contant_no,is_member_head,"
			+ "CREATED_BY,CREATED_DATE,CREATED_MACHINE,inspection_type,isfinalsubmited) "
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?)";
		psmt = conn.prepareStatement(query,psmt.RETURN_GENERATED_KEYS);
				for(int t=0; t<formModel.getMembersDetails().size(); t++){	
					JSONObject reqobj = (JSONObject) formModel.getMembersDetails().get(t);
					System.out.println("reqobj||"+reqobj);
	            	if(reqobj != null){
				psmt.setString(1, General.checknull(formModel.getSession_id()));
				psmt.setString(2, inst_id);
				psmt.setString(3, inspection_by);
				psmt.setString(4, General.checknull(reqobj.get("insp_remarks").toString()));
				psmt.setString(5, General.checknull(reqobj.get("Insp_recm").toString()));
				
				psmt.setString(6, General.checknull(reqobj.get("XMTYPE").toString()));
				psmt.setString(7, General.checknull(reqobj.get("XNAME").toString()));
				psmt.setString(8, General.checknull(reqobj.get("XDEPARTMT").toString()));
				psmt.setString(9, General.checknull(reqobj.get("XPOST").toString()));
				psmt.setString(10, General.checknull(reqobj.get("XCONTACT").toString()));
				psmt.setString(11, General.checknull(is_convinor));
				
				psmt.setString(12, user_id);
				psmt.setString(13, General.checknull(formModel.getIp()));
				//String ishead=General.checknull(reqobj.get("XCONV").toString());
				psmt.setString(14, is_convinor.equals("Y")?"Inspector":"Member");
				psmt.setString(15, General.checknull(formModel.getIsfinalsubmited()));
				//System.out.println("InspectorInspectionManager insert af_inspection_member_detail ||"+psmt);
				//count = psmt.executeUpdate();
				/*ResultSet rs = psmt.getGeneratedKeys();
	
					if (rs.next()) {
						inspection_id = rs.getString(1);
					}*/
					psmt.addBatch();
				    }
	            	}
	            	cnt = psmt.executeBatch();
	            	/*ResultSet rs = psmt.getGeneratedKeys();
	            	
					if (rs.next()) {
						inspection_id = rs.getString(1);
						System.out.println("inspection_id||"+inspection_id);
					}*/
					
			/*}*/
			if (cnt.length > 0) 
				if(is_convinor.equals("Y")){
				if(saveComputerDetailslist(conn,formModel))
				if(saveadministrativeAmenitiesDetailslist(conn,formModel))
					if(saveInfrastructureDetailslist(conn,formModel))
						if(savefacultyDetailsList(conn,formModel))
							if(saveQuestionnairelist(conn,formModel))
				{
					if(items!=null){
						Iterator<FileItem> iter = items.iterator();
							while (iter.hasNext()) {
								FileItem fileItem = iter.next();
								String docName="";
								 saveDoc(machine,inst_id, inspection_id, user_id, conn, fileItem);
					}
					}
					
					conn.commit();
					jSonDataFinalObj.put("status", "Inspection Details Saved Successfully");
					jSonDataFinalObj.put("flag", "Y");
				}else {
					conn.rollback();				
					jSonDataFinalObj.put("status", ApplicationConstants.EXCEPTION_MESSAGE);
					jSonDataFinalObj.put("flag", "N");
				}
				}else{
					conn.commit();
				jSonDataFinalObj.put("status", "Inspection Member Recommendation Saved Successfully");
				jSonDataFinalObj.put("flag", "Y");
			}else {
				conn.rollback();				
				jSonDataFinalObj.put("status", ApplicationConstants.EXCEPTION_MESSAGE);
				jSonDataFinalObj.put("flag", "N");
			}
		 /* }*/
		} catch (Exception e) {
			if (e.toString().contains("Duplicate")){
				jSonDataFinalObj.put("status", ApplicationConstants.UNIQUE_CONSTRAINT);
				jSonDataFinalObj.put("flag", "N");
			}
			if (e.toString().contains("REFERENCES")){
				jSonDataFinalObj.put("status", ApplicationConstants.EXCEPTION_MESSAGE);
				jSonDataFinalObj.put("flag", "N");
			}
			System.out.println("EXCEPTION CAUSED BY InspectorInspectionManager :" + " " + e.getMessage().toUpperCase());
			l.fatal(Logging.logException("InspectorInspectionManager[save]", e.toString()));
		} finally {
			try {
				if (rst != null) {
					rst.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				System.out.println("EXCEPTION IN InspectorInspectionManager CLOSING CONNECTION IS CAUSED BY:" + " " + e.getMessage().trim().toUpperCase());
			}
		}
		return jSonDataFinalObj;
	}
	
public static Boolean saveComputerDetailslist(Connection conn,InspectorInspectionModel formModel) {
		
		PreparedStatement psmt = null;
		PreparedStatement psmtdel=null;
		String query = "";
		int[] cnt = null;
		Boolean status=true;
		String inspection_by=General.checknull(formModel.getInspection_by());
		String delqry = "delete from af_facility_detail_inspection where inspection_by='"+inspection_by+"'"
				+ " and fac_id=? ";
		
		query="insert into af_facility_detail_inspection (FAC_ID,avbl_room_inspection,inspection_status,inspection_by,CREATED_BY,CREATED_DATE,CREATED_MACHINE) "
				+ "value (?,?,?,?,?,now(),?)";
		try {
			psmtdel = conn.prepareStatement(delqry);
			psmt = conn.prepareStatement(query);
			
			 		for(int t=0; t<formModel.getComputerDetails().size(); t++){
	            	JSONObject reqobj = (JSONObject) formModel.getComputerDetails().get(t);
	            	if(reqobj != null){
	            		psmt.setString(1, General.checknull(reqobj.get("fac_id").toString()));
		            	psmt.setString(2, General.checknull(reqobj.get("avbl_room_inspection").toString()));
						psmt.setString(3, General.checknull(reqobj.get("inspection_status").toString()));
						psmt.setString(4, inspection_by);
						psmt.setString(5, General.checknull(formModel.getCreatedBy()));
						psmt.setString(6, General.checknull(formModel.getIp()));
						
						psmtdel.setString(1, General.checknull(reqobj.get("fac_id").toString()));
						psmtdel.executeUpdate();
	            	}
	            	System.out.println("saveComputerDetailslist||"+psmt);
					psmt.addBatch();
			    }
			 		//Save FacilityDetails
			 		for(int t=0; t<formModel.getFacilityDetails().size(); t++){
		            	JSONObject reqobj = (JSONObject) formModel.getFacilityDetails().get(t);
		            	if(reqobj != null){
	            		psmt.setString(1, General.checknull(reqobj.get("fac_id").toString()));
		            	psmt.setString(2, General.checknull(reqobj.get("avbl_room_inspection").toString()));
						psmt.setString(3, General.checknull(reqobj.get("inspection_status").toString()));
						psmt.setString(4, inspection_by);
						psmt.setString(5, General.checknull(formModel.getCreatedBy()));
						psmt.setString(6, General.checknull(formModel.getIp()));
						
						psmtdel.setString(1, General.checknull(reqobj.get("fac_id").toString()));
						psmtdel.executeUpdate();
		            	}
						psmt.addBatch();
				    }
	            
			cnt = psmt.executeBatch();
			if (cnt.length>0){
				status=true;
			}
		} catch (SQLException e) {
			status=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	return status;	
}	

public static Boolean saveadministrativeAmenitiesDetailslist(Connection conn,InspectorInspectionModel formModel) {
	
	PreparedStatement psmt,psmtdel = null;		
	String query = "";
	int[] cnt = null;
	Boolean status=true;
	
	String inspection_by=General.checknull(formModel.getInspection_by());
	String delqry = "delete from af_block_details_inspection where inspection_by='"+inspection_by+"'"
			+ " and BD_ID=? ";
	
	query="insert into af_block_details_inspection (BD_ID,avbl_carpet_inspection,avbl_room_inspection,inspection_status,inspection_by,CREATED_BY,CREATED_DATE,CREATED_MACHINE) "
			+ "value (?,?,?,?,?,?,now(),?)";
	
	/*query="update af_block_details set avbl_carpet_inspection=?, avbl_room_inspection=? ,"
			+ "inspection_status=? where BD_ID=? ";*/
	try {
		psmtdel = conn.prepareStatement(delqry);
		psmt = conn.prepareStatement(query);
		
		 		for(int t=0; t<formModel.getAdministrativeDetails().size(); t++){
	            	JSONObject reqobj = (JSONObject) formModel.getAdministrativeDetails().get(t);
	            	if(reqobj != null){
            		psmt.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
            		psmt.setString(2, General.checknull(reqobj.get("avbl_carpet_inspection").toString()));
	            	psmt.setString(3, General.checknull(reqobj.get("avbl_room_inspection").toString()));
					psmt.setString(4, General.checknull(reqobj.get("inspection_status").toString()));
					psmt.setString(5, inspection_by);
					psmt.setString(6, General.checknull(formModel.getCreatedBy()));
					psmt.setString(7, General.checknull(formModel.getIp()));
					
					psmtdel.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
					psmtdel.executeUpdate();
	            	}
					psmt.addBatch();
		 		}
		 		
		 		for(int t=0; t<formModel.getAmenitiesDetails().size(); t++){
	            	JSONObject reqobj = (JSONObject) formModel.getAmenitiesDetails().get(t);
	            	if(reqobj != null){
	            		psmt.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
	            		psmt.setString(2, General.checknull(reqobj.get("avbl_carpet_inspection").toString()));
		            	psmt.setString(3, General.checknull(reqobj.get("avbl_room_inspection").toString()));
						psmt.setString(4, General.checknull(reqobj.get("inspection_status").toString()));
						psmt.setString(5, inspection_by);
						psmt.setString(6, General.checknull(formModel.getCreatedBy()));
						psmt.setString(7, General.checknull(formModel.getIp()));
						
						psmtdel.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
						psmtdel.executeUpdate();
	            	}
					psmt.addBatch();
		 		}
		
		cnt = psmt.executeBatch();
		if (cnt.length>0){
			status=true;
		}
	} catch (SQLException e) {
		status=false;
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

return status;	
}

public static Boolean saveInfrastructureDetailslist(Connection conn,InspectorInspectionModel formModel) {
	
	PreparedStatement psmt,psmtdel = null;		
	String query = "";
	int[] cnt = null;
	Boolean status=true;
	//query="update af_infrastructure_detail set avbl_room_inspection=? ,inspection_status=? where INFRA_ID=? ";
	String inspection_by=General.checknull(formModel.getInspection_by());
	String delqry = "delete from af_infrastructure_detail_inspection where inspection_by='"+inspection_by+"'"
			+ " and INFRA_ID=? ";
	
	query="insert into af_infrastructure_detail_inspection (INFRA_ID,avbl_room_inspection,inspection_status,inspection_by,CREATED_BY,CREATED_DATE,CREATED_MACHINE) "
			+ "value (?,?,?,?,?,now(),?)";
	
	try {
		psmtdel = conn.prepareStatement(delqry);
		psmt = conn.prepareStatement(query);
		
		 		for(int t=0; t<formModel.getInfraDetails().size(); t++){
            	JSONObject reqobj = (JSONObject) formModel.getInfraDetails().get(t);
            	if(reqobj != null){
        		psmt.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
            	psmt.setString(2, General.checknull(reqobj.get("avbl_room_inspection").toString()));
				psmt.setString(3, General.checknull(reqobj.get("inspection_status").toString()));
				psmt.setString(4, inspection_by);
				psmt.setString(5, General.checknull(formModel.getCreatedBy()));
				psmt.setString(6, General.checknull(formModel.getIp()));
				
				psmtdel.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
				psmtdel.executeUpdate();
            	}
				psmt.addBatch();
		    }
            
		cnt = psmt.executeBatch();
		if (cnt.length>0){
			status=true;
		}
	} catch (SQLException e) {
		status=false;
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

return status;	
}

//Boolean Method to save facultyDetailsList
public static Boolean savefacultyDetailsList(Connection conn,InspectorInspectionModel formModel) {
	
	PreparedStatement psmt,psmtdel = null;		
	int[] cnt = null;
	Boolean status=true;
	String query = "";
	//query="update af_faculty_detail set inspection_status=? where FD_ID=? ";
	String inspection_by=General.checknull(formModel.getInspection_by());
	String delqry = "delete from af_faculty_detail_inspection where inspection_by='"+inspection_by+"'"
			+ " and FD_ID=? ";
	
	query="insert into af_faculty_detail_inspection (FD_ID,inspection_status,inspection_by,CREATED_BY,CREATED_DATE,CREATED_MACHINE) "
			+ "value (?,?,?,?,now(),?)";
	try {
		psmtdel = conn.prepareStatement(delqry);
		psmt = conn.prepareStatement(query);
		
		 		for(int t=0; t<formModel.getFacultyDetails().size(); t++){
            	JSONObject reqobj = (JSONObject) formModel.getFacultyDetails().get(t);
            	if(reqobj != null){
        		psmt.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
				psmt.setString(2, General.checknull(reqobj.get("inspection_status").toString()));
				psmt.setString(3, inspection_by);
				psmt.setString(4, General.checknull(formModel.getCreatedBy()));
				psmt.setString(5, General.checknull(formModel.getIp()));
				
				psmtdel.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
				psmtdel.executeUpdate();
            	}
				psmt.addBatch();
		    }
            
		cnt = psmt.executeBatch();
		if (cnt.length>0){
			status=true;
		}
	} catch (SQLException e) {
		status=false;
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

return status;	
}

//Boolean Method to save questionnairelist
public static Boolean saveQuestionnairelist(Connection conn,InspectorInspectionModel formModel) {
	
	PreparedStatement psmt = null,psmtdel = null,new_psmt=null;		
	String query = "",query_insert="";
	int[] cnt = null;
	Boolean status=true;
	String inspection_by=General.checknull(formModel.getInspection_by());
	//query="update af_questionnaire_details set inspection_status=?,inspection_by=? where QUES_ID=? and AF_QUES_ID=? and id=? ";
	String delqry = "delete from af_questionnaire_details_inspection where inspection_by='"+inspection_by+"'"
			+ " and id=? ";
	query="insert into af_questionnaire_details_inspection (id,inspection_status,inspection_by,CREATED_BY,CREATED_DATE,CREATED_MACHINE) "
			+ "value (?,?,?,?,now(),?)";
	
	//Query for inspector questions review
	query_insert="INSERT INTO af_questionnaire_details (AF_QUES_ID,QUES_ID, isActive,"
				+ "CREATED_BY, CREATED_DATE, CREATED_MACHINE ) values(?,?,'N',?,now(),?)";
	try {
		
		
		//System.out.println("formModel.getQuestionnairesDetails()||"+formModel.getQuestionnairesDetails().toString());
		if(formModel.getQuestionnairesDetails().size()>0){
	 		for(int t=0; t<formModel.getQuestionnairesDetails().size(); t++){
          	JSONObject reqobj = (JSONObject) formModel.getQuestionnairesDetails().get(t);
          	
          	if(reqobj != null && !(reqobj.get("pkey_id").equals(""))){
          		psmtdel = conn.prepareStatement(delqry);
          		psmt = conn.prepareStatement(query);
          		psmt.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
				psmt.setString(2, General.checknull(reqobj.get("inspection_status").toString()));
				psmt.setString(3, inspection_by);
				psmt.setString(4, General.checknull(formModel.getCreatedBy()));
				psmt.setString(5, General.checknull(formModel.getIp()));
				
				psmtdel.setString(1, General.checknull(reqobj.get("pkey_id").toString()));
				psmtdel.executeUpdate();
				//System.out.println("Update Insert"+psmt);
				psmt.addBatch();
          	}else if (reqobj != null && reqobj.get("ques_type").equals("I")){
          		new_psmt = conn.prepareStatement(query_insert,psmt.RETURN_GENERATED_KEYS);
          		new_psmt.setString(1, General.checknull(formModel.getInst_Id()));
          		new_psmt.setString(2, General.checknull(reqobj.get("ques_id").toString()));
          		new_psmt.setString(3, General.checknull(formModel.getCreatedBy()));
          		new_psmt.setString(4, General.checknull(formModel.getIp()));
				//System.out.println("saveQuestionnairelist Insert"+psmt);
          		new_psmt.executeUpdate();
				ResultSet rs = new_psmt.getGeneratedKeys();
				String p_key="";
				if (rs.next()) {
					p_key = rs.getString(1);
				}
				psmt = conn.prepareStatement(query);
          		psmt.setString(1, General.checknull(p_key));
				psmt.setString(2, General.checknull(reqobj.get("inspection_status").toString()));
				psmt.setString(3, inspection_by);
				psmt.setString(4, General.checknull(formModel.getCreatedBy()));
				psmt.setString(5, General.checknull(formModel.getIp()));
				psmt.addBatch();
          	}
          	cnt = psmt.executeBatch();	
		    }
	 		if (cnt.length>0){
				status=true;
			}
	}
		
	} catch (SQLException e) {
		status=false;
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

return status;	
}

public static ArrayList<InspectorInspectionModel> getAffiliationDetails(String Inst_Id,String inspection_by,String type){ 
	PreparedStatement psmt = null;
	ResultSet rst = null;
	Connection conn = null;
	String qry = "";
	ArrayList<InspectorInspectionModel> model = new ArrayList<InspectorInspectionModel>();
	try{
		conn=DBConnection.getConnection();

		JSONObject jsonobj=new JSONObject();
			JSONObject finalObject=new JSONObject();
			finalObject.put("tablename", "academic_degree_desc_mast");
			finalObject.put("columndesc","course_name");
			finalObject.put("id", "CR_ID");
			jsonobj= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
			JSONArray deparr = (JSONArray) jsonobj.get("commondata");
		
			qry="select mrt.session ssn, (select if(count(*)=0, 'Y', 'N') val from af_reuest_form_payment afp where is_submitted='Y' and "
			+" payment_status='paid' and cand_id='"+Inst_Id+"' and session=mrt.session and request_id=mrt.request_id and "
			+" sub_request_id=mrt.sub_request_id) Payment_Status, mrt.id, mrt.AFF_ID, mrt.session, mrt.request_id, rt.req_desc, "
			+" mrt.sub_request_id, st.sub_request_id sub_request_name, date_format(mrt.applied_on, '%d/%m/%Y') date, fc.amount, "
			+" fc.additional_cost, mrt.Payment_Status P, st.is_noc as noc, mrt.course_name, (SELECT insp_remarks FROM af_inspection_member_detail "
			+" where af_reg_id='"+Inst_Id+"' and inspection_by='"+inspection_by+"' and inspection_type='Inspector' order by CREATED_DATE desc limit 1) as insp_remarks,(SELECT insp_recm FROM "
			+" af_inspection_member_detail where af_reg_id='"+Inst_Id+"' and inspection_by='"+inspection_by+"' and inspection_type='Inspector' order by CREATED_DATE desc limit 1) as "
			+" insp_recm,(SELECT inspection_id FROM af_inspection_member_detail where af_reg_id='"+Inst_Id+"' and inspection_by='"+inspection_by+"' and inspection_type='Inspector' order by "
			+" CREATED_DATE desc limit 1) as inspection_id,"
			+ "(select concat(file_attachment_id,'_',file_name) from file_attachment where "
			+" reference_id='"+Inst_Id+"' "
			/*+ "in (SELECT inspection_id FROM af_inspection_member_detail where af_reg_id='"+Inst_Id+"' and inspection_by='"+inspection_by+"' and inspection_type='Inspector' order "
			+" by CREATED_DATE desc ) "*/
			+ "and table_name='af_inspection_member_detail' and file_type='inspection_file' order by CREATED desc limit 1) "
			+" as memberfile from af_apply_for_affiliation mrt, af_request_type rt, af_manage_request_type_sub_type st, af_manage_fee_configration "
			+" fc where mrt.request_id=rt.req_id and mrt.sub_request_id=st.MRT_ID and mrt.request_id=fc.request_type and "
			+" mrt.sub_request_id=fc.sub_request_type and fc. man_fee_config_id=mrt.fee_config_id and fc.req_type='AC' and mrt.AFF_ID='"+Inst_Id+"' "; 
			System.out.println("getAffiliationDetails ||"+qry);
		psmt = conn.prepareStatement(qry);
		rst= psmt.executeQuery();
		while(rst.next()){
			InspectorInspectionModel afm = new InspectorInspectionModel();
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
			afm.setCourse_Id(General.checknull(rst.getString("course_name")));
			for (int i = 0; i < deparr.size(); i++){
				JSONObject jsn = (JSONObject) deparr.get(i);
				if (jsn.get("id").equals(rst.getString("course_name"))) {
					afm.setCourse_name(General.checknull(jsn.get("desc").toString()));
				}
			}
			afm.setSession(General.checknull(rst.getString("ssn")));
			
			afm.setInspection_id(General.checknull(rst.getString("inspection_id")));
			afm.setInsp_remarks(General.checknull(rst.getString("insp_remarks")));
			afm.setInsp_recm(General.checknull(rst.getString("insp_recm")));
			afm.setFile_name(General.checknull(rst.getString("memberfile")));
			model.add(afm);
		}
	}catch(Exception e){
		System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getAffiliationDetails]" + " " + e.getMessage().trim().toUpperCase());
		l.fatal(Logging.logException("InspectorInspectionManager [getAffiliationDetails]", e.toString()));
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

public static ArrayList<InspectorInspectionModel> getAffIntakeDetail(String Inst_Id, String session_code){ 
	PreparedStatement psmt = null;
	ResultSet rst = null;
	Connection conn = null;
	String qry = "";
	ArrayList<InspectorInspectionModel> model = new ArrayList<InspectorInspectionModel>();
	try{
		conn=DBConnection.getConnection();
		JSONObject jsonobj = new JSONObject();
		JSONObject finalObject = new JSONObject();
/*		finalObject.put("tablename", "academic_degree_master ");
		finalObject.put("columndesc", "degree_name_description");
		finalObject.put("id", "id");*/
		//finalObject.put("cond", "a.faculty_name=b.id and a.IS_ACTIVE='Y' ");
		finalObject.put("id", "");
		jsonobj = commonAPI.getDropDownByWebService("rest/apiServices/degree_by_degreetype-A", finalObject);
		JSONArray deparr = (JSONArray) jsonobj.get("ProgDD");
		
		/*JSONObject finalObjectPtype = new JSONObject();
		JSONObject jsonobjPtype = new JSONObject();
		jsonobjPtype.put("tablename", "academic_degree_year_type_master ");
		jsonobjPtype.put("columndesc", "degree_year_type");
		jsonobjPtype.put("id", "id");
		
		finalObjectPtype = commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", jsonobjPtype);
		JSONArray projtypearr = (JSONArray) finalObjectPtype.get("commondata");
		System.out.println("deparr||"+projtypearr.toJSONString());*/
		
		/*qry="select rid.PROG_ID,rid.PROG_TYPE, shift, app_in_AICTE, app_in_COPCI, app_in_univ "
    			+ "from af_clg_reg_mast_detail rmd, af_req_intake_detail rid "
    			+ "where rmd.AF_REG_ID=rid.RID_ID and rmd.PROG_ID=rid.PROG_ID "
    			+ "and rmd.PROG_TYPE=rid.PROG_TYPE and AF_REG_ID='"+Inst_Id+"'";*/
		qry="select rid.PROG_ID,rid.PROG_TYPE, (select mr.sub_request_id from af_manage_request_type_sub_type mr where mr.MRT_ID=rid.sub_request_id)"
				   + " as sub_req, app_in_AICTE, app_in_COPCI, app_in_univ  from af_req_intake_detail rid where RID_ID='"+Inst_Id+"' and session='"+session_code+"'";
		psmt = conn.prepareStatement(qry);
		//System.out.println("getAffIntakeDetail||"+psmt);
		rst= psmt.executeQuery();
		while(rst.next()){
    		InspectorInspectionModel afm = new InspectorInspectionModel();
    		for (int i = 0; i < deparr.size(); i++) {
				JSONObject jsn = (JSONObject) deparr.get(i);
				if (jsn.get("id").equals(rst.getString("PROG_ID"))) {
					afm.setProg_id(General.checknull(jsn.get("name").toString()));
				}
			}
    		
    	/*	for (int i = 0; i < projtypearr.size(); i++) {
				JSONObject jsn = (JSONObject) projtypearr.get(i);
				if (jsn.get("id").equals(rst.getString("PROG_TYPE"))) {
					afm.setProg_type(General.checknull(jsn.get("desc").toString()));
				}
			}*/
			//afm.setProg_id(General.checknull(rst.getString("PROG_ID")));
			//afm.setProg_type(General.checknull(rst.getString("PROG_TYPE")));
			afm.setShift(General.checknull(rst.getString("sub_req")));
			afm.setApp_in_AICTE(General.checknull(rst.getString("app_in_AICTE")));
			afm.setApp_in_COPCI(General.checknull(rst.getString("app_in_COPCI")));
			afm.setApp_in_univ(General.checknull(rst.getString("app_in_univ")));
			model.add(afm);
    	}
	}catch(Exception e){
		System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getAffIntakeDetail]" + " " + e.getMessage().trim().toUpperCase());
		l.fatal(Logging.logException("InspectorInspectionManager [getAffIntakeDetail]", e.toString()));
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

public static boolean saveDoc(String machine,String inst_id, String id, String userid, Connection conn, FileItem fileItem){		
	java.io.File file;
	try{
		String attachid=saveFileattachment(machine, fileItem, inst_id, userid, conn);
		
		if(!attachid.equals("")){
			String directoryName = ReadProps.getkeyValue("document.path", "sitsResource")+"INSPECTOR_INSPECTION/"+inst_id+"/"/*+id+"/"*/;
			File directory = new File(directoryName);
			if (!directory.isDirectory()){
				directory.mkdirs();
			}
			file = new File(directoryName+attachid+"_"+fileItem.getName().replace("&", "and"));
			fileItem.write(file);	
			//file2.transferTo(file);
		}
  	}catch(Exception e){
  		System.out.println("Error in InspectorInspectionManager[saveDoc] : "+e.getMessage());
  		l.fatal(Logging.logException("InspectorInspectionManager[saveDoc]", e.toString()));
  		return false;
  	}
	return true;
}

public static String saveFileattachment(String machine, FileItem fileItem, String id, String userid, Connection conn){ 
	PreparedStatement psmt = null;
	ResultSet rst = null;
	String qry = "";
	String attachid="";
	int count=0;
	try{
		qry="INSERT INTO file_attachment ( file_name, file_type, table_name, reference_id, CREATED_BY, CREATED, MACHINE) VALUES "
		+ "( ? , ?, 'af_inspection_member_detail', ?, ?,now(), ?); ";
		psmt = conn.prepareStatement(qry, psmt.RETURN_GENERATED_KEYS);
		String file_type;
		if(fileItem.getFieldName().equals("upload_doc1")){
			file_type="inspection_file";
		}else{
			file_type=fileItem.getFieldName();
		}
			
		
		psmt.setString(1, fileItem.getName());
		psmt.setString(2, file_type);
		psmt.setString(3, id);
		psmt.setString(4, userid);
		psmt.setString(5, machine);
		//System.out.println("saveFileattachment Insert InspectorInspectionManager>>"+psmt);
		count= psmt.executeUpdate();
		ResultSet rs = psmt.getGeneratedKeys();

		if (rs.next()) {
			attachid = rs.getString(1);
		}
		 
	}
	catch(Exception e)
	{
		attachid="";
		System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager" + "[saveFileattachment]" + " " + e.getMessage().trim().toUpperCase());
		l.fatal(Logging.logException("InspectorInspectionManager [saveFileattachment]", e.toString()));
		return attachid;
	}
	return attachid;
}

//Society information methods

public static JSONObject getSavedData(String id) {
	JSONObject json = new JSONObject();
	PreparedStatement pst = null;
	Connection conn = null;
	ResultSet rst = null;
	String query = "";
	query = "select * from af_society_info_mast where AF_SOC_ID=?";
	try {
		conn = DBConnection.getConnection();
		pst = conn.prepareStatement(query);
		pst.setString(1, General.checknull(id));
		rst = pst.executeQuery();
		if (rst.next()) {
			
			json.put("SOC_ID", rst.getString("SOC_ID"));
			json.put("AF_SOC_ID", rst.getString("AF_SOC_ID"));
			json.put("SOC_TRUST_NAME", rst.getString("SOC_TRUST_NAME"));
			json.put("SOC_TRUST_ADDRESS", rst.getString("SOC_TRUST_ADDRESS"));
			json.put("STATE_NAME", rst.getString("STATE_NAME"));
			json.put("DISTRICT_NAME", rst.getString("DISTRICT_NAME"));
			json.put("TAHSIL_NAME", rst.getString("TAHSIL_NAME"));
			json.put("PIN_CODE", rst.getString("PIN_CODE"));
			json.put("PHONE_NO", rst.getString("PHONE_NO"));
			json.put("EMAIL_ID", rst.getString("EMAIL_ID"));
			json.put("FAX", rst.getString("FAX"));
			
		}
	} catch (Exception e) {
		System.out.println("FileName=[InspectorInspectionManager],MethodName=[getSavedData()]" + e.getMessage().toString());
		l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getSavedData()]", e.getMessage().toString()));
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
			l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getSavedData()]", e.getMessage().toString()));
		}
	}
	return json;
}

//Static method to get the Member Information retun list of InspectionSocietyInfoModel
public static ArrayList<InspectionSocietyInfoModel> viewRecordDetails(String soc_id) {
	ArrayList<InspectionSocietyInfoModel> list = new ArrayList<InspectionSocietyInfoModel>();
	String cSql="";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rst = null;
	try {		
		conn = DBConnection.getConnection();
		cSql= " select *,date_format(MEMBER_DOB, '%d/%m/%Y') as PROGRAM_DATE,"
				+ " (select concat(file_attachment_id,'_',file_name) from file_attachment  where table_name='AF_SOCIETY_INFO_DETAIL' "
				+ "and reference_id=SOC_DID and file_type='SI_UPL_PHOTO')as mem_photo,"
				+ "(select concat(file_attachment_id,'_',file_name) from file_attachment  "
				+ "where table_name='AF_SOCIETY_INFO_DETAIL' and reference_id=SOC_DID "
				+ "and file_type='SI_UPL_SIGN')as mem_sign,"
				+ "(select concat(file_attachment_id,'_',file_name) from file_attachment  "
				+ "where table_name='AF_SOCIETY_INFO_DETAIL' and reference_id=SOC_DID "
				+ "and file_type='SI_UPL_MEMO')as mem_memo"
				+ " from af_society_info_detail where soc_id='"+soc_id+"'; ";
		pstmt = conn.prepareStatement(cSql);
		rst = pstmt.executeQuery();
		
		if(rst.next()==false) {
		} else {		
			do{
				InspectionSocietyInfoModel sd = new InspectionSocietyInfoModel();
				sd.setSoc_id(General.checknull(rst.getString("SOC_ID")));
				sd.setSoc_did(General.checknull(rst.getString("SOC_DID")));
				sd.setMem_name(General.checknull(rst.getString("MEMBER_NAME")));
				sd.setMem_aadhar(General.checknull(rst.getString("MEMBER_AADHAR")));
				sd.setMem_email(General.checknull(rst.getString("MEMBER_EMAIL")));
				sd.setMember_dob(General.checknull(rst.getString("PROGRAM_DATE")));
				sd.setMem_desg(General.checknull(rst.getString("MEMBER_DESG")));
				sd.setMem_mob_no(General.checknull(rst.getString("MEMBER_MOBILE")));
				sd.setMem_photo(General.checknull(rst.getString("mem_photo")));
				sd.setMem_memo(General.checknull(rst.getString("mem_memo")));
				sd.setMem_sign(General.checknull(rst.getString("mem_sign")));
				
				list.add(sd);
				} while(rst.next());
		}
	} catch (Exception e) {
		System.out.println("Error in InspectorInspectionManager[viewRecordDetails] : "+e.getMessage());
		l.fatal(Logging.logException("InspectorInspectionManager[viewRecordDetails]", e.getMessage().toString()));
	} finally {
		try {
			if(pstmt != null) pstmt = null;
			if(rst != null) rst = null;
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	return list;
}


//added by Ashwani General Information
public static JSONObject getRegSavedData(String id) {
	JSONObject json = new JSONObject();
	PreparedStatement pst = null;
	Connection conn = null;
	ResultSet rst = null;
	String query = "";
	try {
		    JSONObject jsonobj=new JSONObject();
			JSONObject finalObject=new JSONObject();
			finalObject.put("tablename", "state_mast");
			finalObject.put("columndesc","STATE");
			finalObject.put("id", "STATE_ID");
			jsonobj= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
			JSONArray deparr = (JSONArray) jsonobj.get("commondata");
			JSONObject jsonobj1=new JSONObject();
			
			JSONObject finalObject1=new JSONObject();
			finalObject1.put("tablename", "dist_mast");
			finalObject1.put("columndesc","DIST");
			finalObject1.put("id", "DIST_ID");
			jsonobj1= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject1);
			JSONArray deparr1 = (JSONArray) jsonobj1.get("commondata");
		
	   query = "select * from af_clg_reg_mast where AF_REG_ID=?";
		conn = DBConnection.getConnection();
		pst = conn.prepareStatement(query);
		pst.setString(1, General.checknull(id));
		rst = pst.executeQuery();
		while(rst.next()) {
			for(int i=0; i<deparr.size(); i++){
				JSONObject jsn=	(JSONObject) deparr.get(i);
				if(jsn.get("id").equals(rst.getString("STATE")))
				{
					 json.put("STATE",General.checknull(jsn.get("desc").toString()));
				}
			   }
			for(int i=0; i<deparr1.size(); i++){
				JSONObject jsn1=	(JSONObject) deparr1.get(i);
				if(jsn1.get("id").equals(rst.getString("DISTRICT")))
				{
					 json.put("DISTRICT",General.checknull(jsn1.get("desc").toString()));
				}
			   }
			json.put("INST_TYPE", General.checknull(rst.getString("INST_TYPE")));
			json.put("INST_CAT", General.checknull(rst.getString("INST_CAT")));
			json.put("REG_TYP", General.checknull(rst.getString("REG_TYP")));
			json.put("MD_NAME", General.checknull(rst.getString("MD_NAME")));
			json.put("IS_MINORITY", General.checknull(rst.getString("IS_MINORITY")));
			json.put("PROP_INST_NAME", General.checknull(rst.getString("PROP_INST_NAME")));
			json.put("ADDR", General.checknull(rst.getString("ADDR")));
			json.put("COUNTRY", General.checknull(rst.getString("COUNTRY")));
			//json.put("STATE", General.checknull(rst.getString("STATE")));
			//json.put("DISTRICT", General.checknull(rst.getString("DISTRICT")));
			json.put("TAHSIL", General.checknull(rst.getString("TAHSIL")));
			json.put("CONTACT", General.checknull(rst.getString("CONTACT")));
			json.put("EMAIL", General.checknull(rst.getString("EMAIL")));
			json.put("AADHAR", General.checknull(rst.getString("AADHAR")));
			json.put("DIRECTOR_NAME", General.checknull(rst.getString("DIRECTOR_NAME")));
			json.put("DIRECTOR_MOBILE", General.checknull(rst.getString("DIRECTOR_MOBILE")));
			//System.out.println("json||"+json);
		}
	} catch (Exception e) {
		System.out.println("FileName=[InspectorInspectionManager],MethodName=[getSavedData()]" + e.getMessage().toString());
		l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getSavedData()]", e.getMessage().toString()));
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
			l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getSavedData()]", e.getMessage().toString()));
			System.out.println("FileName=[InspectorInspectionManager],MethodName=[getSavedData()]" + e.getMessage().toString());
		}
	}
	return json;
}


public static JSONObject getgeneralInfo(String id) {
	JSONObject json = new JSONObject();
	PreparedStatement pst = null;
	Connection conn = null;
	ResultSet rst = null;
	String query = "";
	try {
		conn = DBConnection.getConnection();
		query = "select * from af_basic_detail where AF_BD_ID=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, General.checknull(id));
		//System.out.println("check --"+pst);
		rst = pst.executeQuery();
		if(rst.next()) {
			json.put("session", General.checknull(rst.getString("session")));
			json.put("register_name", General.checknull(rst.getString("register_name")));
			json.put("register_mobile", General.checknull(rst.getString("register_mobile")));
			json.put("register_email", General.checknull(rst.getString("register_email")));
			json.put("register_aadhar", General.checknull(rst.getString("register_aadhar")));
			json.put("ID", General.checknull(rst.getString("ID")));
		}else{
		json.put("session", "");
		json.put("register_name","");
		json.put("register_mobile", "");
		json.put("register_email", "");
		json.put("register_aadhar", "");
		json.put("ID", "");
		}
	} catch (Exception e) {
		System.out.println("FileName=[InspectorInspectionManager],MethodName=[getgeneralInfo()]" + e.getMessage().toString());
		l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getgeneralInfo()]", e.getMessage().toString()));
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
			l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getgeneralInfo()]", e.getMessage().toString()));
			System.out.println("FileName=[InspectorInspectionManager],MethodName=[getgeneralInfo()]" + e.getMessage().toString());
		}
	}
	return json;
}


public static JSONArray getUplData(String id) {
	PreparedStatement psmt = null;
	ResultSet rst = null;
	Connection conn = null;
	String qry = "";
	
	JSONArray arr = new JSONArray();
	try {
		conn = DBConnection.getConnection();
		qry = "select d.doc_name, (select concat(file_attachment_id,'_',file_name) from file_attachment fa, "
				+ "af_upl_doc_mast udm, af_doc_mast dm where table_name='af_upl_doc_mast' and fa.reference_id=udm.UD_ID "
				+ "and udm.DOC_NAME=dm.doc_id and dm.doc_id=d.doc_id and udm.AFF_UD_ID=? order by file_attachment_id desc limit 1) AS file "
				+ "from af_doc_mast d  order by CONVERT(order_id, UNSIGNED INTEGER) ";
		psmt = conn.prepareStatement(qry);
		psmt.setString(1, id);
		rst = psmt.executeQuery();
		//System.out.println(psmt);
		while (rst.next()) {
			JSONObject data = new JSONObject();
			//data.put(General.checknull(rst.getString("file_type")), General.checknull(rst.getString("file_name")));
			//String part[] = General.checknull(rst.getString("file")).split("~");
			//if (part.length > 1) {
				//data.put("Attid", General.checknull(part[0]));
				//data.put("FileName", General.checknull(part[1]));
				data.put("FileName", General.checknull(rst.getString("file")));
				data.put("DocName", General.checknull(rst.getString("doc_name")));
			/*} else {
				data.put("Attid", "");
				data.put("FileName", "");
				data.put("DocName", "");
			}*/
			arr.add(data);
		}
	} catch (Exception e) {
		System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getUplData]" + " "
				+ e.getMessage().trim().toUpperCase());
		l.fatal(Logging.logException("InspectorInspectionManager [getUplData]", e.toString()));
		return arr;
	} finally {
		try {
			conn.close();
			psmt.close();
			rst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	return arr;
}

	public static JSONArray getProgDetail(String id){
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		String qry="";
		JSONArray arr = new JSONArray();
	 try {
 	conn = DBConnection.getConnection();
 	JSONObject jsonobj = new JSONObject();
	JSONObject finalObject = new JSONObject();
	finalObject.put("tablename", "academic_degree_master ");
	finalObject.put("columndesc", "degree_name_description");
	finalObject.put("id", "id");
	jsonobj = commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
	JSONArray deparr = (JSONArray) jsonobj.get("commondata");
	
	JSONObject finalObjectPtype = new JSONObject();
	JSONObject jsonobjPtype = new JSONObject();
	jsonobjPtype.put("tablename", "academic_degree_year_type_master ");
	jsonobjPtype.put("columndesc", "degree_year_type");
	jsonobjPtype.put("id", "id");
	finalObjectPtype = commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", jsonobjPtype);
	JSONArray projtypearr = (JSONArray) finalObjectPtype.get("commondata");
	
 	qry="select rid.PROG_ID,rid.PROG_TYPE,(select mr.sub_request_id from af_manage_request_type_sub_type mr where mr.MRT_ID=rid.sub_request_id) as shift, app_in_univ from af_req_intake_detail rid where RID_ID=?";
 	psmt = conn.prepareStatement(qry);
 	psmt.setString(1, General.checknull(id));
 	rst = psmt.executeQuery();
 	while(rst.next()) {
 		JSONObject obj = new JSONObject();
 		for (int i = 0; i < deparr.size(); i++) {
			JSONObject jsn = (JSONObject) deparr.get(i);
			if (jsn.get("id").equals(rst.getString("PROG_ID"))) {
				obj.put("PROG_ID",General.checknull(jsn.get("desc").toString()));
			}
		}
		
		for (int i = 0; i < projtypearr.size(); i++) {
			JSONObject jsn = (JSONObject) projtypearr.get(i);
			if (jsn.get("id").equals(rst.getString("PROG_TYPE"))) {
				obj.put("PROG_TYPE",General.checknull(jsn.get("desc").toString()));
			}
		}
 			//obj.put("PROG_ID", General.checknull(rst.getString("PROG_ID")));
			//obj.put("PROG_TYPE", General.checknull(rst.getString("PROG_TYPE")));
			obj.put("shift", General.checknull(rst.getString("shift")));
			obj.put("univ", General.checknull(rst.getString("app_in_univ")));
			arr.add(obj);
	 	}
	 } catch (Exception ex) {
	     System.out.println("Error in BasicDetailManager[getProgDetail] :"+ex.getMessage());
			l.fatal(Logging.logException("BasicDetailManager[getProgDetail]", ex.getMessage().toString()));
	 } finally {
			try {
				if(psmt != null) psmt = null;
				if(rst != null) rst = null;
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
	 }
	 return arr;
	}

//added By Amit Dangi Date 01-May-2024 Method Replication from portal 
	public static JSONObject getattachList(String id){ 
		PreparedStatement psmt = null;
		ResultSet rst = null;
		Connection conn = null;
		String qry = "";
		JSONObject data = new JSONObject();
		try{
			conn=DBConnection.getConnection();
			qry="select file_type, concat(file_attachment_id,'_',file_name) file_name from file_attachment where reference_id=? and table_name in ('af_req_intake_detail', 'INTAKE')";
			psmt = conn.prepareStatement(qry);
			psmt.setString(1, id);
			//System.out.println("doc Is noc"+psmt);
			rst= psmt.executeQuery();
			
			while(rst.next()){
				data.put(General.checknull(rst.getString("file_type")), General.checknull(rst.getString("file_name")));
			}
		}catch(Exception e){
			System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getattachList]" + " " + e.getMessage().trim().toUpperCase());
			l.fatal(Logging.logException("InspectorInspectionManager [getattachList]", e.toString()));
			return data;
		}finally {
			try {
				conn.close();
				psmt.close();
				rst.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}
	
	//added By Amit Dangi Date 01-May-2024 Method Replication from portal
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
			System.out.println("FileName=[InspectorInspectionManager],MethodName=[getDetailForDiffrenceAmtPaid()]" + e.getMessage().toString());
			l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getDetailForDiffrenceAmtPaid()]", e.getMessage().toString()));
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
				l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getDetailForDiffrenceAmtPaid()]", e.getMessage().toString()));
			}
		}
		return is_submit;
	}
	
	//added By Amit Dangi Date 01-May-2024 Method Replication from portal
	public static JSONObject getEOAIsChecked(String id,String ssn){ 
		PreparedStatement psmt = null;
		ResultSet rst = null;
		Connection conn = null;
		String qry = "";
		JSONObject data = new JSONObject();
		try{
			conn=DBConnection.getConnection();
			qry="select is_nirf_rank,is_qs_world_rank,is_NBA_accreditation,is_NAAC_score, is_ugc_autonomy, "
					+ "is_adm_consecutively  from af_req_intake_detail where RID_ID=? and session =? ";
			psmt = conn.prepareStatement(qry);
			psmt.setString(1, id);
			psmt.setString(2, ssn);
			rst= psmt.executeQuery();
			if(rst.next()){
				data.put("is_nirf",General.checknull(rst.getString("is_nirf_rank")));
				data.put("is_qs",General.checknull(rst.getString("is_qs_world_rank")));
				data.put("is_NBA",General.checknull(rst.getString("is_NBA_accreditation")));
				data.put("is_NAAC",General.checknull(rst.getString("is_NAAC_score")));
				data.put("is_ugc",General.checknull(rst.getString("is_ugc_autonomy")));
				data.put("is_adm",General.checknull(rst.getString("is_adm_consecutively")));
			}else{
				data.put("is_nirf","");
				data.put("is_qs","");
				data.put("is_NBA","");
				data.put("is_NAAC","");
				data.put("is_ugc","");
				data.put("is_adm","");
			}
			System.out.println("data||"+data.toJSONString());
		}catch(Exception e){
			System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getEOAIsChecked]" + " " + e.getMessage().trim().toUpperCase());
			l.fatal(Logging.logException("InspectorInspectionManager [getEOAIsChecked]", e.toString()));
			return data;
		}finally {
			try {
				conn.close();
				psmt.close();
				rst.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}
	
	//added By Amit Dangi Date 01-May-2024 Method Replication from portal
	public static JSONObject getSubOptions(String user_id,String subReq,String mid){  
		PreparedStatement psmt = null;
		ResultSet rst = null;
		Connection conn = null;
		String qry = "";
		JSONObject data = new JSONObject();
		try{
			conn=DBConnection.getConnection();
			JSONObject jsonobj=new JSONObject();
 			JSONObject finalObject=new JSONObject();
 			finalObject.put("tablename", "academic_session_master");
 			finalObject.put("columndesc","session");
 			finalObject.put("id", "id");
 			
 			jsonobj= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
 			JSONArray deparr = (JSONArray) jsonobj.get("commondata");
			
			qry=" select old_inst_name,new_inst_name,gap_fyear,gap_tyear,gap_year_count from af_apply_for_affiliation "
				 + "where AFF_ID =? and  id=? and sub_request_id=?";
			psmt = conn.prepareStatement(qry);
			psmt.setString(1, user_id);
			psmt.setString(2, mid);
			psmt.setString(3, subReq);
			//System.out.println("getSubOptions||"+psmt);
			rst= psmt.executeQuery();
			while(rst.next()){
				data.put("old_inst_name", General.checknull(rst.getString("old_inst_name")));
				data.put("new_inst_name", General.checknull(rst.getString("new_inst_name")));
				data.put("gap_count", General.checknull(rst.getString("gap_year_count")));
				for(int i=0; i<deparr.size(); i++){
					JSONObject jsn=	(JSONObject) deparr.get(i);
					if(jsn.get("id").equals(rst.getString("gap_fyear")))
					{
						data.put("gap_fyear",General.checknull(jsn.get("desc").toString()));
					}
					if(jsn.get("id").equals(rst.getString("gap_tyear")))
					{
						data.put("gap_tyear",General.checknull(jsn.get("desc").toString()));
					}
				}
				//System.out.println("data||"+data.toJSONString());
			}
		}catch(Exception e){
			System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getSubOptions]" + " " + e.getMessage().trim().toUpperCase());
			l.fatal(Logging.logException("InspectorInspectionManager [getSubOptions]", e.toString()));
			return data;
		}finally {
			try {
				conn.close();
				psmt.close();
				rst.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}

	//added By Amit Dangi Date 01-May-2024 Method Replication from portal
	public static ArrayList<InspectorInspectionModel> getMergeinstName(String user_id,String Subreq,String mid,String obj){ 
		PreparedStatement psmt = null;
		ResultSet rst = null;
		Connection conn = null;
		String qry = "";
		ArrayList<InspectorInspectionModel> model = new ArrayList<InspectorInspectionModel>();
		try{
			conn=DBConnection.getConnection();
			  if(obj.equals("change_course_name")){
				  qry="select OLD_COURSE_NAME,NEW_DEGREE_NAME,OLD_DEGREE_NAME,'' PROP_INST_NAME from af_change_sub_req_data_details a,af_apply_for_affiliation b  where a.AF_APAF_ID=b.id and b.id=?";
			  }else{
				  qry=" select PROP_INST_NAME,''OLD_COURSE_NAME,''NEW_DEGREE_NAME,'' OLD_DEGREE_NAME  from af_clg_reg_mast where find_in_set(AF_REG_ID, (select merge_inst_name from af_apply_for_affiliation where id=?))";
			  }
			  psmt = conn.prepareStatement(qry);
			psmt.setString(1, mid);
			rst= psmt.executeQuery();
			//System.out.println(psmt);
			while(rst.next()){
				InspectorInspectionModel afm = new InspectorInspectionModel();
				afm.setPROP_INST_NAME(General.checknull(rst.getString("PROP_INST_NAME")));
				afm.setOld_course_name(General.checknull(rst.getString("OLD_COURSE_NAME")));
				afm.setNew_course_name(General.checknull(rst.getString("NEW_DEGREE_NAME")));
				afm.setOld_degree(General.checknull(rst.getString("OLD_DEGREE_NAME")));
				model.add(afm);
			}
		}catch(Exception e){
			System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getMergeinstName]" + " " + e.getMessage().trim().toUpperCase());
			l.fatal(Logging.logException("InspectorInspectionManager [getMergeinstName]", e.toString()));
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
	
	//added By Amit Dangi Date 01-May-2024 Method Replication from portal
	public static ArrayList<InspectorInspectionModel> getProgramNameByCourseName(String user_id,String course_id,String mid){ 
		PreparedStatement psmt = null;
		ResultSet rst = null;
		Connection conn = null;
		String qry = "";
		ArrayList<InspectorInspectionModel> model = new ArrayList<InspectorInspectionModel>();
		try{
			conn=DBConnection.getConnection();
			
			JSONObject jsonobj=new JSONObject();
 			JSONObject finalObject=new JSONObject();
 			finalObject.put("tablename", "academic_degree_master a, academic_faculty_master b");
 			finalObject.put("columndesc","b.faculty");
 			finalObject.put("id", "a.id");
 			finalObject.put("cond", "a.faculty_name=b.ID and a.IS_ACTIVE='Y'");
 			jsonobj= commonAPI.getDropDownByWebService("rest/apiServices/masterdetails", finalObject);
 			JSONArray deparr = (JSONArray) jsonobj.get("commondata");
 			
			qry=" select distinct dt.degree from af_apply_for_affiliation mt, af_apply_for_affiliation_details dt "
					+ "where mt.id=dt.aafa_id and AFF_ID ='"+user_id+"' and course_name='"+course_id+"' and mt.id='"+mid+"'";
			psmt = conn.prepareStatement(qry);
			rst= psmt.executeQuery();
			//System.out.println("getProgramNameByCourseName||"+psmt);
			while(rst.next()){
				InspectorInspectionModel afm = new InspectorInspectionModel();
				for (int i = 0; i < deparr.size(); i++){
					JSONObject jsn = (JSONObject) deparr.get(i);
					if (jsn.get("id").equals(rst.getString("degree"))) {
						afm.setDegree(General.checknull(jsn.get("desc").toString()));
					}
				}
				model.add(afm);
			}
		}catch(Exception e){
			System.out.println("EXCEPTION IS CAUSED BY: InspectorInspectionManager [getProgramNameByCourseName]" + " " + e.getMessage().trim().toUpperCase());
			l.fatal(Logging.logException("InspectorInspectionManager [getProgramNameByCourseName]", e.toString()));
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
	
	 public static JSONArray viewMembersDetails(String Inst_Id,String session_code,String employee_id) {
		        final JSONArray arr = new JSONArray();
		        String cSql = "";
		        Connection conn = null;
		        PreparedStatement pstmt = null;
		        ResultSet rst = null;
		        try {
		            conn = DBConnection.getConnection();
		            cSql = "select inspection_id,session,af_reg_id,inspection_by,insp_remarks,insp_recm,isfinalsubmited,"
		            		+ "inspection_type,emp_type,member_name,department,designation,contant_no,is_member_head FROM "
		            		+ "af_inspection_member_detail WHERE af_reg_id='"+Inst_Id+"' and session='"+session_code+"' and inspection_type in "
		            		+ "('Inspector','Member') and inspection_by='"+employee_id+"' ";
		            pstmt = conn.prepareStatement(cSql);
		            System.out.println("viewMembersDetails:: " + pstmt);
		            rst = pstmt.executeQuery();
		            if (rst.next()) {
		                do {
		                    final JSONObject obj = new JSONObject();
		                    obj.put((Object)"inspection_id", (Object)General.checknull(rst.getString("inspection_id")));
		                    obj.put((Object)"session", (Object)General.checknull(rst.getString("session")));
		                    obj.put((Object)"af_reg_id", (Object)General.checknull(rst.getString("af_reg_id")));
		                    obj.put((Object)"inspection_by", (Object)General.checknull(rst.getString("inspection_by")));
		                    obj.put((Object)"insp_remarks", (Object)General.checknull(rst.getString("insp_remarks")));
		                    obj.put((Object)"insp_recm", (Object)General.checknull(rst.getString("insp_recm")));
		                    obj.put((Object)"isfinalsubmited", (Object)General.checknull(rst.getString("isfinalsubmited")));
		                    obj.put((Object)"inspection_type", (Object)General.checknull(rst.getString("inspection_type")));
		                    obj.put((Object)"emp_type", (Object)General.checknull(rst.getString("emp_type")));
		                    obj.put((Object)"member_name", (Object)General.checknull(rst.getString("member_name")));
		                    obj.put((Object)"department", (Object)General.checknull(rst.getString("department")));
		                    obj.put((Object)"designation", (Object)General.checknull(rst.getString("designation")));
		                    obj.put((Object)"contant_no", (Object)General.checknull(rst.getString("contant_no")));
		                    obj.put((Object)"is_member_head", (Object)General.checknull(rst.getString("is_member_head")));
		                    arr.add((Object)obj);
		                    
		                    
		                } while (rst.next());
		            }
		        }
		        
		        catch (Exception e) {
		            System.out.println("Error in InspectorInspectionManager[viewMembersDetails] : " + e.getMessage());
		            l.fatal(Logging.logException("InspectorInspectionManager [viewMembersDetails]", e.toString()));
		            try {
		                if (pstmt != null) {
		                    pstmt = null;
		                }
		                if (rst != null) {
		                    rst = null;
		                }
		                if (conn != null) {
		                    conn.close();
		                }
		            }
		            catch (Exception e2) {
		                e2.printStackTrace();
		            }
		            return arr;
		        }
		        finally {
		            try {
		                if (pstmt != null) {
		                    pstmt = null;
		                }
		                if (rst != null) {
		                    rst = null;
		                }
		                if (conn != null) {
		                    conn.close();
		                }
		            }
		            catch (Exception e2) {
		                e2.printStackTrace();
		            }
		            
		        }
		        return arr;
		    }
	
	 /*Static Method to get the Application saved details if any or
		get the selected proposal details from Project Proposal Submission form as per proposal id
		Return type is object Json*/
		public static JSONObject getApplicationDetailsForReopen(InspectorInspectionModel raModel) {
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
		        	query="select distinct (select isfinalsubmited from af_inspection_member_detail where af_reg_id=crm.AF_REG_ID and session=aff.session and inspection_type='Inspector' and inspection_by='"+General.checknull(raModel.getInspection_by())+"' order by CREATED_DATE desc limit 1) as isfinalsubmited,"
		        			+ "session,PROP_INST_NAME,contact,email,AF_REG_ID,REG_NO,date_format(Payment_Date,'%d/%m/%Y') as Payment_Date,panel_code,review_remarks,"
		        			+ "is_final_submit_app,coalesce((select (case when member_type is not null then 'Y' else 'N' END) from af_approv_pannel_detail where panel_id=crm.panel_code and member_type='"+General.checknull(raModel.getInspection_by())+"' and issActive='Y' LIMIT 1),'N') "
		        			+ "as is_pannel_member,coalesce((select (case when member_type is not null then 'Y' else 'N' END) from af_approv_pannel_detail where panel_id=crm.panel_code and member_type='"+General.checknull(raModel.getInspection_by())+"' and issActive='Y' and is_convenor='Y' LIMIT 1),'N') as is_convinor, "
	    					+ "(select member_type from af_approv_pannel_detail where panel_id=crm.panel_code  and issActive='Y' and is_convenor='Y' LIMIT 1) as convinor_id,DISTRICT from af_clg_reg_mast crm,af_apply_for_affiliation aff "
		        			+ "where crm.AF_REG_ID=aff.AFF_ID and session='"+General.checknull(raModel.getSession_id())+"' and DISTRICT='"+General.checknull(raModel.getS_district())+"' ";
		        			//		+ "and aff.request_id='RT0001' ";
		        			
		        			if(!General.checknull(raModel.getInst_name()).trim().equals("")){
		        				query += "and PROP_INST_NAME like '%"+General.checknull(raModel.getInst_name())+"%' ";
							}
		        			if(!General.checknull(raModel.getMobile_no()).trim().equals("")){
		        				query += "and contact='"+General.checknull(raModel.getEmail_id())+"' ";
							}
		        			if(!General.checknull(raModel.getEmail_id()).trim().equals("")){
				        		query += "and email='"+General.checknull(raModel.getEmail_id())+"' ";
							}
		        			if(!(General.checknull(raModel.getXFROMDATE()).trim().equals("") && General.checknull(raModel.getXTODATE()).trim().equals("")) ){
				        		query += "and date_format(review_date,'%d/%m/%Y') between '"+General.checknull(raModel.getXTODATE())+"' and '"+General.checknull(raModel.getXFROMDATE())+"' ";
							}
		        			query += "and crm.panel_code is not null and AF_REG_ID in (select af_reg_id from af_inspection_member_detail where isfinalsubmited='Y') order by is_final_submit_app_dt asc";
		 			psmt = conn.prepareStatement(query);
		 			System.out.println("InspectorInspectionManager getApplicationDetailsForReopen psmt||"+psmt);
		 			rst = psmt.executeQuery();
		 			
		 			while (rst.next()) {
		 				JSONObject json= new JSONObject();
		 				String sess=rst.getString("session");
		 				
		 				deparr.forEach(jssn-> {
		 					JSONObject jval = (JSONObject) jssn;
								if(jval.get("id").equals(sess))
								{
									 json.put("session",General.checknull(jval.get("desc").toString()));
								}
		 				});
		 				
		        		 json.put("session_id",General.checknull(sess));
						 json.put("REG_FOR_NAME",General.checknull(rst.getString("PROP_INST_NAME")));
						 json.put("contact",General.checknull(rst.getString("contact")));
						 json.put("email",General.checknull(rst.getString("email")));
						 json.put("AF_REG_ID",General.checknull(rst.getString("AF_REG_ID")));
						 json.put("REG_NO",General.checknull(rst.getString("REG_NO")));
						 json.put("Payment_Date",General.checknull(rst.getString("Payment_Date")));
						 json.put("panel_code",General.checknull(rst.getString("panel_code")));
						 json.put("remarks",General.checknull(rst.getString("review_remarks")));
						 json.put("isfinalsubmited_convinor",General.checknull(rst.getString("isfinalsubmited")));
						 json.put("is_final_submit_app",General.checknull(rst.getString("is_final_submit_app")));
						 json.put("is_pannel_member",General.checknull(rst.getString("is_pannel_member")));
						 json.put("is_convinor",General.checknull(rst.getString("is_convinor")));
						 json.put("convinor_id",General.checknull(rst.getString("convinor_id")));
						 jsonArray.add(json);
		        	 }
		        	 
		        	 objectJson.put("Applicationlist", jsonArray);
		        	 
		          } catch (Exception e) {
		           System.out.println("FileName=[InspectorInspectionManager] MethodName=[getApplicationDetailsForReopen()] :"+ e.getMessage().toString());
		           l.fatal(Logging.logException("FileName=[InspectorInspectionManager] MethodName=[getApplicationDetailsForReopen()] :", e.getMessage().toString()));
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
		            	  l.fatal(Logging.logException("FileName=[InspectorInspectionManager],MethodName=[getApplicationDetails()]", e.getMessage().toString()));
		              }
		          }
		         return objectJson;
		    }
		
		public static String saveReopen(InspectorInspectionModel formModel) {
			l = Logger.getLogger("exceptionlog");
			Connection conn = null;
			PreparedStatement psmt = null;
			String query = "", msg = "0";
			int cnt = 0, count = 0;
			try {
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);
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
						/*This query will be used we need to open the inspection page as well with portal after final submit
						 * query="UPDATE af_clg_reg_mast acm,af_inspection_member_detail aim "
								+ "SET acm.is_final_submit_app='N',aim.isfinalsubmited='N' , acm.is_final_submit_app_dt=now() WHERE acm.AF_REG_ID=? "
								+ "and acm.AF_REG_ID=aim.af_reg_id and aim.inspection_type='Inspector'";
						*/
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
					conn.commit();
					msg = "1";
				} else {
					conn.rollback();
					msg = "0";
				}
			} catch (Exception e) {
				msg = "0";
				System.out.println("Exception in InspectorInspectionManager[saveReopen]" + " " + e.getMessage());
				l.fatal(Logging.logException("InspectorInspectionManager[saveReopen]", e.toString()));
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