package com.sits.affiliation.transaction.new_affi_request_approval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sits.commonApi.commonAPI;
import com.sits.conn.DBConnection;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;
import com.sits.general.ReadProps;
import com.sits.mailUtils.SendMailDetailModel;
import com.sits.mailUtils.SendMailModel;
import com.sits.mailUtils.SendMailUtil;

public class NewAffiRequestApprovalManager {
	
	static Logger log = Logger.getLogger("exceptionlog");
	static  Logger l=null;
	
	 public static ArrayList<NewAffiRequestApprovalModel>  getList(NewAffiRequestApprovalModel umodel){
		 l=Logger.getLogger("exceptionlog"); 
		 Connection          conn=null;
		 PreparedStatement   psmt=null;
		 ResultSet rst=null;
		 String qry          ="";
		 ArrayList<NewAffiRequestApprovalModel> al = new ArrayList<NewAffiRequestApprovalModel>();
		 try{
			 conn=DBConnection.getConnection();
			qry=" select AF_REG_ID, REG_NO, (select DESCP1 from cparam where CODE='AKTU_PORT' and SERIAL='REG_TYP' and PDOC=REG_TYP) REG_TYP, "
				+ "REG_FOR_NAME, PROP_INST_NAME, CONTACT, EMAIL, is_reg_approved,(case when (select InstituteCode from af_already_reg_clg_mast arc "
				+ "where arc.InstituteCode=acrm.clg_code) is not null then 'Existing Affiliation' when(select Applicaion_ID from af_already_reg_noc_mast "
				+ "arf where arf.Applicaion_ID=acrm.clg_code) is not null then 'New (Registered Offline)'else 'New Affiliation' end) as affiliation_type,"
				+ "ifnull((select InstituteCode from af_already_reg_clg_mast arc where arc.InstituteCode=acrm.clg_code),"
				+ "(select Applicaion_ID from af_already_reg_noc_mast arf where arf.Applicaion_ID=acrm.clg_code)) as ccode "
				+ "from af_clg_reg_mast acrm where 1=1";
			
			if(!General.checknull(umodel.getFrom_date()).trim().equals("")
					&&(!General.checknull(umodel.getTo_date()).trim().equals(""))) {
					qry+=" and date_format(CREATED_DATE, '%d/%m/%Y')  between '"+umodel.getFrom_date()+"' and '"+umodel.getTo_date()+"' " ;
			}
			
			 if(!General.checknull(umodel.getStatus()).equals("")){
				  qry+=" and is_reg_approved ='"+umodel.getStatus()+"'";
			 }
			 if(!General.checknull(umodel.getReg_type()).equals("")){
				  qry+=" and REG_TYP='"+umodel.getReg_type()+"'";
			 }	
			 System.out.println("getList"+qry);
			psmt=conn.prepareStatement(qry);
			 rst=psmt.executeQuery();
			 while(rst.next()){
				 NewAffiRequestApprovalModel model = new NewAffiRequestApprovalModel();
				 model.setMast_id(General.checknull(rst.getString("AF_REG_ID")));
				 model.setReg_id(General.checknull(rst.getString("REG_NO")));
				 model.setReg_type(General.checknull(rst.getString("REG_TYP")));
				 model.setSocTrSec_name(General.checknull(rst.getString("REG_FOR_NAME")));
				 model.setProposed_ins_name(General.checknull(rst.getString("PROP_INST_NAME"))+" ("+General.checknull(rst.getString("ccode"))+ ")");
				 model.setMob_no(General.checknull(rst.getString("CONTACT")));
				 model.setEmail_id(General.checknull(rst.getString("EMAIL")));
				 model.setApp_status(General.checknull(rst.getString("is_reg_approved")));
				 model.setAffiliation_type(General.checknull(rst.getString("affiliation_type")));
				 al.add(model);
			 }
		 }catch(Exception e){
			 System.out.println("Exception in NewAffiRequestApprovalManager[getList]"+" "+e.getMessage());
			 l.fatal(Logging.logException("NewAffiRequestApprovalManager[getList]", e.toString()));
			 return  al;
		 }finally{
			 try{
				  conn.close();
				  psmt.close();
				  rst.close();
			   }catch(Exception  ex){
				   ex.printStackTrace();
			   }
		 }
		 return al;
	 }

