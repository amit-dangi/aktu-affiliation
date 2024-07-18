$(document).ready(function(){
	$("#btnReset").click(function(){
		location.reload();
	});
	
	var count = parseInt($("#totRow").val());
	if(count == '' || count == undefined || count==0){
		hideElement("btnSave");	
	}
});

function UploadDoc() {
	try {
		var form_data = new FormData();
		var cnt = parseInt($("#totRow").val());
		var docArray = [], x=0;
		var Inst_Id=$("#Inst_Id").val();
		if (Inst_Id == "") {
			displaySuccessMessages("errMsg2", "College Id is Required.", "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
			return false;
	    }
		for (let i=1; i<=cnt; i++) {
			if($('#doc_status'+i).val()=="N"){
				if($('#doc_comp'+i).val()=="Y"){
					if(($('#doc_file'+i).val()=="" || $('#doc_file'+i).val()==null )&& ($('#doc_att'+i).val()=='') ){
						$('#doc_file'+i).focus();
						showerr($('#doc_file'+i)[0],"Document is required","block");
						return false;
					}
				}
				if ($("#doc_file"+i).val()!='' && $("#doc_file"+i).val()!=undefined){
					docArray.push({
						"name"		: $("#doc_name"+i).val(),
						"comp"		: $("#doc_comp"+i).val(),
						"id"		: $("#doc_id"+i).val(),
						"doc_typ"   : $("#doc_type"+i).val()
						//"doc_name"	: $("#doc_name"+i).val(), "docId"		: $("#docId"+i).val(),
					});
					++x;
					
					var fileCount = document.getElementById("doc_file"+i).files.length;
					for (j = 0; j < fileCount; j++) {
						form_data.append("uplDoc_"+x, document.getElementById("doc_file"+i).files[j]);
					}
				}				
			}
		}
		
		if (docArray.length > 0){
			var objjson={docArray:docArray};
			
			hideElement("btnSave");
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("POST", "../InspectionUploadDocService?fstatus=1&Inst_Id="+Inst_Id, true);
			xmlHttp.setRequestHeader("objjson", JSON.stringify(objjson));
			xmlHttp.send(form_data);
			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
					var data = JSON.parse(this.responseText);
					if(data.status=="1") {
						displaySuccessMessages("errMsg1", data.msg, "");
						clearSuccessMessageAfterFiveSecond("errMsg1");
						/*setTimeout(function () {
							document.location.href="../AFFILIATION/apply_for_affiation.jsp";
						}, 3000);*/
						setTimeout(function () {
							 parent.location.reload();
						}, 500);
					}else {
						displaySuccessMessages("errMsg2", data.msg, "");
						clearSuccessMessageAfterFiveSecond("errMsg2");
						showElementAfterFiveSecond("btnSave");
					}
				}
			}
		}else{
			hideElement("btnSave");
			displaySuccessMessages("errMsg2", "Attachment Already Updated", "");
			clearSuccessMessageAfterFiveSecond("errMsg2");
			showElementAfterFiveSecond("btnSave");
		}
	} catch (e) {
		// TODO: handle exception
		alert("ERROR :"+e);
	}
}

function filechk(id){  
	if($('#'+id).val()!=''){
		var photoSize  = parseInt(($("#"+id)[0].files[0].size / 1024));
		if(!(isJpg($('#'+id).val())||isJpeg($('#'+id).val())||isPng($('#'+id).val())||isPdf($('#'+id).val()))){
			$('#'+id).focus();
			$('#'+id).val('');
			alert("Upload Documents File must be .JPG,.JPEG,.PNG,.PDF!");
			return false;
		}
		else if((photoSize>5120 || photoSize<10)){
			$('#'+id).focus();
			$('#'+id).val('');
			alert("Upload Documents File size should be greater than 10KB and less than 3MB!");
			return false;
		} 
	}
}

var isJpg = function (name) {
	return name.match(/jpg$/i);
};
var isJpeg = function (name) {
	return name.match(/jpeg$/i);
};
var isPng = function (name) {
	return name.match(/png$/i);
};
var isPdf = function (name) {
	return name.match(/pdf$/i);
};

function deletedocfile(attid,fliename,mastid,name,id, mid){
	var retVal = confirm("File will be deleted permanently. \nAre you sure you want to remove this file?");
	if(retVal == true) {
		try{
			var Inst_Id=$("#Inst_Id").val();
			$.ajax({
				type: "POST",
				url: "../InspectionUploadDocService",
				data:{"fstatus":"D", "attid":attid, "filename":fliename,"mastid":mastid, "mid":mid,"Inst_Id":Inst_Id},
				success: function (data){
					//cons='<input type="file" class="form-control" id="doc_file'+id+'" name="doc_file'+id+'" onchange="filechk(this.id);" >';	
					//$('#'+name).html(cons);
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

function showButton(){
	$("#btnSave").show();
} 