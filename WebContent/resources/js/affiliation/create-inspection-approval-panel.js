$(document).ready(function(){ 
	$("#XBTNRESET").click(function() {
	location.reload();
});   
	 
$("#X_BTNRESET").click(function(){
		document.getElementById("frmAffliD").reset();
		});
$("#X_BTNSEARCH").click(function() {
	document.frmAffliD.target = "btmAffliD";
	document.frmAffliD.action = "create_inspection_approval_panel_l.jsp";
	document.frmAffliD.submit();
});
$("#XBTNBCK").click(function() {
	window.location.href = "create_inspection_approval_panel_e.jsp";
});
$("#XHREF").click(function() {
	window.location.href = "create_inspection_approval_panel_e.jsp";
});

$("#addQuotation").click(function(){
	var index=parseInt($('#count').val());
	if($('#XMTYPE_'+index).val()=="")
	{
		   $('#XMTYPE_'+index).focus();
		   showerr($('#XMTYPE_'+index)[0],"Member Type  is required","block");
		   return false;
    }
	
	
	if(($('#XNAME_'+index).val()=="" || $('#XNAME_'+index).val()==null) && $('#XNAME_'+index).val()!=undefined)
	{ 
		   $('#XNAME_'+index).focus();
		   showerr($('#XNAME_'+index)[0],"Name  is required","block");
		   return false;
    }
	if(($('#XDEPARTMT_'+index).val()=="" || $('#XDEPARTMT_'+index).val()==null) && $('#XDEPARTMT_'+index).val()!=undefined)
	{ 
		   $('#XDEPARTMT_'+index).focus();
		   showerr($('#XDEPARTMT_'+index)[0],"Department Name is required","block");
		   return false;
	}
	if(($('#XPOST_'+index).val()=="" || $('#XPOST_'+index).val()==null) && $('#XPOST_'+index).val()!=undefined)
	{ 
		   $('#XPOST_'+index).focus();
		   showerr($('#XPOST_'+index)[0],"Designation Name is required","block");
		   return false;
	}
	if(($('#XEMAIL_'+index).val()=="" || $('#XEMAIL_'+index).val()==null) && $('#XEMAIL_'+index).val()!=undefined)
	{
	   $('#XEMAIL_'+index).focus();
	   showerr($('#XEMAIL_'+index)[0],"Email is required","block");
	   return false;
	}if(($('#XCONTACT_'+index).val()=="" || $('#XCONTACT_'+index).val()==null) && $('#XCONTACT_'+index).val()!=undefined)
		{
			$('#XCONTACT_'+index).focus();
			showerr($('#XCONTACT_'+index)[0],"Contact is required","block");
			return false;
	}if(($('#XCONV_'+index).val()=="" || $('#XCONV_'+index).val()==null) && $('#XCONV_'+index).val()!=undefined)
	{
		$('#XCONV_'+index).focus();
		showerr($('#XCONV_'+index)[0],"Convenor is requrired","block");
		return false;
   }
	if(($('#XACT_'+index).val()=="" || $('#XACT_'+index).val()==null) && $('#XACT_'+index).val()!=undefined)
	{
		$('#XACT_'+index).focus();
		showerr($('#XACT_'+index)[0],"IsActive is requrired","block");
		return false;
   }
	else
	{
		var text="";
		for(var i=index;i>=0;i--)
		{
			if($("#srn_"+i).text()!=undefined)
			{	
				text=$("#srn_"+i).text();
				if(text!='')
					break;
			}
		}
		index=index+1;
		//alert("index :: "+index);
		 var des="";
		if($("#XCONV_1").is(":checked")){
			 des="disabled";
			}else{
				des="";
			}
		var newRow = $("<tr>");	 
		var cols = '<tr >'
			    +' <td style="text-align:center; width:3%;" id="srn_'+index+'">'+(parseInt(text)+1)+'</td>'
			    +'<td style="text-align:center; width:10%;">'
			    +'<select class="form-control" id="XMTYPE_'+index+'" name="XMTYPE_'+index+'" onchange="getEmpbymember(this.value,'+index+');">'
			    +'<option value="">Select Member Type</option>'
			    +'<option value="I">Internal</option>'
			    +'<option value="E">External</option>'
			    +'</select>'
			    +'</td>'
				+' <td style="text-align:center; width:10%;">'
				+' <select class="form-control" id="XNAME_'+index+'" name="XNAME_'+index+'" onchange="getExternalEmpDetails(this.value,'+index+')">'
				+' <option value="">Select Member Name</option>'
				+' </select></td>'
				+'<option value=""> Select Member Type</option>'
				+' <td style="text-align:center; width:10%;">'
				+' <select class="form-control" id="XDEPARTMT_'+index+'" name="XDEPARTMT_'+index+'" disabled>'
				+' <option value="">Select Department Type</option>'
				+' </select></td>'
				+' <td style="text-align:center; width:10%;"> '
				+' <select class="form-control " id="XPOST_'+index+'" name="XPOST_'+index+'" disabled>'
				+' <option value="">Select Post Type </option>'
				+' <td style="text-align:center; width:10%;">'
				+' <input type="text" class="form-control" id="XEMAIL_'+index+'" name="XEMAIL_'+index+'" value=""  maxlength="29" onblur="validEmail(this);" placeholder="Enter the Email Id  " disabled>'
				+' <td style="text-align:right; width:8%;">'
				+' <input type="text" class="form-control" id="XCONTACT_'+index+'" name="XCONTACT_'+index+'"  maxlength="10"  onkeypress="return isNumberKeys(event);" placeholder="Enter Phone No  ">'
				+' <td style="text-align:center; width:10%;">'
				+'<input type="checkbox" class="checkboxcon" id="XCONV_'+index+'" name="XCONV_'+index+'" '+des+' onclick="checkfn('+index+');"></td>'
				+'<td style="text-align:right; width:10%;">'
				+' <select class="form-control " id="XACT_'+index+'" name="XACT_'+index+'">'
				+' <option value="">Select</option>'
				+'<option value="Y">Yes</option>'
			    +'<option value="N"> No </option>'
			    +'</select></td>'
			    
			    +'<td class="colr-red-p text-center" style=" width:5%; color:red;cursor: pointer;" id="Panel_det_id_'+index+'" onclick="deleteDetailsdata('+-1+','+index+',this);">'
				+' <input type="hidden" ><span id=""><i class="fa fa-trash" >&nbsp;Delete</i></span>';
				+'</td>'
				
				+'</tr>';		
			$('.Quotation').append(cols);
			$("#count").val(index);
			//memberNamedropdown('',index);
		/*	getEmployee('','XNAME_'+index);
			getDeprtment('','XDEPARTMT_'+index);
			getDesignation('','XPOST_'+index);*/
	}
});

var fstatus=$("#fstatus").val();
if(fstatus=='E'){ 
	  var jsondata=$("#jsonddata").val();  
	  var obj = jQuery.parseJSON(jsondata); 
	  $.each(obj, function (index, value) {
			 des="";
			  index=index+1;
			  if(index==1){
				  des="disabled";
			  }
			  
		  var emp_type=value["emp_type"].toString();
		  var Panel_det_id=value["Panel_det_id"].toString();
		  var member_type=value["member_type"].toString();
		  var department=value["department"].toString();
		  //alert("check new "+department);
		  var designation=value["designation"].toString();
		  var email_id=value["email_id"].toString();
		  var contant_no=value["contant_no"].toString();
		  var is_convenor=value["is_convenor"].toString();
		  var issActive=value["issActive"].toString();
		  if(is_convenor=='Y'){
			  check="checked";
		  }else{
			  check="disabled";
		  }
		  console.log("member_type||"+member_type);
		  var sel="";
		  var sel1="";
		  var newRow = $("<tr>");	 
		  var cols = '<tr>'
			  cols +='<td style="text-align:center; width:3%;" id="srn_'+index+'">'+index+'</td>'
			    cols +='<td style="text-align:center; width:10%;">'
		    	cols +='<select class="form-control" id="XMTYPE_'+index+'" name="XMTYPE_'+index+'" onchange="getEmpbymember('+"'"+''+emp_type+''+"'"+','+index+','+"'"+''+member_type+''+"'"+');" disabled>'
		    	cols +='<option value="">Select Member Type</option>'
	    		cols +='<option value="I" '+(emp_type=="I"?"selected":"")+'>Internal</option>'
				cols +='<option value="E" '+(emp_type=="E"?"selected":"")+'>External</option>'
	    		cols +='</select></td>'
	    		cols +=' <td style="text-align:center; width:10%;">'
				+' <select class="form-control" id="XNAME_'+index+'" name="XNAME_'+index+'" onchange="getExternalEmpDetails('+"'"+''+member_type+''+"'"+','+index+')" disabled>'
				+' <option value="">Select Member Name</option>'
				+' </select></td>'
				+'<option value=""> Select Member Type</option>'
				+' <td style="text-align:center; width:10%;">'
				+' <select class="form-control" id="XDEPARTMT_'+index+'" name="XDEPARTMT_'+index+'" disabled>'
				+' <option value="">Select Department </option>'
				cols +=' </select></td>'
				+' <td style="text-align:center; width:10%;"> '
				+' <select class="form-control " id="XPOST_'+index+'" name="XPOST_'+index+'" disabled>'
				+' <option value="">Select Post Type </option>'
				cols +=' </select></td>'
				cols +=' <td style="text-align:center; width:10%;">'
				cols +=' <input type="text" class="form-control" id="XEMAIL_'+index+'" name="XEMAIL_'+index+'"   value="'+email_id+'"  maxlength="29" onblur="validEmail(this);" placeholder="Enter the Email Id" disabled>'
				+' <td style="text-align:right; width:8%;">'
				cols +=' <input type="text" class="form-control" id="XCONTACT_'+index+'" name="XCONTACT_'+index+'""  value="'+contant_no+'" maxlength="10" onkeypress="return isNumberKeys(event);" placeholder="Enter Phone No" disabled>'
				+' <td style="text-align:center; width:10%;">'
				cols +='<input type="checkbox" class="checkboxcon"   id="XCONV_'+index+'" name="XCONV_'+index+'" '+check+' onclick="checkfn('+index+');"></td>'
				+'<td style="text-align:right; width:10%;">'
				+' <select class="form-control " id="XACT_'+index+'"    name="XACT_'+index+'" value="'+check+'" >'
				+' <option value="">Select</option>'
				if(issActive=='Y'){
					sel="selected";
				}if(issActive=='N'){
					sel1="selected";
				}
				cols +='<option value="Y" '+sel+'>Yes</option>'
				cols +='<option value="N" '+sel1+'>No </option>'
				cols +='</select>'
			    +' <input type="hidden" id="Panel_det_id_'+index+'" value="'+Panel_det_id+'"/>'				
			    +' </td>'
			    
			    +'<td class="colr-red-p text-center" style=" width:5%; color:red;cursor: pointer;" id="Panel_det_id_'+index+'" onclick="deleteDetailsdata('+Panel_det_id+','+index+',this);">'
				+' <input type="hidden" ><span id=""><i class="fa fa-trash" >&nbsp;Delete</i></span>';
				+'</td>'
				+'</tr>';		
				$('.Quotation').append(cols);
				$("#count").val(index);
	       		//memberNamedropdown(member_type,index);
				//getEmployee(member_type,'XNAME_'+index);
	       		//getDeprtment(department,'XDEPARTMT_'+index);
	       		//getDesignation(designation,'XPOST_'+index);
				getEmpbymember(emp_type,index,member_type);
				getExternalEmpDetails(member_type,index);
		}); 
}
});

