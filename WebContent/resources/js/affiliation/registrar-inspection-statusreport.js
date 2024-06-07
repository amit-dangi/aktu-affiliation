/**
 * @author Amit DanGi
 */
$(document).ready(function(){
	$(".hidediv").hide();
	$("#btnReset").click(function(){
		location.reload();
	});
	// use for the get  AcademicSession  
	getAcademicSession();
});

function getApplicationDetail(){ 
	
	try{
		var session_id = $('#session_id').val();
		if (session_id == "") {
			$('#session_id').focus();
			showerr($("#session_id")[0], "Session is required.", "block");
			return false;
		}
		
		   $("#consolidateinspectionreportD").attr("action","registrar_inspection_statusreport_l.jsp");
	  	   $("#consolidateinspectionreportD").attr("method","post");
	  	   $("#consolidateinspectionreportD").attr("target","consolidateinspectionreportL");
	  	   $("#consolidateinspectionreportD").submit();

	}catch(err){
		alert("Error :"+err);
	}

}
