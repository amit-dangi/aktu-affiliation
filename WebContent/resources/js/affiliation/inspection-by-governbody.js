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
try {
	$.ajax({
		type: "POST",
		url: "../InspectionByGovernbodyService",
		data:{"fstatus":"GETDETAILS", "session_id":session_id,"inst_name":inst_name,"mobile_no":mobile_no,
			"email_id":email_id,"XTODATE":XTODATE,"XFROMDATE":XFROMDATE},
		async: false,
		success: function (response){
			$('#stable').html("");
			console.log("response");
			console.log(response.Applicationlist);
			 var index=0;
			 if (typeof response.Applicationlist!= 'undefined' && response.Applicationlist.length > 0) {
				$.each(response.Applicationlist, function (key, val) {
					$("#searchTable").show();
					var session 	= val.session;
                    var AF_REG_ID 	= val.AF_REG_ID;
                    var email 		= val.email;
                    var contact 	= val.contact;
                    var REG_NO 		= val.REG_NO;
                    var REG_FOR_NAME = val.REG_FOR_NAME;
                    var panel_code 	= val.panel_code;
                    var remarks 	= val.remarks;
                    var panel_name  =val.panel_name;
                    var registrar_remark =val.registrar_remarks;
                    var registrar_recm   =val.registrar_recm;
                    var registrar_name   =val.registrar_id;
                    var governbody_finalsubmit=val.governbody_finalsubmit;
                    var bg_color=governbody_finalsubmit=="Y"?"#0080001f":"";
                    index=index+1;
                    var cols ='<tr style="background-color:'+bg_color+';" >'
                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+contact+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+email+'</td>'
                    	+'<td style="text-align:center; width:10%;"><a onclick="downloadJasperReport('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+')"><b>Click Here to View</b></a></td>'
                    	/*+'<td style="text-align:center; width:8%;">'+panel_name+'</td>'
                    	
            			cols +='<td style="text-align:center; width:6%;">'
            			cols +='<button type="button" class="btn btn-success" '
        				cols +='id="" onClick="actionClick('+"'"+''+panel_name+''+"'"+','+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+panel_code+''+"'"+','+"'"+''+registrar_remark+''+"'"+','+"'"+''+registrar_recm+''+"'"+','+"'"+''+REG_FOR_NAME+''+"'"+','+"'"+''+registrar_name+''+"'"+')">Action</button></td>'
                	*/
                    	cols +='<input type="hidden" id="registrar_inspection_id_'+AF_REG_ID+'" value="'+val.governbody_inspection_id+'" />'
                    	+'<input type="hidden" id="governbody_id_'+AF_REG_ID+'" value="'+val.governbody_id+'" />'
                    	+'<input type="hidden" id="governbody_remarks_'+AF_REG_ID+'" value="'+val.governbody_remarks+'" />'
                    	+'<input type="hidden" id="governbody_recm_'+AF_REG_ID+'" value="'+val.governbody_recm+'" />'
                    	+'<input type="hidden" id="governbody_finalsubmit_'+AF_REG_ID+'" value="'+val.governbody_finalsubmit+'" />'
                    	cols +='</tr>'
        				$('#stable').append(cols);
                    	$("#searchTable").show();
                    	$("#headerdiv").show();
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

function actionClick(panel_name,AF_REG_ID,panel_code,registrar_remark,registrar_recm,int_name,registrar_name){
	if (AF_REG_ID == "") {
		alert("Application Id not found.");
		return false;
	}
	$('#selectedApplication').val(AF_REG_ID);	
	$('#Xinst_name').text(int_name+' & panel : '+panel_name);
	$('#remarks').text($("#governbody_remarks_"+AF_REG_ID).val());
	$('#Recommendation').text($("#governbody_recm_"+AF_REG_ID).val());

	if(($("#governbody_finalsubmit_"+AF_REG_ID).val())=="Y"){
	$('.btnSave').hide();
	$("#errMsg1").show();
	var governbody_name=$("#governbody_id_"+AF_REG_ID).val();
	displaySuccessMessages("errMsg1", "Application has been accepted by "+ (governbody_name=='undefined'?"ADMIN":governbody_name) + " & transferred to Registrar ", "");
	}else{
		$('.btnSave').show();
		$("#errMsg1").hide();
	}
	$("#Dtable").show();
	$("#showbtn").show();
	$("#paneltable").show();
	$('html, body').animate({
        scrollTop: $("#paneltable").offset().top
    }, 2000);
	$('#Dtable').html("");
	console.log("registrar_name|"+registrar_name);
	 var cols ='<tr>'
     	+'<td style="text-align:center; width:10%;">'+(registrar_name=='undefined'?"ADMIN":registrar_name)+'</td>'
     	//+'<td style="text-align:center; width:10%;"><a onclick="openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session+''+"'"+','+"'"+''+inspection_by_id+''+"'"+')"><b>Click Here to View</b></a></td>'
     	+'<td style="text-align:center; width:8%;">'+registrar_remark+'</td>'
     	+'<td style="text-align:center; width:8%;">'+registrar_recm+'</td>'
     	+'</tr>'
		$('#Dtable').append(cols);
}

function Resetbtn(){ 
	$("#remarks").val("");
	$("#Recommendation").val("");
}

function openInspection(Inst_id,session_id,isfinalsubmited,is_pannel_member,panned_member){
	try {
		if(Inst_id!="" ){
			document.myform.target="InspectionDetailViewFrame";
			document.myform.action="inspector_inspection_detail_view.jsp?Inst_Id="+Inst_id+"&session_id="+session_id+
							"&isfinalsubmited=Y&is_pannel_member=N&inspection_by_id="+isfinalsubmited;
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
		showerr($("#remarks")[0],"Government Body Feedback is required","block");
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
		"inspection_id":"",
		"session_id":$('#session_id').val(),
		"selectedApplication" : selectedApplication,
		"Recom"   : Recommendation,
		"remarks" : remarks,
		"actiontype":actiontype,
		"inspection_type":"Governbody"
	};

	console.log("Sending jsonObject :" + JSON.stringify(jsonObject));
	var encData = encAESData($("#AESKey").val(), jsonObject);
	$('#btnSave').hide();
	$('#rstBtn').hide();
		
		$.ajax({
			type: "POST",
			url: "../InspectionByGovernbodyService",
			data: {encData: encData, fstatus: "S"},
			dataType: "json",
			success : function(response) {
			var data = decAESData($("#AESKey").val(), response);
			$('#btnSave').show();
			$('#rstBtn').show();
			$("#errMsg1").show();
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