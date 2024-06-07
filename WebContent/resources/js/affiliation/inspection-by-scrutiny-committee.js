$(document).ready(function(){
	$(".hidediv").hide();
	$('#btnSave').hide();
	$("#btnReset").click(function(){
		location.reload();
	});
	
	
	$("#insp_status").on("change", function(){
		$('#btnSave').show();
		var selectedValue = $(this).val();
	    if (selectedValue === "Y") {
	        $('#btnSave,#btnSave1').text("Application Accepted & transfer to Inspection Committee");
	    } else if (selectedValue === "N") {
	        $('#btnSave,#btnSave1').text("Application Rejected & acknowledgement mail shared to college");
	    } else {
	        $('#btnSave,#btnSave1').text("Application Re-open & acknowledgement mail shared to college");
	    }
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
			url: "../InspectionByScrutinyCommitteeService",
			data:{"fstatus":"GETDETAILS", "session_id":session_id, "inst_name":inst_name, "mobile_no":mobile_no, "email_id":email_id, "XTODATE":XTODATE, "XFROMDATE":XFROMDATE},
			async: false,
			success: function (response){
				$('#stable').html("");
				console.log("response");
				console.log(response.Applicationlist);
				var index=0;
				if (typeof response.Applicationlist!= 'undefined' && response.Applicationlist.length > 0) {
					$.each(response.Applicationlist, function (key, val) {
						$("#searchTable").show();
						var is_final_submit_app_dt=val.is_final_submit_app_dt;
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
	                    var is_finalsubmit_open=val.is_finalsubmit_open;
	                    var clg_code=		val.clg_code;
	                    var bg_color="";
	                    var status = "";
	                    if(val.STATUS==""){
	                    	status = "Pending";
	                    	bg_color="#f37f7f26";
	                    }else if(val.STATUS=="N"){
	                    	status = "Rejected";
	                    	bg_color="#fb424287";
	                    }else if(val.STATUS=="Y"){
	                    	status = "Approved";
	                    	bg_color="#3d8d3d91";
	                    }else if(val.STATUS=="RO"){
	                    	status = "Re-open";
	                    	bg_color="#18b0f142";
	                    }
	                    
	                    index=index+1;
	                    var cols ='<tr style="background-color:'+bg_color+';" >'
	                    	+'<td style="text-align:center; width:2%;"><b>'+index+'</b></td>'
	                    	+'<td style="text-align:center; width:5%;">'+is_final_submit_app_dt+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
	                    	+'<td style="text-align:center; width:6%;">'+REG_NO+'</td>'
	                    	+'<td style="text-align:center; width:14%; word-break: break-word !important;">'+REG_FOR_NAME+'</td>'
	                    	+'<td style="text-align:center; width:4%;">'+contact+'</td>'
	                    	+'<td style="text-align:center; width:4%;">'+email+'</td>'
	                    	+'<td style="text-align:center; width:6%; cursor: pointer; word-break: break-word !important;"><a onclick="downloadJasperReport('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+')"><b>View Application</b></a></td>'
	                    	+'<td style="text-align:center; width:6%;"><b>'+status+'</b></td>'
	                    	+'<td style="text-align:center; width:6%;cursor: pointer;">'
	                    	+'<a href="../InspectionByScrutinyCommitteeDownloadZipFile?filename='+AF_REG_ID+'&af_reg_no='+REG_NO+'&clg_code='+clg_code+'&folderName=AKTU_AFFILIATION_PORTAL&fstatus=dwnZipFrmDir">'
	                    	+'<span  style="cursor:pointer" >'
	                    	+'<i style="color: black" class="fa fa-download p-l-3"></i></span></a></td>'
	                    	+'<td style="text-align:center; width:6%;">'
	                    		+'<a style="color:green;cursor: pointer;" id="inspectionhref" onclick="openInspection('+"'"+''+AF_REG_ID+''+"'"+', '+"'"+''+session_id+''+"'"+', '+"'"+''+val.STATUS+''+"'"+', '+"'"+''+val.REMARKS+''+"'"+',\''+is_finalsubmit_open+'\')"><b>Preview & Action</b></a>'
	                    	+'</td>'
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

function openInspection(Inst_id, session_id, status, remks,is_finalsubmit_open){
	try {
		if(Inst_id!="" ){
			document.InspectionSCform.target="InspectionDetailViewFrame";
			document.InspectionSCform.action="inspection_by_scrutiny_committee_view.jsp?Inst_Id="+Inst_id+"&session_id="+session_id+"&status="+status+"&remarks="+remks+"&is_finalsubmit_open="+is_finalsubmit_open;
			document.InspectionSCform.submit();
			$('html, body').animate({
				scrollTop: $(".containerframe").offset().top
			}, 800);
		}else{
			alert("ERROR IN getInspection() Inst_id is requiered.");	
		}
	} catch (e) {
		// TODO: handle exception
		alert("ERROR IN inspector_inspection_detail_view.jsp getInspection() -:"+e);
	}
}

function save(savetype, Inst_Id){
	try {
		var insp_remarks=$('#insp_remarks').val();
		var insp_status=$('#insp_status').val();
		
		if(insp_status==""){
			$('#insp_status').focus();	
			showerr($("#insp_status")[0], "Status is required.","block");
			return false;
		}
		if(insp_remarks==""){
			$('#insp_remarks').focus();	
			showerr($("#insp_remarks")[0], "Remarks is required.","block");
			return false;
		}
		
		if(insp_status=="RO"){
			var yes = confirm("Are you sure to re-open the application \nRe-open mail will send to college with the given remarks.")
			if (yes == false) {
			return;
			}
		}
		var jsonobj={"Inst_Id":Inst_Id, "insp_remarks":insp_remarks, "insp_status":insp_status, "inspection_type":"Scrutiny_head", "session_id": $('#session_id').val()};
		var encData = encAESData($("#AESKey").val(), jsonobj);
		
		$('#btnSave').hide();
		$('#btnReset').hide();
		try{
			$.ajax({
				type: "POST",
				url: "../InspectionByScrutinyCommitteeService",
				data: {encData: encData, fstatus: savetype},
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
					}, 3000);
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