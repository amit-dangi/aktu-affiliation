/**
 * @ AUTHOR Amit DanGi
 */
package com.sits.affiliation.transaction.pending_payment_verification;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sits.common.AesUtil;
import com.sits.general.General;
import com.sits.general.Logging;
import com.sits.general.ReadProps;
/**
 * Servlet implementation class PendingPaymentVerificationService
 */
@WebServlet("/AFFILIATION/PendingPaymentVerificationService")
public class PendingPaymentVerificationService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(PendingPaymentVerificationService.class.getName());
	AesUtil aesUtil = new AesUtil(128,10);
	/**
     * @see HttpServlet#HttpServlet()
     */
    public PendingPaymentVerificationService() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String fstatus = General.checknull(request.getParameter("fstatus"));
	      if (fstatus.equals("SAVE")) {
	         this.processRequest(request, response);
	      }
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PendingPaymentVerificationModel model =new PendingPaymentVerificationModel();
		JSONObject jsonObj=null; 
		String status="", user_id="";
		try{				
			user_id =General.checknull((String)request.getSession().getAttribute("user_id"));
			response.setContentType("text/json;charset=UTF-8");
			jsonObj = new JSONObject();
			String uniqueKey = General.checknull((String)request.getSession().getAttribute("AESUniqueKey"));
			String encData	= General.checknull(request.getParameter("encData"));
			String decData=AesUtil.parseAes(encData, aesUtil, uniqueKey);
			JSONObject dataobj 	= (JSONObject) new JSONParser().parse(decData);
			
			//System.out.println("dataobj||"+dataobj.toJSONString());
			model.setMerchantorderno(General.checknull((String) dataobj.get("merchantorderno")));
			model.setTranId(General.checknull((String) dataobj.get("tranId")));
			model.setPaymenttype(General.checknull((String) dataobj.get("paymenttype")));
			
			PrintWriter out=response.getWriter();
			if(!user_id.equals("")){
				
				model.setKey_id(General.checknull(ReadProps.getkeyValue("key_id", "sitsResource")));
				model.setKey_secret(General.checknull(ReadProps.getkeyValue("key_secret", "sitsResource")));
				model.setPayment_gateWayUrl(General.checknull(ReadProps.getkeyValue("TrackingUrl", "sitsResource")));
	
				JSONObject obj=PendingPaymentVerificationManager.RazorpayAPIFatchall(model);
				
			     if(obj.get("status").toString().equals("paid") || obj.get("status").toString().equals("attempted") 
			    		 	|| obj.get("status").toString().equals("created")){
			    	 if(model.getPaymenttype().equals("R")){
			    		 status = PendingPaymentVerificationManager.saveGatewayResponse(obj); 
			    	 }else{//for condition 'S' subrequest
			    		 status = PendingPaymentVerificationManager.saveGatewayAffiResponse(obj); 
			    	 }
				  jsonObj.put("errMsg", status);
				  jsonObj.put("flag", true);
			   }else {
				  jsonObj.put("errMsg", "Payment Status Not updated.");
			      jsonObj.put("flag", false); 
			   }
			}else {
				jsonObj.put("errMsg", "Session Expire");
				jsonObj.put("flag", false);
			}
			out.print(jsonObj);
		}catch(Exception e){
			System.out.println("EXCEPTION IS CAUSED BY: PendingPaymentVerificationService.java" + "[processRequest()]" + " " + e.getMessage().trim().toUpperCase());
			log.fatal(Logging.logException("PendingPaymentVerificationService [processRequest]", e.toString()));
		}
	}
}