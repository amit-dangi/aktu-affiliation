$(document).ready(function(){
	/*$("#btnNew").click(function(){
		 document.location.href="quistionnaire_parameter_master_e.jsp?fstatus=N";
	});
	$("#btnBack").click(function(){
		 document.location.href="quistionnaire_parameter_master_d.jsp";
	});
	$("#btnBack1").click(function(){
		 document.location.href="quistionnaire_parameter_master_d.jsp";
	});
	$("#btnReset").click(function(){
		 document.location.reload();
	});
	*/
	$("#btnReset1").click(function(){
		 document.location.reload();
	});
	$("#btnBack").click(function(){		
		var flag = $("#flag").val();
		var ques_name = $("#ques_name").val();
		var ques_type = $("#ques_type").val();
		//alert(ques_name+"|"+ques_type+"|"+flag);
		if(flag=="Y"){
		var xyz=confirm("Do You Want To GoBack Without Save Data?");
		//alert("xyz::"+xyz);
		if(xyz){
			//alert("xyz::"+xyz);
			window.location.href="quistionnaire_parameter_master_e.jsp?fstatus=N&ques_name="+ques_name+"&ques_type="+ques_type;
		}else{
			$(".com-course-sem-map").show();
		}
		}else{
			window.location.href="quistionnaire_parameter_master_e.jsp?fstatus=N&ques_name="+ques_name+"&ques_type="+ques_type;
		}
		
});
	if($("#fstatus").val()!="E")
		$(".com-course-sem-map").hide();
	
	var wrapper   	   = $(".input_fields_wrap"); //Fields wrapper
	var add_field      = $(".add_field_button"); //Add button ID
	var x = $("#count").val(); //initlal text box count
	$(add_field).click(function(){
	var x = $("#count").val();
	x++; 	
	$(wrapper).append('  <div class="" <div class="form-group"><div class="col-sm-12"  id="divOpt'+x+'"> <div class="row">'
			+' <label class="manageTxt1 col-sm-2 col-form-label required-field" style="" >Option  Value</label>'
			+' <div class="col-sm-4"><input type="text" class="manageTxt form-control" id="opt'+x+'" name="opt'+x+'" style="width: 100%" placeholder="Enter Option Value" maxlength="150"/><input type="hidden" name="opt_hide'+x+'" id="opt_hide'+x+'" value=""/> </div> <a href="#" class="remove_field"><i class="fa fa-times-circle fa-2x"style="padding-left: 30px; padding-top: 5px; color:red"></i></a></div></div></div></div></div>');
	$("#count").val(x);
	});

	$(wrapper).on("click",".remove_field", function(e){ //user click on remove text
		e.preventDefault();
		var x = $("#count").val();
	    $(this).parent("div").remove();
		/*x--;*/
		$("#count").val(x);
		
	 });
	
});

$(document).off('change', '#ques_name').on('change', '#ques_name',function() {
	var ques_name = $("#ques_name").val();
	setDropDown(ques_name);
});

$(document).off('change', '#param_flag').on('change', '#param_flag',function() {
	onChangeParam_flag();
});


function setQues_type(ques_type){
	$("#ques_type").val(ques_type);
}

function disabledParamFlag(ques_type){
	if(ques_type == "CC" || ques_type == "TS" || ques_type=="RP" ){
		$("#param_flag").val('N');
		$("#param_flag").attr("disabled",true);
		$(".option_value").hide();
	}
	else{
		$("#param_flag").attr("disabled",false);
		$("#param_flag").val("");
		$(".option_value").show();
	}
	
}
function disableIsSubQues(ques_type){debugger;
	if(ques_type == "CQ" ){
		$("#is_sub_ques").attr("disabled",false);
	}
	else{
		$("#is_sub_ques").attr("disabled",true);
	}
	
}


function onChangeParam_flag(){
	var param_flag = $("#param_flag").val();
	 if(param_flag =="M" || param_flag =="A" || param_flag =="N"){
		$("#is_sub_ques").prop('checked',false);
		$("#is_sub_ques").attr("disabled",true);
	}
	 else{
		 $("#is_sub_ques").attr("disabled",false);
	 }
	 
	 if(param_flag =="M" ){
		 $( ".add_field_button" ).prop( "disabled", true );
		 var num=0;
		 var cnt = $("#count").val();
		 for(var jj=0;jj<cnt;jj++){
				++num;
				if(num==1){
					$( ".add_field_button" ).prop("disabled", true );
					$("#opt1").val("");
					 $("#opt1").prop("disabled", true);
				}else{
					$("#opt"+num).val(""); 
					$(".remove_field").parent("div").remove();
				}
			}
			$("#count").val("1");
			
	 }else{
		 $( ".add_field_button" ).prop("disabled", false);
		 $("#opt1").prop("disabled", false);
	 }
}

