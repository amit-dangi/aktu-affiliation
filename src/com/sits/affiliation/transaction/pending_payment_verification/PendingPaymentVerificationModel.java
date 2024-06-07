package com.sits.affiliation.transaction.pending_payment_verification;

public class PendingPaymentVerificationModel {
	
	private String sname;
	private String studentCategoryName;	
	private String merchantorderno;
	private String status;
	private String fromDate;
	private String toDate;
	private String hallTicketNo;
	private String msg;

	private String SESSION;
	private String pgmYrTyp;
	private String dept;
	private String progId;
	private String sel_from_date;
	private String sel_to_date;
	private String sel_hall_ticket;
	private String amt;
	private String requestType;
	private String tranId; 
	private String amount;
	private String date;
	private String feeTyp;
	
	private String type;
	private String post;
	private String XFROMDATE;
	private String XTODATE;
	private String regno;
	private String id;
	
	private String key_id;
	private String key_secret;
	private String Payment_gateWayUrl;
	
	private String paymenttype;
	private String payment_status;
		
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getKey_secret() {
		return key_secret;
	}
	public void setKey_secret(String key_secret) {
		this.key_secret = key_secret;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getXFROMDATE() {
		return XFROMDATE;
	}
	public void setXFROMDATE(String xFROMDATE) {
		XFROMDATE = xFROMDATE;
	}
	public String getXTODATE() {
		return XTODATE;
	}
	public void setXTODATE(String xTODATE) {
		XTODATE = xTODATE;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}
	public String getTranId() {
		return tranId;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	boolean isValidate;
	
	public Boolean getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(Boolean isValidate) {
		this.isValidate = isValidate;
	}
	
	
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getStudentCategoryName() {
		return studentCategoryName;
	}
	public void setStudentCategoryName(String studentCategoryName) {
		this.studentCategoryName = studentCategoryName;
	}
	public String getMerchantorderno() {
		return merchantorderno;
	}
	public void setMerchantorderno(String merchantorderno) {
		this.merchantorderno = merchantorderno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getHallTicketNo() {
		return hallTicketNo;
	}
	public void setHallTicketNo(String hallTicketNo) {
		this.hallTicketNo = hallTicketNo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSESSION() {
		return SESSION;
	}
	public void setSESSION(String sESSION) {
		SESSION = sESSION;
	}
	public String getPgmYrTyp() {
		return pgmYrTyp;
	}
	public void setPgmYrTyp(String pgmYrTyp) {
		this.pgmYrTyp = pgmYrTyp;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getProgId() {
		return progId;
	}
	public void setProgId(String progId) {
		this.progId = progId;
	}
	public String getSel_from_date() {
		return sel_from_date;
	}
	public void setSel_from_date(String sel_from_date) {
		this.sel_from_date = sel_from_date;
	}
	public String getSel_to_date() {
		return sel_to_date;
	}
	public void setSel_to_date(String sel_to_date) {
		this.sel_to_date = sel_to_date;
	}
	public String getSel_hall_ticket() {
		return sel_hall_ticket;
	}
	public void setSel_hall_ticket(String sel_hall_ticket) {
		this.sel_hall_ticket = sel_hall_ticket;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getFeeTyp() {
		return feeTyp;
	}
	public void setFeeTyp(String feeTyp) {
		this.feeTyp = feeTyp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPayment_gateWayUrl() {
		return Payment_gateWayUrl;
	}
	public void setPayment_gateWayUrl(String payment_gateWayUrl) {
		Payment_gateWayUrl = payment_gateWayUrl;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	
}