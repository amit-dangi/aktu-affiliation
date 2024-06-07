package com.sits.affiliation.transaction.new_affi_request_approval;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sits.common.AesUtil;
import com.sits.general.General;
import com.sits.general.Logging;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet({"/AFFILIATION/NewAffiRequestApprovalService"})
public class NewAffiRequestApprovalService extends HttpServlet{
  private static final long serialVersionUID = 1L;
  AesUtil aesUtil;
  /**
   * @see HttpServlet#HttpServlet()
   */
  public NewAffiRequestApprovalService() {
  	this.aesUtil = new AesUtil(128, 10);
      // TODO Auto-generated constructor stub
  }
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.getWriter().append("Served at: ").append(request.getContextPath());
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String fstatus = General.checknull(request.getParameter("fstatus"));
      if (fstatus.equals("save")) {
         this.processRequest(request, response);
      }if (fstatus.equals("prgm")){
    	  this.getPrgmDetail(request, response);
      }
   }

   protected void getPrgmDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			JSONArray arr = new JSONArray();
			JSONObject finalResult = new JSONObject();
			String id = General.checknull(request.getParameter("id"));
			
			arr = NewAffiRequestApprovalManager.getPrgmDetail(id);
			finalResult.put("PrgmDetail", arr);
			response.setContentType("application/json");
			out.print(finalResult);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION CAUSED BY: NewAffiRequestApprovalService[getPrgmDetail]" + " " + e.getMessage().toUpperCase());
			Logger.getLogger("usglog").fatal(Logging.logException("NewAffiRequestApprovalService", e.getMessage().toString()));
		}
	}
   
   protected void processRequest(HttpServletRequest request, 
		   HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/json;charset=UTF-8");
       PrintWriter out = response.getWriter();
       try {
			String errMsg="",flg="Y";
			JSONObject returnJSONObj = new JSONObject();
			NewAffiRequestApprovalModel model   = new NewAffiRequestApprovalModel();
			String fstatus 		= General.check_null(request.getParameter("fstatus")).trim();
			String uniqueKey	= General.checknull((String)request.getSession().getAttribute("AESUniqueKey"));
			String encData		= General.checknull(request.getParameter("encData"));
			String decodeData	= new String(java.util.Base64.getDecoder().decode(encData));
			String decData		= AesUtil.parseAes(encData,aesUtil,uniqueKey);
			JSONObject dataObj 	= (JSONObject) new JSONParser().parse(decData);
			String ip 			= General.checknull((String)request.getSession().getAttribute("s_ip"));
			String user_id 		= General.checknull((String)request.getSession().getAttribute("user_id"));
			String remarks 		= General.checknull((String) dataObj.get("send_mail")).trim();
			String jString = "";
			
			if(user_id.equals("")) {
				errMsg="Session Expire";
   			flg="N";
			} else if(!((fstatus.equals("save")))) {
				errMsg="Invalid Entry Type";
				flg="N";
			} 

			if((flg.trim().equalsIgnoreCase("Y"))&&(errMsg.trim().equals(""))){
				Type type = new TypeToken<NewAffiRequestApprovalModel>() {
				}.getType();
				model = new Gson().fromJson(dataObj.toString(), type);
				if(fstatus.equals("save")) {
					model = NewAffiRequestApprovalManager.saveRecord(model, user_id, ip);
				} 

				if(model.isValid()) {
					flg="V";
					errMsg=model.getErrMsg();
   				Logger.getLogger("usglog").debug(Logging.onSucess(user_id, ip, "NewAffiRequestApprovalService", model.getErrMsg()));
				} else {
					flg="I";
					errMsg=model.getErrMsg();
   				Logger.getLogger("usglog").debug(Logging.onSucess(user_id, ip, "NewAffiRequestApprovalService", model.getErrMsg()));
				}
			}
			returnJSONObj.put("flg", flg);
			returnJSONObj.put("errMsg", errMsg);
			returnJSONObj.put("fstatus", fstatus);

			jString=aesUtil.encrypt(returnJSONObj.toString(),decodeData.split("::")[0],uniqueKey,decodeData.split("::")[1]);
			out.println(new Gson().toJson(jString));
       }  catch (Exception ex) {
       	System.out.println("Error in NewAffiRequestApprovalService : "+ex.getMessage());
       	Logger.getLogger("usglog").fatal(Logging.logException("NewAffiRequestApprovalService", ex.getMessage().toString()));
       } finally {
       	out.close();
       }
	}
}