function vldReset(){
	$("#ques_desc").val("");
	$("#is_sub_ques").prop('checked',false);
	$("#order_no").val("");
	$("#param_flag").val("");
	$("#opt1").val("");
	$("#is_active").prop('checked',false);
	
	var num=0;
	var cnt = $("#count").val();
	for(var jj=0;jj<cnt.length;jj++){
		++num;
		$("#opt"+num).val(""); 
		$(".remove_field").parent("div").remove();
	}
	$("#count").val("1");
	return true;
	
}
function editView(ques_name,status){
	if(ques_name != ""){	
	$(".com-course-sem-map").show();
	$("#ques_name_hide").val($("#ques_name").val());
	$("#ques_name").attr("disabled",true);
	$("#btnView").hide();
	$("#btnReset1").hide();
	$("#btnBack1").hide();
//	vldReset();
	getList(ques_name,status);
	}
}

function view(ques_name, status){
	var ques_name = $("#ques_name").val();
	var ques_type_id = $("#ques_type_id").val();
	var ques_sub_type_id = $("#ques_sub_type_id").val();
	var status = $("#fstatus").val();
	
	if(ques_name==""||ques_name==null)
	  {
		showerr($("#ques_name")[0],"Questionnaire Name is required","block");
		$("#ques_name").focus();
		return false;
	  }
	vldReset();
	//$("#ques_name_hide").val(ques_type_id);
	//$("#ques_name").attr("disabled",true);
	$("#btnView").hide();
	$("#btnReset1").hide();
	$("#btnBack1").hide();
	$(".com-course-sem-map").show();
	
	//disabledParamFlag(ques_type_id);
	//disableIsSubQues(ques_type_id);
	
	getList(ques_name, status);
}

//add new row

