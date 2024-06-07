$(document).ready(function(){
	
$("#btnBack").click(function(){   // call on Back Button
	document.location.href="questionnaire_master_e.jsp";
});

$("#btnNew").click(function(){   // call on New Button
	document.location.href="questionnaire_master_e.jsp?opt_typ=N";
});

$("#btnReset").click(function(){   // call on Reset Button
	window.location.reload();
});

$("#btnReseTas").click(function(){   // call on Reset Button
	$("#ASQUES_TYPE").val('');
	$("#ASQUES_NAME").val('');
});

$(document).off('change', '#XQUES_TYPE').on('change', '#XQUES_TYPE', function() {
	var type=$("#XQUES_TYPE").val();
	getQuesSubTypeByType(type, "XQUES_SUB_TYPE", "");
});

});

function vldSearch(){
	frmQuaterMastD.target ="btmfrmQuestnrMastD";
	frmQuaterMastD.action="questionnaire_master_l.jsp";
	frmQuaterMastD.submit();
}

function formValidation(){
	if($("#XQUES_NAME").val()==''){
		$("#XQUES_NAME").focus();
		showerr($("#XQUES_NAME")[0],"Please enter Questionnier name.","block");
		return false;
	}else if($("#XQUES_TYPE").val()==''){
		$("#XQUES_TYPE").focus();
		showerr($("#XQUES_TYPE")[0],"Please select Questionnier Type","block");
		return false;
	}else if($("#XQUES_SUB_TYPE").val()==''){
		$("#XQUES_SUB_TYPE").focus();
		showerr($("#XQUES_SUB_TYPE")[0],"Please select Questionnier Sub Type","block");
		return false;
	}else{
		return true;
	}
}

function saveRecord(){
	if(formValidation()){
		if($("#fstatus").val()=="N"){
			excuteSaveRecord();
		}else if($("#fstatus").val()=="E"){
			excuteUpdateRecord();
		}
	}
}

function excuteSaveRecord(){
	try{
	var ques_name 	= $("#XQUES_NAME").val();
	var ques_type 	= $("#XQUES_TYPE").val();
	var fstatus		= $("#fstatus").val();
	var ques_id		= $("#ques_id").val();
	var ques_sub_type 	= $("#XQUES_SUB_TYPE").val();
	var isActive;
	if ($('input#XIS_ACTIVE').is(':checked')) {
	 	isActive = "Y";
	 }else{
		 isActive= "N";
	 }
	$.ajax({
		type : "POST",
		url: 'QuestionnaireMastService', 
	    data: {
	    	is_active		: isActive,
	    	ques_name		: ques_name,
	    	ques_type		: ques_type,
	    	fstatus			: fstatus,
	    	ques_id         : ques_id,
	    	ques_sub_type 	: ques_sub_type
	    	  },
		success : function(data) {
			saveOrUpdateCommonFunctionInRegMast(data);
			
			
			
			/*setTimeout(function () {		
				displaySuccessMessages("errMsg", data.errMsg,"");  
			}, 1000);
			clearSuccessMessageAfterFiveSecond("errMsg");
    	  if(data.status=="N"){
    	  setTimeout(function () {	
    		 
				document.location.reload(); 
			}, 3000);		 
			}
			else if(data.status == "E"){
				 setTimeout(function () {		
						document.location.href="questionnaire_master_d.jsp"; 
					}, 3000);	
			}*/
	    },
	    
	    error: function() {
        	alert("Error"); 
        }
	    /*error: function(xhr, status, error) {
	    	alert("xhr "+JSON.stringify(xhr));
	    	alert("error "+error);
	    	}*/
	    });
	}catch (err){
		alert(err);
	} 
	
}

function excuteUpdateRecord(){
	var ques_id		= $("#ques_id").val();
	var ques_name 	= $("#XQUES_NAME").val();
	var ques_type 	= $("#XQUES_TYPE").val();
	var ques_sub_type 	= $("#XQUES_SUB_TYPE").val();
	var fstatus		= $("#fstatus").val();
	
	var isActive;
	if ($('input#XIS_ACTIVE').is(':checked')) {
	 	isActive = "Y";
	 }else{
		 isActive= "N";
	 }
	$.ajax({
		type : "POST",
		url: 'QuestionnaireMastService', 
	    data: {
	    	is_active		: isActive,
	    	ques_id			: ques_id,
	    	ques_name		: ques_name,
	    	ques_type		: ques_type,
	    	fstatus			: fstatus,
	    	ques_sub_type 	: ques_sub_type
	    	  },
	    	  success : function(data) {
			    	saveOrUpdateCommonFunctionInRegMast(data);
			    },
		        error: function() {
		        	alert("Error"); 
		        }
	    	  
	    	  
		/*success : function(data) {		
			setTimeout(function () {		
				displaySuccessMessages("errMsg", data.errMsg,"");  
			}, 1000);
			clearSuccessMessageAfterFiveSecond("errMsg");
   	  if(data.status=="N"){
   	  setTimeout(function () {		
				document.location.reload(); 
			}, 2000);		 
			}
			else if(data.status == "E"){
				 setTimeout(function () {		
						document.location.href="questionnaire_master_d.jsp"; 
					}, 5000);	
			}
	    },
	    error: function(xhr, status, error) {
	    	alert("xhr "+JSON.stringify(xhr));
	    	alert("error "+error);
	    	}*/
	    });
}


