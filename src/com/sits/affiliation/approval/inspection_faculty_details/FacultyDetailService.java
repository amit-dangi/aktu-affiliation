package com.sits.affiliation.approval.inspection_faculty_details;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.sits.common.AesUtil;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;

/**
 * Servlet implementation class FacultyDetailService
 */
@WebServlet("/InspectionFacultyDetailService")
public class FacultyDetailService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger("exceptionlog");
	AesUtil aesUtil = new AesUtil(128, 10);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FacultyDetailService() {
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
			System.out.println("Exception in FacultyDetailService[Get]" + e.getMessage());
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
			System.out.println("Exception in FacultyDetailService[Post]" + e.getMessage());
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String action = General.checknull(request.getParameter("fstatus"));
		System.out.println("action parm||"+action);
		String fstatus = General.checknull(request.getHeader("fstatus"));
		 System.out.println("action header||"+action);
		 if (action.equals(""))
			 action = fstatus; 
		
		switch (action) {
		case "1":
			saveFacDetail(request, response);
			break;
		case "PD":
			processDelete(request, response);
			break;
		case "Delete":
			deleteattachfile(request, response);
			break;
		default:
			System.out.println("Invalid grade in FacultyDetailService");
		}
	}

	public synchronized void deleteattachfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
	    try {
	      PrintWriter out = response.getWriter();
	      response.setContentType("application/json");
	      JSONObject finalResult = new JSONObject();
	      String fileid = General.checknull(request.getParameter("fileid"));
	      String fliename = General.checknull(request.getParameter("fliename"));
	      String user_id = General.checknull((String)request.getSession().getAttribute("s_user_id"));
	      String delete = FacultyDetailManager.deletattchdata(fileid, fliename, user_id);
	      if (delete.equals("")) {
	        finalResult.put("status", Integer.valueOf(0));
	        finalResult.put("msg", "Unable to Process Request Kindly Contact Your Admin");
	      } else {
	        finalResult.put("status", Integer.valueOf(1));
	        finalResult.put("msg", "Record Deleted Successfully ");
	      } 
	      response.setContentType("application/json");
	      response.setHeader("Cache-Control", "no-store");
	      out.print(finalResult);
	    } catch (Exception e) {
	      System.out.println("EXCEPTION CAUSED BY: FacultyDetailService[deleteattachfile] " + e.getMessage().toUpperCase());
	      log.fatal(Logging.logException("FacultyDetailService[deleteattachfile]", e.toString()));
	    } 
	  }
	
	protected void saveFacDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			String jString = "";
			boolean flag = true;
			JSONObject returnJSONObj = new JSONObject();

			String ip = General.checknull((String) request.getSession().getAttribute("s_ip"));
			String user_id = General.checknull(request.getHeader("Inst_Id"));
			String login_id = General.checknull((String) request.getSession().getAttribute("s_login_id"));
			
			response.setContentType("application/json");
			String uniqueKey = General.checknull((String) request.getSession().getAttribute("AESUniqueKey"));
			String encData = General.checknull(request.getHeader("encData"));
			String decodeData = new String(java.util.Base64.getDecoder().decode(encData));
			String decData=AesUtil.parseAes(encData, aesUtil, uniqueKey);
			JSONObject dataObj = (JSONObject) new JSONParser().parse(decData);
			
			String list	= General.checknull(dataObj.get("list").toString());
			JSONArray aflist = ((JSONArray) JSONValue.parse(list));
			System.out.println("aflist||"+aflist.toJSONString());
			
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		      List<FileItem> items = null;
		      if (isMultipart) {
		        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		        ServletFileUpload upload = new ServletFileUpload((FileItemFactory)diskFileItemFactory);
		        items = upload.parseRequest(request);
		      } 
			if(flag){
				JSONObject save = new JSONObject();
				save = FacultyDetailManager.saveFacDetail(aflist, ip, user_id, login_id,items);
				returnJSONObj = save;
			}else{
				returnJSONObj.put("status", 0);
				returnJSONObj.put("msg", ApplicationConstants.FAIL);
			}
			jString = aesUtil.encrypt(returnJSONObj.toString(), decodeData.split("::")[0], uniqueKey, decodeData.split("::")[1]);
			out.println(new Gson().toJson(jString.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: FacultyDetailService[saveFacDetail]" + " " + e.getMessage().toUpperCase());
			log.fatal(Logging.logException("FacultyDetailService[saveFacDetail]", e.toString()));
		}
	}

	protected void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String errMsg="";
		try{
			JSONObject obj            = new JSONObject();
			
			String id		= General.checknull(request.getParameter("id"));
			String machine  = General.checknull(request.getRemoteHost());
			String user_id  = General.checknull((String)request.getSession().getAttribute("s_user_id"));
			String file		= General.checknull(request.getParameter("file"));

			errMsg          =FacultyDetailManager.delete(id,file,user_id);
			
			obj.put("fstatus", "D");
			obj.put("errMsg", errMsg);
			out.print(obj);
			Logger.getLogger("usglog").debug( Logging.onSucess(user_id, machine,"FacultyDetailService[Delete_Service]", ApplicationConstants.DELETED));
		}catch(Exception e){
			Logger.getLogger("usglog").fatal(Logging.logException("FacultyDetailService", e.toString()));
		}
	}
}