function addRow(){debugger;
		var ques_name 	 = $("#ques_name").val();
		var ques_desc	 = $("#ques_desc").val();
		var order_no	 = $("#order_no").val();
		var param_flag	 = $("#param_flag").val();
		var opt1 		 = $("#opt1").val();
		$("#flag").val("Y");
		var is_sub_ques  = "N";
		var is_active    = "N";
		var opt="", span_opt="";
		
		//var q_n = ques_name.split("~");
	//added by om kumar on 06-12-2023 to run default condition	
		//if(q_n[1] == "CQ"){
			var cnt = $("#count").val();
			for(i=1;i<=cnt;i++){
				if($("#opt"+i).val()!=undefined){
					opt += $("#opt"+i).val()+"~";
					span_opt += $("#opt"+i).val()+",";
				}
			}
			opt = opt.substring(0, opt.length - 1);
			span_opt = span_opt.substring(0, span_opt.length - 1);
		/*}else{
			opt="";
		}*/
		
		
		
		if ($('#is_sub_ques').prop('checked') == true) {
			is_sub_ques = "Y";
		}
		if ($('#is_active').prop('checked') == true) {
			is_active = "Y";
		}
		var is_sub_ques_desc = "";
		if(is_sub_ques=="N"){
			is_sub_ques_desc="No";
		}else{
			is_sub_ques_desc="Yes";
		}
		
		if(ques_name==""||ques_name==null)
		  {
			showerr($("#ques_name")[0],"Questionnaire Name is required","block");
			$("#ques_name").focus();
			return false;
		  }
		
		if(ques_desc==""||ques_desc==null)
		  {
				showerr($("#ques_desc")[0],"Feedback Question Description is required","block");
				$("#ques_desc").focus();
				return false;
	      }
		if(order_no==""||order_no==null)
		  {
				showerr($("#order_no")[0],"Order number is required","block");
				$("#order_no").focus();
				return false;
	      }
		if(!chkIsInteger(order_no)){
			showerr($("#order_no")[0],"Order number should be numeric. ","block");
			$("#order_no").focus();
			return false;
		}
		if(parseInt(order_no)<0 || parseInt(order_no) ==0){
			showerr($("#order_no")[0],"Order number should be greater than 0.","block");
			$("#order_no").focus();
			return false;
		}
						
		if(param_flag==""||param_flag==null )
		  {
			showerr($("#param_flag")[0],"Parameter Flag is required","block");
			$("#param_flag").focus();
			return false;
		  }
		if(param_flag !="M" && param_flag !="A" && param_flag !="N"){
			for(z=1;z<=cnt;z++){
				if( $("#opt"+z).val() !=undefined &&  $("#opt"+z).val() ==""){
					showerr($("#opt"+z)[0],"Option Value is required.","block");
					$("#opt"+z).focus();
					return false;
				}
			}
		}
		
		var ques_name_val	 = $("#ques_name option:selected").text();
		var param_flag_val 	 = $("#param_flag option:selected").text();
		
 var newRow = $("<tr>");	 
 var cols = "";
 var rr= $('#tot_row_count').val();
 var r = parseInt(rr)+1;
	cols += '<tr id="tr_'+r+ '">';
	cols += '<td style="width:4%"><span id="sno'+r+'"> '+r+'</span></td>';
	
	cols += '<td style="width:10%; text-align:center;"><span  id="span_ques_name'+r+'">'+ ques_name_val 	      	
		 +'</span><input type="hidden" class="form-control" name="ques_name_'+r+'" id="ques_name_'+r+'" value="'+ ques_name +'" readOnly/> </td>';	
		      	
	cols += '<td style="width:10% ;text-align:center"><span id="span_ques_desc'+r+'">'+ques_desc	      	
		+'</span><input type="hidden" class="form-control" name="ques_desc_'+r+'" id="ques_desc_'+r+'" value="'+ ques_desc +'" readOnly/> </td>';
	
	cols += '<td style="width:10% ;text-align:center"><span id="span_is_sub_ques_desc'+r+'">'+is_sub_ques_desc	      	
		+'</span><input type="hidden" class="form-control" name="is_sub_ques_'+r+'" id="is_sub_ques_'+r+'" value="'+ is_sub_ques +'" readOnly/> </td>';
	
	cols += '<td style="width:10%; text-align:right;"><span id="span_order_no'+r+'">'+order_no	      	
		+'</span><input type="hidden" class="form-control" name="order_no_'+r+'" id="order_no_'+r+'" value="'+ order_no +'" readOnly/>'
		+'<input type="hidden" class="form-control" name="is_active_'+r+'" id="is_active_'+r+'" value="'+ is_active +'" readOnly/>'	
		+'<input type="hidden" class="form-control" name="ques_param_id_'+r+'" id="ques_param_id_'+r+'" value="" readOnly/>'	
		+'<input type="hidden" class="form-control" name="ques_sub_param_id_'+r+'" id="ques_sub_param_id_'+r+'" value="" readOnly/></td>';
	
	cols += '<td style="width:10%; text-align:center;"><span id="span_param_flag'+r+'">'+param_flag_val	      	
		+'</span><input type="hidden" class="form-control" name="param_flag_'+r+'" id="param_flag_'+r+'" value="'+ param_flag +'" readOnly/> </td>';	

	cols += '<td style="width:10%; text-align:center;"><span id="span_opt1'+r+'">'+span_opt	      	
		+'</span><input type="hidden" class="form-control" name="opt1_'+r+'" id="opt1_'+r+'" value="'+ opt +'" readOnly/> </td>';	
	
	cols += ' <td class="text-center colr-blue-p" style="width:8%;"><a href="JavaScript:Void(0)"  id="editRow_'+r+'" style="font-size: 13px !important;cursor: pointer;width:10%" onclick="editGridRecord('+r+');" target="" ><i class="fa fa-edit p-l-3"></i>Edit</a></td>';
	
	cols += ' <td class="text-center colr-red-p" style="width:8%;"><a href="JavaScript:Void(0)"  id="Deleterow_'+r+'" style=" color:#d73925;font-size: 13px !important;cursor: pointer;width:10%" onclick="deleteGridRecord('+r+');" target="" ><i class="fa fa-trash p-l-3"></i>Delete</a></td>';
	
	cols += '</tr>';
	
	$('#searchTable').append(cols);
		//cols="";                     
		$('#tot_row_count').val(r);	         	        
		$("#ques_desc").val("");
		$("#is_sub_ques").prop('checked',false);
		$("#order_no").val("");
		$("#param_flag").val("");
		$("#opt1").val("");
		$("#is_active").prop('checked',false);
		
		$("#sno").val("");
		$("#span_ques_name").val("");
		$("#span_ques_desc").val("");
		$("#span_order_no").val("");
		$("#span_param_flag").val("");
		$("#span_is_sub_ques_desc").val("");
		//$("#span_opt1").val("");
	
		var num=0;
		for(var jj=0;jj<cnt.length;jj++){
			++num;
			$("#opt"+num).val(""); 
			$(".remove_field").parent("div").remove();
		}
		$("#count").val("1");
		return true;
		
	} 


