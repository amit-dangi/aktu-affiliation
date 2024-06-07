/**
 * @author Amit dangi
 */
$(document).ready(function(){
	$(".hidediv").hide();
	$("#btnReset, #btnReset1, #btnReset2").click(function(){
		location.reload();
	});
	// use for the get  AcademicSession  
	getAcademicSession();
	
	$("#btnSave").click(function(){
			try {
			var selectedApplication = $('#selectedApplication').val();
			var panel_id = $('#panel_id').val();
			var remarks = $('#remarks').val();

			if (panel_id == "" || panel_id == null) {
				showerr($("#panel_id")[0], " Panel is required", "block");
				$("#panel_id").focus();
				return false;
				}
			
			var jsonObject = {
				selectedApplication : selectedApplication,
				panel_id : panel_id,
				"remarks" : remarks
			};

			console.log("Sending jsonObject :" + JSON.stringify(jsonObject));
			var encData = encAESData($("#AESKey").val(), jsonObject);

			$('#btnSave').hide();
			$('#btnReset').hide();
				
				$.ajax({
					type: "POST",
					url: "../ReviewApplicationService",
					data: {encData: encData, fstatus: "S"},
					dataType: "json",
					success : function(response) {
					var data = decAESData($("#AESKey").val(), response);
					$('#btnSave').show();
					$('#btnReset').show();
					if (data.flag == "Y") {
						setTimeout(function() {
							displaySuccessMessages("errMsg1", data.status, "");
							$('#Detailsdiv').hide();
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
						$('#btnReset').show();
					}
				});	
			} catch (e) {
				// TODO: handle exception
				alert("error :"+e);
			}
	});
	
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
			url: "../ReviewApplicationService",
			data:{"fstatus":"GETDETAILS", "session_id":session_id,"inst_name":inst_name,"mobile_no":mobile_no,
				"email_id":email_id,"XTODATE":XTODATE,"XFROMDATE":XFROMDATE},
			async: false,
			success: function (response){
				$('#stable').html("");
				console.log("response");
				console.log(response.Applicationlist);
				 var index=0;
					$.each(response.Applicationlist, function (key, val) {
						$("#searchTable").show();
						var session 	= val.session;
	                    var AF_REG_ID 	= val.AF_REG_ID;
	                    var email 		= val.email;
	                    var contact 	= val.contact;
	                    var REG_NO 		= val.REG_NO;
	                    var REG_FOR_NAME 	= val.REG_FOR_NAME;
	                    var Payment_Date 	= val.Payment_Date;
	                    var panel_code 	= val.panel_code;
	                    var remarks 	= val.remarks;
	                    var bg_color=panel_code!=""?"#0080001f":"";
	                    index=index+1;
	                    var cols ='<tr style="background-color:'+bg_color+'" >'
	                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+contact+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+email+'</td>'
	                    	+'<td style="text-align:center; width:10%;cursor: pointer;"><a onclick="downloadJasperReport('+"'"+''+AF_REG_ID+''+"'"+');"><b>Click Here to View</b></a></td>'
	                    	+'<td style="text-align:center; width:6%;">'
	                    	+'<button type="button" class="btn btn-success" '
	                    	+'id="" onClick="actionClick('+"'"+''+session+''+"'"+','+"'"+''+REG_FOR_NAME+''+"'"+','+"'"+''+contact+''+"'"+','+"'"+''+email+''+"'"+','+"'"+''+Payment_Date+''+"'"+','+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+panel_code+''+"'"+','+"'"+''+remarks+''+"'"+')">Action</button>'
	                    	+'</td></tr>'
	                    	+'<tr id="Detailsdiv_'+AF_REG_ID+'" style="display: none;"><td colspan="8">'
	                    	/*+'<input type="text" class="form-control" disabled id="Xsession" name="Xsession" value="">'*/
	                    	
	                    	+'<div class="form-group">'
	                    	+'<div class="col-md-12">'
	                    	+'<div class="row">'
	                    	+'<label class="col-sm-2 col-form-label" for="">View Payment</label>'
	                    	+'<div class="col-sm-4">'
	                    	+'<a href="javscript:void(0)" style="text-decoration: underline;padding-left:0px;padding-right:0px; cursor: pointer;" onclick="getReport(\'paymentdetails\');"><b>View Payment Details</b></a>'
	                    	+'</div>'
	                    	+'</div>'
	                    	+'</div>'
	                    	+'</div>'
	     				 
	                    	+'<div class="form-group">'
	                    	+'<div class="col-md-12">'
	                    	+'<div class="row">'
	                    	+'<label class="col-sm-2 col-form-label required-field" for="">Select Panel</label>'
	                    	+'<div class="col-sm-3">'
	                    	+'<select class="form-control" id="panel_id" name="panel_id" >'
	                    	+'</select>'
	                    	+'</div>'
	                    	+'<a href="javscript:void(0)"   class="col-sm-1 col-from-label" style="text-decoration: underline;padding-left:0px;padding-right:0px;cursor: pointer; " onclick="getReport();"><b>View Member </b></a>'
	                    	+'<label class="col-sm-2 col-form-label" for="">Remarks</label>'
	                    	+'<div class="col-sm-4">'
	                    	+' <textarea class="" id="remarks" maxlength="250" style="height: 80px;width: 100%;resize:vertical;max-height:120px;"></textarea>'
	                    	+' </div>'
	                    	+'</div>'
	                    	+'</div>'
	                    	+'</div>'
	    				
	    				
	                    	+'<input type="hidden" id="selectedApplication" value=""/>'
	                    	+'<div class="col-md-12 text-center m-t-20">'
	                    	+'<button type="button" class="btn btn-view" id="btnSave" onClick="vldSave()">Save</button>'
	                    	+'<button type="button" class="btn btn-view" id="btnReset" onClick="btnReset();">Reset</button>' 
	    					+'</div>'
	                    	+' </td></tr>'
	        				$('#stable').append(cols);
	                    	$("#searchTable").show();
	                    	$("#searchTableHeader").show();
	                    	 var optionsHTML = document.getElementById('dropdownOptions').innerHTML;
	                         console.log(optionsHTML);
	                         document.getElementById('panel_id').innerHTML = optionsHTML;
					});
			}
			
		});
	} catch (e) {
		// TODO: handle exception
		alert("ERROR :"+e);
	}
	
}

function actionClick(session,REG_FOR_NAME,contact,email,Payment_Date,AF_REG_ID,panel_code,remarks){
	$('#Xsession').val(session);
	$('#Xinst_name').val(REG_FOR_NAME);
	$('#Xmobile_no').val(contact);
	$('#Xemail_id').val(email);
	$('#payment_date').val(Payment_Date);
	$('#panel_id').val(panel_code);
	$('#remarks').val(remarks);
	if (AF_REG_ID == "") {
		alert("Application Id not found.");
		return false;
	}
	$('#selectedApplication').val(AF_REG_ID);
	$('#Detailsdiv_'+AF_REG_ID+'').show();
	$("#jasperdiv").hide();
	 /*$('html, body').animate({
         scrollTop: $("#Detailsdiv").offset().top
     }, 3000);*/
}

function showTable(i) {
	try {
		for (k = 1; k <= 4; k++) {
			document.getElementById("ftb" + k).className = "fileTabs";
			document.getElementById("tab" + k).style.display = "none";
			document.getElementById("ftb" + k).style.background = "#0A819C";
		}
		document.getElementById("ftb" + i).className = "fileTabs";
		document.getElementById("tab" + i).style.display = "";
		document.getElementById("ftb" + i).style.background = "#ff8c00";

		$("#currenttab").val(i);
	} catch (err) {
		alert(err.message);
	}
}

function ishowTable(i) {
	document.getElementById("ftb" + i).className = "fileTabs_show";
}
 document.onkeydown = function(evt) {
	var keyCode = evt ? (evt.which ? evt.which : evt.keyCode) : event.keyCode;
	if (keyCode == 123) {
		return false;
	} else if (keyCode == 17) {
		return false;
	} else {
		return true;
	}
};


// Added By Ashwani Kumar use for View Member list  Date (10 -Jan -2024 )
function getReport(mode) {
	var selectedApplication = $('#selectedApplication').val();
	var id = $('#panel_id').val();
	if ((id == "" || id == null) && (mode!="paymentdetails")) {
		showerr($("#panel_id")[0], " Panel is required", "block");
		$("#panel_id").focus();
		return false;
		}
	else{
	callModal(id,selectedApplication,mode);
		}
	}
//Added By Ashwani Kumar use for View Application Details Date (10 -Jan -2024 )

function ViewDetails() {
	var id = $('#panel_id').val();
	callModal(id);
}

function callModal(id,selectedApplication,mode){
			 $('#reportDiolog').modal({backdrop: 'static', keyboard: false},'show');
			 $('#showframe').html("");
			 $('#showframe').append('<iframe class="embed-responsive-item" onload="resizeIframe(this)" name="1_Report" id="1_Report" width="100%;" height="" src="review_application_panel_list.jsp?panel_id='+id+'&mode='+mode+'&selectedApplication='+selectedApplication+' " frameborder="0" scrolling="no"></iframe>');
}
