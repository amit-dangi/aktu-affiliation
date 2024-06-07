package com.sits.affiliation.reports.affiliation_payment_details_report;

import com.sits.general.Common;

public class AffiliationPaymentDetailsModel extends Common{
	
	private String college_id;
	private String request_id;
	private String sub_req_id;
	private String college_name;
	private String request_name;
	private String sub_req_name;
	private String amount;
	private String payment_status;
	private String TransactionDate;
	private String is_final_submit_app;
	private String bank_orderId;
	private String report_type;
	private String final_submit;

	public String getReport_type() {
		return report_type;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	public String getFinal_submit() {
		return final_submit;
	}
	public void setFinal_submit(String final_submit) {
		this.final_submit = final_submit;
	}
	public String getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}
	public String getCollege_id() {
		return college_id;
	}
	public void setCollege_id(String college_id) {
		this.college_id = college_id;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getSub_req_id() {
		return sub_req_id;
	}
	public void setSub_req_id(String sub_req_id) {
		this.sub_req_id = sub_req_id;
	}
	public String getCollege_name() {
		return college_name;
	}
	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}
	public String getRequest_name() {
		return request_name;
	}
	public void setRequest_name(String request_name) {
		this.request_name = request_name;
	}
	public String getSub_req_name() {
		return sub_req_name;
	}
	public void setSub_req_name(String sub_req_name) {
		this.sub_req_name = sub_req_name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public String getIs_final_submit_app() {
		return is_final_submit_app;
	}
	public void setIs_final_submit_app(String is_final_submit_app) {
		this.is_final_submit_app = is_final_submit_app;
	}
	public String getBank_orderId() {
		return bank_orderId;
	}
	public void setBank_orderId(String bank_orderId) {
		this.bank_orderId = bank_orderId;
	}

}