function saveRecord(status){
	
	var n = $("#tot_row_count").val();
	var jsonArrayObj=[];
	var jsonArrayObj2={};
	
   for(var i=1;i<=n;i++){
		jsonArrayObj.push({						
			ques_desc		: $("#ques_desc_" + i).val(),
			is_sub_ques		: $("#is_sub_ques_"+i).val(),
			order_no		: $("#order_no_"+i).val(),
			param_flag	 	: $("#param_flag_"+i).val(),
			opt1 			: $("#opt1_"+i).val(),	
			is_active	 	: $("#is_active_" + i).val(),	
			ques_param_id	: $("#ques_param_id_"+i).val(),
			ques_sub_param_id : $("#ques_sub_param_id_"+i).val(),
		});	
		}
	jsonArrayObj2=JSON.stringify(jsonArrayObj);
	
	 $.ajax({
			type : "POST",
			url: 'QuestionnaireParamMastService', 
		    data: {
		    	fstatus		: "S",		    	
		    	jsonObj 	: jsonArrayObj2,
		    	status		: status,
		    	ques_name	: $("#ques_name_hide").val(),
		    			    	
		    	  },
			success : function(data) {	
			
       	  if(data.status=="N"){
       		  
       		  if(data.flg=="N"){
		       		setTimeout(function () {	
						displaySuccessMessages("errMsg", data.errMsg,"");  
					}, 3000);
		       		clearSuccessMessageAfterTenSecond("errMsg");
       		  }else{
       			setTimeout(function () {	
    				displaySuccessMessages("errMsg1", data.errMsg,"");  
    			}, 3000);
       			clearSuccessMessageAfterTenSecond("errMsg1");
       		  }
       		  
       		  setTimeout(function () {	
       		  if(data.flg=="Y"){
					window.location.reload(); 
       		  }
				}, 5000);		 
				}
				else if(data.status == "E"){
					 if(data.flg=="N"){
				       		setTimeout(function () {	
								displaySuccessMessages("errMsg", data.errMsg,"");  
							}, 3000);
				       		clearSuccessMessageAfterTenSecond("errMsg");
		       		  }else{
		       			setTimeout(function () {	
		    				displaySuccessMessages("errMsg1", data.errMsg,"");  
		    			}, 3000);
		       			clearSuccessMessageAfterTenSecond("errMsg1");
		       		  }
					
					setTimeout(function () {	
						 if(data.flg=="Y"){
							 document.location.href="quistionnaire_parameter_master_d.jsp"; 
			       		  }							
						}, 5000);	
				}
		    },
		    
		    error: function(xhr, status, error) {
		    	alert("error");
		    	}
		    });
	
}
/*function vldSearch(){
	frmQuesParamMastD.target="btmQuesParamMast";
	frmQuesParamMastD.action="quistionnaire_parameter_master_l.jsp";
	frmQuesParamMastD.submit();
 }*/

