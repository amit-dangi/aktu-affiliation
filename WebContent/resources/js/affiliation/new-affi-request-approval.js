/** * @author:Om KUMAR */
 $(document).ready(function(){
	$("#Xstatus").on('change', function(){
	var status= $("#Xstatus").val();
		if(status=="R"){
			  $("#Xshowremarks").show();
		}else{
			  $("#Xshowremarks").hide();
		}
		
	});
 });
 
function  resetForm(){
	  location.reload();
}
function getReport(mast_id) {
	 window.parent.callModal(mast_id);
	}
function btnReset(){
	location.reload();
}

function vldSearch(){
	frmNewAffiReqApprovald.target="btmfrmNewAffiRequestAprrovalD";
	frmNewAffiReqApprovald.action="new_affi_request_approval_e.jsp";
	frmNewAffiReqApprovald.submit();
}

function vldSave(){
	//alert("in "+$("#AESKey").val())
	try{
		var sendMail = "N";
		var status = $("#Xstatus").val();
		 if(status==""||status==null){
			 $("#Xstatus").focus();
				showerr($("#Xstatus")[0], "Status is required", "block");
				return false;
		 }
		if(status=="A"){
			var sendMail = "Y";
		}else{
			var remarks=$("#Xremarks").val();
			 if(remarks==""||remarks==null){
				 $("#Xremarks").focus();
					showerr($("#Xremarks")[0], "Remarks is required when Approval Request is rejected", "block");
					return false;
			 }
			if (($("#send_mail").prop('checked')) == true) {
				sendMail = 'Y';
			}
		}
		var objjson = {"app_status":$("#Xstatus").val(),"remarks":$("#Xremarks").val(),"send_mail":sendMail,
					"mast_id":$("#mast_id").val(),"det_id":$("#det_id").val(),"email_id":$("#Xmail_id").val(),
					"dpassword":$("#dpassword").val()};
		//alert(JSON.stringify(objjson));

		var encData=encAESData($("#AESKey").val(), objjson);
		$.ajax({
		    type : "POST",
		    url  : "NewAffiRequestApprovalService",
		    data: {	encData	 :encData,
	    			fstatus	 :"save"
		    },
		    success : function(data) {
		    	saveOrUpdateCommonFunctionInParty(decAESData($("#AESKey").val(), data));
		    	},
	        error: function() {
	        	alert("Error"); 
	        }
		});
	} catch (err){
		alert(err);
	} 
}

function saveOrUpdateCommonFunctionInParty(data) {
		if(data.flg=="V") {
			displaySuccessMessages("errMsg1", data.errMsg, "");
			clearSuccessMessageAfterFiveSecond("errMsg1");
			setTimeout(function () {
				parent.location.reload();
    		}, 1500);	
	    } else {
			displaySuccessMessages("errMsg2", data.errMsg, "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
	    }
	
}

function getPrgmList(id) {
	let cnt=0, r=0;
	$.ajax({
		type : "POST",
		url  : "NewAffiRequestApprovalService",
		data : {fstatus : "prgm",id:id},
	    success : function(response) {
			if(typeof response.PrgmDetail != 'undefined' && response.PrgmDetail.length > 0){
				$.each(response.PrgmDetail, function (key, val) {
					var newRow = $("<tr>");	 
					var cols = "";
					r = ++cnt;
					
					cols += '<tr id="tr_'+r+'">';
					cols += '<td style="text-align:center; width:5%;">'
								+'<span id="sno_'+r+'">'+parseInt(r)+'</span>'
							+'</td>';
					cols += '<td style="text-align:center; width:20%;">'
								+'<input type="text" id="prog_name_'+r+'" name="prog_name_'+r+'" value="" placeholder="Enter Detail" readonly>'
								+'<input type="hidden" id="prog_id_'+r+'" name="prog_id_'+r+'" value="'+val.PROG_ID+'" placeholder="Enter Detail">'
							+'</td>';
					cols += '<td style="text-align:center; width:20%;">'
								+'<input type="text" maximum="99" id="prog_typ_'+r+'" name="prog_typ_'+r+'" value="" placeholder="Enter Detail" readonly>'
								+'<input type="hidden" maximum="99" id="prog_typ_id_'+r+'" name="prog_typ_id_'+r+'" value="'+val.PROG_TYPE+'" placeholder="Enter Detail">'
							+'</td>';
					cols += '</tr>';
					
					$('#prgmTypeName').append(cols);
					cols=""; 
					//$("#totRowIntakeDetail").val(r);	
					getProgrammeList(val.PROG_ID, r, 'T');
					getProgrammeTypeByProgrammeId(val.PROG_ID, val.PROG_TYPE, r, 'T');
										
				});
			}
	    },
	    error: function(xhr, status, error) {
			alert(xhr.responseText);
			alert(error);
			alert(status);
		}
	});	
}

function getProgrammeList(id, i, typ){
	var hrmsApi =$("#hrmsApi").val().trim();
	var sel="";
	$.ajax({
		type: "GET",
		url: hrmsApi+"rest/apiServices/degree_by_degreetype",
		jsonp: "parseResponse",
		dataType: "jsonp",
		success: function (response){
			if (typeof response.ProgDD != 'undefined' && response.ProgDD.length > 0){
				var moduleHtml = "<option value=''>Select Programme Name</option>";
				
				$.each(response.ProgDD, function (key, val) {
					var widgetKey = val.id;
					var widgetValue = val.name;
				
					if(typ=="T"){
						if(id==widgetKey){
							$("#prog_name_"+i).val(widgetValue);
						}
					}else{
						if(id==widgetKey){
							sel="selected";
							moduleHtml += "<option value='" + widgetKey+ "'"+sel+">" + widgetValue + "</option>";
						}else{
							sel="";
							moduleHtml += "<option value='" + widgetKey + "'"+sel+">" + widgetValue + "</option>";					
						}		
					}
				});
				
				if(typ!="T")				
					$("#prog_name_"+i).html(moduleHtml);
			} else {
				if(typ!="T")
					$("#prog_name_"+i).html("<option value=''>Select Programme Name</option>");
			}
		}
	}); 		
};

function getProgrammeTypeByProgrammeId(prog_id, id, i, typ){
	var hrmsApi =$("#hrmsApi").val().trim();
	var sel="";
	
	$.ajax({
		type: "GET",
		url: hrmsApi+"rest/apiServices/getProgYearTypeByProgramme",
		jsonp: "parseResponse",
		data: {"prog_id":prog_id},
		dataType: "jsonp",
		success: function (response){
			if (typeof response.progYearDropDown != 'undefined' && response.progYearDropDown.length > 0){
				var moduleHtml = "<option value=''>Select Programme Type</option>";
				
				$.each(response.progYearDropDown, function (key, val) {
					var widgetKey = val.id;
					var widgetValue = val.name;
					
					if(typ=="T"){
						if(id==widgetKey){
							$("#prog_typ_"+i).val(widgetValue);
						}
					}else{
						if(id==widgetKey){
							sel="selected";
							moduleHtml += "<option value='" + widgetKey+ "'"+sel+">" + widgetValue + "</option>";
						}else{
							sel="";
							moduleHtml += "<option value='" + widgetKey + "'"+sel+">" + widgetValue + "</option>";					
						}	
					}
				});
				if(typ!="T")
					$("#prog_typ_"+i).html(moduleHtml);
			} else {
				if(typ!="T")
					$("#prog_typ_"+i).html("<option value=''>Select Programme Type</option>");
			}
		}
	}); 		
};