package com.sits.mailUtils;

import java.util.List;

public class SendMailModel  {
	private String senderCode;
	private String smtpHost;
	private String pop3Host;
	@Override
	public String toString() {
		return "SendMailModel [senderCode=" + senderCode + ", smtpHost=" + smtpHost + ", pop3Host=" + pop3Host
				+ ", fromAddress=" + fromAddress + ", smtpPort=" + smtpPort + ", pop3Port=" + pop3Port + ", userName="
				+ userName + ", password=" + password + ", smtpSsl=" + smtpSsl + ", subModule=" + subModule
				+ ", mailSeqNo=" + mailSeqNo + ", tEmailId=" + tEmailId + ", cEmailId=" + cEmailId + ", bEmailId="
				+ bEmailId + ", subject=" + subject + ", bodyText=" + bodyText + ", bodyText_sms=" + bodyText_sms
				+ ", mailDate=" + mailDate + ", mailType=" + mailType + ", module=" + module + ", descp1=" + descp1
				+ ", descp2=" + descp2 + ", descp3=" + descp3 + ", descp4=" + descp4 + ", descp5=" + descp5
				+ ", mailBounceId=" + mailBounceId + ", mailBounceDate=" + mailBounceDate + ", mailBounceReason="
				+ mailBounceReason + ", attchmentPath=" + attchmentPath + ", attchmentPath1=" + attchmentPath1
				+ ", attchmentPath2=" + attchmentPath2 + ", mobile=" + mobile + ", createdBy=" + createdBy + ", ip="
				+ ip + ", list=" + list + "]";
	}
	private String fromAddress;
	private String smtpPort;
	private String pop3Port;
	private String userName;
	private String password;
	private String smtpSsl;
    private String subModule;
	private String mailSeqNo;
	private String tEmailId;
	private String cEmailId;
	private String bEmailId;
	private String subject;
	private String bodyText;
	private String bodyText_sms;
	private String mailDate;
	private String mailType;
	private String module;
	private String descp1;
	private String descp2;
	private String descp3;
	private String descp4;
	private String descp5;
	private String mailBounceId;
	private String mailBounceDate;
	private String mailBounceReason;
	private String attchmentPath;
	private String attchmentPath1;
	private String attchmentPath2;
	private String mobile;
	private String createdBy;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	private String ip;
    public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getAttchmentPath() {
		return attchmentPath;
	}
	public void setAttchmentPath(String attchmentPath) {
		this.attchmentPath = attchmentPath;
	}
	private List<SendMailDetailModel> list;
	public String getSenderCode() {
		return senderCode;
	}
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getPop3Host() {
		return pop3Host;
	}
	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getPop3Port() {
		return pop3Port;
	}
	public void setPop3Port(String pop3Port) {
		this.pop3Port = pop3Port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSmtpSsl() {
		return smtpSsl;
	}
	public void setSmtpSsl(String smtpSsl) {
		this.smtpSsl = smtpSsl;
	}
	public String getMailSeqNo() {
		return mailSeqNo;
	}
	public void setMailSeqNo(String mailSeqNo) {
		this.mailSeqNo = mailSeqNo;
	}
	public String gettEmailId() {
		return tEmailId;
	}
	public void settEmailId(String tEmailId) {
		this.tEmailId = tEmailId;
	}
	public String getcEmailId() {
		return cEmailId;
	}
	public void setcEmailId(String cEmailId) {
		this.cEmailId = cEmailId;
	}
	public String getbEmailId() {
		return bEmailId;
	}
	public void setbEmailId(String bEmailId) {
		this.bEmailId = bEmailId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBodyText() {
		return bodyText;
	}
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	public String getMailDate() {
		return mailDate;
	}
	public void setMailDate(String mailDate) {
		this.mailDate = mailDate;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getDescp1() {
		return descp1;
	}
	public void setDescp1(String descp1) {
		this.descp1 = descp1;
	}
	public String getDescp2() {
		return descp2;
	}
	public void setDescp2(String descp2) {
		this.descp2 = descp2;
	}
	public String getDescp3() {
		return descp3;
	}
	public void setDescp3(String descp3) {
		this.descp3 = descp3;
	}
	public String getDescp4() {
		return descp4;
	}
	public void setDescp4(String descp4) {
		this.descp4 = descp4;
	}
	public String getDescp5() {
		return descp5;
	}
	public void setDescp5(String descp5) {
		this.descp5 = descp5;
	}
	public String getMailBounceId() {
		return mailBounceId;
	}
	public void setMailBounceId(String mailBounceId) {
		this.mailBounceId = mailBounceId;
	}
	public String getMailBounceDate() {
		return mailBounceDate;
	}
	public void setMailBounceDate(String mailBounceDate) {
		this.mailBounceDate = mailBounceDate;
	}
	public String getMailBounceReason() {
		return mailBounceReason;
	}
	public void setMailBounceReason(String mailBounceReason) {
		this.mailBounceReason = mailBounceReason;
	}
	public List<SendMailDetailModel> getList() {
		return list;
	}
	public void setList(List<SendMailDetailModel> list) {
		this.list = list;
	}
	public String getSubModule() {
		return subModule;
	}
	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBodyText_sms() {
		return bodyText_sms;
	}
	public void setBodyText_sms(String bodyText_sms) {
		this.bodyText_sms = bodyText_sms;
	}
	public String getAttchmentPath1() {
		return attchmentPath1;
	}
	public void setAttchmentPath1(String attchmentPath1) {
		this.attchmentPath1 = attchmentPath1;
	}
	public String getAttchmentPath2() {
		return attchmentPath2;
	}
	public void setAttchmentPath2(String attchmentPath2) {
		this.attchmentPath2 = attchmentPath2;
	}
	
}