//onclick on view and edit case fetch list.
function getList(ques_name,status){ 
	$("#ques_name_hide").val(ques_name);
	var data=""
	var r=0;
	data= 'fstatus=L&ques_name='+ques_name;
    $.ajax({
        type: 'POST',
        url:  'QuestionnaireParamMastService',
        data: data,
        async: false,
        success: function (response)
        { 
        	$("#searchTable tr").remove(); 
            if (typeof response.allList != 'undefined' && response.allList.length > 0)
            {
            	for (var i = 0; i <response.allList.length; i++) {
                	var newRow = $("<tr>");	 
                	 var cols = "";
                	 r=r+1;
                	 	cols += '<tr id="tr_'+r+ '">';
                		cols += '<td style="width:4%"><span id="sno'+r+'"> '+r+'</span></td>';
                		
                		cols += '<td style="width:10%; text-align:left;"><span  id="span_ques_name'+r+'">'+ response.allList[i].ques_name  	      	
                			 +'</span><input type="hidden" class="form-control" name="ques_name_'+r+'" id="ques_name_'+r+'" value="'+ response.allList[i].ques_id +'" readOnly/> </td>';	
                			      	
                		cols += '<td style="width:10%; text-align:left;"><span id="span_ques_desc'+r+'">'+response.allList[i].Feedback_desc      	
                			+'</span><input type="hidden" class="form-control" name="ques_desc_'+r+'" id="ques_desc_'+r+'" value="'+ response.allList[i].Feedback_desc +'" readOnly/> </td>';	
                			var is_sub_ques_desc="";
                			if(response.allList[i].is_sub_ques=="N"){
                				is_sub_ques_desc="No";
                			}else{
                				is_sub_ques_desc="Yes";
                			}
                		cols += '<td style="width:10% ;text-align:left"><span id="span_is_sub_ques_desc'+r+'">'+is_sub_ques_desc	      	
                			+'</span><input type="hidden" class="form-control" name="is_sub_ques_'+r+'" id="is_sub_ques_'+r+'" value="'+ response.allList[i].is_sub_ques +'" readOnly/> </td>';
                	
                		cols += '<td style="width:10%; text-align:right;"><span id="span_order_no'+r+'">'+response.allList[i].order_no
                			+'</span><input type="hidden" class="form-control" name="order_no_'+r+'" id="order_no_'+r+'" value="'+ response.allList[i].order_no +'" readOnly/>'
                			+''
                			+'<input type="hidden" class="form-control" name="is_active_'+r+'" id="is_active_'+r+'" value="'+ response.allList[i].is_active +'" readOnly/>'
                			+'<input type="hidden" class="form-control" name="ques_param_id_'+r+'" id="ques_param_id_'+r+'" value="'+ response.allList[i].QUES_PARAM_ID +'" readOnly/>'
                			+'<input type="hidden" class="form-control" name="ques_sub_param_id_'+r+'" id="ques_sub_param_id_'+r+'" value="'+ response.allList[i].ques_sub_param_id +'" readOnly/></td>';
                		
                		if(response.allList[i].param_flag == "C"){
                			param_flag = "Check box";
                		}
                		else if(response.allList[i].param_flag == "D"){
                			param_flag = "Drop Down";
                		}
                		else if(response.allList[i].param_flag == "T"){
                			param_flag = "Text";
                		}
                		else if(response.allList[i].param_flag == "M"){
                			param_flag = "Multiline Text box";
                		}
                		else if(response.allList[i].param_flag == "A"){
                			param_flag = "Alpha Numeric Text box";
                		}
                		else if(response.allList[i].param_flag == "N"){
                			param_flag = "Numeric Text box";
                		}
                		else{
                			param_flag = "";
                		}
                		cols += '<td style="width:10%; text-align:left;"><span id="span_param_flag'+r+'">'+param_flag	      	
                			+'</span><input type="hidden" class="form-control" name="param_flag_'+r+'" id="param_flag_'+r+'" value="'+ response.allList[i].param_flag +'" readOnly/> </td>';	

                		var span_opt_arr = response.allList[i].opt_val.split("~");
                		var span_opt = "";
                		for(var l=0;l<span_opt_arr.length;l++){
                			span_opt += span_opt_arr[l]+",";
                		}
                		span_opt = span_opt.substring(0, span_opt.length - 1);
                		cols += '<td style="width:10%; text-align:left;"><span id="span_opt1'+r+'">'+span_opt		      	
                			+'</span><input type="hidden" class="form-control" name="opt1_'+r+'" id="opt1_'+r+'" value="'+ response.allList[i].opt_val	 +'" readOnly/> </td>';	
                		
                		if(parseInt(response.allList[i].cnt) == 0){
                		
                			cols += ' <td class="text-center colr-blue-p" style="width:8%;"><a href="JavaScript:Void(0)"  id="editRow_'+r+'" style="font-size: 13px !important;cursor: pointer;width:10%" onclick="editGridRecord('+r+');" target="" ><i class="fa fa-edit p-l-3"></i>Edit</a></td>';
                		}
                		else{
                			cols += '<td></td>';
                		}
                		if(parseInt(response.allList[i].cnt) == 0){
                			
                			cols += ' <td class="text-center colr-red-p" style="width:8%;"><a href="JavaScript:Void(0)"  id="Deleterow_'+r+'" style=" color:#d73925;font-size: 13px !important;cursor: pointer;width:10%" onclick="deleteGridRecord('+r+');" target="" ><i class="fa fa-trash p-l-3"></i>Delete</a></td>';
                		}
                		else{
                			cols += '<td></td>';
                		}
                		cols += '</tr>';
                		
                		$('#searchTable').append(cols);
                			cols="";                     
                			         	        
                			cols="";                     
                			$('#tot_row_count').val(r);	         	        
                			$("#ques_desc").val("");
                			$("#is_sub_ques").prop('checked',false);
                			$("#order_no").val("");
                			//$("#param_flag").val("");
                			$("#opt1").val("");
                			$("#is_active").prop('checked',false);
                			
                			$("#sno").val("");
                			$("#span_ques_name").val("");
                			$("#span_ques_desc").val("");
                			$("#span_order_no").val("");
                			$("#span_param_flag").val("");
                			$("#span_is_sub_ques_desc").val("");
                			$("#span_opt1").val("");           			
            	}
            } 
        },
	         error:function(){
	        	 alert("err");
	         }
    });
}

