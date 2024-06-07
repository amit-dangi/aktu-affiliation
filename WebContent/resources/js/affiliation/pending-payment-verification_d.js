$(document).ready(function(){

	$('#btnReset').click(function(){    // call on Reset  button
		location.reload();
	});
	
	$('#btnSave').click(function(){
		var len = $('input:radio:checked[name^="radioB"]').length;
		if (len > 0){
			try {
				var str = $('input:radio:checked[name^="radioB"]').val();
				var arr = str.split(',');
				var tracking_id=arr[0];
				var paymenttype=arr[1];
				var objjson= {"tranId":tracking_id,"paymenttype":paymenttype};
				$("#btnSavediv").hide();
				console.log("Sending jsonObject :" + JSON.stringify(objjson));
				var encData = encAESData($("#AESKey").val(), objjson);
				$.ajax({
					type: "POST",
					url: "../PendingPaymentVerificationService",
					data:{"fstatus":"SAVE", "encData":encData},
					async: false,
					success: function (response) {						
						if(response.flag==true){
							displaySuccessMessages("errMsg", response.errMsg,""); 
							clearSuccessMessageAfterFiveSecond("errMsg");
							showWinAfterFiveSecondForNew();							
						}else{
							displaySuccessMessages("errMsg1", response.errMsg,"");
							clearSuccessMessageAfterFiveSecond("errMsg1");
							$("#btnSavediv").show();
						}
					}
				});
			}catch(err){
				alert("Error Caused in Save Record :"+err);
			}
		}else{
			document.getElementById("errMsg1").innerHTML="<center><strong>At least one Record should be checked !</strong></center>";
			clearSuccessMessageAfterFiveSecond("errMsg1");
		}
	});
	
});

function displayMsg(data,fstatus) {	
	setTimeout(function () {
		displaySuccessMessages("errMsg", data, "");
	}, 2000);
	clearSuccessMessageAfterTenSecond("errMsg");
	if (fstatus == "N") 
		showWinAfterFiveSecondForNew();          
}

function showWinAfterFiveSecondForNew() {
	setTimeout(function () {
		location.reload();
    }, 5000);
}

function getApplicationDetail(){ 
	
	//Validate file as click on view
	var type = $('#type').val();
	if (type == "") {
	$('#type').focus();
	showerr($("#type")[0], "Fee Type is required.", "block");
	return false;
	}
	var regno=$('#regno').val();
	var XTODATE=$('#XTODATE').val();
	var XFROMDATE=$('#XFROMDATE').val();
	
try {
	$.ajax({
		type: "POST",
		url: "../PendingPaymentVerificationService",
		data:{"fstatus":"GETDETAILS", "type":type,"regno":regno,
				"XTODATE":XTODATE,"XFROMDATE":XFROMDATE},
		async: false,
		success: function (response){
			$("#btnSavediv").show();
			$('#stable').html("");
			console.log("response");
			console.log(response.Applicationlist);
			if (typeof response.Applicationlist!= 'undefined' && response.Applicationlist.length > 0) {
				var index=0;
				$.each(response.Applicationlist, function (key, val) {
					$("#searchTable").show();
					var PROP_INST_NAME 	= val.PROP_INST_NAME;
                    var order_id 	= val.order_id;
                    var REG_NO 		= val.REG_NO;
                    var order_status 	= val.order_status;
                    var status_message 		= val.status_message;
                    var date = val.date;
                    var amount 	= val.amount;
                    var id 	= val.id;
                    var dt  =val.dt;
                    var tracking_id =val.tracking_id;
                    var paymenttype=val.paymenttype;
                    index=index+1;
                    var disabled=order_status=='paid'?'disabled':"";
                    var cols ='<tr>'
                    	+'<td style="text-align:center; width:3%;"><input type="radio" id="radioB" name="radioB" value="'+tracking_id+','+paymenttype+'" '+disabled+'></td>'
                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
                    	+'<td style="text-align:center; width:14%;">'+PROP_INST_NAME+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+order_id+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+date+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+order_status+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+status_message+'</td>'
                    	+'</tr>'
        				$('#stable').append(cols);
                    	$("#searchTable").show();
				});
				
			}else{
				$("#btnSavediv").hide();
				$('#stable').html("");
				$('#searchTable').hide();
				var data ='No Data Found';
			    displaySuccessMessages("errMsg4", data, "");
				clearSuccessMessageAfterFiveSecond("errMsg4");
				setTimeout(function() {
					location.reload();
				}, 1000);
		  }
		}
		
	});
} catch (e) {
	// TODO: handle exception
	alert("ERROR :"+e);
}
}
