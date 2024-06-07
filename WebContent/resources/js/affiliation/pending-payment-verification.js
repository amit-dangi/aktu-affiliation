/**
 * @ AUTHOR Amit DanGi
 */

$(document).ready(function(){

	$('#btnReset').click(function(){    // call on Reset  button
		location.reload();
	});
	
	$("#btnView").click(function() {
			try{
				if($('#type').val()==''){
					$('#type').focus();
					showerr($("#type")[0], "Payment Type is required!","block");   
					return false;
				}
				
				   $("#pendingpaymentd").attr("action","pending_payment_verification_l.jsp");
			  	   $("#pendingpaymentd").attr("method","post");
			  	   $("#pendingpaymentd").attr("target","iframdoubleverification");
			  	   $("#pendingpaymentd").submit();

			}catch(err){
				alert("Error :"+err);
			}
	});

	$('#btnSave').click(function(){
		var len = $('input:radio:checked[name^="radioB"]').length;
		if (len > 0){
			try {
				var str = $('input:radio:checked[name^="radioB"]').val();
				var arr = str.split(',');
				var order_no=arr[0];
				var tracking_id=arr[2];
				var paymenttype=arr[3];
				
				$("#btnSave").hide();
				var objjson= {"merchantorderno":order_no, "tranId":tracking_id,"paymenttype":paymenttype};
				console.log("Sending jsonObject :" + JSON.stringify(objjson));
				var encData = encAESData($("#AESKey").val(), objjson);
				$.ajax({
					type: "POST",
					url: "PendingPaymentVerificationService",
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
							$("#btnSave").show();
						}
					}
				});
			}catch(err){
				alert("Error Caused in Save Record :"+err);
			}
		}else{
			document.getElementById("errMsg1").innerHTML="<center><strong>At least one student should be checked !</strong></center>";
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