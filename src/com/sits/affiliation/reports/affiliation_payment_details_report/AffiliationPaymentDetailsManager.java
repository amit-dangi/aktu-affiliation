package com.sits.affiliation.reports.affiliation_payment_details_report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.sits.conn.DBConnection;
import com.sits.general.General;
import com.sits.general.Logging;


public class AffiliationPaymentDetailsManager {
	static Logger log = Logger.getLogger("exceptionlog");
	
	 public static ArrayList<AffiliationPaymentDetailsModel> getList(AffiliationPaymentDetailsModel model) {
	      Connection conn = null;
	      PreparedStatement psmt = null;
	      ResultSet rst = null;
	      ArrayList<AffiliationPaymentDetailsModel> al = new ArrayList();
	      String query = "";
	      AffiliationPaymentDetailsModel model1 = null;

      try {
         conn = DBConnection.getConnection();
         if(General.checknull(model.getReport_type()).equals("TR")){
	         query = "select concat(a.InstituteName,'(',InstituteCode,')') as InstituteName, p.request_id,(select req_desc from af_request_type r "
	         		+ "where r.req_id=p.request_id) as req_name,(select sub_request_id from "
	         		+ "af_manage_request_type_sub_type s where MRT_ID=p.sub_request_id) as "
	         		+ "sub_request_name,p.sub_request_id, p.payment_amount, p.payment_status,is_final_submit_app,"
	         		+ " date_format(p.TransactionDate, '%d/%m/%Y : %r') TransactionDate,p.bank_orderId from af_reuest_form_payment p,"
	         		+ " af_clg_reg_mast c, af_already_reg_clg_mast a where p.CAND_ID=c.AF_REG_ID and "
	         		+ "a.InstituteCode=c.clg_code and p.is_submitted='Y' and c.is_final_submit_app like '%" + General.checknull(model.getIs_final_submit_app()) + "' ";
	         
	         if (!General.checknull(model.getCollege_id()).equals("")) {
	            query = query + " AND c.clg_code='" + model.getCollege_id() + "' ";
	         }

	         if (!General.checknull(model.getRequest_id()).equals("")) {
	            query = query + " AND p.request_id='" + model.getRequest_id() + "' ";
	         }

	         if (!General.checknull(model.getSub_req_id()).equals("")) {
	            query = query + " AND p.sub_request_id='" + model.getSub_req_id() + "' ";
	         }
         }else{
        	 query="select InstituteName,clg_code,request_id,sub_request_id,group_concat(distinct req_name) as req_name,group_concat(distinct sub_request_name) as sub_request_name,"
        	 		+ "sum(payment_amount) as payment_amount,coalesce(group_concat(distinct payment_status),'Not paid') as payment_status,is_final_submit_app, '' as TransactionDate,"
        	 		+ "'' as bank_orderId from ( select concat(c.PROP_INST_NAME,'(',c.clg_code,')') as InstituteName,clg_code, p.request_id,(select req_desc from af_request_type r "
        	 		+ "where r.req_id=p.request_id) as req_name,(select sub_request_id from af_manage_request_type_sub_type s where MRT_ID=p.sub_request_id) as sub_request_name,"
        	 		+ "p.sub_request_id, p.payment_amount, p.payment_status,is_final_submit_app, date_format(p.TransactionDate, '%d/%m/%Y : %r') TransactionDate,p.bank_orderId from"
        	 		+ " af_clg_reg_mast c left join af_reuest_form_payment p  on c.AF_REG_ID=p.CAND_ID where c.is_final_submit_app like '%" + General.checknull(model.getIs_final_submit_app()) + "') "  
        	 		+ " as details where 1=1 " ;
	 	         
	 	         if (!General.checknull(model.getCollege_id()).equals("")) {
	 	            query += " AND clg_code='" + model.getCollege_id() + "' ";
	 	         }

	 	         if (!General.checknull(model.getRequest_id()).equals("")) {
	 	            query += " AND request_id='" + model.getRequest_id() + "' ";
	 	         }

	 	         if (!General.checknull(model.getSub_req_id()).equals("")) {
	 	            query += " AND sub_request_id='" + model.getSub_req_id() + "' ";
	 	         }
	 	        query += " group by InstituteName ";
	         }

	         psmt = conn.prepareStatement(query);
	         rst = psmt.executeQuery();
	         System.out.println("psmt getList||"+psmt);
	         while(rst.next()) {
	            model1 = new AffiliationPaymentDetailsModel();
	            model1.setCollege_name(General.checknull(rst.getString("InstituteName")));
	            model1.setRequest_name(General.checknull(rst.getString("req_name")));
	            model1.setSub_req_name(General.checknull(rst.getString("sub_request_name")));
	            model1.setAmount(General.checknull(rst.getString("payment_amount")));
	            model1.setPayment_status(General.checknull(rst.getString("payment_status")));
	            model1.setTransactionDate(General.checknull(rst.getString("TransactionDate")));
	            model1.setBank_orderId(General.checknull(rst.getString("bank_orderId")));
	            model1.setIs_final_submit_app(General.checknull(rst.getString("is_final_submit_app")));
	            al.add(model1);
	         }
	      } catch (Exception var16) {
	         System.out.println("EXCEPTION CAUSED BY: " + var16.getMessage().toUpperCase());
	         log.fatal(Logging.logException("AffiliationPaymentDetailsManager[getList]", var16.toString()));
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
	            
	         } catch (SQLException var15) {
	            var15.printStackTrace();
	         }

	      }

	      return al;
	   }

}