function saveRecord(){
	var panelCode		= $("#XPANELC").val();
	var panelName   	= $("#XPANELN").val();
	var isActive	    = $("#XISACTIVE").val();
	if (panelCode =="" || panelCode  == null) {
		$("#XPANELC").val("");
		$("#XPANELC").focus();
		showerr($("#XPANELC")[0], "Panel Code is required", "block");
		return false;
	} 
	 
	 if (panelName ==""  || panelName  == null){
		$("#XPANELN").val("");
		$("#XPANELN").focus();
		showerr($("#XPANELN")[0], "Panel Name is required", "block");
		return false;
	}
	 if (isActive ==""  || isActive  == null){
			$("#XISACTIVE").val("");
			$("#XISACTIVE").focus();
			showerr($("#XISACTIVE")[0], "isActive is required", "block");
			return false;
		}
	 else{
		executeSaveRecord();
	}
}
function executeSaveRecord() {  
	try {
		 var cnt = parseInt($("#count").val()); 
		 var affDetail = [];
		if ($("#XISACTIVE").is(":checked"))
		{
		 var is_active = 'Y';
		}
		
		if (cnt > 0) {
			var is_con = '';
			for (var i = 1; i <=cnt; i++) {
				
				if (($("#XMTYPE_"+i).val() ==""  || $("#XMTYPE_"+i).val()  == null) && $("#XMTYPE_"+i).val()!=undefined){
					showerr($("#XMTYPE_"+i)[0], "Member Type is required", "block");
					$("#XMTYPE_"+i).focus();
					return false;
				}
				
				if (($("#XNAME_"+i).val() ==""  || $("#XNAME_"+i).val()  == null) && $("#XNAME_"+i).val()!=undefined){
					showerr($("#XNAME_"+i)[0], "Member Name is required", "block");
					$("#XNAME_"+i).focus();
					return false;
				}
				
				if (($("#XDEPARTMT_"+i).val() ==""  || $("#XDEPARTMT_"+i).val()  == null) && $("#XDEPARTMT_"+i).val()!=undefined){
					showerr($("#XDEPARTMT_"+i)[0], "Department is required", "block");
					$("#XDEPARTMT_"+i).focus();
					return false;
				}
				if (($("#XPOST_"+i).val() ==""  || $("#XPOST_"+i).val()  == null) && $("#XPOST_"+i).val()!=undefined){
					showerr($("#XPOST_"+i)[0], "Designation is required", "block");
					$("#XPOST_"+i).focus();
					return false;
				}
				if (($("#XEMAIL_"+i).val() ==""  || $("#XEMAIL_"+i).val()  == null) && $("#XEMAIL_"+i).val()!=undefined){
					showerr($("#XEMAIL_"+i)[0], "Email Id is required", "block");
					$("#XEMAIL_"+i).focus();
					return false;
				}
				 
				if (($("#XCONTACT_"+i).val() ==""  || $("#XCONTACT_"+i).val()  == null) && $("#XCONTACT_"+i).val()!=undefined){
					showerr($("#XCONTACT_"+i)[0], "Contact No. is required", "block");
					$("#XCONTACT_"+i).focus();
					return false;
				}
				if ($("#XCONTACT_"+i).val() !="" && $("#XCONTACT_"+i).val()!=undefined){
					  if(!$("#XCONTACT_"+i).val().match(Phonevalidation()) ){
						showerr($("#XCONTACT_"+i)[0], "Please enter a valid Contact Number.","block");
						$("#XCONTACT_"+i).focus();
						   return false;
					   }
					}
				
				if (($("#XACT_"+i).val() ==""  || $("#XACT_"+i).val()  == null) && $("#XACT_"+i).val()!=undefined){
					showerr($("#XACT_"+i)[0], "Is Active is required", "block");
					$("#XACT_"+i).focus();
					return false;
				}
				
				if(cnt>1){
					for(var j=i+1; j<=cnt; j++){
						
						if($("#XNAME_"+i).val()==$("#XNAME_"+j).val() && $("#XNAME_"+i).val()==$("#XNAME_"+j).val()){
						 $("#XNAME_"+j).focus();
							showerr($("#XNAME_"+j)[0],"Member Name is Already Selected","block");
							return false;
						}
					}
				}	
				
				
				
				if($("#XCONV_"+i).is(":checked")){
					  is_con ='Y';
					} else{
						is_con ='N';
					}
				affDetail.push({
						"emp_type" 				: $("#XMTYPE_"+i).val(),
						"member_type" 			: $("#XNAME_"+i).val(),
						"Panel_det_id"          : $("#Panel_det_id_" + i).val(),
						"department" 			: $("#XDEPARTMT_" + i).val(),
						"designation" 			: $("#XPOST_" + i).val(),
						"email_id" 				: $("#XEMAIL_" + i).val(),
						"contact_no" 	        : $("#XCONTACT_" + i).val(),
						"is_convenor" 		    : is_con,
						"iss_active" 	        : $("#XACT_" + i).val()
					});
			}
			 
			var objjson = {
				"panel_code" 		 : $("#XPANELC").val(),
				"panel_name" 	 	 : $("#XPANELN").val(),
				"is_active" 		 : is_active,
				"panel_id" 		 	 : $("#panel_id").val(),
				"list" 			     : affDetail
			};
			console.log("Save penal json123:" + JSON.stringify(objjson));
			var encData = encAESData($("#AESKey").val(), objjson);
			$.ajax({
				type : "POST",
				url : "InspectionApprovalPanelService",
				data : {
					encData : encData,
					fstatus : $("#fstatus").val()
				},
				success : function(data) {
					saveOrUpdateCommonFunctionInMemberMast(decAESData($( "#AESKey").val(), data));
				},
				error : function() {
					alert("Error");
				}
			});
		}
	} catch (err) {
		alert(err);
	}
}

