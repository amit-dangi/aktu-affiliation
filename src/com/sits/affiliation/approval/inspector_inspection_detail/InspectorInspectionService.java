/**
 * @ AUTHOR Amit Dangi
 */
package com.sits.affiliation.approval.inspector_inspection_detail;

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
 * Servlet implementation class InspectorInspectionService
 */

@WebServlet("/InspectorInspectionService")
public class InspectorInspectionService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger("exceptionlog InspectorInspectionService");
	AesUtil aesUtil = new AesUtil(128,10);
	
    
    public InspectorInspectionService() {
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
			response.getWriter().append("Served at InspectorInspectionService: ").append(request.getContextPath());
		}		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		switch (General.checknull(request.getParameter("fstatus"))){
		case "GETDETAILS":
			getApplicationDetails(request, response);
			break;
		case "SAVE":
			saverequest(request, response);
			break;
		case "PD":
			ProgDetail(request, response);
			break;
		case "GET_REOPEN_DETAILS":
			getApplicationDetailsForReopen(request, response);
			break;
		case "SAVE_REOPEN":
			saveReopenrequest(request, response);
			break;
			
		default: System.out.println("Invalid grade InspectorInspectionService");
		}
	}
	
	/*Void Method to get the getApplicationDetails saved details*/
	public synchronized void getApplicationDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		// TODO Auto-generated method stub
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject finalResult= new JSONObject();
			InspectorInspectionModel raModel= new  InspectorInspectionModel();
			raModel.setInspection_by(General.checknull(request.getParameter("inspection_by")));
			raModel.setSession_id(General.checknull(request.getParameter("session_id")));
			raModel.setInst_name(General.checknull(request.getParameter("inst_name")));
			raModel.setMobile_no(General.checknull(request.getParameter("mobile_no")));
			raModel.setEmail_id(General.checknull(request.getParameter("email_id")));
			raModel.setXTODATE(General.checknull(request.getParameter("XTODATE")));
			raModel.setXFROMDATE(General.checknull(request.getParameter("XFROMDATE")));
			raModel.setS_district(General.checknull((String)request.getSession().getAttribute("s_district")));
			raModel.setLoginId(General.checknull((String)request.getSession().getAttribute("login_id")));
			System.out.println("s_district||"+raModel.getS_district());
			finalResult = InspectorInspectionManager.getApplicationDetails(raModel);

			response.setContentType("application/json");
		    response.setHeader("Cache-Control", "no-store");
		    out.print(finalResult);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: InspectorInspectionService [getApplicationDetails]"+" "+e.getMessage().toUpperCase());
			log.fatal(Logging.logException("InspectorInspectionService [getApplicationDetails]", e.toString()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void saverequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		try{
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject finalResult= new JSONObject();
			String errMsg="", flg="N";
			
			String uniqueKey = General.checknull((String)request.getSession().getAttribute("AESUniqueKey"));
			String encData	= General.checknull(request.getHeader("jsonobj"));
			JSONObject obj 	= (JSONObject) new JSONParser().parse(encData);
			
			InspectorInspectionModel model = new InspectorInspectionModel();
			
			Type type = new TypeToken<InspectorInspectionModel>() {}.getType(); 
			model = new Gson().fromJson(obj.toString(), type);
			String user_id = General.checknull((String) request.getSession().getAttribute("user_id"));
			String machine = General.checknull((String) request.getSession().getAttribute("s_ip"));
			String employee_id =General.checknull((String) request.getSession().getAttribute("employee_id"));
		    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			List<FileItem> items=null;
            if (isMultipart) { 
            	 FileItemFactory factory = new DiskFileItemFactory();
	             ServletFileUpload upload = new ServletFileUpload(factory);
	              items = upload.parseRequest(request);
	        }
            model.setCreatedBy(user_id);
            model.setIp(machine);
            model.setInspection_by(employee_id);
            model.setComputerDetails((JSONArray) obj.get("computerDetails"));
            model.setFacilityDetails((JSONArray) obj.get("facilityDetails"));
            model.setAdministrativeDetails((JSONArray) obj.get("administrativeDetails"));
            model.setAmenitiesDetails((JSONArray) obj.get("amenitiesDetails"));
            model.setInfraDetails((JSONArray) obj.get("InfraDetails"));
            model.setFacultyDetails((JSONArray) obj.get("facultyDetails"));
            model.setQuestionnairesDetails((JSONArray) obj.get("questionnaireDetails"));
            model.setMembersDetails((JSONArray) obj.get("membersDetails"));
            
            finalResult = InspectorInspectionManager.save(model, machine, user_id,items);
          
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "no-store");
        	out.print(finalResult);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: InspectorInspectionManager [saverequest]"+" "+e.getMessage().toUpperCase());
			log.fatal(Logging.logException("InspectorInspectionManager [saverequest]", e.toString()));
		}
	}
	
	// added by ashwani kumar get program details
	
		protected void ProgDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/json;charset=UTF-8");
			try {
				PrintWriter out = response.getWriter();
				JSONArray arr = new JSONArray();
				JSONObject finalResult = new JSONObject();
				//String id = General.checknull((String) request.getSession().getAttribute("s_user_id"));
				String id =(General.checknull(request.getParameter("id")));
				arr = InspectorInspectionManager.getProgDetail(id);
				finalResult.put("ProgDetail", arr);
				response.setContentType("application/json");
				out.print(finalResult);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("EXCEPTION CAUSED BY: InspectorInspectionService[ProgDetail]" + " " + e.getMessage().toUpperCase());
				log.fatal(Logging.logException("InspectorInspectionService[ProgDetail]", e.toString()));
			}
		}
		
		/*Void Method to get the getApplicationDetailsForReopen saved details*/
		public synchronized void getApplicationDetailsForReopen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
			// TODO Auto-generated method stub
			try{
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				JSONObject finalResult= new JSONObject();
				InspectorInspectionModel raModel= new  InspectorInspectionModel();
				raModel.setInspection_by(General.checknull(request.getParameter("inspection_by")));
				raModel.setSession_id(General.checknull(request.getParameter("session_id")));
				raModel.setInst_name(General.checknull(request.getParameter("inst_name")));
				raModel.setMobile_no(General.checknull(request.getParameter("mobile_no")));
				raModel.setEmail_id(General.checknull(request.getParameter("email_id")));
				raModel.setXTODATE(General.checknull(request.getParameter("XTODATE")));
				raModel.setXFROMDATE(General.checknull(request.getParameter("XFROMDATE")));
				raModel.setS_district(General.checknull((String)request.getSession().getAttribute("s_district")));
				
				finalResult = InspectorInspectionManager.getApplicationDetailsForReopen(raModel);
				System.out.println("finalResult||"+finalResult);
				response.setContentType("application/json");
			    response.setHeader("Cache-Control", "no-store");
			    out.print(finalResult);
			    
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("EXCEPTION CAUSED BY: InspectorInspectionService [getApplicationDetailsForReopen]"+" "+e.getMessage().toUpperCase());
				log.fatal(Logging.logException("InspectorInspectionService [getApplicationDetailsForReopen]", e.toString()));
			}
		}
	
		@SuppressWarnings("unchecked")
		public synchronized void saveReopenrequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
			try {
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				JSONObject finalResult = new JSONObject();
				String uniqueKey = General.checknull((String) request.getSession().getAttribute("AESUniqueKey"));
				String encData = General.checknull(request.getParameter("encData"));
				String decodeData = new String(java.util.Base64.getDecoder().decode(encData));
				String decData = AesUtil.parseAes(encData, aesUtil, uniqueKey);
				JSONObject obj = (JSONObject) new JSONParser().parse(decData);

				String user_id = General.check_null((String) request.getSession().getAttribute("user_id"));
				String machine = General.checknull((String) request.getSession().getAttribute("ip"));
				String remarks = General.checknull((String) obj.get("insp_remarks"));
				String status = General.checknull((String) obj.get("insp_status"));

				if (General.checknull(user_id).equals("")) {
					finalResult.put("flag", "N");
					finalResult.put("status", "Session Expire");
				} else if ((General.checknull(status).equals("R") || General.checknull(status).equals("O"))
						&& General.checknull(remarks).equals("")) {
					finalResult.put("flag", "N");
					finalResult.put("status", "Remarks can not be blank");
				} else if (General.checknull(status).equals("")) {
					finalResult.put("flag", "N");
					finalResult.put("status", "Status can not be blank");
				} else {
					InspectorInspectionModel model = new InspectorInspectionModel();
					model.setRemarks(remarks);
					model.setStatus(status);
					model.setInspection_id(General.checknull((String) obj.get("Inst_Id")));
					model.setInspection_by(General.checknull((String) request.getSession().getAttribute("employee_id")));
					model.setInspection_type(General.checknull((String) obj.get("inspection_type")));
					model.setSession_id(General.checknull((String) obj.get("session_id")));
					model.setIp(machine);
					model.setUpdatedBy(user_id);

					String save = "";
					save = InspectorInspectionManager.saveReopen(model);
					if (save.equals("1")) {
						finalResult.put("flag", "Y");
						finalResult.put("status", "Application Re-Open Successfully");
					} else {
						finalResult.put("flag", "N");
						finalResult.put("status", ApplicationConstants.FAIL);
					}
				}
				response.setContentType("application/json");
				response.setHeader("Cache-Control", "no-store");
				String jString = aesUtil.encrypt(finalResult.toString(), decodeData.split("::")[0], uniqueKey, decodeData.split("::")[1]);
				out.println(new Gson().toJson(jString));

				// out.print(finalResult);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("EXCEPTION CAUSED BY: InspectionByScrutinyCommitteeService [saverequest]" + e.getMessage().toUpperCase());
				log.fatal(Logging.logException("InspectionByScrutinyCommitteeService [saverequest]", e.toString()));
			}
		}
}