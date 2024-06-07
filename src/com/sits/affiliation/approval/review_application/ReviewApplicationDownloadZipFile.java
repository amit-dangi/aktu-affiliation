/**
 * @ AUTHOR Ashwani Kumar 
 * Date 27-May-2024
 */

package com.sits.affiliation.approval.review_application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import com.sits.common.ZipUtils;
import com.sits.general.ReadProps;
import com.sits.general.General;
import com.sits.affiliation.approval.review_application.ReviewApplicationManager;
/**
 * Servlet implementation class ReviewApplicationDownloadZipFile
 */
@WebServlet("/ReviewApplicationDownloadZipFile")
//@WebServlet(name = "ReviewApplicationDownloadZipFile", urlPatterns = {"/ReviewApplicationDownloadZipFile"})
public class ReviewApplicationDownloadZipFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewApplicationDownloadZipFile() {
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
		returnurl=ReviewApplicationManager.downloadZipFile(can_id,reg_no,clg_code);
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
	private void copyDirectory(String folderName, String primaryKey,String reg_no) throws IOException{
		String sourceDir="",desDir="",path="";
		path = ReadProps.getkeyValue("document.path", "sitsResource"); 
		if(folderName.equals("REGISTRATION_DOCUMENT")){
			sourceDir =path+"AKTU_AFFILIATION_PORTAL/"+folderName+"/"+reg_no;
		}else{
			sourceDir =path+"AKTU_AFFILIATION_PORTAL/"+folderName+"/"+primaryKey;
		}
		desDir =path+"AFFILIATION_TEMP/"+reg_no+"/"+folderName;
		File sourceFile = new File(sourceDir);
		File desFile = new File(desDir);
		
		try {
			if (!desFile.isDirectory()){ 
				desFile.mkdirs();
			}
			FileUtils.copyDirectory(sourceFile, desFile);
		} catch (Exception e) {
		    e.printStackTrace();
		}finally {
			sourceFile.delete();
			desFile.delete();
		}
	}	
	
}
