package com.sits.affiliation.approval.inspection_upload_doc;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sits.conn.DBConnection;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;
import com.sits.general.ReadProps;

public class UploadDocManager {
	static Logger l = Logger.getLogger("exceptionlog");
	
	public static JSONObject upload(List<FileItem> fs, String ip, String user_id, String login_id, JSONArray doobj){
		l = Logger.getLogger("exceptionlog");
		Connection conn = null;
		int chk=0;
		JSONObject jsonObj = new JSONObject();
		PreparedStatement psmt = null;
		String Qry = "";
		int count = 0, k=0, id=0;
		try {
			conn=DBConnection.getConnection();
			conn.setAutoCommit(false);

			if(doobj.size()>0) {
				for (int i = 0; i < doobj.size(); i++) {
					++k;
					JSONObject obj = (JSONObject) doobj.get(i);
					
					Qry = "INSERT INTO af_upl_doc_mast (AFF_UD_ID, DOC_NAME, CREATED_BY, CREATED_DATE, CREATED_MACHINE) VALUES (?,?,?,now(),?)";
					psmt = conn.prepareStatement(Qry, psmt.RETURN_GENERATED_KEYS);
					psmt.setString(1, General.checknull(user_id));
					psmt.setString(2, General.checknull(obj.get("id").toString()));
					psmt.setString(3, General.checknull(login_id));  
					psmt.setString(4, General.checknull(ip));
					count = psmt.executeUpdate();
					ResultSet rs = psmt.getGeneratedKeys();
					while(rs.next()) {
						id=(rs.getInt(1));
					}
					
					if (count > 0) {
						if(fs !=null){
							Iterator<FileItem> iter = fs.iterator();
							while (iter.hasNext()) {
								FileItem fileItem = iter.next();
								if (fileItem.getFieldName().equals("uplDoc_"+k)){
									saveDoc(ip, "af_upl_doc_mast", General.checknull(String.valueOf(id)), user_id, conn, fileItem, "", General.checknull(fileItem.getFieldName()));
									++chk;
								}
							}
						}
					}
				}
			}

			/*FileItem fileItem = null;
			if(fs !=null){
				Iterator<FileItem> iter = fs.iterator(); 
				while (iter.hasNext()) {
					fileItem = iter.next();
					saveDoc(ip, "upload_document", user_id, login_id, conn, fileItem, "", fileItem.getFieldName());
					++chk;
				}
			}*/
			if(chk>0){
				jsonObj.put("msg", "Document Uploaded Successfully");
				jsonObj.put("status", "1");
				conn.commit();
			} else {
				jsonObj.put("status", "0");
				jsonObj.put("msg", ApplicationConstants.FAIL);
				conn.rollback();
			}
		} catch (Exception e) {
			if (e.getMessage().contains("unique")) {
				jsonObj.put("status", "0");
				jsonObj.put("msg", ApplicationConstants.UNIQUE_CONSTRAINT);
			} else {
				jsonObj.put("status", "0");
				jsonObj.put("msg", ApplicationConstants.FAIL);
			}
			System.out.println("Exception in UploadDocManager[upload]" + " " + e.getMessage());
			l.fatal(Logging.logException("UploadDocManager[upload]", e.toString()));
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return jsonObj;
	}

	public static boolean saveDoc(String machine, String tablename, String mastid, String userid, Connection conn, FileItem fileItem, String attid, String desc){
	  	java.io.File file;
	  	try{
	  		
	  		String attachid=attid;
	  		String f_name="", l_name="", file_name="";
	  		file_name=fileItem.getName();
	  		
	  		if(file_name.lastIndexOf(".") != -1 && file_name.lastIndexOf(".") != 0){
				f_name=General.getFileName(file_name);
				l_name=General.getFileExtension(file_name);
		  		if(f_name.contains("&")){
		  			f_name=f_name.replaceAll("&", "And");
		  		}
		  		f_name=f_name.replaceAll("[^a-zA-Z0-9]", " ");
		  		file_name=f_name+"."+l_name;
			}
	  		  
	  		if(attachid==null || attachid.trim().equals("")){ 
	  			attachid=filedetailsave(machine, file_name.replaceAll("\\s", ""), mastid, userid, fileItem.getSize(), tablename, conn, desc);
	  		}
	  		
	  		String directoryName=ReadProps.getkeyValue("document.path", "sitsResource")+"AKTU_AFFILIATION_PORTAL/UPLOAD_DOCUMENT/"+userid;
	  		
	  		File directory = new File(directoryName);
	  		if (!directory.isDirectory()){
     			directory.mkdirs();
     		}
    		file = new File(directoryName+"/"+attachid+"_"+file_name.replaceAll("\\s", ""));
    		fileItem.write(file);
	  	}catch(Exception e){
	  		System.out.println("Error in NocDetailManager[saveDoc] : "+e.getMessage());
	  		l.fatal(Logging.logException("NocDetailManager[saveDoc]", e.toString()));
	  		return false;
	  	}
		return true;
	}	
	
	public static String filedetailsave(String machine, String name,String mastid,String userid, long size,String tablename,Connection conn,String desc){ 
		PreparedStatement psmt = null;
		ResultSet rst = null;
		String qry = "";
		String attachid="";
		int count=0;
		try{
			String Qry = "SELECT IFNULL(MAX(file_attachment_id)+1,1) FROM file_attachment"; 
			psmt = conn.prepareStatement(Qry);
			rst= psmt.executeQuery();
			if(rst.next())
				attachid=General.checknull(rst.getString(1));
			
			qry="INSERT INTO file_attachment(file_attachment_id,file_name,file_type,table_name,"
					+ "reference_id,CREATED_BY,CREATED,MACHINE) VALUES (?, ?,?,?, ?,?,now(),?)";
			psmt = conn.prepareStatement(qry);
			psmt.setString(1, attachid);
			psmt.setString(2, name);
			psmt.setString(3, desc);
			psmt.setString(4, tablename);
			psmt.setString(5, mastid);
			psmt.setString(6, userid);
			psmt.setString(7, machine);
			count= psmt.executeUpdate();
			if(count==0)
				attachid="";
		}
		catch(Exception e)
		{
			attachid="";
			System.out.println("EXCEPTION IS CAUSED BY: NocDetailManager" + "[filedetailsave]" + " " + e.getMessage().trim().toUpperCase());
			l.fatal(Logging.logException("NocDetailManager [filedetailsave]", e.toString()));
			return attachid;
		}
		return attachid;
	}

	public static JSONObject getUplData(String id){ 
		PreparedStatement psmt = null;
		ResultSet rst = null;
		Connection conn = null;
		String qry = "";
		JSONObject data = new JSONObject();
		try{
			conn=DBConnection.getConnection();
			qry="select file_type, concat(file_attachment_id,'_',file_name) file_name from file_attachment where reference_id=? and table_name='upload_document'";
			
			//select '' doc_id, '' doc_name, '' is_comp, file_name, file_attachment_id, SUBSTRING_INDEX(SUBSTRING_INDEX(file_type, '_', 1), '_', -1) AS a1, SUBSTRING_INDEX(SUBSTRING_INDEX(file_type, '_', 2), '_', -1) AS a2, SUBSTRING_INDEX(SUBSTRING_INDEX(file_type, '_', 3), '_', -1) AS a3 from file_attachment where reference_id='CR00002' and table_name='upload_document') ch;

			
			psmt = conn.prepareStatement(qry);
			psmt.setString(1, id);
			rst= psmt.executeQuery();
			while(rst.next()){
				data.put(General.checknull(rst.getString("file_type")), General.checknull(rst.getString("file_name")));
			}
		}catch(Exception e){
			System.out.println("EXCEPTION IS CAUSED BY: UploadDocManager [getUplData]" + " " + e.getMessage().trim().toUpperCase());
			l.fatal(Logging.logException("UploadDocManager [getUplData]", e.toString()));
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

	public static ArrayList<UploadDocModel> getdocumentlist(String id, String req_id) {
		l = Logger.getLogger("exceptionlog");
		String cSql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<UploadDocModel> concatenated_list = new ArrayList<UploadDocModel>();
		ArrayList<UploadDocModel> al = new ArrayList<UploadDocModel>();
		try {
			conn = DBConnection.getConnection();
			cSql = "select d.doc_id, d.doc_name, d.is_comp, d.is_additional_doc, "
					+ "(select concat(file_attachment_id,'~',file_name,'~',udm.UD_ID) from file_attachment fa, af_upl_doc_mast udm, af_doc_mast dm "
					+ "where table_name='af_upl_doc_mast' and fa.reference_id=udm.UD_ID and udm.DOC_NAME=dm.doc_id and dm.doc_id=d.doc_id and udm.AFF_UD_ID=? "
					+ "order by file_attachment_id desc limit 1) AS file from af_doc_mast d where d.is_active='Y' and req_name=? "
					+ "order by CONVERT(order_id, UNSIGNED INTEGER) ";
			pstmt = conn.prepareStatement(cSql);
			pstmt.setString(1, id);
			pstmt.setString(2, req_id);
			System.out.println(req_id+" pstmabct:::::::: "+pstmt);
			rst = pstmt.executeQuery();
			while (rst.next()) {
				UploadDocModel cme = new UploadDocModel();
				cme.setDoc_id(General.checknull(rst.getString("doc_id")));
				cme.setDoc_name(General.checknull(rst.getString("doc_name")));
				cme.setDoc_compul(General.checknull(rst.getString("is_comp")));
				String part[] = General.checknull(rst.getString("file")).split("~");
				if (part.length > 1) {
					cme.setAttid(part[0]);
					cme.setFilename(part[1]);
					cme.setMid(part[2]);
				} else {
					cme.setAttid("");
					cme.setFilename("");
					cme.setMid("");
				}
				if(General.checknull(rst.getString("is_additional_doc")).equals("Y")){
					//cme.setType("AU");
					cme.setType("DU");
				}else{
					cme.setType("DU");	
				}
				al.add(cme);
			}
			
			concatenated_list.addAll(al);
		} catch (Exception e) {
			System.out.println("Error in UploadDocManager[getdocumentlist] : " + e.getMessage());
			l.fatal(Logging.logException("UploadDocManager[getdocumentlist]", e.getMessage().toString()));
		} finally {
			try {
				if (pstmt != null)
					pstmt = null;
				if (rst != null)
					rst = null;
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return concatenated_list;
	}

	public static String deletattchdata(String id, String name, String mastid, String mid, String user_id) {
		l = Logger.getLogger("exceptionlog");
		String cSql = "", msg = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			
			cSql = "delete from af_upl_doc_mast where AFF_UD_ID=? and UD_ID=? ";
			pstmt = conn.prepareStatement(cSql);
			pstmt.setString(1, user_id.trim());
			pstmt.setString(2, mid.trim());
			count = pstmt.executeUpdate();
			
			if (count > 0) {
				count=0; cSql=""; pstmt=null;
				
				cSql = "delete from file_attachment where file_attachment_id=? ";
				pstmt = conn.prepareStatement(cSql);
				pstmt.setString(1, id.trim());
				count = pstmt.executeUpdate();
				
				if (count > 0) {
					String directoryName=ReadProps.getkeyValue("document.path", "sitsResource")+"AKTU_AFFILIATION_PORTAL/UPLOAD_DOCUMENT/"+mastid+"/"+name;
					File file = new File(directoryName);
					file.delete();
					msg = ApplicationConstants.DELETED;
					conn.commit();
				}	
			}
		} catch (Exception e) {
			System.out.println("Error in UploadDocManager[deletattchdata] : " + e.getMessage());
			l.fatal(Logging.logException("UploadDocManager[deletattchdata]", e.getMessage().toString()));
		} finally {
			try {
				if (pstmt != null)
					pstmt = null;
				if (rst != null)
					rst = null;
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
	
}