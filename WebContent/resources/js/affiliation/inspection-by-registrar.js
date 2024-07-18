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
	getDist('ST0001','','district');	
});

function getApplicationDetail(){ 
	
	//Validate file as click on view
	var session_id = $('#session_id').val();
if (session_id == "") {
	$('#session_id').focus();
	showerr($("#session_id")[0], "Session is required.", "block");
	return false;
}
	
	var inst_name=$('#inst_name').val();
	var mobile_no=$('#mobile_no').val();
	var email_id=$('#email_id').val();
	var XTODATE=$('#XTODATE').val();
	var XFROMDATE=$('#XFROMDATE').val();
	var district=$('#district').val();
	var request_name=$('#request_name').val();
	var submit_status=$('#submit_status').val();
try {
	$.ajax({
		type: "POST",
		url: "../InspectionByRegistrarService",
		data:{"fstatus":"GETDETAILS", "session_id":session_id,"inst_name":inst_name,"mobile_no":mobile_no,
			"email_id":email_id,"XTODATE":XTODATE,"XFROMDATE":XFROMDATE,"district":district,"request_name":request_name},
		async: false,
		success: function (response){
			$('#stable').html("");
			console.log("response");
			console.log(response.Applicationlist);
			 var index=0;
			 if (typeof response.Applicationlist!= 'undefined' && response.Applicationlist.length > 0) {
				$.each(response.Applicationlist, function (key, val) {
					$("#searchTable").show();
					var memberfile	= val.memberfile;
					var session 	= val.session;
                    var AF_REG_ID 	= val.AF_REG_ID;
                    var email 		= val.email;
                    var contact 	= val.contact;
                    var REG_NO 		= val.REG_NO;
                    var REG_FOR_NAME = val.REG_FOR_NAME;
                    var panel_code 	= val.panel_code;
                    var remarks 	= val.remarks;
                    var panel_name  =val.panel_name;
                    var cons_remark =val.cons_insp_remarks;
                    var cons_recm   =val.cons_insp_recm;
                    var consolidate_review_by_id=val.consolidate_review_by_id;
                    var consolidate_review_by   =val.consolidate_review_by;
                    var consolidate_finalsubmit =val.consolidate_finalsubmit;
                    var registrar_finalsubmit=val.registrar_finalsubmit;
                    var bg_color=consolidate_finalsubmit=="Y"?"#0080001f":"#a9424226";
                    
                    var governbody_finalsubmit= val.governbody_finalsubmit;
                    	bg_color=governbody_finalsubmit=="Y"?"#a9424226":bg_color;
                    	
                	var isfinal_acknowledge=val.isfinal_acknowledge;
                		bg_color=isfinal_acknowledge=="Y"?"#3d8d3d91":bg_color;
            		
    		if((submit_status=='pending' && consolidate_finalsubmit!='Y' ) || 
    				(submit_status=='submitted' && consolidate_finalsubmit=='Y')
    				|| (submit_status=='')){
                    index=index+1;
                    var cols ='<tr style="background-color:'+bg_color+';" >'
                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+contact+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+email+'</td>'
                    	+'<td style="text-align:center; width:10%;"><a onclick="downloadJasperReport('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+')"><b>View Application Details</b></a></td>'
                    	/*+'<td style="text-align:center; width:8%;">'+panel_name+'</td>'
                    	
                    	if(consolidate_finalsubmit=='Y' && governbody_finalsubmit!='Y'){
                			cols +='<td style="text-align:center; width:6%;">'
                			cols +='<button type="button" class="btn btn-success" '
            				cols +='id="" onClick="actionClick('+"'"+''+panel_name+''+"'"+','+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+panel_code+''+"'"+','+"'"+''+cons_remark+''+"'"+','+"'"+''+cons_recm+''+"'"+','+"'"+''+REG_FOR_NAME+''+"'"+','+"'"+''+consolidate_finalsubmit+''+"'"+','+"'"+''+consolidate_review_by+''+"'"+',\''+consolidate_review_by_id+'\')">Action</button></td>'
                    	}else{
                    		var buttontext=isfinal_acknowledge=="Y"?"Final Submitted":"Acknowledgement Pending";
                    		cols +='<td style="text-align:center; width:6%;">'
                			cols +='<button type="button" class="btn btn-success" '
            				cols +='id="" onClick="actionClick('+"'"+''+panel_name+''+"'"+','+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+panel_code+''+"'"+','+"'"+''+cons_remark+''+"'"+','+"'"+''+cons_recm+''+"'"+','+"'"+''+REG_FOR_NAME+''+"'"+','+"'"+''+consolidate_finalsubmit+''+"'"+','+"'"+''+consolidate_review_by+''+"'"+',\''+consolidate_review_by_id+'\')">'+buttontext+'</button></td>'
                        }*/
                    	if(consolidate_finalsubmit=='Y'){
                    		cols +='<td style="text-align:center; width:10%;"><a onclick="openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+','+"'"+''+consolidate_review_by_id+''+"'"+')"><b>View Submmitted Inspection Details</b></a></td>'
                    		cols +='<td style="text-align:center; width:6%; cursor: pointer; word-break: break-word !important;"><b><a href="../downloadfile?filename='+memberfile+'&folderName=INSPECTOR_INSPECTION/'+AF_REG_ID+'/&fstatus=dwnFileFrmDir" target="_blank">'+memberfile+'</b></a></td>'
                    	}else{
                    		cols +='<td style="text-align:center; width:10%;"><a onclick="openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+','+"'"+''+consolidate_review_by_id+''+"'"+')"><b>Pending at CDO/ADM/Professor nominated by VC</b></a></td>'
                    		cols +='<td style="text-align:center; width:6%;">-</td>'
                        	}
                    	cols +='<input type="hidden" id="registrar_inspection_id_'+AF_REG_ID+'" value="'+val.registrar_inspection_id+'" />'
                    	+'<input type="hidden" id="registrar_id_'+AF_REG_ID+'" value="'+val.registrar_id+'" />'
                    	+'<input type="hidden" id="registrar_remarks_'+AF_REG_ID+'" value="'+val.registrar_remarks+'" />'
                    	+'<input type="hidden" id="registrar_recm_'+AF_REG_ID+'" value="'+val.registrar_recm+'" />'
                    	+'<input type="hidden" id="registrar_finalsubmit_'+AF_REG_ID+'" value="'+val.registrar_finalsubmit+'" />'
                    	+'<input type="hidden" id="governbody_finalsubmit_'+AF_REG_ID+'" value="'+val.governbody_finalsubmit+'" />'
                    	+'<input type="hidden" id="isfinal_acknowledge_'+AF_REG_ID+'" value="'+val.isfinal_acknowledge+'" />'
                    	+'<input type="hidden" id="regno_'+AF_REG_ID+'" value="'+val.REG_NO+'" />'
                    	+'<input type="hidden" id="reg_name_'+AF_REG_ID+'" value="'+val.REG_FOR_NAME+'" />'
                    	+'<input type="hidden" id="email_'+AF_REG_ID+'" value="'+val.email+'" />'
                    	cols +='</tr>'
                		$('#stable').append(cols);
                    	$("#searchTable").show();
                    	$("#headerdiv").show();
    		}
				});
			 }else{
				  $('#stable').html("");
				  var data ='No Data Found';
				    displaySuccessMessages("errMsg2", data, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					setTimeout(function() {
						location.reload();
					}, 3000);
			  }
		}
		
	});
} catch (e) {
	// TODO: handle exception
	alert("ERROR :"+e);
}

}

