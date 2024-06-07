/**
 * @ AUTHOR Amit Dangi
 */
package com.sits.affiliation.approval.review_application;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sits.common.AesUtil;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;

/**
 * Servlet implementation class ReviewApplicationService
 */

@WebServlet("/ReviewApplicationService")
public class ReviewApplicationService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger("exceptionlog ReviewApplicationService");
	AesUtil aesUtil = new AesUtil(128,10);
	
    
    public ReviewApplicationService() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("Served at ReviewApplicationService: ").append(request.getContextPath());
		}		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		switch (General.checknull(request.getParameter("fstatus"))){
		case "S":
			saverequest(request, response);
			break;
		case "GETDETAILS":
			getApplicationDetails(request, response);
			break;
		default: System.out.println("Invalid grade ReviewApplicationService");
		}
	}
	
	/*Void Method to get the getApplicationDetails saved details*/
	public synchronized void getApplicationDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		// TODO Auto-generated method stub
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject finalResult= new JSONObject();
			ReviewApplicationModel raModel= new  ReviewApplicationModel();
			raModel.setSession_id(General.checknull(request.getParameter("session_id")));
			raModel.setInst_name(General.checknull(request.getParameter("inst_name")));
			raModel.setMobile_no(General.checknull(request.getParameter("mobile_no")));
			raModel.setEmail_id(General.checknull(request.getParameter("email_id")));
			raModel.setXTODATE(General.checknull(request.getParameter("XTODATE")));
			raModel.setXFROMDATE(General.checknull(request.getParameter("XFROMDATE")));
			
			finalResult = ReviewApplicationManager.getApplicationDetails(raModel);

			response.setContentType("application/json");
		    response.setHeader("Cache-Control", "no-store");
		    out.print(finalResult);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: ReviewApplicationService [getApplicationDetails]"+" "+e.getMessage().toUpperCase());
			log.fatal(Logging.logException("ReviewApplicationService [getApplicationDetails]", e.toString()));
		}
	}
	
	public synchronized void saverequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject finalResult= new JSONObject();
			//String errMsg="", flg="N";
			String uniqueKey = General.checknull((String)request.getSession().getAttribute("AESUniqueKey"));
			String encData	= General.checknull(request.getParameter("encData"));
			String decodeData=new String(java.util.Base64.getDecoder().decode(encData));
			String decData=AesUtil.parseAes(encData, aesUtil, uniqueKey);
			JSONObject obj 	= (JSONObject) new JSONParser().parse(decData);
			String user_id = General.check_null((String) request.getSession().getAttribute("user_id"));
		    String machine = General.checknull((String) request.getSession().getAttribute("ip"));
			String applicationId = General.checknull((String) obj.get("selectedApplication"));
			String panel_id = General.checknull((String) obj.get("panel_id"));
			String remarks = General.checknull((String) obj.get("remarks"));
			

			ReviewApplicationModel model = new ReviewApplicationModel();
        	//model.setfAgency(FundAgency);
        	model.setApplicationId(applicationId);
        	model.setPanel_id(panel_id);
        	model.setRemarks(remarks);
        	
			String save = "";
			save = ReviewApplicationManager.save(model, user_id, machine);
        	if(save.equals("1")){
        		finalResult.put("flag", "Y");
    			finalResult.put("status",  "Assign for Inspection Successfully");
        		}else{
        			finalResult.put("flag", "N");
            		finalResult.put("status", ApplicationConstants.FAIL);
        		}
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "no-store");
        	
        	String jString = aesUtil.encrypt(finalResult.toString(), decodeData.split("::")[0], uniqueKey,decodeData.split("::")[1]);
			out.println(new Gson().toJson(jString));        	
        	
        	//out.print(finalResult);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: ReviewApplicationService [saverequest]"+" "+e.getMessage().toUpperCase());
			log.fatal(Logging.logException("ReviewApplicationService [saverequest]", e.toString()));
		}
	}
	
	/*@SuppressWarnings("unchecked")
	public synchronized void TargetAchievementUploadrequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject finalResult= new JSONObject();
			String jString="";
			
			String user_id = General.checknull((String) request.getSession().getAttribute("user_id"));
			String machine = General.checknull((String) request.getSession().getAttribute("s_ip"));
			System.out.println("user_id-"+user_id);
			String savemode =General.checknull(request.getParameter("fstatus"));
			

			String uniqueKey = General.checknull((String)request.getSession().getAttribute("AESUniqueKey"));
			String encData	= General.checknull(request.getHeader("encData"));
			String decodeData=new String(java.util.Base64.getDecoder().decode(encData));
			String decData=AesUtil.parseAes(encData, aesUtil, uniqueKey);
			JSONObject obj 	= (JSONObject) new JSONParser().parse(decData);
			
			ReviewApplicationModel TAmodel = new ReviewApplicationModel();
			
			Type type = new TypeToken<ReviewApplicationModel>() {}.getType(); 
			TAmodel = new Gson().fromJson(obj.toString(), type);
			System.out.println("TAmodel||"+TAmodel.getResPrpsId());
			
			//Getting the resPrpsId for Target/Achivements certificate
			String resPrpsId =General.checknull(TAmodel.getResPrpsId());
			System.out.println("savemode-"+savemode+" resPrpsId-"+resPrpsId);
		    
		    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			List<FileItem> items=null;
            if (isMultipart) { 
            	 FileItemFactory factory = new DiskFileItemFactory();
	             ServletFileUpload upload = new ServletFileUpload(factory);
	              items = upload.parseRequest(request);
	        }
            
            finalResult=ReviewApplicationManager.TargetAchievementUpload(TAmodel,machine, user_id,items, resPrpsId);
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "no-store");
        	jString = aesUtil.encrypt(finalResult.toString(), decodeData.split("::")[0], uniqueKey,
					decodeData.split("::")[1]);
			out.println(new Gson().toJson(jString));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: ReviewApplicationService [TargetAchievementUploadrequest]"+" "+e.getMessage().toUpperCase());
			log.fatal(Logging.logException("ReviewApplicationService [TargetAchievementUploadrequest]", e.toString()));
		}
	}*/
	
}