function saveOrUpdateCommonFunctionInMemberMast(data) {
	if (data.fstatus == "N") hideElement("XBTNSAVE");
	if (data.fstatus == "E") hideElement("XBTNUPDATE");
	 
	var tRec = $("#totRow").val();
	setTimeout(function () {
		if (data.fstatus == "D"){
			for(var i=1;i<=tRec;i++){
				$("#EDIT_RECORD_"+i).wrap('<td style="display:none"/>');
				$("#DELETE_RECORD_"+i).wrap('<td style="display:none"/>');
			}
		}
		if(data.flg=="V") {
			displaySuccessMessages("errMsg1", data.errMsg, "");
			clearSuccessMessageAfterFiveSecond("errMsg1");
	    	if (data.fstatus == "N") reloadWinAfterFiveSecond();
	        else if (data.fstatus == "E") showWinAfterFiveSecond();
	        else if (data.fstatus == "D") reloadWinAfterFiveSecond();
		} else {
			displaySuccessMessages("errMsg2", data.errMsg, "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
	    	if (data.fstatus == "N") showElementAfterFiveSecond("XBTNSAVE");
	        else if (data.fstatus == "E") showElementAfterFiveSecond("XBTNUPDATE");
	        else if (data.fstatus == "D") reloadWinAfterFiveSecond();
		}
	}, 1000);
}

function showWinAfterFiveSecond() {
    setTimeout(function () {
    	document.location.href = 'create_inspection_approval_panel_e.jsp';
    }, 4000);
}
//for reload form after 5 sec
function reloadWinAfterFiveSecond() {
    setTimeout(function () {
    	location.reload();
    }, 1000);
}

function vldSearch(){
	document.frmAffliaD.target = "btmAffliD" ;
	document.frmAffliaD.action = "create_inspection_approval_panel_l.jsp";
	document.frmAffliaD.submit();
}

function editRow(panel_id, typ) {
	try {
		if (typ == "D") {
			var del = confirm("Are You Sure?")
			if (del == true) {
				$.ajax({
					type : "POST",
					url : "InspectionApprovalPanelService",
					data : {
						panel_id : panel_id,
						fstatus : typ,
					},
					success : function(data) {
						saveOrUpdateCommonFunctionInMemberMast(data);
					},
					error : function() {
						alert("Error");
					}
				});
			}
		} else {
			document.getElementById("panel_id").value = panel_id;
			document.getElementById("opt_typ").value = typ;
			document.frmInspectionL.target = "_parent";
			document.frmInspectionL.action = "create_inspection_approval_panel_e.jsp";
			document.frmInspectionL.submit();
		}
		
	} catch (err) {
		alert(err);
	}
}
function deleteDetailsdata(id,index,panel_id){  
	var rowcountAfterDelete = document.getElementById("END1").rows.length; 
	//var rowcountAfterDelete = parseInt($("#count").val());
	if(rowcountAfterDelete!=1)
	{  
		var chk=0;
		if(id!='-1'&& id!=undefined )
		{
			if(!deletemastdata(id))
				chk=1;
		}	
		if(chk==0)
		{
			var x=parseInt($("#srn_"+index).text());
			for(var i=parseInt(index);i<=rowcountAfterDelete;i++)
			{
				if($("#srn_"+(i+1)).text()!=undefined && $("#srn_"+(i+1)).text()!="" )
				{	
					$("#srn_"+(i+1)).text(x);
					++x;
				}
			}
			$(panel_id).parents("tr").remove();
		}
		$("#count").val(index-1);
	}
	else
	{ 
			displaySuccessMessages("errMsg2", "At least 1 rows should be present in the table", "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
	}
}
 function deletemastdata(id) {
	var retVal = confirm("Data will be deleted permanently. \nAre you sure you want to remove this Data?");
	if (retVal == true) {
		try {
			$.ajax({
				type : "POST",
				url : "InspectionApprovalPanelService",
				data : {
					id : id,
					fstatus : 'RD',
				},
				success : function(data) {
				},
				error : function() {
					alert("Error");
				}
			});
		} catch (err) {
			alert(err);
		}
		return true;
	} else {
		return false;
	}
}
 
 function checkfn(id){
	 var count = $("#count").val();
	 if($("#XCONV_"+id).is(':checked')){
		 for(var i=1;i<=count;i++){
			 if(id  == i){
				 $("#XCONV_"+i).prop('disabled', false);				 
			 }else{
				 $("#XCONV_"+i).prop('disabled', true);
			 }
		 } 
	 }else{
		 $(".checkboxcon").prop('disabled', false);
	 }
 }
 
 function isNumberKeys(evt){
	    var charCode = (evt.which) ? evt.which : event.keyCode
	    if (charCode > 31 && (charCode < 48 || charCode > 57))
	        return false;
	    return true;
	}
 
 function isAlphaNumeric(inputField,inputFieldId) {          
	 if( /[^a-zA-Z 0-9]/.test( inputField.value ) ){   
	  showerr($("#"+inputFieldId)[0], "Only alphanumeric values are allowed.","block");  
	  $("#"+inputFieldId).val('');
	  $("#"+inputFieldId).focus();
	     return false;
	 }
	 return true;      
	}

 function getEmpbymember(emp_type,index,emp_id){
	 if(emp_type=='I'){
	 getEmployee(emp_id,'XNAME_'+index);
	 }else{
	 getExternalEmployee(emp_id,'XNAME_'+index);	 
	 }
 }
 
 
//added by Amit Dangi to get External Employee Details from user mast Date 03-May-2024
 
 function getExternalEmpDetails(empid,index){
		var hrmsApi = $("#hrmsApi").val().trim();
		var emp_type = $("#XMTYPE_"+index).val();
		try {
			$.ajax({
				type: "GET",
				url: hrmsApi+"rest/apiServices/getUserEmpDetail?userType=&userId="+empid,
				jsonp: "parseResponse",
				dataType: "jsonp",
				success: function (response){
					console.log("response");
					console.log(response);
					getDeprtment(response.deptId,'XDEPARTMT_'+index);
					 if(emp_type=='I'){
						 getDesignation(response.desgId,'XPOST_'+index);
						 }else{
							 var moduleHtml = "<option value='"+response.desg+"'>"+response.desg+"</option>";
							 $("#XPOST_"+index).html(moduleHtml);
						 }
					$("#XEMAIL_"+index).val(response.email);
				}
			});	
		} catch (e) {
			alert(e);
		}
	}