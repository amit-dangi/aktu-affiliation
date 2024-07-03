$(document).ready(function(){

	$("#vldReset").click(function(){   // call on Reset Button
		window.location.reload();
	});
});

function vldSearch(){
	var college_id=$("#college_name").val();
	var request_id=$("#request_name").val();
	var sub_req_id=$("#sub_req_name").val();
	var report_type=$("#report_type").val();
	var final_submit=$("#final_submit").val();
	frmAFPaymentDetailMast.target ="btmPaymentDetailMast";
	frmAFPaymentDetailMast.action="affiliation_payment_details_report_l.jsp?college_id="+college_id+"&request_id="+request_id+"&sub_req_id="+sub_req_id
							+"&report_type="+report_type+"&final_submit="+final_submit;
	frmAFPaymentDetailMast.submit();
}