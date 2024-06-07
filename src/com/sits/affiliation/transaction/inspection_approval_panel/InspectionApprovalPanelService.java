
package com.sits.affiliation.transaction.inspection_approval_panel;
import java.util.Map;
import java.lang.reflect.Type;
import com.sits.general.Logging;
import org.apache.log4j.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.Base64;
import java.io.PrintWriter;
import org.json.simple.JSONObject;
import com.sits.general.General;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.sits.common.AesUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/AFFILIATION/InspectionApprovalPanelService" })
public class InspectionApprovalPanelService extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    AesUtil aesUtil;
    
    public InspectionApprovalPanelService() {
        this.aesUtil = new AesUtil(128, 10);
    }
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String formStatus = General.checknull(request.getParameter("fstatus").trim().toUpperCase());
        if (formStatus.equals("N") || formStatus.equals("E")) {
            this.SaveUpdateRecord(request, response);
        }
        else if (formStatus.equals("D")) {
            this.processDeleteRequest(request, response);
        }
        else if (formStatus.equals("RD")) {
            this.processDeleteDetailsRequest(request, response);
        }
         
    }
protected void SaveUpdateRecord(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=UTF-8");
        final PrintWriter out = response.getWriter();
        try {
            String errMsg = "";
            String flg = "Y";
             int j = 1;
             int k = 1;
            JSONObject returnJSONObj = new JSONObject();
            InspectionApprovalPanelModel model = new InspectionApprovalPanelModel();
            String fstatus = General.check_null(request.getParameter("fstatus")).trim();
            String uniqueKey = General.checknull((String)request.getSession().getAttribute("AESUniqueKey"));
            String encData = General.checknull(request.getParameter("encData"));
            String decodeData = new String(Base64.getDecoder().decode(encData));
            String decData = AesUtil.parseAes(encData, this.aesUtil, uniqueKey);
            JSONObject dataObj = (JSONObject)new JSONParser().parse(decData);
            String arrayLIst = General.checknull(dataObj.get((Object)"list").toString());
            JSONArray detList = (JSONArray)JSONValue.parse(arrayLIst);
            String ip = General.check_null((String)request.getSession().getAttribute("s_ip"));
            String user_id = General.checknull((String)request.getSession().getAttribute("user_id"));
            String panel_id = General.checknull((String)dataObj.get((Object)"panel_id")).trim();
            String panel_code = General.checknull((String)dataObj.get((Object)"panel_code")).trim();
            String panel_name = General.checknull((String)dataObj.get((Object)"panel_name")).trim();
            String isActive = General.checknull((String)dataObj.get((Object)"isActive")).trim();
            Type type = new TypeToken<InspectionApprovalPanelModel>() {
            	
    			// private static final long serialVersionUID = 1L;
    		}.getType();
    		model = new Gson().fromJson(dataObj.toString(), type);
            String jString = "";
            if (user_id.equals("")) {
                errMsg = "Session Expire";
                flg = "N";
            }
            else if (!fstatus.equals("N") && !fstatus.equals("E")) {
                errMsg = "Invalid Entry Type";
                flg = "N";
            }
            if (flg.trim().equals("Y") && errMsg.trim().equals("")) {
                if (fstatus.equals("N") || fstatus.equals("E")) {
                    model = InspectionApprovalPanelManager.saveRecord(model, user_id, ip, fstatus);
                }
                if (model.isValid()) {
                    flg = "V";
                    errMsg = model.getErrMsg();
                    Logger.getLogger("usglog").debug((Object)Logging.onSucess(user_id, ip, "InspectionApprovalPanelService", model.getErrMsg()));
                }
                else {
                    flg = "I";
                    errMsg = model.getErrMsg();
                    Logger.getLogger("usglog").debug((Object)Logging.onSucess(user_id, ip, "InspectionApprovalPanelService", model.getErrMsg()));
                }
            }
            returnJSONObj.put((Object)"flg", (Object)flg);
            returnJSONObj.put((Object)"errMsg", (Object)errMsg);
            returnJSONObj.put((Object)"fstatus", (Object)fstatus);
            jString = this.aesUtil.encrypt(returnJSONObj.toString(), decodeData.split("::")[0], uniqueKey, decodeData.split("::")[1]);
            out.println(new Gson().toJson((Object)jString));
        }
        catch (Exception ex) {
            System.out.println("Error in InspectionApprovalPanelService : " + ex.getMessage());
            Logger.getLogger("usglog").fatal((Object)Logging.logException("InspectionApprovalPanelService", ex.getMessage().toString()));
            return;
        }
        finally {
            out.close();
        }
        out.close();
    }
    
    
    protected void processDeleteRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject returnJSONObj = new JSONObject();
        String flg = "";
        String jString = "";
        String errMsg = "";
        try {
            String ip = General.check_null((String)request.getSession().getAttribute("s_ip"));
            String user_id = General.check_null((String)request.getSession().getAttribute("user_id"));
            String fstatus = General.checknull(request.getParameter("fstatus"));
            String panel_id = General.checknull(request.getParameter("panel_id"));
            String departmnt_id= General.checknull(request.getParameter("departmnt_id"));
            String college_name= General.checknull(request.getParameter("college_name"));
            if (!user_id.equals("")) {
                final InspectionApprovalPanelModel model = InspectionApprovalPanelManager.deleteRecord(panel_id.trim());
                if (model.isValid()) {
                {
                    flg = "V";
                    errMsg = model.getErrMsg();
                    Logger.getLogger("usglog").debug((Object)Logging.onSucess(user_id, ip, "InspectionApprovalPanelService[[delete]]", model.getErrMsg()));
                }
                }
                else {
                    flg = "I";
                    errMsg = model.getErrMsg();
                    Logger.getLogger("usglog").debug((Object)Logging.onSucess(user_id, ip, "InspectionApprovalPanelService[[delete]]", model.getErrMsg()));
                }
            }
            else {
                errMsg = "session expire!";
                flg = "N";
            }
            returnJSONObj.put((Object)"flg", (Object)flg);
            returnJSONObj.put((Object)"errMsg", (Object)errMsg);
            returnJSONObj.put((Object)"fstatus", (Object)fstatus);
            jString = JSONObject.toJSONString((Map)returnJSONObj);
            out.println(jString);
        }
        catch (Exception e) {
            System.out.println("ERROR IN InspectionApprovalPanelService[delete] IS CAUSED BY " + e.getMessage().toUpperCase());
            Logger.getLogger("usglog").fatal((Object)Logging.logException("InspectionApprovalPanelService[delete]", e.getMessage().toString()));
        }
    }
    
    
    protected void processDeleteDetailsRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String delete = "";
        try {
            JSONObject finalResult = new JSONObject();
            String user_id = General.check_null((String)request.getSession().getAttribute("user_id"));
            String id = General.checknull(request.getParameter("id"));
            if (!user_id.equals("")) {
                delete = InspectionApprovalPanelManager.deleteDetailRecord(id.trim());
            }
            if (delete.equals("")) {
                finalResult.put((Object)"status", (Object)0);
                finalResult.put((Object)"msg", (Object)"Unable to Process Request Kindly Contact Your Admin");
            }
            else {
                finalResult.put((Object)"status", (Object)1);
                finalResult.put((Object)"msg", (Object)"Record Deleted Successfully ");
            }
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            out.print(finalResult);
        }
        catch (Exception e) {
            System.out.println("ERROR IN InspectionApprovalPanelService[delete] IS CAUSED BY " + e.getMessage().toUpperCase());
            Logger.getLogger("usglog").fatal((Object)Logging.logException("InspectionApprovalPanelService[delete]", e.getMessage().toString()));
        }
    }
    
   
}


