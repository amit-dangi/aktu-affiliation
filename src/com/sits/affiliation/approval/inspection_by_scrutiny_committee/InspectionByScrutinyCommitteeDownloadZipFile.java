/**
 * @ AUTHOR Ashwani Kumar 
 * Date 31-May-2024
 */

package com.sits.affiliation.approval.inspection_by_scrutiny_committee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.sits.common.ZipUtils;
import com.sits.conn.DBConnection;
import com.sits.general.General;
import com.sits.general.Logging;
import com.sits.general.ReadProps;

/**
 * Servlet implementation class InspectionByScrutinyCommitteeDownloadZipFile
 */
@WebServlet("/InspectionByScrutinyCommitteeDownloadZipFile")
public class InspectionByScrutinyCommitteeDownloadZipFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger l = Logger.getLogger("exceptionlog research activity InspectionByScrutinyCommitteeDownloadZipFile");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InspectionByScrutinyCommitteeDownloadZipFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = General.checknull(request.getParameter("fstatus"));
    	if(action.equals("dwnZipFrmDir")){
    		downloadfile(request, response);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String action = General.checknull(request.getParameter("fstatus"));
    	if(action.equals("dwnZipFrmDir")){
    		downloadfile(request, response);
    	}
	}
	public void downloadfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String can_id=General.checknull(request.getParameter("filename"));
		String reg_no=General.checknull(request.getParameter("af_reg_no"));
		String	clg_code =General.checknull(request.getParameter("clg_code"));
		ZipUtils zu=new ZipUtils();
		String returnurl="";
		copyDirectory("FACULTY_PHOTO",can_id,reg_no);
		copyDirectory("INTAKE_DETAILS",can_id,reg_no);
		copyDirectory("NOC_DETAIL",can_id,reg_no);
		copyDirectory("QUESTIONNAIRE_DETAIL",can_id,reg_no);
		copyDirectory("REGISTRATION_DOCUMENT",can_id,reg_no);
		copyDirectory("SOC_DETAIL",can_id,reg_no);
		copyDirectory("UPLOAD_DOCUMENT",can_id,reg_no);
		returnurl=InspectionByScrutinyCommitteeManager.downloadZipFile(can_id,reg_no,clg_code);
		File downloadFile = new File(returnurl);
		FileInputStream inStream = new FileInputStream(downloadFile);
		ServletContext context = getServletContext();
		String mimeType = context.getMimeType(returnurl);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());
			String headerKey = "Content-Disposition";
			String headerValue = String.format("inline; filename=\"%s\"", downloadFile.getName());
			response.setHeader(headerKey, headerValue);
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
	inStream.close();
	outStream.close();
	String 	path = ReadProps.getkeyValue("document.path", "sitsResource"); 
	String  desDir =path+"AFFILIATION_TEMP/"+reg_no;
	zu.deleteFolder(new File(returnurl));
	FileUtils.deleteDirectory(new File(desDir));
	response.flushBuffer();

}

private void copyDirectory(String folderName, String primaryKey,String reg_no ) throws IOException{
	String sql = "";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String file_name[] = null;
	try {
		conn = DBConnection.getConnection();
		String directory_path = ReadProps.getkeyValue("document.path", "sitsResource");
		if(folderName.equals("FACULTY_PHOTO")){
			sql = "select distinct concat(reference_id,'_', file_name) as file_name  from af_faculty_detail  af, file_attachment f  "
			   + " where af.FD_ID=f.reference_id and af.AF_FD_ID='"+primaryKey+"' and f.table_name ='af_faculty_detail' and f.CREATED_BY='"+primaryKey+"' and  concat(file_name,(CREATED)) "
			   + "in (select concat(file_name,max(CREATED)) from file_attachment where table_name ='af_faculty_detail' and CREATED_BY='"+primaryKey+"' group by file_name) ";
		
		 }if(folderName.equals("INTAKE_DETAILS")){
			 sql="select distinct concat(file_attachment_id,'_',file_name) as file_name  from file_attachment"
			  + " where  table_name='af_req_intake_detail'   and reference_id ='"+primaryKey+"'";
			
		 }if(folderName.equals("NOC_DETAIL")){
			 sql="select distinct concat(file_attachment_id,'_',file_name) as file_name from file_attachment "
			 		+ "where table_name ='af_noc_detail' and reference_id='"+primaryKey+"'";
			
		 }if(folderName.equals("QUESTIONNAIRE_DETAIL")){
			sql="select distinct concat(file_attachment_id,'_', file_name) as file_name  from af_questionnaire_details  qr,"
				+ " file_attachment f  where qr.id=f.reference_id and AF_QUES_ID='"+primaryKey+"' and f.table_name ='AF_QUESTIONNAIRE_DETAILS'"
				+ " and f.CREATED_BY='"+primaryKey+"' and  concat(file_name,(CREATED)) in (select concat(file_name,max(CREATED)) "
				+ " from file_attachment where table_name ='AF_QUESTIONNAIRE_DETAILS' and CREATED_BY='"+primaryKey+"' group by file_name)";
		
		 }if(folderName.equals("REGISTRATION_DOCUMENT")){
			sql="select  distinct concat(file_attachment_id,'_',file_name) as file_name from file_attachment "
				+ "where table_name= 'af_clg_reg_mast_temp' and  reference_id='"+primaryKey+"' ";
		
		 }if(folderName.equals("SOC_DETAIL")){
			sql="select distinct  concat(file_attachment_id,'_',file_name) as file_name  from AF_SOCIETY_INFO_DETAIL  sd, file_attachment f "
				+ " where sd.SOC_DID=f.reference_id and sd.CREATED_BY='"+primaryKey+"' and f.table_name ='AF_SOCIETY_INFO_DETAIL' and f.CREATED_BY='"+primaryKey+"'";
		 
		 }if(folderName.equals("UPLOAD_DOCUMENT")){
				sql="select distinct concat(file_attachment_id,'_',file_name) as file_name from file_attachment "
					+ " where table_name ='af_upl_doc_mast' and  CREATED_BY='"+primaryKey+"'";
		}
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			do {
				String AF_ID="";
				String reference_id = primaryKey;
				 file_name = rs.getString("file_name").split(",");
				for (int i = 0; i < file_name.length; i++) {
					try {
						File desFile = new File(directory_path+"AFFILIATION_TEMP/"+reg_no+"/"+folderName+"/");
						if (!desFile.isDirectory()){ 
							desFile.mkdirs();
						} if(folderName.equals("REGISTRATION_DOCUMENT")){
							AF_ID=reg_no;
						}else{
							AF_ID=primaryKey;
						}
						Path sourcePath = Paths.get(directory_path+"AKTU_AFFILIATION_PORTAL/"+folderName+ "/"+AF_ID+"/"+file_name[i]);
						Path destinationPath = Paths.get(directory_path+"AFFILIATION_TEMP/"+reg_no+"/"+folderName+"/"+file_name[i]);
						Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						System.err.println("Error copying the file: " + e.getMessage());
					}
				}

			} while (rs.next());
		}
	} catch (Exception e) {
		System.out.println("Error in InspectionByScrutinyCommitteeDownloadZipFile[copyDirectory] : " + e.getMessage());
	} finally{
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			} if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			} if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (final Exception e) {
			l.fatal(Logging.logException("FileName=[InspectionByScrutinyCommitteeDownloadZipFile],MethodName=[copyDirectory()]", e.getMessage().toString()));
		}
	}
}
}
