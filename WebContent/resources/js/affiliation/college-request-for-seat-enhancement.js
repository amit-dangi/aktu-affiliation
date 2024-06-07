/**
 * @author Amit DanGi
 * 
 * 
 */

/*This page will be discarded from 28 May as per by client changes*/

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
		url: "../ConsolidateInspectionByCommitteeService",
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
                    var cons_remark =val.cons_insp_remarks;
                    var cons_recm   =val.cons_insp_recm;
                    var isfinalsubmited_byinspector   =val.isfinalsubmited_byinspector;
                    var is_convinor=val.is_convinor;
                    var consolidate_finalsubmit=val.consolidate_finalsubmit;
                    var bg_color=consolidate_finalsubmit=="Y"?"#0080001f":"";
                    if((is_convinor=='Y' || $('#user_status').val()=='A') && isfinalsubmited_byinspector=='Y' ){
                    index=index+1;
                    var cols ='<tr style="background-color:'+bg_color+' ">'
                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+contact+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+email+'</td>'
                    	+'<td style="text-align:center; width:10%;"><a onclick="downloadJasperReport('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+')"><b>Click Here to View</b></a></td>'
                    	+'<td style="text-align:center; width:8%;">'+panel_name+'</td>'
                    	
                    	if(is_convinor=='Y'){
                			cols +='<td style="text-align:center; width:6%;">'
                			cols +='<button type="button" class="btn btn-success" '
            				cols +='id="" onClick="actionClick('+"'"+''+panel_name+''+"'"+','+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+panel_code+''+"'"+','+"'"+''+cons_remark+''+"'"+','+"'"+''+cons_recm+''+"'"+','+"'"+''+REG_FOR_NAME+''+"'"+','+"'"+''+consolidate_finalsubmit+''+"'"+')">Action</button></td>'
                        		
                    	}else{
                    		cols +='<td style="text-align:center; width:6%; color: #a94442f7;"><b>Only Panel Member Convenor Allowed</b></td>'	
                    		}
                    	+'</tr>'
                    	}
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

function actionClick(panel_name,AF_REG_ID,panel_code,remark,recm,int_name,consolidate_finalsubmit){
	if (AF_REG_ID == "") {
		alert("Application Id not found.");
		return false;
	}
	$('#selectedApplication').val(AF_REG_ID);	
	$('#Xinst_name').text(int_name+' & panel : '+panel_name);
	$('#remarks').text(remark);
	$('#Recommendation').text(recm);
	
	if(consolidate_finalsubmit=="Y"){
	$('.btnSave').hide();
	displaySuccessMessages("errMsg1", "Application has been approved", "");
	}
	try {
		$.ajax({
			type: "POST",
			url: "../ConsolidateInspectionByCommitteeService",
			data:{"fstatus":"GETPANELDT", "panel_code":panel_code,"AF_REG_ID":AF_REG_ID},
			async: false,
			success: function (response){
				$('#Dtable').html("");
				console.log("response");
				console.log(response.Applicationlist);
				if(response.Applicationlist.length> 0){
				 var index=0;
					$.each(response.Applicationlist, function (key, val) {
						$("#Dtable").show();
						$("#showbtn").show();
						var session 	    = val.session;
	                    var insp_member 	= val.inspection_by;
	                    var remarks 	    = val.remarks;
	                    var insp_recm       = val.insp_recm;
	                    var inspection_by_id= val.inspection_by_id;
	                    index=index+1;
	                    var cols ='<tr>'
	                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
	                    	+'<td style="text-align:center; width:10%;">'+insp_member+'</td>'
	                    	+'<td style="text-align:center; width:10%;"><a onclick="openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session+''+"'"+','+"'"+''+inspection_by_id+''+"'"+')"><b>Click Here to View</b></a></td>'
	                    	+'<td style="text-align:center; width:8%;">'+remarks+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+insp_recm+'</td>'
	                    	+'</tr>'
	        				$('#Dtable').append(cols);
	                    	$("#paneltable").show();
	                    	$('html, body').animate({
	       			         scrollTop: $("#paneltable").offset().top
	       			     }, 2000); 
					});
				}else{
                    var cols ='<tr>'
                    	+'<td colspan="5"><span style="color:red;"><b> Data Not Found </b></span></td>'
                    	+'</tr>'
                    	
                    	$('#Dtable').append(cols);
                	    $("#paneltable").show();
                	    $("#showbtn").hide();
				}
			}
			
		});
	} catch (e) {
		// TODO: handle exception
		alert("ERROR :"+e);
	}
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
		showerr($("#remarks")[0],"Remarks is required","block");
		$("#remarks").focus();
		return false;
	  }
	if(Recommendation==""||Recommendation==null)
	  {
		showerr($("#Recommendation")[0],"Recommendation is required","block");
		$("#Recommendation").focus();
		return false;
	  }
	
	
	var jsonObject = {
		"session_id":$('#session_id').val(),
		"selectedApplication" : selectedApplication,
		"Recom"   : Recommendation,
		"remarks" : remarks,
		"actiontype":actiontype,
		"inspection_type":"Convenor"
	};

	console.log("Sending jsonObject :" + JSON.stringify(jsonObject));
	var encData = encAESData($("#AESKey").val(), jsonObject);

	$('#btnSave').hide();
	$('#rstBtn').hide();
		
		$.ajax({
			type: "POST",
			url: "../ConsolidateInspectionByCommitteeService",
			data: {encData: encData, fstatus: "S"},
			dataType: "json",
			success : function(response) {
			var data = decAESData($("#AESKey").val(), response);
			$('#btnSave').show();
			$('#rstBtn').show();
			if (data.flag == "Y") {
				setTimeout(function() {
					displaySuccessMessages("errMsg1", data.status, "");
					//$('#Detailsdiv').hide();
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