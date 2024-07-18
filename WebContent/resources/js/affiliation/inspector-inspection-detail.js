/**
 * @author Amit dangi
 */
$(document).ready(function(){
	//	 $("#loader").fadeOut(2000);
	 
	$(".hidediv").hide();
	$("#btnReset, #btnReset1, #btnReset2").click(function(){
		location.reload();
	});
	/*$("#Insp_recm_1").change(function(){
		var selectedvalue = $(this).find('option:selected').val();
		if(selectedvalue!='')
			showRemarks(selectedvalue,'1');
	});*/
	
	getAcademicSession();
	setTimeout(function() {
		getApplicationDetail();
	}, 300);
	
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
			data:{"fstatus":"GETDETAILS", "session_id":session_id,"inst_name":inst_name,"mobile_no":mobile_no,
				"email_id":email_id,"XTODATE":XTODATE,"XFROMDATE":XFROMDATE,"inspection_by":inspection_by},
			async: false,
			success: function (response){
				
				if (typeof response.Applicationlist!= 'undefined' && response.Applicationlist.length > 0) {
				$('#stable').html("");
			console.log("response");
			console.log(response.Applicationlist);
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
	                    var isfinalsubmited	= val.isfinalsubmited;
	                    var is_pannel_member= val.is_pannel_member;
	                    var is_convinor		= val.is_convinor;
	                    var convinor_id		= val.convinor_id;
	                    var request_id		= val.request_id;
	                    
	                    if(is_pannel_member=='Y' /*|| $('#user_status').val()=='A'*/){
	                    	index=index+1;
	                    var cols ='<tr>'
	                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
	                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+contact+'</td>'
	                    	+'<td style="text-align:center; width:8%;">'+email+'</td>'
	                    	+'<td style="text-align:center; width:8%;"><a style="color:green;cursor: pointer;" id="inspectionhref" onclick=openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+','+"'"+''+isfinalsubmited+''+"'"+','+"'"+''+is_pannel_member+''+"'"+','+"'"+'N'+"'"+',\''+is_convinor+'\',\''+convinor_id+'\',\''+request_id+'\',\''+session+'\')>Start Inspection & View Application Form</a></td>'
	                    	if(isfinalsubmited=='Y'){
	                    		cols +='<td style="text-align:center; width:8%; color: #2c5e2c;"><b>Submitted</b></td>'	
	                    	}else{
	                    		if(is_convinor=='Y'){
	                    		cols +='<td style="text-align:center; width:8%;"><button type="button" class="btn btn-view1" style="background-color:#2c5e2c !important; color: #ffffff;" onclick=openInspection('+"'"+''+AF_REG_ID+''+"'"+','+"'"+''+session_id+''+"'"+','+"'"+''+isfinalsubmited+''+"'"+','+"'"+''+is_pannel_member+''+"'"+','+"'"+'Y'+"'"+',\''+is_convinor+'\',\''+convinor_id+'\',\''+request_id+'\',\''+session+'\')>Submit</button></td>'
	                    		}else{
                    			cols +='<td style="text-align:center; width:8%; color: #a94442f7;"><b>Only Panel Head Allowed</b></td>'	
	                    		}
                    		}
	                    	cols +='<td style="text-align:center; width:8%;"><button type="button" class="btn btn-view" id="printMe1" onclick="downloadJasperReport('+"'"+''+AF_REG_ID+''+"'"+',\''+convinor_id+'\');">Print</button></td>'
	                    	+'</td>'
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

function openInspection(Inst_id,session_id,isfinalsubmited,is_pannel_member,IsSubmitClick,is_convinor,convinor_id,request_id,session_name){
	try {
		if(Inst_id!="" ){
			document.InspectionDetailFrame.target="InspectionDetailViewFrame";
			document.InspectionDetailFrame.action="inspector_inspection_detail_view.jsp?Inst_Id="+Inst_id+"&session_id="+session_id+
							"&isfinalsubmited="+isfinalsubmited+"&is_pannel_member="+is_pannel_member+"&IsSubmitClick="+IsSubmitClick+
							"&is_convinor="+is_convinor+"&convinor_id="+convinor_id+"&request_id="+request_id+"&session_name="+session_name ;
			document.InspectionDetailFrame.submit();
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

function save(savetype,isfinalsubmited,Inst_Id,login_id){
			var form_data = new FormData();
			var workarray = [];
			var exp_index = "1";
			var is_upload_file="N";
			var insp_remarks=$('#insp_remarks').val();
			//var insp_recm=$('#insp_recm').val();
			var session_id=$("#session_id").val();
			var inspection_id=$("#inspection_id").val();
			var is_convinor=$("#is_convinor").val();
			//alert("inspection_id||"+inspection_id);
			//return;
			try{
				if(isfinalsubmited=="Y" && ($("#attachment1").val()=="" && $("#is_doc_up").val()=="")){
				$('#attachment1').focus();	
				showerr($("#attachment1")[0], "Upload Printed Report with Member Signature is Required while final submit.","block");
				return false;	
					}/*else if(insp_remarks==""){
				$('#insp_remarks').focus();	
				showerr($("#insp_remarks")[0], "Kindly enter the remarks.","block");
				return false;
				}*/
		/*	else if(insp_recm==""){
				$('#insp_recm').focus();	
				showerr($("#insp_recm")[0], "Kindly enter the Recommendation.","block");
				return false;
				}*/
				var membersrno = $("#membersrno").val();
				for(var i = 0; i < membersrno; i++){
					if((login_id || '').includes("dm")){
					 if($("#XNAME_"+ i).val()==""){
							$('#XNAME_'+i).focus();	
							showerr($("#XNAME_"+ i)[0], "Member Name is Required.","block");
							return false;
							}
					 if($("#XDEPARTMT_"+ i).val()==""){
							$("#XDEPARTMT_"+ i).focus();	
							showerr($("#XDEPARTMT_"+ i)[0], "Department is Required.","block");
							return false;
							}
					 if($("#XPOST_"+ i).val()==""){
							$("#XPOST_"+ i).focus();	
							showerr($("#XPOST_"+ i)[0], "Designation is Required.","block");
							return false;
							}
					 if($("#XCONTACT_"+ i).val()==""){
							$("#XCONTACT_"+ i).focus();	
							showerr($("#XCONTACT_"+ i)[0], "Contact No. is Required.","block");
							return false;
							}
					}
					if($("#Insp_recm_"+ (i+1)).val()=="" ){
						$("#Insp_recm_"+ (i+1)).focus();	
						showerr($("#Insp_recm_"+ (i+1))[0], $("#XNAME_"+i).val()+"Inspection Member Recommendation is Required.","block");
						return false;
						}
					
					 if($("#Insp_recm_"+ (i+1)).val()=="Not_Recommended" && $("#Insp_remarks_"+ (i+1)).val()==""){
							$("#Insp_remarks_"+ (i+1)).focus();	
							showerr($("#Insp_remarks_"+ (i+1))[0], $("#XNAME_"+i).val()+" Not Recommended Reason is Required.","block");
							return false;
							}
				}
				
			}catch(e){
				alert("ERROR :"+e);
			}
			//return true;
			
			for(i=0; i<=parseInt(exp_index); i++){
				if($("#attachment"+i).val()!="" && $("#attachment"+i).val()!=undefined){
					is_upload_file="Y";
					var ext = $("#attachment"+i).val().split('.').pop().toLowerCase();
					
					if(ext !=""){
						if($.inArray(ext, ['pdf']) == -1) {						
							$('#attachment'+i).focus();
							alert("Note: Only .pdf files will be allowed for uploading.!");
							return false;
						}
					}
					var	fsize=$('#attachment'+i)[0].files[0].size;
					var file = Math.round((fsize / 1024));
					if (parseInt(file) > 20480 ) {
						$('#attachment'+i).focus();
						alert("File size should be less than 20 MB!");
						return false;
					}
					
					var mapedJson={doctitle: $("#doctitle"+i).val()}
					workarray.push(mapedJson);
					if ($("#attachment"+i).val()!='' && $("#attachment"+i).val()!=undefined){
						var fileCount = document.getElementById("attachment"+i).files.length;
						for (j = 0; j < fileCount; j++) {
							form_data.append("upload_doc"+i, document.getElementById("attachment"+i).files[j]);
						}
					}	
				}
				
			}	
			
			//Computer Peripheral Details 
			var computerDetails = [];
			var comsrno = $("#comsrno").val();
			for(var i = 1; i <= comsrno; i++){
				console.log("|"+$("#avbl_room_inspection_" +i).val()+"|");
			if ($("#avbl_room_inspection_" +i).val() != "" && $("#avbl_room_inspection_" + i).val() != undefined) {
				var arr = {
						avbl_room_inspection   	: $("#avbl_room_inspection_" + i).val(), 
						inspection_status 		: $("#inspection_status_" + i).val(),
						fac_id 					: $("#fac_id"+i).val(),
						}
				computerDetails.push(arr);
			}
			}
			//facility Details 
			var facilityDetails = [];
			var fsrno = $("#fsrno").val();
			for(var i = 1; i <= fsrno; i++){
			if ($("#avbl_room_insp_fac_" +i).val() != "" && $("#avbl_room_insp_fac_" + i).val() != undefined) {
				var arr = {
						avbl_room_inspection   	: $("#avbl_room_insp_fac_" + i).val(), 
						inspection_status 		: $("#inspection_status_fac_" + i).val(),
						fac_id 					: $("#fac_id_fac_"+i).val(),
						}
				facilityDetails.push(arr);
			}
			}
			//Administrative Details
			var administrativeDetails = [];
			var adm_srno = $("#adm_srno").val();
			for(var i = 1; i <= adm_srno; i++){
			if ($("#avbl_carpet_insp_adm_" +i).val() != "" && $("#avbl_carpet_insp_adm_" + i).val() != undefined) {
				var arr = {
						avbl_carpet_inspection  : $("#avbl_carpet_insp_adm_" + i).val(),
						avbl_room_inspection   	: $("#avbl_room_insp_adm_" + i).val(), 
						inspection_status 		: $("#inspection_status_adm_" + i).val(),
						pkey_id 				: $("#adm_id_"+i).val(),
						}
				administrativeDetails.push(arr);
			}
			}
			
			//Pre Affiliation Amenities  Details
			var amenitiesDetails = [];
			var amniti_srno = $("#amniti_srno").val();
			for(var i = 1; i <= amniti_srno; i++){
			if ($("#avbl_carpet_insp_amniti_" +i).val() != "" && $("#avbl_carpet_insp_amniti_" + i).val() != undefined) {
				var arr = {
						avbl_carpet_inspection  : $("#avbl_carpet_insp_amniti_" + i).val(),
						avbl_room_inspection   	: $("#avbl_room_insp_amniti_" + i).val(), 
						inspection_status 		: $("#inspection_status_amniti_" + i).val(),
						pkey_id 				: $("#amniti_id_"+i).val(),
						}
				amenitiesDetails.push(arr);
			}
			}
			
			//Infrastructure Details 
			var InfraDetails = [];
			var infrasrno = $("#infrasrno").val();
			for(var i = 1; i <= infrasrno; i++){
				console.log("InfraDetails avbl_room_inspection||"+$("#avbl_room_inspection_" +i).val()+"|");
			if ($("#avbl_room_inspection_infra_" +i).val() != "" && $("#avbl_room_inspection_infra_" + i).val() != undefined) {
				var arr = {
						avbl_room_inspection   	: $("#avbl_room_inspection_infra_" + i).val(), 
						inspection_status 		: $("#inspection_status_infra_" + i).val(),
						pkey_id 				: $("#infra_id_"+i).val(),
						}
				InfraDetails.push(arr);
			}
			}
			
			//Faculty Information  
			var facultyDetails = [];
			var facsrno = $("#facsrno").val();
			for(var i = 1; i <= facsrno; i++){
			if ($("#fac_inspection_status_" +i).val() != "" && $("#fac_inspection_status_" + i).val() != undefined) {
				var arr = {
						inspection_status   	: $("#fac_inspection_status_" + i).val(), 
						pkey_id 				: $("#fac_info_id_"+i).val(),
						}
				facultyDetails.push(arr);
			}
			}
			
			//questionnaire Details  
			var questionnaireDetails = [];
			var querno = $("#querno").val();
			for(var i = 1; i <= querno; i++){
			if ($("#que_inspection_status_" +i).val() != "" && $("#que_inspection_status_" + i).val() != undefined) {
				var arr = {
						inspection_status   	: $("#que_inspection_status_" + i).val(), 
						ques_id 				: $("#quer_id_"+i).val(),
						pkey_id 				: $("#quer_pkey_id_"+i).val(),
						ques_type 				: "A",
						}
				questionnaireDetails.push(arr);
			}
			}
			/*for inspection questionaire same questionnaireDetails list will be 
			use for update as per queation id*/
			var insquerno = $("#insquerno").val();
			for(var i = 1; i <= insquerno; i++){
			if ($("#que_insp_status_" +i).val() != "" && $("#que_insp_status_" + i).val() != undefined) {
				var arr = {
						inspection_status   	: $("#que_insp_status_"+ i).val(), 
						ques_id 				: $("#ins_quer_id_"+i).val(),
						pkey_id 				: $("#ins_pkey_id_"+i).val(),
						ques_type 				: "I",
						}
				questionnaireDetails.push(arr);
			}
			}
			
			//Members Details  
			var membersDetails = [];
			var membersrno = $("#membersrno").val();
			for(var i = 0; i < membersrno; i++){
				if ($("#Insp_recm_" +(i+1)).val() != "" && $("#Insp_recm_" + (i+1)).val() != undefined) {
					var arr = {
							XMTYPE   	: $("#XMTYPE_"+ i).val(), 
							XNAME		: $("#XNAME_"+i).val(),
							XDEPARTMT 	: $("#XDEPARTMT_"+i).val(),
							XPOST		: $("#XPOST_"+i).val(),
							XCONTACT	: $("#XCONTACT_"+i).val(),
							XCONV 		: $("#XCONV_"+i).is(":checked")?"Y":"N",
							Insp_recm 	: $("#Insp_recm_"+(i+1)).val(),
							insp_remarks: $("#Insp_remarks_"+(i+1)).val(),
							}
					membersDetails.push(arr);
				}
				}debugger;
			
			var jsonObject={"session_id":session_id,"Inst_Id":Inst_Id,"is_convinor":is_convinor,/*"insp_remarks":insp_remarks,"insp_recm":insp_recm,*/"isfinalsubmited":isfinalsubmited,
							"computerDetails":computerDetails,"facilityDetails":facilityDetails,"administrativeDetails":administrativeDetails,
							"amenitiesDetails":amenitiesDetails,"InfraDetails":InfraDetails,"facultyDetails":facultyDetails,
							"questionnaireDetails":questionnaireDetails,"membersDetails":membersDetails,"inspection_id":inspection_id};
				console.log("inspector-inspection-detail Save Json Object");
				jsonobj=JSON.stringify(jsonObject);
				console.log(jsonobj);
			//return false;
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("POST", "../InspectorInspectionService?fstatus="+savetype+"&Inst_Id="+Inst_Id, true);
			
			xmlHttp.setRequestHeader("jsonobj", jsonobj);
			xmlHttp.send(form_data);
			try{
				xmlHttp.onreadystatechange = function() {
					if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
						var data=JSON.parse(this.responseText);
						console.log("Response");
						console.log(data);
						    displaySuccessMessages("errMsg1", data.status, "");
							clearSuccessMessageAfterFiveSecond("errMsg1");
							$("#btnSave").show();
							$("#btnReseT").show();
							if(isfinalsubmited!="Y"){
							setTimeout(function() {
								location.reload();
							}, 2000);
							}else{
								setTimeout(function () {
									parent.location.reload();
								}, 2000);
							}
						
					}
				}
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
	if(AF_REG_ID==""){
		alert("Application Id not found.");
		return false;
	}
	$('#selectedApplication').val(AF_REG_ID);
	$("#Detailsdiv").show();
}


//Added By Ashwani Use for General Information list 

function getProgList(id) {
	let cnt=0, r=0;
	$.ajax({
		type : "POST",
		url  : "../InspectorInspectionService",
		data : {fstatus : "PD","id":id},
	    success : function(response) {
			if(typeof response.ProgDetail != 'undefined' && response.ProgDetail.length > 0){
				$.each(response.ProgDetail, function (key, val) {
					var newRow = $("<tr>");	 
					var cols = "";
					r = ++cnt;
					cols += '<tr id="tr_'+r+'">';
					cols += '<td style="text-align:center; width:5%;">'
								+'<span id="sno_'+r+'">'+parseInt(r)+'</span>'
							+'</td>';
					cols += '<td style="text-align:center; width:20%;">'
								+'<input class="form-control" type="text" id="prog_name_'+r+'" name="prog_name_'+r+'" value="'+val.PROG_ID+'" placeholder="Enter Detail" disabled>'
								+'<input type="hidden" id="prog_id_'+r+'" name="prog_id_'+r+'" value="'+val.PROG_ID+'" placeholder="Enter Detail">'
							+'</td>';
					cols += '<td style="text-align:center; width:20%;">'
								+'<input class="form-control" type="text" maximum="99" id="prog_typ_'+r+'" name="prog_typ_'+r+'" value="'+val.PROG_TYPE+'" placeholder="Enter Detail" disabled>'
								+'<input type="hidden" maximum="99" id="prog_typ_id_'+r+'" name="prog_typ_id_'+r+'" value="'+val.PROG_TYPE+'" placeholder="Enter Detail">'
							+'</td>';
					cols += '<td style="text-align:center; width:8%;">'
								+'<input class="form-control" type="text" maximum="99" id="shift_'+r+'" name="shift_'+r+'" value="'+val.shift+'" placeholder="Enter Detail" disabled>'
							+'</td>';
				 
					cols += '<td style="text-align:center; width:8%;">'
								+'<input class="form-control" type="text" maximum="99" id="univ_'+r+'" name="univ_'+r+'" value="'+val.univ+'" placeholder="Enter Detail" disabled>'
							+'</td>';
					cols += '</tr>';
					
					$('#affProgDetails').append(cols);
					cols=""; 
					$("#affProgDetails").val(r);	
					//getProgrammeList(val.PROG_ID, r, 'T');
					//getProgrammeTypeByProgrammeId(val.PROG_ID, val.PROG_TYPE, r, 'T');
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

function getIsSubOptions(id, mid,obj) {	
	callModalForCourse(id, mid,obj);
}
function callModalForCourse(id, mid,obj){
	 var Inst_Id=$("#Inst_Id").val();
	 $('#reportDiologPay').modal({backdrop: 'static', keyboard: false},'show');
	 $('#showframePay').html("");
	 $('#showframePay').append('<iframe class="embed-responsive-item" onload="resizeIframe(this)" name="1_Report" id="1_Report" width="100%;" height="200x" src="inspector_inspection_detail_subrequest_model.jsp?id='+id+'&mid='+mid+'&obj='+obj+'&Inst_Id='+Inst_Id+' " frameBorder="0" border="no" scrolling="no" style="border: none !important"></iframe>');
}

function showRemarks(selectedvalue,srno){
	if(selectedvalue=="Not_Recommended")
		{
		 $('#insp_remarks_'+srno).show();
		}else if (selectedvalue=="Recommended")
			{
			 $('#insp_remarks_'+srno).hide();
			 $('#insp_remarks_'+srno).val("");
			}
		 else{
			 $('#insp_remarks_1').hide();
			 $('#insp_remarks_2').hide();
			 $('#insp_remarks_3').hide();
		}
}

function isNumberKeys(evt){
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}


function allowNumberOrDecimal(object)
{

  var pattern = /^\d*\.?\d{0,4}$/;
  var id=$(object).attr("id");
  var mLength = $("#"+id).attr('maxlength');

  var decimalValue=$("#"+id).val();
  var totValidLength=mLength-6;

  if(!pattern.test(decimalValue))
  {
     $("#"+id).focus();
     showerr($("#"+id)[0],"Only Numeric with 5 decimal digit allowed","block");
     //$("#"+id).val("0.00");
    // $("#"+id).val("");
     return false;
  }
   if(decimalValue.indexOf('.')== -1)
   {
	if(decimalValue.length>totValidLength)
	 {
		 $("#"+id).focus();
	     showerr($("#"+id)[0],"Only "+totValidLength+" numeric digits are allowed"  ,"block");
	     //$("#"+id).val("0.00");
	    // $("#"+id).val("");
	     return false;
	 }
   }else if(decimalValue.indexOf('.')>1){
	   var arr=decimalValue.split(".");
	   if(arr[0].length>totValidLength)
		 {
			 $("#"+id).focus();
		     showerr($("#"+id)[0],"Only"+totValidLength+" numeric digits are allowed before decimal"  ,"block");
		     //$("#"+id).val("0.00");
		  //   $("#"+id).val("");
		     return false;
		 }

   }
}

function callModalForFaculty(Inst_Id){
	 $('#reportDiolog').modal({backdrop: 'static', keyboard: false},'show');
	 $('#showframe').html("");
	 $('#showframe').append('<iframe class="embed-responsive-item" onload="resizeIframe(this)" name="1_Report" id="1_Report" width="100%;" height="" src="inspection_faculty_detail.jsp?Inst_Id='+Inst_Id+' " frameborder="0" scrolling="no"></iframe>');
}


function callModalForDocument(Inst_Id,request_id,session_name){
	 $('#reportDiolog2').modal({backdrop: 'static', keyboard: false},'show');
	 $('#showframe_uploaddoc').html("");
	 $('#showframe_uploaddoc').append('<iframe class="embed-responsive-item" onload="resizeIframe(this)" name="1_Report" id="1_Report" width="100%;" height="" src="inspection_upload_documents.jsp?Inst_Id='+Inst_Id+'&request_id='+request_id+'&session_name='+session_name+' " frameborder="0" scrolling="no"></iframe>');
}