package com.sits.affiliation.approval.inspection_upload_doc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.sits.common.AesUtil;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;

/**
 * Servlet implementation class UploadDocService
 */
@WebServlet("/InspectionUploadDocService")
public class UploadDocService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger("exceptionlog");
	AesUtil aesUtil = new AesUtil(128, 10);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadDocService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (Exception e) {
			System.out.println("Exception in UploadDocService[Get]" + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (Exception e) {
			System.out.println("Exception in UploadDocService[Post]" + e.getMessage());
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String action = General.checknull(request.getParameter("fstatus"));
		switch (action) {
		case "1":
			uploadDoc(request, response, "1");
			break;
		case "D":
			deleteattachfile(request, response);
			break;
		default:
			System.out.println("Invalid grade in UploadDocService");
		}
	}

	protected void uploadDoc(HttpServletRequest request, HttpServletResponse response, String typ) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String errMsg="", flg="Y";
			JSONObject returnJSONObj = new JSONObject();

			String ip = General.checknull((String) request.getSession().getAttribute("s_ip"));
			String user_id = General.checknull((String) request.getParameter("Inst_Id"));
			System.out.println("user_id||"+user_id);
			String login_id = General.checknull((String) request.getSession().getAttribute("user_id"));
			System.out.println("login_id||"+login_id);
			JSONObject dataObj = (JSONObject) new JSONParser().parse(General.checknull(request.getHeader("objjson")));
			JSONArray doobj = (JSONArray)JSONValue.parse(General.checknull(dataObj.get("docArray").toString()));
			//System.out.println(doobj);
			
			if(user_id.equals("") || login_id.equals("")) {
				errMsg="Session Expire";
    			flg="N";
			}
			
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			List<FileItem> items = null;
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				items = upload.parseRequest(request);
			}
			
			if (errMsg.trim().equals("") ) {
				JSONObject save = new JSONObject();
				save = UploadDocManager.upload(items, ip, user_id, login_id, doobj);
				returnJSONObj = save;
			}else{
				returnJSONObj.put("status", 0);
				returnJSONObj.put("msg", errMsg);
			}
			out.println(returnJSONObj);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: UploadDocService[uploadDoc]" + " " + e.getMessage().toUpperCase());
			log.fatal(Logging.logException("UploadDocService[uploadDoc]", e.toString()));
		}
	}
	
	public synchronized void deleteattachfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject finalResult= new JSONObject();
			String id		= General.checknull(request.getParameter("attid"));
			String filename	= General.checknull(request.getParameter("filename"));
			String mastid	= General.checknull(request.getParameter("mastid"));
			String mid		= General.checknull(request.getParameter("mid"));
			String user_id = General.checknull(request.getParameter("Inst_Id"));
			
        	String delete=UploadDocManager.deletattchdata(id, filename, mastid, mid, user_id);
	        if(delete.equals("")){
	        	finalResult.put("status", 0);
	        	finalResult.put("msg", ApplicationConstants.FAIL);
	        }else{
	        	finalResult.put("status", 1);
	        	finalResult.put("msg", ApplicationConstants.DELETED);
	        }
	        response.setContentType("application/json");
		    response.setHeader("Cache-Control", "no-store");
		    out.print(finalResult);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: UploadDocService[deleteattachfile]" + " " + e.getMessage().toUpperCase());
			log.fatal(Logging.logException("UploadDocService[deleteattachfile]", e.toString()));
		}
	}	 
	
	
}