/*function editRecord(ques_name){
	parent.location.href="quistionnaire_parameter_master_e.jsp?fstatus=E&ques_name="+ques_name;
}*/

//delete grid row
function deleteGridRecord(id){
var t =id;	
var row = $("#tot_row_count").val();

for(i=t;i<row;i++)
{
var k=parseInt(i)+1;
$("#ques_name_"+i).val($("#ques_name_"+k).val());
$("#ques_desc_"+i).val($("#ques_desc_"+k).val());
$("#order_no_"+i).val($("#order_no_"+k).val());
$("#is_sub_ques_"+i).val($("#is_sub_ques_"+k).val());
$("#is_active_"+i).val($("#is_active_"+k).val());
$("#ques_param_id_"+i).val($("#ques_param_id_"+k).val());
$("#ques_sub_param_id_"+i).val($("#ques_sub_param_id_"+k).val());
$("#param_flag_"+i).val($("#param_flag_"+k).val());
$("#opt1_"+i).val($("#opt1_"+k).val());

$("#span_ques_name"+i).text($("#span_ques_name"+k).text());
$("#span_ques_desc"+i).text($("#span_ques_desc"+k).text());
$("#span_order_no"+i).text($("#span_order_no"+k).text());
$("#span_param_flag"+i).text($("#span_param_flag"+k).text());
$("#span_opt1"+i).text($("#span_opt1"+k).text());     
$("#span_is_sub_ques_desc"+i).text($("#span_is_sub_ques_desc"+k).text());
$("#editRow_"+i).text($("#editRow_"+k).text());
$("#Deleterow_"+i).text($("#Deleterow_"+k).text());


}
$('#searchTable tr:last').remove();
$("#tot_row_count").val((parseInt(row)-1));
}


