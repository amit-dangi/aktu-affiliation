/**
 * @author Amit dangi
 */
$(document).ready(function(){
	//	 $("#loader").fadeOut(2000);
	 
	$(".hidediv").hide();
	$("#btnReset, #btnReset1, #btnReset2").click(function(){
		location.reload();
	});
	$("#Insp_recm_1").change(function(){
		var selectedvalue = $(this).find('option:selected').val();
		if(selectedvalue!='')
			showRemarks(selectedvalue,'1');
	});
	
	getAcademicSession();
});

function allowOnlyNumeric(event) {
    var keyCode = event.which ? event.which : event.keyCode;
    if (keyCode < 48 || keyCode > 57) {
        if (keyCode !== 8) {
            event.preventDefault();
            return false;
        }
    }
    return true;
}

function getApplicationDetail(){
	
		//Validate file as click on view
		var session_id=$('#session_id').val();
		
		if(session_id == ""){ 
			$('#session_id').focus();	
			showerr($("#session_id")[0], "Session is required.","block");
			return false;	
		}
		var inspection_by=$('#inspection_by').val();
		var inst_name=$('#inst_name').val();
		var mobile_no=$('#mobile_no').val();
		var email_id=$('#email_id').val();
		var XTODATE=$('#XTODATE').val();
		var XFROMDATE=$('#XFROMDATE').val();
	try {
		$.ajax({
			type: "POST",
			url: "../InspectorInspectionService",
			data:{"fstatus":"GET_REOPEN_DETAILS", "session_id":session_id,"inst_name":inst_name,"mobile_no":mobile_no,
				"email_id":email_id,"XTODATE":XTODATE,"XFROMDATE":XFROMDATE,"inspection_by":inspection_by},
			async: false,
			success: function (response){
				console.log("response");
				console.log(response.Applicationlist);
				if (typeof response.Applicationlist!= 'undefined' && response.Applicationlist.length > 0) {
				$('#stable').html("");
				 var index=0;
					$.each(response.Applicationlist, function (key, val) {
						$("#searchTable").show();
						var session 		= val.session;
						var session_id 		= val.session_id;
	                    var AF_REG_ID 		= val.AF_REG_ID;
	                    var email 			= val.email;
	                    var contact 		= val.contact;
	                    var REG_NO 			= val.REG_NO;
	                    var REG_FOR_NAME 	= val.REG_FOR_NAME;
	                    var Payment_Date 	= val.Payment_Date;
	                    var panel_code 		= val.panel_code;
	                    var remarks 		= val.remarks;
	                    var is_final_submit_app	= val.is_final_submit_app;
	                    var is_pannel_member= val.is_pannel_member;
	                    var is_convinor		= val.is_convinor;
	                    var convinor_id		= val.convinor_id;
	                    var isfinalsubmited_convinor= val.isfinalsubmited_convinor;
	                    
	                    if(is_pannel_member=='Y' /*|| $('#user_status').val()=='A'*/){
	                    	index=index+1;
	                    var cols ='<tr>'
	                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+contact+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+email+'</td>'
	                    	if(is_final_submit_app=='Y'){
	                    		if(isfinalsubmited_convinor=='Y'){
	                    			cols +='<td style="text-align:center; width:8%; color: #green;"><b>Application Submitted by Inspection Panel</b></td>'
	                    		}else{
	                    			cols +='<td style="text-align:center; width:8%;"><button type="button" class="btn btn-view1" style="color:green; background-color:#2c5e2c !important; color: #ffffff;" onclick=appReopen('+"'"+''+AF_REG_ID+''+"'"+')>Re-Open</button></td>'
                    			}
	                    		cols +='<td style="text-align:center; width:8%; color: #2c5e2c;"><b>Submitted</b></td>'	
	                    	}else{
	                    		cols +='<td style="text-align:center; width:8%; color: #green;"><b>Application Reopened by Panel</b></td>'
	                    		/*if(is_convinor=='Y'){
	                    		cols +='<td style="text-align:center; width:8%;"><button type="button" class="btn btn-view1" style="background-color:#2c5e2c !important; color: #ffffff;" onclick=openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+','+"'"+''+isfinalsubmited+''+"'"+','+"'"+''+is_pannel_member+''+"'"+','+"'"+'Y'+"'"+',\''+is_convinor+'\',\''+convinor_id+'\')>Submit</button></td>'
	                    		}else{*/
                    			cols +='<td style="text-align:center; width:8%; color: #a94442f7;"><b>Not Submitted By College</b></td>'	
	                    		/*}*/
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
					    displaySuccessMessages("errMsg4", data, "");
						clearSuccessMessageAfterFiveSecond("errMsg4");
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



function appReopen(Inst_Id){
	try {
		var insp_remarks="Application Re-open from CDO/ADM Nominated by DM Page";
		var insp_status="RO";
		
		/*if(insp_status=="RO"){
			var yes = confirm("Are you sure to re-open the application \nRe-open mail will send to college with the given remarks.")
			if (yes == false) {
			return;
			}
		}*/
		var jsonobj={"Inst_Id":Inst_Id,"insp_remarks":insp_remarks, "insp_status":insp_status, "inspection_type":"Inspector_head", "session_id": $('#session_id').val()};
		var encData = encAESData($("#AESKey").val(), jsonobj);
		
		$('#btnSave').hide();
		$('#btnReset').hide();
		try{
			$.ajax({
				type: "POST",
				url: "../InspectorInspectionService",
				data: {encData: encData, fstatus: 'SAVE_REOPEN'},
				dataType: "json",
				success : function(response) {
				var data = decAESData($("#AESKey").val(), response);
				$('#btnSave').show();
				$('#btnReset').show();
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
						 parent.location.reload();
					}, 2000);
				}
			},
			error: function(xhr, status, error) {
				alert(xhr.responseText);
				alert(status);
				alert("error :"+error);
				
				$('#btnSave').show();
				$('#btnReset').show();
			}
			});	
		} catch (e) {
			// TODO: handle exception
			alert("ERROR :"+e);
		}
		
	} catch (e) {
		// TODO: handle exception
		alert("ERROR :"+e);
	}
}