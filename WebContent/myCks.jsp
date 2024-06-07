<%@page import="com.sits.general.General"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="com.sits.general.SecurityFile"%> 
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="com.sits.general.ReadProps"%>

<%
 	response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");   
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1); //prevents caching at the proxy
	response.addHeader("Cache-Control", "post-check=0, pre-check=0"); 
	String user_id="",user_name="",user_status="",user_style="",user_dashboard="",soft_ver="",menuRights="",session_id="",csession_id="",employee_id="",base_url="",loct_id="";
	String ddo_id="",ddoLocatId="",dept_id="",is_dept_head="",s_ip="";
try {	
	JSONParser parser       = new JSONParser();
	ServletContext context  = request.getServletContext();
	
	user_id 		= General.checknull((String)session.getAttribute("user_id"));
	user_name 		= General.checknull((String)session.getAttribute("user_name"));
	user_status 	= /* "U"; */General.checknull((String)session.getAttribute("user_status"));
	employee_id 	= General.checknull((String)session.getAttribute("employee_id"));
	//employee_id 	= "0003";
	//employee_id 	= "0001";
	session.setAttribute(employee_id, employee_id);
	ddo_id			= General.checknull((String)session.getAttribute("ddo_id"));
	loct_id			= General.checknull((String)session.getAttribute("loct_id"));
	ddoLocatId		= General.checknull((String)session.getAttribute("ddoLocatId"));
	dept_id 		= General.checknull((String) session.getAttribute("depratment_id")).trim();
	is_dept_head 	= General.checknull((String) session.getAttribute("is_depratment_head")).trim();
	s_ip 			= General.checknull((String) session.getAttribute("s_ip")).trim();
	session.setAttribute("employee_id", employee_id);
	System.out.println("TEST TRANSPORT[myCks.jsp] : "+user_id+"|"+loct_id+"|"+csession_id+"|"+ddo_id+"|"+ddoLocatId+"|"+employee_id+"|"+user_status+"|"+s_ip);
	base_url	 	= ReadProps.getkeyValue("root.url", "sitsResource")+"/SessionExp.jsp?x=y";
	
	if((user_id.trim().equals("")) || (user_status.trim().equals(""))  /* || (!session_id.equals(csession_id)) */) {
		response.sendRedirect(base_url);
 	}
} catch (Exception e) {
	System.out.println("Error in myCks.jsp [TRANSPORT] : "+e);
	response.sendRedirect(base_url);
}	
%>