//edit grid row
function editGridRecord(id){debugger;
	$("#btnModifyRow").show();
	$("#btnAddRow").hide();
	$("#btnReset2").hide();
	$("#modRec").val(id);
	//	$(".remove_field").parent("div").removeAll();
	var cnt = $("#count").val();
	//var num=0;
	for(var jj=0;jj<cnt.length;jj++){
		/*++num;
		$("#opt"+num).val(""); */
		$(".remove_field").parent("div").remove();
	}
	
	
	var ques_desc	 = $("#ques_desc_"+id).val();
	var order_no	 = $("#order_no_"+id).val();
	var param_flag	 = $("#param_flag_"+id).val();
	var opt1 		 = $("#opt1_"+id).val();
	var opt_hide	 = $("#ques_sub_param_id_"+id).val();
	var is_sub_ques  = $("#is_sub_ques_"+id).val();
	var is_active    = $("#is_active_"+id).val();
	
	$("#ques_desc").val(ques_desc);
	$("#order_no").val(order_no);
	$("#param_flag").val(param_flag);

	if(is_active == "N"){
		$("#is_active").prop('checked',false);
	}
	else{
		$("#is_active").prop('checked',true);
	}
	if(is_sub_ques == "N"){	
		$("#is_sub_ques").prop('checked',false);
	}
	else{
		$("#is_sub_ques").prop('checked',true);
	}
	
	onChangeParam_flag();
	
	var opt  = opt1.split("~");
	var opt_hide = opt_hide.split("~"); 
	var num =1;
	var wrapper   	   = $(".input_fields_wrap"); 
	for(var i=0;i<opt.length;i++){
		if(i==0){
			$("#opt1").val(opt[i]);
			$("#opt_hide1").val(opt_hide[i]);
		}else{
			num++;
			$(wrapper).append('  <div class="" <div class="form-group"><div class="col-sm-12" id="divOpt'+num+'"> <div class="row"><label class="manageTxt1 col-sm-2 col-form-label required-field" style="">Option Value </label> '
					+'<div class="col-sm-4"><input type="text" class="manageTxt form-control" id="opt'+ num +'" name="opt'+ num +'" style="width: 100%" value="'+opt[i]+'" placeholder="Enter Option Value"/><input type="hidden" name="opt_hide'+num+'" id="opt_hide'+num+'" value="'+opt_hide[i]+'" maxlength="150"/></div> <a href="#" class="remove_field"><i class="fa fa-times-circle fa-2x"style="padding-left: 30px; padding-top: 5px; color:red"></i></a></div></div></div></div>');
			$("#count").val(num);
		}
	}
	num=0;
}

// modify grid
function modifyGrid() {
	try {
		var i = $("#modRec").val();
		$("#flag").val("Y");
		if(parseInt(i)>0) {
			if(addDetailsRows(i)){			
			$("#btnModifyRow").hide();
			$("#btnAddRow").show();
			$("#btnReset2").show();
			$("#modifyStatus").html("<br>Row No. "+i+" modified");
			clearSuccessMessageAfterThreeSecond("modifyStatus");
			}				
		} else {
			return false;
		}	
	} catch (err) {
		alert(err);
	}	
}

