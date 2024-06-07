/**
 * @ AUTHOR Amit DanGi
 */
package com.sits.affiliation.transaction.pending_payment_verification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sits.conn.DBConnection;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;


public class PendingPaymentVerificationManager {
	static Logger log = Logger.getLogger("exceptionlog");
	
	 public static ArrayList<PendingPaymentVerificationModel> getList(PendingPaymentVerificationModel model) {
			String cSql = "";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rst = null;
		 	ArrayList<PendingPaymentVerificationModel> sm = new ArrayList<PendingPaymentVerificationModel>();
			try {
				conn = DBConnection.getConnection();
				if(General.checknull(model.getType()).equals("R")){
					cSql = "select b.PROP_INST_NAME inst_name, b.MD_NAME md_name, a.cand_id registration, a.order_status, a.amount, a.order_id, "
							+ "bank_orderId tracking_id, date_format(a.created, '%d/%m/%Y %r') as date, a.status_message, a.id, date_format(now(), "
							+ "'%Y-%m-%d') as dt from online_registration_form_payment a, af_clg_reg_mast_temp b where a.primary_id=b.AF_REG_ID and "
							+ "a.cand_id=b.REG_NO and a.order_status not in ('IN REJECT') ";
					if(!General.checknull(model.getRegno()).trim().equals("")){
						cSql+=" and a.CAND_ID='"+model.getRegno().trim()+"' ";
					}
					if(!General.checknull(model.getXFROMDATE()).trim().equals("") && !General.checknull(model.getXTODATE()).trim().equals("")){
						cSql+=" and date_format(a.created,'%Y-%m/%d') between str_to_date('"+model.getXFROMDATE()+"','%d/%m/%Y') and str_to_date('"+model.getXTODATE()+"','%d/%m/%Y') ";
					}
					if(!General.checknull(model.getPayment_status()).trim().equals("")){
						cSql+=" and a.order_status='"+model.getPayment_status().trim()+"' ";
					}
				}
				if(General.checknull(model.getType()).equals("S")){
					cSql = "select b.PROP_INST_NAME inst_name,(select req_desc from af_request_type where req_id=a.request_id) inst_name, (select sub_request_id from af_manage_request_type_sub_type " 
							+"where MRT_ID=a.sub_request_id) md_name, b.REG_NO registration, a.order_status, a.amount, a.order_id, bank_orderId tracking_id, "
							+"date_format(a.created, '%d/%m/%Y %r') as date, a.status_message, a.id, date_format(now(), '%Y-%m-%d') as dt from online_reuest_form_payment a , "
							+"af_clg_reg_mast b where a.cand_id=b.AF_REG_ID and  a.order_status not in ('IN REJECT')  ";
					if(!General.checknull(model.getRegno()).trim().equals("")){
						cSql+=" and b.REG_NO='"+model.getRegno().trim()+"' ";
					}
					if(!General.checknull(model.getXFROMDATE()).trim().equals("") && !General.checknull(model.getXTODATE()).trim().equals("")){
						cSql+=" and date_format(a.created,'%Y-%m/%d') between str_to_date('"+model.getXFROMDATE()+"','%d/%m/%Y') and str_to_date('"+model.getXTODATE()+"','%d/%m/%Y') ";
					}
					if(!General.checknull(model.getPayment_status()).trim().equals("")){
						cSql+=" and a.order_status='"+model.getPayment_status().trim()+"' ";
					}

				}
				pstmt = conn.prepareStatement(cSql);
				rst = pstmt.executeQuery();
				
				if (rst.next() == false) {
				} else {
					do {
						PendingPaymentVerificationModel clgmodel = new PendingPaymentVerificationModel();
						clgmodel.setSname(General.checknull(rst.getString("inst_name")));
						clgmodel.setStudentCategoryName(General.checknull(rst.getString("md_name")));
						clgmodel.setMerchantorderno(General.checknull(rst.getString("order_id")));
						clgmodel.setRegno(General.checknull(rst.getString("registration")));						
						clgmodel.setStatus(General.checknull(rst.getString("order_status")));
						clgmodel.setMsg(General.checknull(rst.getString("status_message")));
						clgmodel.setDate(General.checknull(rst.getString("date")));
						clgmodel.setAmt(General.checknull(rst.getString("amount")));
						clgmodel.setId(General.checknull(rst.getString("id")));
						clgmodel.setToDate(General.checknull(rst.getString("dt")));
						clgmodel.setTranId(General.checknull(rst.getString("tracking_id")));
						clgmodel.setPaymenttype(General.checknull(model.getType()));
					
						sm.add(clgmodel);
					} while (rst.next());
				}
			} catch (Exception e) {
				System.out.println("Error in PendingPaymentVerificationManager[getList] : " + e.getMessage());
				log.fatal(Logging.logException("PendingPaymentVerificationManager[getList]", e.getMessage().toString()));
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
	 
	 public static JSONObject RazorpayAPIFatchall(PendingPaymentVerificationModel model){
    	 String orderResponse=null;
    	 JSONObject obj= new JSONObject();
    	 try {
		 String key = model.getKey_id();
		 String secret = model.getKey_secret();
         String authString = key + ":" + secret;
//         System.out.println("key:secret||"+authString);
         String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
         String payment_gateWayUrl = model.getPayment_gateWayUrl()+"/"+model.getTranId();
         
             URL url = new URL(payment_gateWayUrl);
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             connection.setRequestProperty("Authorization", "Basic " + encodedAuthString);
             connection.setRequestProperty("Content-Type", "application/json");

             connection.setDoOutput(true);
             connection.setDoInput(true);
             int responseCode = connection.getResponseCode();
             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             String inputLine;
             StringBuilder response = new StringBuilder();
             while ((inputLine = in.readLine()) != null) {
                 response.append(inputLine);
             }
             in.close();
//             System.out.println("Response Code: " + responseCode);
//             System.out.println("Response Body: " + response.toString());
             obj 	= (JSONObject) new JSONParser().parse(response.toString());
             connection.disconnect();
         } catch (Exception e) {
        	 log.fatal(Logging.logException("PendingPaymentVerificationManager[RazorpayAPIFatchall]", e.toString()));
             e.printStackTrace();
         }
		return obj;
    }
	 
	 public static String saveGatewayResponse(JSONObject obj) throws SQLException {
			PendingPaymentVerificationModel model = new PendingPaymentVerificationModel();
			String status = ApplicationConstants.EXCEPTION_MESSAGE;
			Connection conn = null;
			PreparedStatement psmt = null;
	 		String is_sumitted = "N";
			String order_status=obj.get("status").toString();
			if(order_status.equals("paid")) { //paid
				is_sumitted = "Y";
			}
			
			int count1 = 0,count = 0;
			String qry="";
			int i=0;
			try {
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);
				
				qry = " update online_registration_form_payment set order_status=?, isSubmitted=?,"
						+ "trxn_amt_paise=? ,attempts=?,created_at=?,updated=now() where bank_orderId=?";
				 
				psmt = conn.prepareStatement(qry);
				psmt.setString(++i, order_status);
	 			psmt.setString(++i, is_sumitted);
	 			psmt.setString(++i, obj.get("amount").toString());
	 			psmt.setString(++i, obj.get("attempts").toString());
				psmt.setString(++i, obj.get("created_at").toString());
				psmt.setString(++i, obj.get("id").toString()); 
				//System.out.println("saveGatewayResponse psmt ::"+psmt);
				count = psmt.executeUpdate();
				if (count > 0) {
					if(order_status.equals("paid"))
					{
						qry="";
						psmt=null; 
						qry=" INSERT INTO af_registration_form_payment (online_id, CAND_ID, bank_name,  "
								+ " payment_status, payment_mode, payment_amount, merchantorderno, is_submitted, "
								+ "  message, TransactionDate, created_by, created, machine_created,MerchantId,  "
								+ " razorpay_payment_id,razorpay_order_id,razorpay_signature, trxn_amt_paise"
								+ " ,bank_orderId,entity,attempts,created_at)  "
								+ " SELECT id, cand_id, bank_name,  order_status, payment_mode, "
								+ " amount, order_id, isSubmitted,  "
								+ " status_message,  trans_date, created_by, created, machine_created,MerchantId, "
								+ " razorpay_payment_id,razorpay_order_id,razorpay_signature,trxn_amt_paise"
								+ " ,bank_orderId,entity,attempts,created_at  FROM online_registration_form_payment where bank_orderId=? ";
						
						psmt = conn.prepareStatement(qry);
						psmt.setString(1, obj.get("id").toString());
//						System.out.println("Insert rec_form_payment psmt ::"+psmt);
						count1=psmt.executeUpdate();
						if(count1>0){
							count1 = 0; count = 0; qry=""; psmt=null;
							qry = "insert into af_clg_reg_mast (AF_REG_ID, REG_NO, REG_TYP, REG_FOR_NAME, INST_TYPE, INST_CAT, "
									+ "IS_MINORITY, PROP_INST_NAME, ADDR, COUNTRY, STATE, DISTRICT, TAHSIL, MD_NAME, CONTACT, "
									+ "EMAIL, AADHAR, IS_INST_DIPLOMA_COURSES, IS_INST_APP_TO_START_THE_PROGRAM, CREATED_BY, "
									+ "CREATED_DATE, CREATED_MACHINE) (select AF_REG_ID, REG_NO, REG_TYP, REG_FOR_NAME, INST_TYPE, "
									+ "INST_CAT, IS_MINORITY, PROP_INST_NAME, ADDR, COUNTRY, STATE, DISTRICT, TAHSIL, MD_NAME, "
									+ "CONTACT, EMAIL, AADHAR, IS_INST_DIPLOMA_COURSES, IS_INST_APP_TO_START_THE_PROGRAM, "
									+ "CREATED_BY, CREATED_DATE, CREATED_MACHINE from af_clg_reg_mast_temp where AF_REG_ID="
									+ "(SELECT primary_id FROM online_registration_form_payment where bank_orderId=?))";
							psmt = conn.prepareStatement(qry);
							psmt.setString(1, obj.get("id").toString());
							count = psmt.executeUpdate();
							if (count > 0) {
								psmt=null; qry="";
								qry = "insert into af_clg_reg_mast_detail (AF_REG_DID, AF_REG_ID, PROG_ID, PROG_TYPE, CREATED_BY, "
										+ "CREATED_DATE, CREATED_MACHINE) (select AF_REG_DID, AF_REG_ID, PROG_ID, PROG_TYPE, CREATED_BY, "
										+ "CREATED_DATE, CREATED_MACHINE from af_clg_reg_mast_detail_temp where AF_REG_ID="
										+ "(SELECT primary_id FROM online_registration_form_payment where bank_orderId=?))";
										psmt = conn.prepareStatement(qry);
										psmt.setString(1, obj.get("id").toString());
										count1 = psmt.executeUpdate();
									}
							
							status = "Double verification has been done successfully";
							model.setIsValidate(true);
							conn.commit();	
						}
						else
						{
							status = ApplicationConstants.FAIL;
							model.setIsValidate(false);
							conn.rollback();	
						}
					}
					else
					{
						status = "Double verification has been done successfully";
						model.setIsValidate(true);
						conn.commit();	
					}
				} else {
						status = ApplicationConstants.FAIL;
						model.setIsValidate(false);
					conn.rollback();
				}
				
			}catch(final Exception se){
					model.setIsValidate(false);
					status=(ApplicationConstants.FAIL);
					System.out.println("Exception caused in saveResponse() in [PendingPaymentVerificationManager.java]:"+se.getMessage());
					return status;		
			}
			return status;
		}
	 
	 public static String saveGatewayAffiResponse(JSONObject obj) throws SQLException {
			PendingPaymentVerificationModel model = new PendingPaymentVerificationModel();
			String status = ApplicationConstants.EXCEPTION_MESSAGE;
			Connection conn = null;
			PreparedStatement psmt = null;
	 		String is_sumitted = "N";
			String order_status=obj.get("status").toString();
			if(order_status.equals("paid")) { //paid
				is_sumitted = "Y";
			}
			
			int count1 = 0,count = 0;
			String qry="";
			int i=0;
			try {
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);
				
				qry = " update online_reuest_form_payment set order_status=?, isSubmitted=?,"
						+ "trxn_amt_paise=? ,attempts=?,created_at=?,updated=now() where bank_orderId=?";
				 
				psmt = conn.prepareStatement(qry);
				psmt.setString(++i, order_status);
	 			psmt.setString(++i, is_sumitted);
	 			psmt.setString(++i, obj.get("amount").toString());
	 			psmt.setString(++i, obj.get("attempts").toString());
				psmt.setString(++i, obj.get("created_at").toString());
				psmt.setString(++i, obj.get("id").toString()); 
//				System.out.println("saveGatewayResponse psmt ::"+psmt);
				count = psmt.executeUpdate();
//				System.out.println("count||"+count);
				if (count > 0) {
					if(order_status.equals("paid"))
					{
						qry="";
						psmt=null; 
						qry=" INSERT INTO af_reuest_form_payment (online_id, CAND_ID, bank_name, payment_status, payment_mode, payment_amount, "
								+ "merchantorderno, is_submitted, message, TransactionDate, created_by, created, machine_created, MerchantId, "
								+ "razorpay_payment_id, razorpay_order_id, razorpay_signature, trxn_amt_paise, bank_orderId, entity, attempts, "
								+ "created_at, session, request_id, sub_request_id) SELECT id, cand_id, bank_name, order_status, payment_mode, "
								+ "amount, order_id, isSubmitted, status_message, trans_date, created_by, created, machine_created, MerchantId, "
								+ "razorpay_payment_id, razorpay_order_id, razorpay_signature, trxn_amt_paise, bank_orderId, entity, attempts, "
								+ "created_at, session, request_id, sub_request_id FROM online_reuest_form_payment where bank_orderId=? ";
						psmt = conn.prepareStatement(qry);
						psmt.setString(1, obj.get("id").toString());
//						System.out.println("Insert af_reuest_form_payment psmt ::"+psmt);
						count1=psmt.executeUpdate();
						if(count1>0)
						{
							status = "Double verification has been done successfully";
							model.setIsValidate(true);
							conn.commit();	
						}
						else
						{
							status = ApplicationConstants.FAIL;
							model.setIsValidate(false);
							conn.rollback();	
						}
					}
					else
					{
						status = "Double verification has been done successfully";
						model.setIsValidate(true);
						conn.commit();	
					}
				} else {
						status = ApplicationConstants.FAIL;
						model.setIsValidate(false);
						conn.rollback();
				}
				
			}catch(final Exception se){
					model.setIsValidate(false);
					status=(ApplicationConstants.FAIL);
					System.out.println("Exception caused in saveResponse() in [PendingPaymentVerificationManager.java]:"+se.getMessage());
					return status;		
			}
			return status;
		}
	 
	 
}
