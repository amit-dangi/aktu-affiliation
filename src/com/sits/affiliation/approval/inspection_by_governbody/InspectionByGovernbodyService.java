/**
 * @ AUTHOR Amit Dangi
 */

package com.sits.affiliation.approval.inspection_by_governbody;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import com.sits.common.AesUtil;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;

/**
 * Servlet implementation class InspectionByGovernbodyService
 */
@WebServlet("/InspectionByGovernbodyService")
public class InspectionByGovernbodyService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger("exceptionlog ReviewApplicationService");
	AesUtil aesUtil = new AesUtil(128,10);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InspectionByGovernbodyService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		default: System.out.println("Invalid grade InspectionByGovernbodyService");
		}
	}
	
	public synchronized void getApplicationDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		// TODO Auto-generated method stub
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject finalResult= new JSONObject();
			String employee_id 	= General.checknull((String)request.getSession().getAttribute("employee_id"));
			InspectionByGovernbodyModel raModel= new  InspectionByGovernbodyModel();
			raModel.setSession_id(General.checknull(request.getParameter("session_id")));
			raModel.setInst_name(General.checknull(request.getParameter("inst_name")));
			raModel.setMobile_no(General.checknull(request.getParameter("mobile_no")));
			raModel.setEmail_id(General.checknull(request.getParameter("email_id")));
			raModel.setXTODATE(General.checknull(request.getParameter("XTODATE")));
			raModel.setXFROMDATE(General.checknull(request.getParameter("XFROMDATE")));
			raModel.setPanel_member(employee_id);
		 
			finalResult = InspectionByGovernbodyManager.getApplicationDetails(raModel);
			response.setContentType("application/json");
		    response.setHeader("Cache-Control", "no-store");
		    out.print(finalResult);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: InspectionByGovernbodyService [getApplicationDetails]"+" "+e.getMessage().toUpperCase());
			log.fatal(Logging.logException("InspectionByGovernbodyService [getApplicationDetails]", e.toString()));
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
			System.out.println("obj||"+obj);
			
			String user_id = General.check_null((String) request.getSession().getAttribute("user_id"));
		    String machine = General.checknull((String) request.getSession().getAttribute("ip"));
			String applicationId = General.checknull((String) obj.get("selectedApplication"));
			String Recom = General.checknull((String) obj.get("Recom"));
			String remarks = General.checknull((String) obj.get("remarks"));
			String actiontype= General.checknull((String) obj.get("actiontype"));
			String employee_id =General.checknull((String) request.getSession().getAttribute("employee_id"));

			InspectionByGovernbodyModel model = new InspectionByGovernbodyModel();
        	model.setApplicationId(applicationId);
        	model.setRecommendation(Recom);
        	model.setRemarks(remarks);
        	model.setActiontype(actiontype);
        	model.setInspection_type(General.checknull((String) obj.get("inspection_type")));
        	model.setInspection_id(General.checknull((String) obj.get("inspection_id")));
        	model.setInspection_by(employee_id);
        	model.setSession_id(General.checknull((String) obj.get("session_id")));
        	model.setIp(machine);
        	
			String save = "";
			save = InspectionByGovernbodyManager.save(model, user_id, machine);
        	if(save.equals("1")){
        		finalResult.put("flag", "Y");
    			finalResult.put("status",  "Government Body Status Saved Successfully");
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
			System.out.println("EXCEPTION CAUSED BY: InspectionByGovernbodyService [saverequest]"+" "+e.getMessage().toUpperCase());
			log.fatal(Logging.logException("InspectionByGovernbodyService [saverequest]", e.toString()));
		}
	}
	
}
