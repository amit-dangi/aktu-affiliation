/** * @author:Amit danGi */
 $(document).ready(function(){
	
 });
 
function  resetForm(){
	  location.reload();
}
 
function btnReset(){
	location.reload();
}

function vldSearch(){
	frmNewAffiReportd.target="btmfrmNewAffiRequestReportL";
	frmNewAffiReportd.action="affiliation_report_l.jsp";
	frmNewAffiReportd.submit();
}