/*function vldSearch(){
	document.frmQuestnrMastD.target = "btmfrmQuestnrMastD" ;
	document.frmQuestnrMastD.action = "questionnaire_master_l.jsp";
	document.frmQuestnrMastD.submit();
}*/


function editRecord(ques_id){
	 parent.location.href="questionnaire_master_e.jsp?opt_typ=E&ques_id="+ques_id;
}

function delRecord(ques_id){
		var del=confirm("Are You Sure?")
	    if(del==true){
		$.ajax({
			type : "POST",
			url : "QuestionnaireMastService",		
			data:{
				fstatus : "D",
				ques_id : ques_id,
			},	    
			success : function(data) {	
				var errMsg=data.errMsg; 
				if(errMsg.includes("Questionnaire")){ 
					setTimeout(function () {
					      displaySuccessMessages("errMsg1", errMsg, "");
					     }, 2000);
						clearSuccessMessageAfterFiveSecond("errMsg1");
						setTimeout(function () {
						      location.reload();
						     }, 3000); 
				}else{  
					setTimeout(function () {
					      displaySuccessMessages("errMsg", errMsg, "");
					     }, 2000);
						clearSuccessMessageAfterFiveSecond("errMsg");
						setTimeout(function () {
						      location.reload();
						     }, 3000);	
				}
				
			}
		});
	    }
	}
function saveOrUpdateCommonFunctionInRegMast(data) {
	if (data.fstatus == "N")hideElement("btnSave");  
	if (data.fstatus == "E") hideElement("btnUpdate"); 
	 
	var tRec = $("#totRow").val();
	setTimeout(function () {
		if (data.fstatus == "D"){
			for(var i=1;i<=tRec;i++){
				$("#EDIT_RECORD_"+i).wrap('<td style="display:none"/>');
				$("#DELETE_RECORD_"+i).wrap('<td style="display:none"/>');
			}
			setTimeout(function () { 
				displaySuccessMessages("errMsg1", data.errMsg, "");
		     }, 1000);
			clearSuccessMessageAfterFiveSecond("errMsg1");
		}
		//displaySuccessMessages("errMsg", data.errMsg, "");
     }, 1000);
	//clearSuccessMessageAfterFiveSecond("errMsg");
    if(data.flg=="V") { 
    	if (data.fstatus != "D") wiepAllBatchMastInE() 
    	if (data.fstatus == "N") {showWinAfterFiveSecondForE();
    	setTimeout(function () { 
			displaySuccessMessages("errMsg1", data.errMsg, "");
	     }, 1000);
		clearSuccessMessageAfterFiveSecond("errMsg1");
    	 
    	} else if (data.fstatus == "E"){ showWinAfterFiveSecondForD(); 
    	setTimeout(function () { 
			displaySuccessMessages("errMsg1", data.errMsg, "");
	     }, 1000);
		clearSuccessMessageAfterFiveSecond("errMsg1");
    	
    	}else if (data.fstatus == "D") {showWinAfterFiveSecondForL(); 
    	setTimeout(function () { 
			displaySuccessMessages("errMsg1", data.errMsg, "");
	     }, 1000);
		clearSuccessMessageAfterFiveSecond("errMsg1");
    	}
    } else {
    	setTimeout(function () { 
			displaySuccessMessages("errMsg", data.errMsg, "");
	     }, 1000);
		clearSuccessMessageAfterFiveSecond("errMsg");
    	
    	if (data.fstatus == "N") showElementAfterFiveSecond("btnSave"); 
        else if (data.fstatus == "E") showElementAfterFiveSecond("btnUpdate"); 
 
    
    }
}


function wiepAllBatchMastInE(){
	$("#ques_id").val("");
	$("#XQUES_NAME").val("");
	$("#XQUES_TYPE").val("");
	$("#XQUES_SUB_TYPE").val("");
	$("#fstatus").val("");
}

  
function showWinAfterFiveSecondForE() {
    setTimeout(function () {
    	document.location.href="questionnaire_master_e.jsp?opt_typ=N";
    }, 5000);
}
// for redirect form into searching form after update
function showWinAfterFiveSecondForD() {
    setTimeout(function () {
    	document.location.href = 'questionnaire_master_e.jsp';
    }, 5000);
}
// for reload form after delete
function showWinAfterFiveSecondForL() {
    setTimeout(function () {
    	location.reload();
    }, 5000);
}


function wiepAllBatchMastInD(){
	$("#ques_id").val("");
	$("#XQUES_NAME").val("");
	$("#XQUES_TYPE").val("");
	$("#XQUES_SUB_TYPE").val("");
	$("#fstatus").val("");
}