function addDetailsRows(i){debugger;
	
	var ques_desc	 = $("#ques_desc").val();
	var order_no	 = $("#order_no").val();
	var param_flag	 = $("#param_flag").val();
	var opt1 		 = $("#opt1").val();
	var ques_name	 = $("#ques_name option:selected").text();
	var param_flag_val =$("#param_flag option:selected").text();
	var opt="";
	var is_sub_ques="N";
	var is_active="N";
	var opt_hide="";
	
	var ques_name11 = $("#ques_name").val();
	var q_n = ques_name11.split("~");
	
	if ($('#is_sub_ques').prop('checked') == true) {
		is_sub_ques = "Y";
	}
	if ($('#is_active').prop('checked') == true) {
		is_active = "Y";
	}
	
	
	var cnt = $("#count").val();
	var span_opt="";
	if(q_n[1] == "CQ"){
	for(ii=1;ii<=cnt;ii++){
		if($("#opt"+ii).val()!=undefined){
		opt += $("#opt"+ii).val()+"~";
		span_opt += $("#opt"+ii).val()+",";
		}
	}
	opt = opt.substring(0, opt.length - 1);
	span_opt = span_opt.substring(0, span_opt.length - 1);
	}
	else{
		opt="";
	}
	if(q_n[1] == "CQ"){
		for(z=1;z<=cnt;z++){
			if($("#opt_hide"+z).val()==undefined || $("#opt_hide"+z).val() == ""){
				opt_hide +="@@~";
			}else{
				opt_hide += $("#opt_hide"+z).val()+"~";
			}
		}
		opt_hide = opt_hide.substring(0, opt_hide.length - 1);
	}
	else{
		opt_hide="";
	}
	
	if(ques_desc==""||ques_desc==null)
	  {
			showerr($("#ques_desc")[0],"Feedback Question Description is required","block");
			$("#ques_desc").focus();
			return false;
	  }
	
	if(order_no==""||order_no==null)
	  {
			showerr($("#order_no")[0],"Order number is required","block");
			$("#order_no").focus();
			return false;
	  }
	if(!chkIsInteger(order_no)){
		showerr($("#order_no")[0],"Order number should be numeric. ","block");
		$("#order_no").focus();
		return false;
	}
	if(parseInt(order_no)<0 || parseInt(order_no) ==0){
		showerr($("#order_no")[0],"Order number should be greater than 0.","block");
		$("#order_no").focus();
		return false;
	}
	if(q_n[1] == "CQ" && param_flag !="M" && param_flag !="A" && param_flag !="N"){
		for(j=1;j<=cnt;j++){
			if( $("#opt"+j).val() !=undefined &&  $("#opt"+j).val() ==""){
				showerr($("#opt"+j)[0],"Option Value is required.","block");
				$("#opt"+j).focus();
				return false;
			}
		}
	}
			
	if(q_n[1] =="CQ" && param_flag==""||param_flag==null )
	  {
		showerr($("#param_flag")[0],"Parameter Flag is required","block");
		$("#param_flag").focus();
		return false;
	  }
	
	else{
		$("#ques_desc_"+i).val(ques_desc);
		$("#order_no_"+i).val(order_no);
		$("#param_flag_"+i).val(param_flag);
		$("#opt1_"+i).val(opt);
		$("#is_active_"+i).val(is_active);
		$("#is_sub_ques_"+i).val(is_sub_ques);
		$("#ques_sub_param_id_"+i).val(opt_hide);

	$("#sno"+i).text(i);
	$("#span_ques_name"+i).text(ques_name);
	$("#span_ques_desc"+i).text(ques_desc);
	$("#span_order_no"+i).text(order_no);
	$("#span_param_flag"+i).text(param_flag_val);
	$("#span_opt1"+i).text(span_opt);
	
	var is_sub_ques_desc = ""
		if(is_sub_ques=="N"){
			is_sub_ques_desc="No";
		}else{
			is_sub_ques_desc="Yes";
		}
	$("#span_is_sub_ques_desc"+i).text(is_sub_ques_desc);
	
	
	$("#ques_desc").val("");
	$("#is_sub_ques").prop('checked',false);
	$("#order_no").val("");
	if(q_n[1] == "CQ"){
		$("#param_flag").val("");
	}
	$("#opt1").val("");
	$("#is_active").prop('checked',false);
	
	if(q_n[1] == "CQ"){
		var num=0;
		var op = opt.split("~");
		for(var jj=0;jj<op.length;jj++){
			++num;
			$("#opt"+num).val(""); 
			$(".remove_field").parent("div").remove();
		}
		$("#count").val("1");
		
		}
	return true;
	}
}

function delRecord(ques_name){
	var del=confirm("Are You Sure?")
    if(del==true){
	$.ajax({
		type : "POST",
		url:  'QuestionnaireParamMastService',
        data: 'fstatus=D&ques_name='+ques_name,	
		success : function(data) {			
			var errMsg=data.errMsg; 
			if(errMsg == " Questionnaire Parameter Master Deleted successfully"){ 
				setTimeout(function () {
				      displaySuccessMessages("errMsg1", errMsg, "");
				     }, 1000);
					clearSuccessMessageAfterFiveSecond("errMsg1");
					setTimeout(function () {
					      location.reload();
					     }, 3000); 
			}else{  
				setTimeout(function () {
				      displaySuccessMessages("errMsg", errMsg, "");
				     }, 1000);
					clearSuccessMessageAfterFiveSecond("errMsg");
					setTimeout(function () {
					      location.reload();
					     }, 3000);	
			}
			
			/*setTimeout(function () {
			      displaySuccessMessages("errMsg", data.errMsg, "");
			     }, 1000);
				clearSuccessMessageAfterFiveSecond("errMsg");
				setTimeout(function () {
					location.reload(); 
			     }, 3000);*/
		}
	});
    }
}

function setDropDown(ques_name) {
	try {
		$.ajax({
			type : "POST",
			url:  'QuestionnaireParamMastService',
	        data: 'fstatus=DD&id='+ques_name,	
			success : function(data) {			
				$("#ques_type_id").val(data.tid);
				$("#ques_type").val(data.tname);
				$("#ques_sub_type_id").val(data.sid);
				$("#ques_sub_type").val(data.sname);
			}
		});
	} catch (e) {
		// TODO: handle exception
		alert("ERROR IN setDropDown :"+e)
	}
}