function actionClick(panel_name,AF_REG_ID,panel_code,con_remark,con_recm,int_name,consolidate_finalsubmit,consolidate_review_by,consolidate_review_by_id){
	if (AF_REG_ID == "") {
		alert("Application Id not found.");
		return false;
	}
	$('#selectedApplication').val(AF_REG_ID);	
	$('#Xinst_name').text(int_name+' & panel : '+panel_name);
	$('#remarks').text($("#registrar_remarks_"+AF_REG_ID).val());
	$('#Recommendation').text($("#registrar_recm_"+AF_REG_ID).val());
	
	if(($("#registrar_finalsubmit_"+AF_REG_ID).val())=="Y"){
	$('.btnSave').hide();
	$("#errMsg1").show();
	$("#btnFinalSave").hide();
	var registrar_name=$("#registrar_id_"+AF_REG_ID).val();
	displaySuccessMessages("errMsg1", "Application has been accepted by "+ (registrar_name=='undefined'?"ADMIN":registrar_name) + " & transfer to Government Body", "");
			if(($("#governbody_finalsubmit_"+AF_REG_ID).val())=="Y"){
				$("#remarks").prop("disabled", true);
				$("#Recommendation").prop("disabled", true);
				displaySuccessMessages("errMsg1", "Application has been accepted by Government Body", "");
				$("#rstBtn").hide();
				$("#btnFinalSave").show();
				if(($("#isfinal_acknowledge_"+AF_REG_ID).val())=="Y"){
					$("#rstBtn").hide();
					$("#btnFinalSave").hide();
					displaySuccessMessages("errMsg1", "Application has been final submitted & approval acknowledgement shared to college", "");
				}
			}
	}else{
		$('.btnSave').show();
		$("#errMsg1").hide();
		$("#btnFinalSave").hide();
	}
	$("#Dtable").show();
	$("#showbtn").show();
	$("#paneltable").show();
	var session_id=$('#session_id').val();
	$('html, body').animate({
        scrollTop: $("#paneltable").offset().top
    }, 2000);
	$('#Dtable').html("");
	 var cols ='<tr>'
     	+'<td style="text-align:center; width:10%;">'+consolidate_review_by+'</td>'
     	+'<td style="text-align:center; width:10%;"><a onclick="openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+','+"'"+''+consolidate_review_by_id+''+"'"+')"><b>View Inspection Details</b></a></td>'
     	+'<td style="text-align:center; width:8%;">'+con_remark+'</td>'
     	+'<td style="text-align:center; width:8%;">'+con_recm+'</td>'
     	+'</tr>'
		$('#Dtable').append(cols);
}

