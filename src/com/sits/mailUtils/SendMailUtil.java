package com.sits.mailUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.sits.conn.DBConnection;
import com.sits.general.General;
import com.sits.general.Logging;
import com.sits.general.ReadProps;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.sits.preadmission.general.General;
import com.sits.preadmission.general.Logging;
import com.sits.preadmission.rank_range_config.RankRangeConfigManager;
*/

public class SendMailUtil{
	static Logger l = Logger.getLogger("exceptionlog");
	static String mailschema = General.checknull(ReadProps.getkeyValue("send_mail.schema", "sitsResource"));
	
	public boolean sendMail(String mailSeqId,String tableNameMast,String tableNameDetail)
	{
		boolean isStatusExist = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String qry = "",sender_code="",body="",attachedFile="",subject="",tmail="",bmail="",cmail="";
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			ArrayList<String> list=new ArrayList<String>();
			Object param[]={mailSeqId};
			conn = DBConnection.getConnection();
		    qry="select sender_code,subject,body_text,attachment from "+mailschema+"."+tableNameMast+" where MAIL_SEQ_NO=?";
		    pstmt = conn.prepareStatement(qry);
			pstmt.setString(1, General.checknull(mailSeqId));
			rst=pstmt.executeQuery();
			while(rst.next())
			{
				list.add(General.checknull(rst.getString("sender_code")));
				list.add(General.checknull(rst.getString("subject")));
				list.add(General.checknull(rst.getString("body_text")));
				list.add(General.checknull(rst.getString("attachment")));
			}
			if(list.size()>0){
			    sender_code =list.get(0);
				subject     =list.get(1);
				body        =list.get(2);
				attachedFile =list.get(3);
			}
				
			tmail=General.checknull(getEmailsForCopy(mailSeqId,"t",tableNameDetail));
			bmail=General.checknull(getEmailsForCopy(mailSeqId,"b",tableNameDetail));
			cmail=General.checknull(getEmailsForCopy(mailSeqId,"c",tableNameDetail));
			
			SendMailModel  mailParameters = getMailParameters(sender_code);
			String host_name    =   mailParameters.getSmtpHost();// "mail.usgrpinc.com";
			String fromAddress  =   mailParameters.getFromAddress();// "iums@stratosphere.co.in";
			final String userName     =   mailParameters.getUserName();// "iums@stratosphere.co.in";
			String password     =   mailParameters.getPassword();// "74g?*ncm-9hk#me-!&pgtz3";
			String smtpPort     =   mailParameters.getSmtpPort();
			String smtpSSL      =   mailParameters.getSmtpSsl();
		
            if(!host_name.equals("") && !smtpPort.equals("")){
			 String imapProtocol = "imap";
			  // Unfortunately there is no way to know sent folder name using Java code, 
			  // so it must be specified as a property
			  String folderName = "Sent Items";
			
			/******************/
			
			/**************/
			  String[] attachFiles=null;
				if(!attachedFile.equals(""))
				{
			    attachFiles = new String[1];
			
				attachFiles[0] = attachedFile;
				}else{
					attachFiles = new String[1];
					attachFiles[0]="";
				}

			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
	    	props.put("mail.smtp.host", smtpPort);

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.user", userName);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.ssl.trust", host_name);
			if (smtpSSL.equals("N")) {
				props.put("mail.smtp.port", smtpPort);
				props.put("mail.smtp.auth", "true");
				props.put("mail.debug", "false");
			} else {
				   
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "false");
				props.put("mail.smtp.port", smtpPort);
				props.put("mail.debug", "false");
				props.put("mail.smtp.socketFactory.port", smtpPort);
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", true);
			}