	 public static NewAffiRequestApprovalModel viewRecord(String mast_id) {
			String cSql = "";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rst = null;
			NewAffiRequestApprovalModel sm = new NewAffiRequestApprovalModel();
			try {
				conn = DBConnection.getConnection();
				cSql = " select * from af_clg_reg_mast m where  m.AF_REG_ID='"+mast_id+"'";
				pstmt = conn.prepareStatement(cSql);
				rst = pstmt.executeQuery();
				
				if (rst.next() == false) {
				} else {
					do {
						sm.setReg_id(General.checknull(rst.getString("REG_NO")));
						sm.setDet_id(General.checknull(rst.getString("AF_REG_ID")));
						sm.setReg_type(General.checknull(rst.getString("REG_TYP")));
						sm.setSocTrSec_name(General.checknull(rst.getString("REG_FOR_NAME")));
						sm.setIns_type(General.checknull(rst.getString("INST_TYPE")));
						sm.setIns_category(General.checknull(rst.getString("INST_CAT")));
						sm.setIs_minority(General.checknull(rst.getString("IS_MINORITY")));
						sm.setProposed_ins_name(General.checknull(rst.getString("PROP_INST_NAME")));
						sm.setAddress(General.checknull(rst.getString("ADDR")));
						sm.setCountry(General.checknull(rst.getString("COUNTRY")));
						sm.setState(General.checknull(rst.getString("STATE")));
						sm.setDistrict(General.checknull(rst.getString("DISTRICT")));
						sm.setTahshil(General.checknull(rst.getString("TAHSIL")));
						sm.setCmanChiefTrusteeMD_name(General.checknull(rst.getString("MD_NAME")));
						sm.setMob_no(General.checknull(rst.getString("CONTACT")));
						sm.setEmail_id(General.checknull(rst.getString("EMAIL")));
						sm.setIsInstAppToStartTheProgram(General.checknull(rst.getString("IS_INST_APP_TO_START_THE_PROGRAM")));
						sm.setIsInstDiplomaCourses(General.checknull(rst.getString("IS_INST_DIPLOMA_COURSES")));
						sm.setApp_status(General.checknull(rst.getString("is_reg_approved")));
						sm.setRemarks(General.checknull(rst.getString("remarks")));
						
					} while (rst.next());
				}
			} catch (Exception e) {
				System.out.println("Error in NewAffiRequestApprovalManager[viewRecord] : " + e.getMessage());
				log.fatal(Logging.logException("NewAffiRequestApprovalManager[viewRecord]", e.getMessage().toString()));
			} finally {
				try {
					if (pstmt != null)pstmt = null;
					if (rst != null)rst = null;
					if (conn != null)conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return sm;
		}
	 
	 public static JSONArray getPrgmDetail(String mast_id) {
		 Connection          conn=null;
		 PreparedStatement   psmt=null;
		 ResultSet rst=null;
			JSONArray arr = new JSONArray();
		 String qry   ="";
			 try{
				 conn=DBConnection.getConnection();
				 qry="select AF_REG_DID,PROG_ID,PROG_TYPE "
				 	+ "from af_clg_reg_mast_detail where AF_REG_ID='"+mast_id+"' ";
				
				psmt=conn.prepareStatement(qry);
				 rst=psmt.executeQuery();
				 while(rst.next()) {
		        		JSONObject obj = new JSONObject();
		        		obj.put("AF_REG_DID", General.checknull(rst.getString("AF_REG_DID")));
						obj.put("PROG_ID", General.checknull(rst.getString("PROG_ID")));
						obj.put("PROG_TYPE", General.checknull(rst.getString("PROG_TYPE")));
						arr.add(obj);
		        	}
			 }catch(Exception e){
				System.out.println("Error in NewAffiRequestApprovalManager[viewRecord] : " + e.getMessage());
				log.fatal(Logging.logException("NewAffiRequestApprovalManager[viewRecord]", e.getMessage().toString()));
			} finally {
				try {
					if (psmt != null)psmt = null;
					if (rst != null)rst = null;
					if (conn != null)conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return arr;
	 }
		public static NewAffiRequestApprovalModel saveRecord(NewAffiRequestApprovalModel aModel, String user_id, String ip) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rst = null;
			String cInsert="",cSql="",id="",str="";
			int updtCnt=0,i=0;
			try
			{
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);
				pstmt=null;
				cInsert ="UPDATE af_clg_reg_mast SET is_reg_approved=?,reg_approved_by=?,remarks=?,"
						+ "reg_approved_date=now(), reg_approved_machine=? where AF_REG_ID= ? ";
				pstmt = conn.prepareStatement(cInsert);
				pstmt.setString(++i, General.checknull(aModel.getApp_status()));
				pstmt.setString(++i, General.checknull(ip));
				pstmt.setString(++i, General.checknull(aModel.getRemarks()));
				pstmt.setString(++i, General.checknull(user_id));
				pstmt.setString(++i, General.checknull(aModel.getMast_id()));
				updtCnt = pstmt.executeUpdate();
				if(updtCnt>0) {
					if(aModel.getSend_mail().equals("Y")){SendMail(aModel);}
					conn.commit();
					aModel.setErrMsg(ApplicationConstants.SAVE);
					aModel.setValid(true);
				} else {
					conn.rollback();
					aModel.setErrMsg(ApplicationConstants.FAIL);
					aModel.setValid(false);					
				}			
			} catch (Exception e) {
				str=e.getMessage().toString() ;
				if(str.indexOf("Duplicate entry") != -1)
					str = ApplicationConstants.UNIQUE_CONSTRAINT;
				else 
					str = ApplicationConstants.EXCEPTION_MESSAGE;
				
				aModel.setErrMsg(str);
				aModel.setValid(false);
				log.fatal(Logging.logException("NewAffiRequestApprovalManager[save]", e.getMessage().toString()));
				System.out.println("Error in NewAffiRequestApprovalManager[save] : "+e.getMessage());			
			} finally {
				try {
					if(pstmt != null) pstmt.close();
					if(rst != null) rst.close();
					conn.close();
					str ="";
				} catch (Exception e1) {
					e1.printStackTrace();
				}						
			}
			return aModel;
		}
	


		public static String SendMail(NewAffiRequestApprovalModel model) {
				String status = "Affiliation College Registration mail sent successfully";
				String sndrCode = "1";//new SendMailUtil().getCparamData("AFILIATION", "MAIL", "SENDER");		
				String subject = ReadProps.getkeyValue("subject.affiliation_reg_mail", "sitsResource");
				ArrayList<SendMailDetailModel> list = new ArrayList<SendMailDetailModel>();
				String bodyText = mailContent(model.getEmail_id(), model.getDpassword());
				
				SendMailDetailModel mdl = new SendMailDetailModel();
				mdl.setEmail(General.checknull(model.getEmail_id()).trim());
				mdl.setTyp("t");
				list.add(mdl);

				SendMailModel mailParameters = new SendMailModel();
				mailParameters.setSenderCode(sndrCode);
				mailParameters.setSubject(subject);
				mailParameters.setBodyText(bodyText);
				mailParameters.setMailType("S");
				mailParameters.setModule("REGISTRATION");
				mailParameters.setSubModule("AFFILIATION REGISTRATION");
				mailParameters.setDescp1("Test");
				mailParameters.setDescp2("OTP");
				mailParameters.setCreatedBy(model.getMast_id());
				mailParameters.setIp(model.getIp());
				mailParameters.setList(list);
				mailParameters.setMobile(model.getMob_no());
				mailParameters.setDescp3(model.getRemarks());

				new SendMailUtil().saveTempMailSender(mailParameters, "temp");

				return status;
			}
			
			public static String mailContent(String email, String pass) {
				StringBuffer mailcontent = new StringBuffer();
				String MAIL_CONTENT_1 = "";
				String uni_name=ReadProps.getkeyValue("welcome.header", "sitsResource");
				
				MAIL_CONTENT_1 = "<b>Dear "+uni_name+", </b><br><br>"
						+ "<b>Greeting from "+uni_name+"!</b><br>"
						+ "Thank You For The Affiliation Registration!<br>"
						+ "Your Registration has been completed successfully.<br>"
						+ "Please find the login details :<br>"
						+ "Registration ID : "+email+"<br>"
						+ "Password : "+pass+"<br><br><br>"
						+ "<b>Affiliation Team,</b><br>" 
						+ uni_name;
				mailcontent.append(MAIL_CONTENT_1);
				return mailcontent.toString();
			}
	
		public static String getFileDetail(String mast_id) {
			String cSql="", fileName="";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rst = null;
			try {			
				conn = DBConnection.getConnection();
				cSql ="select concat(file_attachment_id,'_',file_name) from file_attachment where file_type='CLG_REG_DOC' and table_name='af_clg_reg_mast_temp' and reference_id='"+mast_id+"'";		
				pstmt = conn.prepareStatement(cSql);
				rst = pstmt.executeQuery();
				if(rst.next()) {
					fileName = General.checknull(rst.getString(1));
				}	
			} catch (Exception e) {
				System.out.println("Error in PostServiceCoverUnderNpsManager[getFileDetail] : "+e.getMessage());
				log.fatal(Logging.logException("PostServiceCoverUnderNpsManager[getFileDetail]", e.getMessage().toString()));
			} finally {
				try {
					if(pstmt != null) pstmt = null;
					if(rst != null) rst = null;
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
			return fileName;
		}
}