function Resetbtn(){ 
	$("#remarks").val("");
	$("#Recommendation").val("");
}

function openInspection(Inst_id,session_id,convinor_id,is_pannel_member,panned_member){
	try {
		if(Inst_id!="" ){
			document.myform.target="InspectionDetailViewFrame";
			document.myform.action="inspector_inspection_detail_view.jsp?Inst_Id="+Inst_id+"&session_id="+session_id+
							"&isfinalsubmited=Y&is_pannel_member=N&convinor_id="+convinor_id+"&inspection_mode=registrar";
			document.myform.submit();
			   $('html, body').animate({
			         scrollTop: $(".containerframe").offset().top
			     }, 1000);
		}else{
			alert("ERROR IN getInspection() Inst_id is requiered.");	
		}
	} catch (e) {
		// TODO: handle exception
		alert("ERROR IN inspector_inspection_detail_view.jsp getInspection() -:"+e);
	}
}

function Save(actiontype){
	try {
	var selectedApplication = $('#selectedApplication').val();
	var Recommendation = $('#Recommendation').val();
	var remarks = $('#remarks').val();

	if(remarks==""||remarks==null)
	  {
		showerr($("#remarks")[0],"Feedback by Registrar is required","block");
		$("#remarks").focus();
		return false;
	  }
	if(Recommendation==""||Recommendation==null)
	  {
		showerr($("#Recommendation")[0],"Remarks is required","block");
		$("#Recommendation").focus();
		return false;
	  }
	
	
	var jsonObject = {
		"inspection_id":$('#registrar_inspection_id_'+selectedApplication+'').val(),
		"session_id":$('#session_id').val(),
		"selectedApplication" : selectedApplication,
		"Recom"   : Recommendation,
		"remarks" : remarks,
		"actiontype":actiontype,
		"inspection_type":"Registrar",
		"regno":	$('#regno_'+selectedApplication+'').val(),
		"reg_name":	$('#reg_name_'+selectedApplication+'').val(),
		"email":	$('#email_'+selectedApplication+'').val(),
	};
	console.log("Sending jsonObject :" + JSON.stringify(jsonObject));
	var encData = encAESData($("#AESKey").val(), jsonObject);
	$('#btnSave').hide();
	$('#rstBtn').hide();
		$.ajax({
			type: "POST",
			url: "../InspectionByRegistrarService",
			data: {encData: encData, fstatus: "S"},
			dataType: "json",
			success : function(response) {
			var data = decAESData($("#AESKey").val(), response);
			$('#btnSave').show();
			$('#rstBtn').show();
			$("#errMsg1").show();
			$("#btnFinalSave").hide();
			if (data.flag == "Y") {
				setTimeout(function() {
					displaySuccessMessages("errMsg1", data.status, "");
				}, 1000);
				clearSuccessMessageAfterFiveSecond("errMsg1");
			} else {
				setTimeout(function() {
					displaySuccessMessages("errMsg2", data.status, "");
				}, 3000);
				clearSuccessMessageAfterFiveSecond("errMsg2");
			}

			if (data.flag == "Y") {
				setTimeout(function() {
					location.reload();
				}, 5000);
			}
		},
			error: function(xhr, status, error) {
				alert(xhr.responseText);
				alert(status);
				alert("error :"+error);
				
				$('#btnSave').show();
				$('#rstBtn').show();
			}
		});	
	} catch (e) {
		// TODO: handle exception
		alert("error :"+e);
	}
};