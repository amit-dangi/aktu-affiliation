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
	getDist('ST0001','','district');
	
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
		var district=$('#district').val();
		var request_name=$('#request_name').val();
	try {
		$.ajax({
			type: "POST",
			url: "../ReviewApplicationService",
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
						var session 	= val.session;
	                    var AF_REG_ID 	= val.AF_REG_ID;
	                    var email 		= val.email;
	                    var contact 	= val.contact;
	                    var REG_NO 		= val.REG_NO;
	                    var REG_FOR_NAME 	= val.REG_FOR_NAME;
	                    var Payment_Date 	= val.Payment_Date;
	                    var panel_code 	= val.panel_code;
	                    var remarks 	= val.remarks;
	                    var clg_code 	= val.clg_code;
	                    var is_final_submit_app_dt=val.is_final_submit_app_dt;
	                    var bg_color=panel_code!=""?"#0080001f":"";
	                    index=index+1;
	                    var cols ='<tr style="background-color:'+bg_color+'" >'
	                    	+'<td style="text-align:center; width:2%;">'+index+'</td>'
	                    	+'<td style="text-align:center; width:7%;">'+is_final_submit_app_dt+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+contact+'</td>'
	                    	+'<td style="text-align:center; width:7%;">'+email+'</td>'
	                    	+'<td style="text-align:center; width:10%;cursor: pointer;"><a onclick="downloadJasperReport('+"'"+''+AF_REG_ID+''+"'"+',\''+REG_FOR_NAME+'\');"><b>Click Here to View</b></a></td>'
	                    	+'<td style="text-align:center; width:10%;cursor: pointer;">'
	                    	+'<a href="../ReviewApplicationDownloadZipFile?filename='+AF_REG_ID+'&af_reg_no='+REG_NO+'&clg_code='+clg_code+'&folderName=AKTU_AFFILIATION_PORTAL&fstatus=dwnZipFrmDir">'
	                    	+'<span  style="cursor:pointer" >'
	                    	+'<i style="color: black" class="fa fa-download p-l-3"></i></span></a></td>'
	                    	
	                    	+'<td style="text-align:center; width:6%;">'
	                    	+'<button type="button" class="btn btn-success" '
	                    	+'id="" onClick="actionClick('+"'"+''+session+''+"'"+','+"'"+''+REG_FOR_NAME+''+"'"+','+"'"+''+contact+''+"'"+','+"'"+''+email+''+"'"+','+"'"+''+Payment_Date+''+"'"+','+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+panel_code+''+"'"+','+"'"+''+remarks+''+"'"+')">Action</button>'
	                    	+'</td>'
	                    	+'</tr>'
	        				$('#stable').append(cols);
	                    	$("#searchTable").show();
	                    	$("#searchTableHeader").show();
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
	$("#Detailsdiv").show();
	$("#jasperdiv").hide();
	 $('html, body').animate({
         scrollTop: $("#Detailsdiv").offset().top
     }, 3000);
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