			//java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, "radheradheradhe");
				}
			};
			Session session = Session.getInstance(props, auth);

			// session.setDebug(true);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			message.setSubject(subject);

			// Create mimeBodyPart object and set your message text
			BodyPart messageBodyPart = new MimeBodyPart();

			// HTML mail content
			messageBodyPart.setContent(body, "text/html");

			// Add recipients
			// InternetAddress[] recipient_mail_id =
			// getInternetAddresses("vinaymauryajava@gmail.com,sitsnda123@gmail.com");
			
			
			InternetAddress[] recipient_mail_id = SendMailUtil.getInternetAddresses(tmail);
			message.addRecipients(Message.RecipientType.TO, recipient_mail_id);

			// Add CC
			InternetAddress[] CcAddress = SendMailUtil
					.getInternetAddresses(General.checknull(cmail));
			message.setRecipients(javax.mail.Message.RecipientType.CC, CcAddress);
			// Add BCC
			if(bmail.length()>0){
				InternetAddress[] BccAddress = SendMailUtil
						.getInternetAddresses(General.checknull(bmail));
				message.setRecipients(javax.mail.Message.RecipientType.BCC, BccAddress);
			}
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			
			if(!attachedFile.equals("")){
				if (attachFiles != null && attachFiles.length > 0) {
					for (String filePath : attachFiles) {
						MimeBodyPart attachPart = new MimeBodyPart();
						try {
							attachPart.attachFile(filePath);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						multipart.addBodyPart(attachPart);
					}
				}
			}
			/*// adds attachments
			if (attachFiles != null && attachFiles.length > 0) {
				for (String filePath : attachFiles) {
					MimeBodyPart attachPart = new MimeBodyPart();
					try {
						attachPart.attachFile(filePath);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					multipart.addBodyPart(attachPart);
				}
			}*/

			message.setContent(multipart);
			// System.out.println("multipart :"+multipart);

			if (session != null) {
				Transport transport = session.getTransport("smtp");
				transport.connect(host_name, userName, password);
				transport.sendMessage(message, message.getAllRecipients());
				saveLog(mailSeqId,"Mail sent successfully");
				isStatusExist = true;
			} else if (session == null) {
				isStatusExist = false;
				System.out.println("Unable to Send.. ");
			}
			System.out.println("Sent Email successfully....");
		// transport.close();
            }
			

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			saveLog(mailSeqId,ex.getMessage());
			Logger.getLogger("usglog")
					.fatal(Logging.logException("SendMailUtil[sendMail]", ex.getMessage().toString()));
		}
		return isStatusExist;
	}

	
	/*
	 * this method is used to save record into temp mail sender table.
	 * 
	 * @param mailParameters
	 * 
	 * @return JSONObject
	 */
	public JSONObject saveTempMailSender(SendMailModel mailParameters,String saveType) {
		boolean flag=false;
		JSONObject jSonDataFinalObj = new JSONObject();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst=null;
		String qry = "";
		int mail_seq_no=0;
		String id="";
		System.out.println("mailParameters|"+mailParameters.toString());
		try {
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);
				
				qry	= "insert into "+mailschema+".temp_mail_sender(sender_code, subject, "
								+ " body_text, mail_type, module, "
								+ " sub_module,descp1,descp2,"
								+ " mail_date,created_date,created_by,created_machine,descp3, attachment, attachment1) " 
								+ " values (?,?,?,?,?,?,?,?,now(),now(),?,?,?, ?,?)";
				    pstmt = conn.prepareStatement(qry,pstmt.RETURN_GENERATED_KEYS);
				    pstmt.setString(1, mailParameters.getSenderCode().trim().replaceAll("\\s+", " "));
				    pstmt.setString(2, mailParameters.getSubject().trim().replaceAll("\\s+", " "));
				    pstmt.setString(3, mailParameters.getBodyText().trim().replaceAll("\\s+", " "));
				    pstmt.setString(4, mailParameters.getMailType().trim().replaceAll("\\s+", " "));
				    pstmt.setString(5, mailParameters.getModule().trim().replaceAll("\\s+", " "));
				    pstmt.setString(6, mailParameters.getSubModule().trim().replaceAll("\\s+", " "));
				    pstmt.setString(7, mailParameters.getDescp1().trim().replaceAll("\\s+", " "));
				    pstmt.setString(8, mailParameters.getDescp2().trim().replaceAll("\\s+", " "));
				    pstmt.setString(9, mailParameters.getCreatedBy());
				    pstmt.setString(10, mailParameters.getIp());
				    pstmt.setString(11, mailParameters.getDescp3());
				    pstmt.setString(12, mailParameters.getAttchmentPath());
				    pstmt.setString(13, mailParameters.getAttchmentPath1());
				  //  pstmt.setString(14, mailParameters.getAttchmentPath2());
				    mail_seq_no=pstmt.executeUpdate();
				    ResultSet tableKeys = pstmt.getGeneratedKeys();
				    tableKeys.next();
				    id = tableKeys.getInt(1)+"";
				    if(mail_seq_no>0)
				    {
				    	mail_seq_no=0;
				    String query="";
			   // query for insert
				ArrayList<SendMailDetailModel> al = (ArrayList<SendMailDetailModel>) mailParameters.getList();
				for(SendMailDetailModel mdl:al){
				  query="insert into ";
				  if (saveType.equals("temp"))
				  query += ""+mailschema+".temp_mail_sender_detail ";
				  query += "(mail_seq_no,send_typ,email) values(?,?,?)";
				  pstmt = conn.prepareStatement(query);
				  pstmt.setString(1, id);
				  pstmt.setString(2, General.checknull(mdl.getTyp()));
				  pstmt.setString(3, General.checknull(mdl.getEmail()));
				  mail_seq_no=pstmt.executeUpdate();
				}
				}
			
			if(mail_seq_no>0)
			{
				/*transactionManager.commit(transStatus);*/
				jSonDataFinalObj.put("flag", true);
				jSonDataFinalObj.put("seqNo", mail_seq_no);
				conn.commit();
			} else {
				/*transactionManager.rollback(transStatus);*/
				jSonDataFinalObj.put("flag", false);
				conn.rollback();
			}
		} catch (Exception e) {
			
			//jSonDataFinalObj.put("flag", "fail");
			System.out.println("Errro in SendMailUtil[saveTempMailSender]:" + " " + e.getMessage().toUpperCase());
			l.fatal(Logging.logException("SendMailUtil[saveTempMailSender]", e.toString()));
		} 
		return jSonDataFinalObj;
	}
	
	/*
	 * this method is used to get mail parameter details for sending a mail.
	 * 
	 * @param sendercode
	 * 
	 * @return SendMailDetailModel
	 */
	
	public SendMailModel getMailParameters(String sendercode)
	{
		String query = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		SendMailModel sendMailModel = new SendMailModel();
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			query = "SELECT SENDER_CODE, SMTP_HOST, POP3_HOST, FROM_ADDRESS, SMTP_PORT, POP3_PORT, USER_NAME, PASSWORD"
					+ " , SMTP_SSL FROM "+mailschema+".MAIL_PARAMETER WHERE SENDER_CODE=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, General.checknull(sendercode));
			rst=pstmt.executeQuery();
			while (rst.next()) {
				sendMailModel.setSenderCode(General.checknull(rst.getString("SENDER_CODE")));
				sendMailModel.setSmtpHost(General.checknull(rst.getString("SMTP_HOST")));
				sendMailModel.setPop3Host(General.checknull(rst.getString("POP3_HOST")));
				sendMailModel.setFromAddress(General.checknull(rst.getString("FROM_ADDRESS")));
				sendMailModel.setSmtpPort(General.checknull(rst.getString("SMTP_PORT")));
				sendMailModel.setPop3Port(General.checknull(rst.getString("POP3_PORT")));
				sendMailModel.setUserName(General.checknull(rst.getString("USER_NAME")));
				sendMailModel.setPassword(General.checknull(rst.getString("PASSWORD")));
				sendMailModel.setSmtpSsl(General.checknull(rst.getString("SMTP_SSL")));
			}

		} catch (Exception e) {
			System.out.println("SendMailUtil[getMailParameters]: " + " " + e.getMessage().toUpperCase());
			l.fatal(Logging.logException("SendMailUtil[getMailParameters]: ", e.toString()));
		}
		return sendMailModel;
	}


	/*
	 * this method is used to get cc ,bcc details for sending a mail.
	 * 
	 * @param ddo,location
	 * 
	 * @return ArrayList<SendMailDetailModel>
	 */
	public  ArrayList<SendMailDetailModel> getCopyMails()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String qry = "";
		ArrayList<SendMailDetailModel> al= new ArrayList<SendMailDetailModel>();
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			qry=" select DESCP1,DESCP2 from "+mailschema+".cparam WHERE CODE='PDM' AND PDOC=(select c.doc from cparam c "
	     		+ " where c.code='PDM' and c.SERIAL='MAIL' and PDOC='SENDER' and c.param1='Y') and PARAM1='Y'"; 
	     pstmt = conn.prepareStatement(qry);
	     rst=pstmt.executeQuery();
		 while (rst.next()) {
				SendMailDetailModel mdl = new SendMailDetailModel();
				mdl.setTyp(General.checknull(rst.getString("DESCP2")));
				mdl.setEmail(General.checknull(rst.getString("DESCP1")));
				al.add(mdl);
		 }
	}catch(Exception e){
		System.out.println("SendMailUtil[getCopyMails]: " + " " + e.getMessage().toUpperCase());
		l.fatal(Logging.logException("SendMailUtil[getCopyMails]: ", e.toString()));
		}
		return al;
	}

	
	public String getCparamData(String code,String serial,String pdoc)
	{
		String qry="",senderCode="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			qry="select c.DOC from "+mailschema+".cparam c where c.code=? and c.SERIAL=? and PDOC=? and c.param1='Y'";
			pstmt = conn.prepareStatement(qry);
			pstmt.setString(1, General.checknull(code));
			pstmt.setString(2, General.checknull(serial));
			pstmt.setString(3, General.checknull(pdoc));
			rst=pstmt.executeQuery();
			System.out.println("pstmt sender code------------"+pstmt);
			if(rst.next())
			{
				senderCode = General.checknull(rst.getString("DOC"));
			}
		}catch(Exception e){
			System.out.println("Exception in getSenderCode[]"+e.getMessage());
		}
		return senderCode;
	}
	/*public static JSONObject saveMailSender()
	{
		
	}*/
	
	public  String getEmailsForCopy(String mailSeqNo,String typ,String tableName)
	{
		String qry="",email="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
		  qry="select group_concat(email) as email from "+mailschema+"."+ tableName+" where mail_seq_no=? and send_typ=?";
		  pstmt = conn.prepareStatement(qry);
		  pstmt.setString(1, General.checknull(mailSeqNo));
		  pstmt.setString(2, General.checknull(typ));
		  rst=pstmt.executeQuery();
		  if(rst.next())
		  {
			  email = General.checknull(rst.getString("email"));
		  }
	  }catch(Exception e){
		  System.out.println("Exception in getEmailsForCopy[]"+e.getMessage());
	  }
	  
	  return email;
	}
	
	
	public static InternetAddress[] getInternetAddresses(String recipients) throws AddressException 
	{
		ArrayList<String> recipientsArray = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(recipients, ",");
		while (st.hasMoreTokens()) {
			recipientsArray.add(st.nextToken());
		}
		int sizeTo = recipientsArray.size();
		InternetAddress[] ainternetaddress1 = new InternetAddress[sizeTo];
		for (int i = 0; i < sizeTo; i++) {
			ainternetaddress1[i] = new InternetAddress(recipientsArray.get(i).toString());
		}
		return ainternetaddress1;
	}
	
	

	public  void saveLog(String mailSeq,String msg)
	{
	
		String qry="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			qry="insert into mail_log(mail_seq_no,reason,email)"
					+ " select mail_seq_no,?,email from "+mailschema+".mail_sender_detail"
					+ " where mail_seq_no=? and send_typ='t';";
			pstmt = conn.prepareStatement(qry);
			  pstmt.setString(1, General.checknull(msg));
			  pstmt.setString(2, General.checknull(mailSeq));
			  pstmt.execute();
			
		}catch(Exception e){
			 System.out.println("Exception in saveLog[]"+e.getMessage());
		}
		
	}
	
	/*
	 * this method is used to save record into temp mail sender table.
	 * 
	 * @param mailParameters
	 * 
	 * @return JSONObject
	 */
	public JSONObject saveTempSmsSender(SendMailModel mailParameters,String saveType) {
		String query = "";
		int count = 0,seqNo=0;
		JSONObject jSonDataFinalObj = new JSONObject();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try {
			query = "INSERT INTO ";
			if (saveType.equals("temp"))
				query += " "+mailschema+".TEMP_SMS_SENDER ";
			else if(saveType.equals("sms_alert"))
				query += ""+mailschema+".sms_alert";
			if(saveType.equals("sms_alert")){
				query += "(body_text, module, "
						+ "sub_module,descp1,descp2,sms_date,created_date,created_by,created_machine,mobile,descp3,hours_send_sms) ";
				query += " VALUES (?,?,?,?,?,now(),now(),?,?,?,?,?)";
				ArrayList<String> timing=getListFromCParam("ADM", "SMS", "doc");
				for(int index=0; index<timing.size(); index++){
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, mailParameters.getBodyText().trim().replaceAll("\\s+", " "));
					pstmt.setString(2, mailParameters.getModule().trim().replaceAll("\\s+", " "));
					pstmt.setString(3, mailParameters.getSubModule().trim().replaceAll("\\s+", " "));
					pstmt.setString(4, mailParameters.getDescp1().trim().replaceAll("\\s+", " "));
					pstmt.setString(5, mailParameters.getDescp2().trim().replaceAll("\\s+", " "));
					pstmt.setString(6, mailParameters.getCreatedBy());
					pstmt.setString(7, mailParameters.getIp());
					pstmt.setString(8, mailParameters.getMobile());
					pstmt.setString(9, mailParameters.getDescp3());
					pstmt.setString(10,timing.get(index));
				    count = pstmt.executeUpdate();
				}
				
			}else if(saveType.equals("temp")){
			query += "(body_text, module, "
					+ "sub_module,descp1,descp2,sms_date,created_date,created_by,created_machine,mobile,descp3) ";
			query += " VALUES (?,?,?,?,?,now(),now(),?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mailParameters.getBodyText().trim().replaceAll("\\s+", " "));
			pstmt.setString(2, mailParameters.getModule().trim().replaceAll("\\s+", " "));
			pstmt.setString(3, mailParameters.getSubModule().trim().replaceAll("\\s+", " "));
			pstmt.setString(4, mailParameters.getDescp1().trim().replaceAll("\\s+", " "));
			pstmt.setString(5, mailParameters.getDescp2().trim().replaceAll("\\s+", " "));
			pstmt.setString(6, mailParameters.getCreatedBy());
			pstmt.setString(7, mailParameters.getIp());
			pstmt.setString(8, mailParameters.getMobile());
			pstmt.setString(9, mailParameters.getDescp3());
		    count = pstmt.executeUpdate();
			}
			
			 if(count>0)
			{   
				/*transactionManager.commit(transStatus);*/
				jSonDataFinalObj.put("flag", true);
				jSonDataFinalObj.put("seqNo", seqNo);
			} else {
				/*transactionManager.rollback(transStatus);*/
				jSonDataFinalObj.put("flag", false);
				
			}
		} catch (Exception e) {
			
			//jSonDataFinalObj.put("flag", "fail");
			System.out.println("Errro in SendMailUtil[saveTempSmsSender]:" + " " + e.getMessage().toUpperCase());
			l.fatal(Logging.logException("SendMailUtil[saveTempSmsSender]", e.toString()));
		} 
		return jSonDataFinalObj;
	}
	
	/*
	 * this method is used to get list of mail record from temp mail sender table.
	
	 * @return ArrayList<String>
	 */

	public ArrayList<String> getMailSeqList(String tableName,String hours)
	{

	 String qry="";
	 Connection conn = null;
	 PreparedStatement pstmt = null;
	 ResultSet rst = null;
	 ArrayList<String> list =new ArrayList<String>();
	 try{
		 conn = DBConnection.getConnection();
		 conn.setAutoCommit(false);	
		 if(tableName.equals("mail_alert"))
			 qry="select mail_seq_no from "+mailschema+"."+tableName + " where hours_send_mail='"+hours+"'" ;
		 else
		    qry="select mail_seq_no from "+tableName;
 		 pstmt = conn.prepareStatement(qry);
 		 rst=pstmt.executeQuery();
 		 while(rst.next())
		 {
			 list.add(General.checknull(rst.getString("mail_seq_no")));
		 }
	 }catch(Exception e){
		 System.out.println("Exception in getMailSeqList[]"+e.getMessage());
	 }finally{
		 try{
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
	 }
	 return list;
	}
	
	/*
	 * this method is used to get list of sms record from temp sms sender table.
	
	 * @return ArrayList<String>
	 */

	public ArrayList<String> getSmsSeqList(String tableName,String condition,String hours)
	{

	 String qry="";
	 Connection conn = null;
	 PreparedStatement pstmt = null;
	 ResultSet rst = null;
	 ArrayList<String> list =new ArrayList<String>();
	 try{
		 conn = DBConnection.getConnection();
		 conn.setAutoCommit(false);	
		 if(condition.equals(""))
		    qry="select sms_seq_no from "+mailschema+"."+ tableName ;
		 else
		    qry="select sms_seq_no from "+mailschema+"."+ tableName +" where descp3='"+condition+"' and hours_send_sms='"+hours+"'";	
		 pstmt = conn.prepareStatement(qry);
		 rst=pstmt.executeQuery();
		  if(rst.next())
		  {
			  list.add(General.checknull(rst.getString("sms_seq_no")));
		  }		    
		 
	 }catch(Exception e){
		 System.out.println("Exception in SendMailUtil[getSmsSeqList]"+e.getMessage());
	     l.fatal(Logging.logException("SendMailUtil[getSmsSeqList]: ", e.toString()));
	 }
	 return list;
	}
	
	public boolean saveMailSendParameter(String mailSeqNo,String tableName,String detailTableName)
	{
		String qry="";
		boolean flag=false;
		int cnt=0,updtCnt=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			qry="insert "+mailschema+".into mail_sender(mail_seq_no,sender_code,subject,body_text,mail_type,sub_module,descp1,"
					+ " descp2,descp3,attachment,mail_date,created_date,created_by,created_machine)"
					+ " SELECT mail_seq_no,sender_code,subject,body_text,mail_type,sub_module,descp1,"
					+ " descp2,descp3,attachment,mail_date,now(),created_by,created_machine"
					+ " FROM "+tableName +" WHERE mail_seq_no=?;";
			pstmt = conn.prepareStatement(qry);
			pstmt.setString(1, General.checknull(mailSeqNo));
			cnt=pstmt.executeUpdate();
			if(cnt>0){
				pstmt=null;
				qry="insert into "+mailschema+".mail_sender_detail(mail_seq_no,send_typ,email)"
						+ " select mail_seq_no,send_typ,email from "+detailTableName
						+ " where mail_seq_no=?";
				pstmt = conn.prepareStatement(qry);
				pstmt.setString(1, General.checknull(mailSeqNo));
				updtCnt=pstmt.executeUpdate();
			}
			if(cnt>0 && updtCnt>0){
				flag=true;
				deleteTempMailSender(mailSeqNo,tableName,detailTableName);
			}
		}catch(Exception e){
			 System.out.println("Exception in SendMailUtil[saveMailSendParameter]"+e.getMessage());
		     l.fatal(Logging.logException("SendMailUtil[saveMailSendParameter]: ", e.toString()));
		}
		return flag;
	}
	
	
	public boolean saveSmsSendParameter(String mailSeqNo,String tableName)
	{
		
		String qry="";
		boolean flag=false;
		int cnt=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			qry="insert into "+mailschema+".sms_sender(sms_seq_no,body_text,sub_module,descp1,"
					+ " descp2,sms_date,created_date,created_by,created_machine,mobile,descp3)"
					+ " SELECT sms_seq_no,body_text,sub_module,descp1,"
					+ " descp2,sms_date,now(),created_by,created_machine,mobile,descp3 "
					+ " FROM "+tableName+ " WHERE sms_seq_no=?";
			pstmt = conn.prepareStatement(qry);
			pstmt.setString(1, General.checknull(mailSeqNo));
			cnt=pstmt.executeUpdate(); 
			if(cnt>0){
				flag=true;
				deleteTempSmsSender(mailSeqNo,tableName);
			}
		}catch(Exception e){
			System.out.println("Exception in SendMailUtil[saveSmsSendParameter]"+e.getMessage());
		     l.fatal(Logging.logException("SendMailUtil[saveSmsSendParameter]: ", e.toString()));
		}
		return flag;
	}

	
	public boolean deleteTempMailSender(String seqNo,String tableName,String detailTableName ) {

		String query = "";
		int count = 0,detCnt=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
        try {
        	conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
            query ="delete from "+mailschema+"."+detailTableName+" where mail_seq_no =?";
            pstmt = conn.prepareStatement(query);
  		  	pstmt.setString(1, General.checknull(seqNo));
			detCnt = pstmt.executeUpdate();
			if(detCnt>0){
				pstmt=null;
				query = "delete from "+tableName+" WHERE Mail_SEQ_No=?";
				pstmt = conn.prepareStatement(query);
	  		  	pstmt.setString(1, General.checknull(seqNo));
	  		  	count = pstmt.executeUpdate();
			}
			if (count > 0 && detCnt>0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (e.toString().contains("REFERENCES"))
			
			System.out.println("Error in SendMailUtil[deleteTempMailSender]:" + " " + e.getMessage().toUpperCase());
			l.fatal(Logging.logException("SendMailUtil[deleteTempMailSender]", e.toString()));
			return false;

		} finally {
			try {
				
			} catch (Exception e) {
				System.out.println(
						"EXCEPTION IN CLOSING CONNECTION IS CAUSED BY:" + " " + e.getMessage().trim().toUpperCase());

			}
		}
		
	}
	
	public boolean deleteTempSmsSender(String seqNo,String tableName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "";
		int detCnt=0;
        try {
        	conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
            query ="delete from "+mailschema+"."+tableName+" where sms_seq_no =?";
            pstmt = conn.prepareStatement(query);
  		  	pstmt.setString(1, General.checknull(seqNo));
			detCnt = pstmt.executeUpdate();
			if (detCnt>0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (e.toString().contains("REFERENCES"))
			System.out.println("Error in SendMailUtil[deleteTempSmsSender]:" + " " + e.getMessage().toUpperCase());
			l.fatal(Logging.logException("SendMailUtil[deleteTempSmsSender]", e.toString()));
			return false;
		} 
	}
	
	public ArrayList<String> getListFromCParam(String code, String serial, String columnName) {
		ArrayList<String> list = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			String sql="SELECT "+columnName+" FROM "+mailschema+".CPARAM WHERE CODE=? AND SERIAL=? AND PARAM1='Y' order by "+columnName+" desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, General.checknull(code));
			pstmt.setString(2, General.checknull(serial));
			rst=pstmt.executeQuery();
			while (rst.next()) {
				list.add(General.checknull(rst.getString(columnName)));
			}
		} catch (Exception e) {
		System.out.println("EXCEPTION CAUSED BY: RankRangeConfigManager [getListFromCParam] :"+e.getMessage().toUpperCase());
		}
		return list;
		}
	
  }
