$(document).ready(function(){
	$('#btnReset').click(function(){
		location.reload();
	});
	
	$('#btnSave').click(function(){
		
		var cnt = parseInt($("#totRow").val()); 
		var formData = new FormData();
		var facDetail = [];
		
		var Inst_Id=$("#Inst_Id").val();
		if (Inst_Id == "") {
			displaySuccessMessages("errMsg2", "College Id is Required.", "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
			return false;
	    }
		
		if (cnt == 0) {
			displaySuccessMessages("errMsg2", "At least 1 rows should be present in the table", "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
			return false;
	    }
		if (cnt > 0) {
			
			var chk=0;
			var chkk=0;
			for(var k=1; k<=cnt; k++){
				var FAC_ID	= $("#FAC_ID_"+k).val();
				var FAC_NAME= $("#FAC_NAME_"+k).val();
				var FAC_GEN	= $("#FAC_GEN_"+k).val();
				var FAC_DOJ	= $("#FAC_DOJ_"+k).val();
				var PAN		= $("#PAN_"+k).val();
				var F_NAME	= $("#F_NAME_"+k).val();
				var QUALI	= $("#QUALI_"+k).val();
				var DEPT	= $("#DEPT_"+k).val();
				var DESG	= $("#DESG_"+k).val();
				var IS_DIR	= $("#IS_DIR_"+k).val();
				var C_NAME	= $("#COURSE_NAME_"+k).val();
				var B_NAME	= $("#BRANCH_NAME_"+k).val();
				var SHIFT	= $("#SHIFT_"+k).val();
				
				if(FAC_ID=="" || FAC_ID==null && chk==0){
					chk=1;
					displaySuccessMessages("errMsg2", "Faculty Id is Required at Row: "+k, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					return false;
				}
				if(FAC_NAME=="" || FAC_NAME==null && chk==0){
					chk=1;
					displaySuccessMessages("errMsg2", "Faculty Name is Required at Row: "+k, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					return false;
				}
				if(FAC_GEN=="" || FAC_GEN==null && chk==0){
					chk=1;
					displaySuccessMessages("errMsg2", "Gender is Required at Row: "+k, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
				//	showerr($("#FAC_GEN_"+k)[0], "Gender is Required", "block");
					return false;
				}
				if(PAN=="" || PAN==null && chk==0){
					chk=1;
					displaySuccessMessages("errMsg2", "PAN No. is Required at Row: "+k, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
				//	showerr($("#PAN_"+k)[0], "PAN No. is Required", "block");
					return false;
				}
				
			for(var j=k+1; j<=cnt; j++){
				if($("#FAC_ID_"+k).val()==$("#FAC_ID_"+j).val() ){
					//$("#FAC_ID_"+j).focus();
					//showerr($("#FAC_ID_"+j)[0],"Faculty Id Are Same!","block");
					displaySuccessMessages("errMsg2", "Faculty Id is Same at Row: "+j, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					chkk=1;
					return false;
				}
				/*if($("#FAC_NAME_"+k).val()==$("#FAC_NAME_"+j).val() ){
					//$("#FAC_NAME_"+j).focus();
					//showerr($("#FAC_NAME_"+j)[0],"Faculty Name Are Same!","block");
					displaySuccessMessages("errMsg2", "Faculty Name Are Same! "+j, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					chkk=1;
					return false;
				}*/
				if($("#PAN_"+k).val()==$("#PAN_"+j).val() ){
				//	$("#PAN_"+j).focus();
					//showerr($("#PAN_"+j)[0],"PAN Card No. Are Same!","block");
					displaySuccessMessages("errMsg2", "PAN Card No. is Same at Row: "+j, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					chkk=1;
					return false;
				}
				/*if($("#F_NAME_"+k).val()==$("#F_NAME_"+j).val()){
				//	$("#F_NAME_"+j).focus();
					//showerr($("#F_NAME_"+j)[0],"Father Name	Are Same!","block");
					displaySuccessMessages("errMsg2", "Father Name Are Same! "+j, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					chk=1;
					return false;
				}*/
				
				if($("#IS_DIR_"+j).val()=='Y' ){
				if($("#IS_DIR_"+k).val()==$("#IS_DIR_"+j).val()){
					//$("IS_DIR_"+j).focus();
					//showerr($("#IS_DIR_"+j)[0],"Is Director is Already Assigned!","block");
					displaySuccessMessages("errMsg2", "Is Director is Already Assigned at Row: "+j, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
					chkk=1;
					return false;
					}
				}
			  }
			}
			for(var k=1; k<=cnt; k++){/*
				var FAC_ID	= $("#FAC_ID_"+k).val();
				var FAC_NAME= $("#FAC_NAME_"+k).val();
				var FAC_GEN	= $("#FAC_GEN_"+k).val();
				var FAC_DOJ	= $("#FAC_DOJ_"+k).val();
				var PAN		= $("#PAN_"+k).val();
				var F_NAME	= $("#F_NAME_"+k).val();
				var QUALI	= $("#QUALI_"+k).val();
				var DEPT	= $("#DEPT_"+k).val();
				var DESG	= $("#DESG_"+k).val();
				var IS_DIR	= $("#IS_DIR_"+k).val();
				var C_NAME	= $("#COURSE_NAME_"+k).val();
				var B_NAME	= $("#BRANCH_NAME_"+k).val();
				var SHIFT	= $("#SHIFT_"+k).val();
				
				if(FAC_ID=="" || FAC_ID==null && chk==0){
					chk=1;
					$("#FAC_ID_"+k).val("");
					$("#FAC_ID_"+k).focus();
					showerr($("#FAC_ID_"+k)[0], "Faculty Id is Required", "block");
					return false;
				}
				if(FAC_NAME=="" || FAC_NAME==null && chk==0){
					chk=1;
					$("#FAC_NAME_"+k).val("");
					$("#FAC_NAME_"+k).focus();
					showerr($("#FAC_NAME_"+k)[0], "Faculty Name is Required", "block");
					return false;
				}
				if(FAC_GEN=="" || FAC_GEN==null && chk==0){
					chk=1;
					$("#FAC_GEN_"+k).val("");
					$("#FAC_GEN_"+k).focus();
					showerr($("#FAC_GEN_"+k)[0], "Gender is Required", "block");
					return false;
				}
				if(PAN=="" || PAN==null && chk==0){
					chk=1;
					$("#PAN_"+k).val("");
					$("#PAN_"+k).focus();
					showerr($("#PAN_"+k)[0], "PAN No. is Required", "block");
					return false;
				}*/
				/*if(F_NAME=="" || F_NAME==null){
					chk=1;
					$("#F_NAME_"+cnt).val("");
					$("#F_NAME_"+cnt).focus();
					showerr($("#F_NAME_"+cnt)[0], "Father Name is Required", "block");
					return false;
				} if(QUALI=="" || QUALI==null){
					chk=1;
					$("#QUALI_"+cnt).val("");
					$("#QUALI_"+cnt).focus();
					showerr($("#QUALI_"+cnt)[0], "Qualification is Required", "block");
					return false;
				}
				if(DEPT=="" || DEPT==null){
					chk=1;
					$("#DEPT_"+cnt).val("");
					$("#DEPT_"+cnt).focus();
					showerr($("#DEPT_"+cnt)[0], "Department is Required", "block");
					return false;
				}
				if(DESG=="" || DESG==null){
					chk=1;
					$("#DESG_"+cnt).val("");
					$("#DESG_"+cnt).focus();
					showerr($("#DESG_"+cnt)[0], "Designation is Required", "block");
					return false;
				}
				if(C_NAME=="" || C_NAME==null){
					chk=1;
					$("#COURSE_NAME_"+cnt).val("");
					$("#COURSE_NAME_"+cnt).focus();
					showerr($("#COURSE_NAME_"+cnt)[0], "Course Name is Required", "block");
					return false;
				}
				if(B_NAME=="" || B_NAME==null){
					chk=1;
					$("#BRANCH_NAME_"+cnt).val("");
					$("#BRANCH_NAME_"+cnt).focus();
					showerr($("#BRANCH_NAME_"+cnt)[0], "Branch Name is Required", "block");
					return false;
				}
				if(SHIFT=="" || SHIFT==null){
					chk=1;
					$("#SHIFT_"+cnt).val("");
					$("#SHIFT_"+cnt).focus();
					showerr($("#SHIFT_"+cnt)[0], "Shift is Required", "block");
					return false;
				}*/
				
				/*var chkk=0;
				for(var j=k+1; j<=cnt; j++){
					if($("#FAC_ID_"+k).val()==$("#FAC_ID_"+j).val() && chkk==0){
						$("#FAC_ID_"+j).focus();
						showerr($("#FAC_ID_"+j)[0],"Faculty Id Are Same!","block");
						chkk=1;
						return false;
					}
					if($("#FAC_NAME_"+k).val()==$("#FAC_NAME_"+j).val() && chkk==0){
						$("#FAC_NAME_"+j).focus();
						showerr($("#FAC_NAME_"+j)[0],"Faculty Name Are Same!","block");
						chkk=1;
						return false;
					}
					if($("#PAN_"+k).val()==$("#PAN_"+j).val() && chkk==0){
						$("#PAN_"+j).focus();
						showerr($("#PAN_"+j)[0],"PAN Card No. Are Same!","block");
						chkk=1;
						return false;
					}
					if($("#F_NAME_"+k).val()==$("#F_NAME_"+j).val()){
						$("#F_NAME_"+j).focus();
						showerr($("#F_NAME_"+j)[0],"Father Name	Are Same!","block");
						chk=1;
						return false;
					}
					
					if($("#IS_DIR_"+j).val()=='Y' && chkk==0){
					if($("#IS_DIR_"+k).val()==$("#IS_DIR_"+j).val()){
						$("IS_DIR_"+j).focus();
						showerr($("#IS_DIR_"+j)[0],"Is Director is Already Assigned!","block");
						chkk=1;
						return false;
						}
					}
				}*/
				
				/*if(chk==0){
					continue;
				}else{
					break;
					return false;
				}*/
				 
				facDetail.push({
					"FAC_ID"	:$("#FAC_ID_"+k).val(),	
					"FAC_NAME"	:$("#FAC_NAME_"+k).val(), 
					/*"AADHAR"	:$("#AADHAR_"+k).val(),*/
					"PAN"		:$("#PAN_"+k).val(), 
					"F_NAME"	:$("#F_NAME_"+k).val(),
					"QUALI"		:$("#QUALI_"+k).val(), 
					"DEPT"		:$("#DEPT_"+k).val(),
					"DESG"		:$("#DESG_"+k).val(),
					"IS_DIR"	:$("#IS_DIR_"+k).val(),
					"C_NAME"	:$("#COURSE_NAME_"+k).val(),
					"B_NAME"	:$("#BRANCH_NAME_"+k).val(),
					"SHIFT"		:$("#SHIFT_"+k).val(),
					"FD_STATUS"	:$("#FD_STATUS_"+k).val(),
					"FD_MID"	:$("#FD_MID_"+k).val(),
					"FAC_GEN"	:$("#FAC_GEN_"+k).val(),
					"FAC_DOJ"	:$("#FAC_DOJ_"+k).val()
				});	
				//alert(JSON.stringify(facDetail));

				if ($("#PROFILE_PIC_"+k).val()!='' && $("#PROFILE_PIC_"+k).val()!=undefined){

					var fileSize = document.getElementById("PROFILE_PIC_"+k).files[0];
					if(fileSize.size > (1 * 100 * 1000)){
						displaySuccessMessages("errMsg2", "File size should not greater than 100 KB at Row : "+k, "");
						clearSuccessMessageAfterFiveSecond("errMsg2");
						return false;
					}else{
						formData.append("PROFILE_PIC_"+k, document.getElementById("PROFILE_PIC_"+k).files[0]);						
					}
				}
			}
		//	alert("in");
			$("#btnSave").hide();
			var data = {list:facDetail};
			var encData=encAESData($("#AESKey").val(), data);
			console.log("encData||"+encData);
			var x=1;
			var xmlHttp = new XMLHttpRequest();
		    xmlHttp.open("POST", "../InspectionFacultyDetailService", true);
		    xmlHttp.setRequestHeader("encData", encData);
		    xmlHttp.setRequestHeader("fstatus", "1");
		    xmlHttp.setRequestHeader("Inst_Id", Inst_Id);
		    xmlHttp.send(formData);
		    try{
		    	xmlHttp.onreadystatechange = function() {
		    		if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
						var dataa=JSON.parse(this.responseText);
						$("#btnSave").show();
						var data = decAESData($("#AESKey").val(), dataa);
						if(data.status=='1'){
							displaySuccessMessages("errMsg1", data.msg, "");
							clearSuccessMessageAfterFiveSecond("errMsg1");
							setTimeout(function () {
								//document.location.href="inspector_inspection_detail_e.jsp";
								 parent.location.reload();
							}, 400);
							/*setTimeout(function () {
					    		location.reload();
					    	}, 5000);*/
						}else{
							displaySuccessMessages("errMsg2", data.msg, "");
							clearSuccessMessageAfterFiveSecond("errMsg2");
					    	showElementAfterFiveSecond("save");
						}
		    		}
		    		
		        }
		    } catch (err){
		    	alert(err);
		    }
		}
	});
});

function Integer(sText) {
	var ValidChars = "0123456789";
    var IsNumber=true;
    var v = 0;
    var Char;
    for (i = 0; i < sText.value.length && IsNumber == true; i++) { 
    	Char = sText.value.charAt(i); 
    	if (ValidChars.indexOf(Char) == -1) {
    		showerr(sText,"Value must be numeric.","block");
    		$("#sText").val('');
    		sText.focus();
    		return false;
    	}
    }
    v = sText.value;
    if(v!="") {
    	if (v > 0) {
    		return IsNumber;
    	} else {
    		showerr(sText,"Value must be greater than zero(s)","block");
    		sText.focus();
    		return false;
    	}
    }  
}

function getTable(){
	var cnt = $("#totRow").val();
	var cnt1 = document.getElementById("fac_Table").rows.length;
	var sno = parseInt(cnt1)+1;	
	var chk=0;
	
	for(var k=1; k<=cnt; k++){
		
		var FAC_ID	= $("#FAC_ID_"+k).val();
		var FAC_NAME= $("#FAC_NAME_"+k).val();
		/*var AADHAR	= $("#AADHAR_"+k).val();*/
		var PAN		= $("#PAN_"+k).val();
		var F_NAME	= $("#F_NAME_"+k).val();
		var QUALI	= $("#QUALI_"+k).val();
		var DEPT	= $("#DEPT_"+k).val();
		var DESG	= $("#DESG_"+k).val();
		var IS_DIR	= $("#IS_DIR"+k).val();
		var C_NAME	= $("#COURSE_NAME_"+k).val();
		var B_NAME	= $("#BRANCH_NAME_"+k).val();
		var SHIFT	= $("#SHIFT_"+k).val();
		var FAC_GEN	= $("#FAC_GEN_"+k).val();
		var FAC_DOJ	= $("#FAC_DOJ_"+k).val();
		
		if(FAC_ID=="" || FAC_ID==null && chk==0){
			chk=1;
			displaySuccessMessages("errMsg2", "Faculty Id is Required at Row: "+k, "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
			return false;
		}
		if(FAC_NAME=="" || FAC_NAME==null && chk==0){
			chk=1;
			displaySuccessMessages("errMsg2", "Faculty Name is Required at Row: "+k, "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
			return false;
		}
		if(FAC_GEN=="" || FAC_GEN==null && chk==0){
			chk=1;
			displaySuccessMessages("errMsg2", "Gender is Required at Row: "+k, "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
		//	showerr($("#FAC_GEN_"+k)[0], "Gender is Required", "block");
			return false;
		}
		if(PAN=="" || PAN==null && chk==0){
			chk=1;
			displaySuccessMessages("errMsg2", "PAN No. is Required at Row: "+k, "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
		//	showerr($("#PAN_"+k)[0], "PAN No. is Required", "block");
			return false;
		}
		/*if(F_NAME=="" || F_NAME==null){
			chk=1;
			$("#F_NAME_"+cnt).val("");
			$("#F_NAME_"+cnt).focus();
			showerr($("#F_NAME_"+cnt)[0], "Father Name is Required", "block");
			return false;
		
		}
		if(QUALI=="" || QUALI==null){
			chk=1;
			$("#QUALI_"+cnt).val("");
			$("#QUALI_"+cnt).focus();
			showerr($("#QUALI_"+cnt)[0], "Qualification is Required", "block");
			return false;
		
		}
		if(DEPT=="" || DEPT==null){
			chk=1;
			$("#DEPT_"+cnt).val("");
			$("#DEPT_"+cnt).focus();
			showerr($("#DEPT_"+cnt)[0], "Department is Required", "block");
			return false;
		}
		if(DESG=="" || DESG==null){
			chk=1;
			$("#DESG_"+cnt).val("");
			$("#DESG_"+cnt).focus();
			showerr($("#DESG_"+cnt)[0], "Designation is Required", "block");
			return false;
		
		}
		if(C_NAME=="" || C_NAME==null){
			chk=1;
			$("#COURSE_NAME_"+cnt).val("");
			$("#COURSE_NAME_"+cnt).focus();
			showerr($("#COURSE_NAME_"+cnt)[0], "Course Name is Required", "block");
			return false;
		}
		if(B_NAME=="" || B_NAME==null){
			chk=1;
			$("#BRANCH_NAME_"+cnt).val("");
			$("#BRANCH_NAME_"+cnt).focus();
			showerr($("#BRANCH_NAME_"+cnt)[0], "Branch Name is Required", "block");
			return false;
		}
		if(SHIFT=="" || SHIFT==null){
			chk=1;
			$("#SHIFT_"+cnt).val("");
			$("#SHIFT_"+cnt).focus();
			showerr($("#SHIFT_"+cnt)[0], "Shift is Required", "block");
			return false;
		}*/
		
		var chk=0;
		for(var j=k+1; j<=cnt; j++){
			if($("#FAC_ID_"+k).val()==$("#FAC_ID_"+j).val() ){
				//$("#FAC_ID_"+j).focus();
				//showerr($("#FAC_ID_"+j)[0],"Faculty Id Are Same!","block");
				displaySuccessMessages("errMsg2", "Faculty Id is Same at Row: "+j, "");
				clearSuccessMessageAfterFiveSecond("errMsg2");
				chkk=1;
				return false;
			}
			/*if($("#FAC_NAME_"+k).val()==$("#FAC_NAME_"+j).val() ){
				//$("#FAC_NAME_"+j).focus();
				//showerr($("#FAC_NAME_"+j)[0],"Faculty Name Are Same!","block");
				displaySuccessMessages("errMsg2", "Faculty Name Are Same! "+j, "");
				clearSuccessMessageAfterFiveSecond("errMsg2");
				chkk=1;
				return false;
			}*/
			if($("#PAN_"+k).val()==$("#PAN_"+j).val() ){
			//	$("#PAN_"+j).focus();
				//showerr($("#PAN_"+j)[0],"PAN Card No. Are Same!","block");
				displaySuccessMessages("errMsg2", "PAN Card No. is Same at Row: "+j, "");
				clearSuccessMessageAfterFiveSecond("errMsg2");
				chkk=1;
				return false;
			}
			/*if($("#F_NAME_"+k).val()==$("#F_NAME_"+j).val()){
			//	$("#F_NAME_"+j).focus();
				//showerr($("#F_NAME_"+j)[0],"Father Name	Are Same!","block");
				displaySuccessMessages("errMsg2", "Father Name Are Same! "+j, "");
				clearSuccessMessageAfterFiveSecond("errMsg2");
				chk=1;
				return false;
			}*/
			
			if($("#IS_DIR_"+j).val()=='Y' ){
			if($("#IS_DIR_"+k).val()==$("#IS_DIR_"+j).val()){
				//$("IS_DIR_"+j).focus();
				//showerr($("#IS_DIR_"+j)[0],"Is Director is Already Assigned!","block");
				displaySuccessMessages("errMsg2", "Is Director is Already Assigned at Row: "+j, "");
				clearSuccessMessageAfterFiveSecond("errMsg2");
				chkk=1;
				return false;
				}
			}
		  }
		/*if(chk==0){
			continue;
		}else{
			break;
			return false;
		}*/		
	}
	
	if(chk==0){
		var newRow = $("<tr>");	 
		var cols = "", chk=0;
		var r = parseInt(cnt)+1;
		
		cols += '<tr id="tr_'+r+'">';
		
		cols += '<td style="text-align:center; width:3%;">'
				+'<span id="sno_'+r+'">'+parseInt(sno)+'</span>'
				+'</td>';
		
		cols += '<td style="text-align:center; width:5%;">'
				+'<input type="text" class="form-control" placeholder="Enter Faculty Id" id="FAC_ID_'+r+'" name="FAC_ID_'+r+'" value="" onblur="Integer(this);" maxlength="12" style="text-transform: capitalize;">'
				+'</td>';
		
		cols += '<td style="text-align:center; width:8%;">'
			+'<input type="text" class="form-control" placeholder="Enter Faculty Name" id="FAC_NAME_'+r+'" name="FAC_NAME_'+r+'" value="" maxlength="99" style="text-transform: capitalize;">'
			+'</td>';
		
		cols += '<td style="text-align:center; width:5%;">'
				 +'<select  id="FAC_GEN_'+r+'" class="form-control">'
				 +'<option value="">Select Gender</option>'
				 +' <option value="M" >Male</option>'
				 +' <option value="F" >Female</option>'
				 +' <option value="O" >Other</option>'
				 +'</select> </td>'
	
		 cols += '<td style="text-align:center; width:12%;"> <div class="input-group date" id="">'
				 +'	<div class="input-group-addon"><i class="fa fa-calendar"></i> </div>'
				 +'<input type="text" class="form-control XFDATE1"  id="FAC_DOJ_'+r+'"name="FAC_DOJ_'+r+'" aria-invalid="true" aria-required="true"required="required" placeholder="Enter Date">'
				 +'</div> </td> '
		
		cols += '<td style="text-align:center; width:5%;">'
				+'<input type="text" class="form-control" placeholder="Enter PAN Card No." id="PAN_'+r+'" name="PAN_'+r+'" value="" maxlength="10" style="text-transform: uppercase;">'
				+'</td>';
		cols += '<td style="text-align:center; width:8%;">'
				+'<input type="text" class="form-control" placeholder="Enter Father Name" id="F_NAME_'+r+'" name="F_NAME_'+r+'" value=""maxlength="99" style="text-transform: capitalize;">'
				+'</td>';
		cols += '<td style="text-align:center; width:8%;">'
				+'<input type="text" class="form-control" placeholder="Enter Qualification" id="QUALI_'+r+'" name="QUALI_'+r+'" value="" maxlength="99">'
				+'</td>';
		cols += '<td style="text-align:center; width:6%;">'
				+'<input type="text" class="form-control" placeholder="Enter Department" id="DEPT_'+r+'" name="DEPT_'+r+'" value="" maxlength="99" style="text-transform: capitalize;">'
				+'</td>';
		cols += '<td style="text-align:center; width:6%;">'
				+'<input type="text" class="form-control" placeholder="Enter Designation" id="DESG_'+r+'" name="DESG_'+r+'" value="" maxlength="99" style="text-transform: capitalize;">'
				+'</td>';
		cols += '<td style="text-align:center; width:5%;">'
				+'<select class="form-control" id="IS_DIR_'+r+'" name="IS_DIR_'+r+'">'
				+'<option value="Y">Yes</option>'
				+'<option value="N" selected>No</option>'
				+'</select>'
				+'</td>';
		
		cols += '<td style="text-align:center; width:8%;">'
				+'<input type="text" class="form-control" placeholder="Enter Course Name" id="COURSE_NAME_'+r+'" name="COURSE_NAME_'+r+'" value="" maxlength="99" style="text-transform: capitalize;">'
				+'</td>';
		cols += '<td style="text-align:center; width:8%;">'
				+'<input type="text" class="form-control" placeholder="Enter Branch Name" id="BRANCH_NAME_'+r+'" name="BRANCH_NAME_'+r+'" value="" maxlength="99" style="text-transform: capitalize;">'
				+'</td>';
		cols += '<td style="text-align:center; width:5%;">'
				+'<input type="text" class="form-control" placeholder="Enter Shift" id="SHIFT_'+r+'" name="SHIFT_'+r+'" value="" maxlength="99" style="text-transform: capitalize;">'
				+'</td>';
		cols +='<td style="text-align:center; width:10%;">'
				+'<input type="file" class="form-control" placeholder="Choose photo to upload" id="PROFILE_PIC_'+r+'" name="PROFILE_PIC_'+r+'" value="" onchange="fileValidation("PROFILE_PIC_'+r+'");">'
				+'</td>'
		cols += '<td class="colr-red-p text-center hideButton" style="width:6%;color:red;">'
				+'<span id="Sdel'+r+'" onclick="deleteDetailsdata('+r+',this)"><i class="fa fa-trash p-l-3"></i></span>'
				+'</td>';
		cols += '<input type="hidden" class="form-control" id="FD_STATUS_'+r+'" name="FD_STATUS_'+r+'" value="N">';
		cols += '<input type="hidden" class="form-control" id="FD_MID_'+r+'" name="FD_MID_'+r+'" value="">';
		cols += '</tr>';
	}
	
	$('#fac_Table').append(cols);
	cols=""; 
	$("#totRow").val(parseInt(r));	
	$(document).ready(function() {
		$(".XFDATE1").datepicker({
		   format : 'dd/mm/yyyy',
		   autoclose : true,
		   orientation: 'top',
		   todayHighlight: true,
		});
	});
	
}

function deletelistfile(index, did) {
	var rowcountAfterDelete="";
	rowcountAfterDelete = document.getElementById("fac_Table").rows.length;
	var rowcountAfterDelete1 = parseInt($("#totRow").val());
    if (rowcountAfterDelete != 1) {
        var x = parseInt($("#sno_" + index).text());
        for (var i = parseInt(index); i <= rowcountAfterDelete; i++) {
            if ($("#sno_" + (i + 1)).text() != undefined && $("#sno_" + (i + 1)).text() != "")
            {
                $("#sno_" + (i + 1)).text(x);
                ++x;
            }
        }
        $(did).parents("tr").remove();
        $("#totRow").val(index-1);
    }else {
        displaySuccessMessages("errMsg2", "At least 1 row should be present in the table", "");
        clearSuccessMessageAfterFiveSecond("errMsg2");
    }
}

function deletedata(i, id, file_name) {
	try {
		var del=confirm("Are You Sure?")
		if(del==true){
			$.post("../InspectionFacultyDetailService",{id:id, fstatus:"PD", file:file_name
			}).done(function(data){
				displaySuccessMessages("errMsg1", data.errMsg, "");
			}, 3000);
			setTimeout(function () {
	    		location.reload();
	    	}, 5000);
		}
	} catch (e) {
		// TODO: handle exception
		alert("ERROR :"+e);
	}
}

function deletefile(fileid,fliename,i){
	var retVal = confirm("File will be deleted permanently. \nAre you sure you want to remove this file?");
	if(retVal == true) {
		try{
			$.ajax({
				type: "POST",
				url: "../InspectionFacultyDetailService",
				data:{"fstatus":"Delete", "fileid":fileid, "fliename":fliename},
				success: function (data){
					location.reload();
				}
			});
		}catch(err){
			alert(err);
		}
	} else {
		return false;
	}
}
/*function fileValidation(obj) {
	var fileInput = document.getElementById(obj);
    var filePath = fileInput.value;
    var allowedExtensions = /(\.pdf|\.doc|\.png|\.jpg)$/i;
    if (!allowedExtensions.exec(filePath)) {
        alert('Invalid file type, file format should be JPG/PNG/PDF/DOC.');
        fileInput.value = '';
        return false;
    }
}*/

function fileValidation(id){  
	if($('#'+id).val()!=''){
		var photoSize  = parseInt(($("#"+id)[0].files[0].size / 1024));
		if(!(isJpg($('#'+id).val())||isJpeg($('#'+id).val())||isPng($('#'+id).val()))){
			$('#'+id).focus();
			$('#'+id).val('');
			alert("Upload Documents File must be .JPG,.JPEG,.PNG!");
			return false;
		}
		else if((photoSize>100 || photoSize<10)){
			$('#'+id).focus();
			$('#'+id).val('');
			alert("Upload Documents File size should be greater than 10KB and less than 100KB!");
			return false;
		} 
	}
}