$(document).ready(function(){
	/*$("#btnNew").click(function(){
		 document.location.href="quistionnaire_sub_parameter_master_e.jsp?fstatus=N";
	});
	$("#btnBack").click(function(){
		 document.location.href="quistionnaire_sub_parameter_master_d.jsp";
	});
	$("#btnBack1").click(function(){
		 document.location.href="quistionnaire_sub_parameter_master_d.jsp";
	});
	$("#btnReset").click(function(){
		 document.location.reload();
	});
	$("#btnReset1").click(function(){
		 document.location.reload();
	});*/
	$("#btnBack").click(function(){	
		 $("#ques_name").val("");
		var flag = $("#flag").val();
		var ques_name = $("#ques_name").val();
		
		//var ques_type = $("#ques_type").val();
		//alert(ques_name+"|"+flag);
		if(flag=="Y"){
		var xyz=confirm("Do You Want To GoBack Without Save Data?");
		//alert("xyz::"+xyz);
		if(xyz){
			//alert("xyz::"+xyz);
			window.location.href="quistionnaire_sub_parameter_master_e.jsp?fstatus=N&ques_name="+ques_name;
		}else{
			$(".com-course-sem-map").show();
		}
		}else{
			window.location.href="quistionnaire_sub_parameter_master_e.jsp?fstatus=N&ques_name="+ques_name;			
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
					 +' <label class="manageTxt1 col-sm-2 col-form-label required-field" style="" >Sub Option Value</label>'
					  +' <div class="col-sm-4"><input type="text" class="manageTxt form-control" id="sub_opt'+x+'" name="sub_opt'+x+'" style="width: 100%" placeholder="Enter Sub Option Value" maxlength="225"/></div> <a href="#" class="remove_field"><i class="fa fa-times-circle fa-2x"style="padding-left: 30px; padding-top: 5px; color:red"></i></a></div></div></div></div></div>');
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
$(document).off('change', '#param_flag').on('change', '#param_flag',function() {
	onChangeParam_flag();
});
function download() {
	document.location.href="questionnaire_sub_parameter_report.jsp";
}
function onChangeParam_flag(){
	var param_flag = $("#param_flag").val();
	 if(param_flag =="M" || param_flag =="A" || param_flag =="N"){
		
		var num=0;
		 var cnt = $("#count").val();
		 for(var jj=0;jj<cnt;jj++){
				++num;
				if(num==1){
					$( ".add_field_button" ).prop( "disabled", true );
					$("#sub_opt1").val("");
					 $("#sub_opt1").prop("disabled", true);
				}else{
					$("#sub_opt"+num).val(""); 
					$(".remove_field").parent("div").remove();
				}
			}
			$("#count").val("1");
	}
	 else{
		 $( ".add_field_button" ).prop( "disabled", false );
		 $("#sub_opt1").prop("disabled", false);
	 }
	 
}
/*function onChangeParam_flag(){
	var param_flag = $("#param_flag").val();
	 
	 if(param_flag =="M" ){
		 $( ".add_field_button" ).prop( "disabled", true );
		 $("#sub_opt1").prop("disabled", true);
		 
	 }else{
		 $( ".add_field_button" ).prop( "disabled", false );
		 $("#sub_opt1").prop("disabled", false);
	 }
}*/

$(document).off('change', '#ques_name').on('change', '#ques_name',function() {
	var ques_name = $("#ques_name").val();
	getQuesDesc(ques_name);
});

$(document).off('change', '#ques_desc').on('change', '#ques_desc',function() {
	getSubQuesDescOpt();
});

function getQuesDesc(ques_name){
	 $.ajax({
	        type: 'POST',
	        url:  'QuestionnaireSubParamMastService',
	        data: 'ques_name=' + ques_name+'&fstatus=QD',
	        async: false,
	        success: function (response)
	        {
	            if (typeof response.quesDesc != 'undefined' && response.quesDesc.length > 0)
	            {
	                var moduleHtml = "<option value=''>Select feedback Question Description</option>";
	                $.each(response.quesDesc, function (key, val) {
	                    var widgetKey = val.id;
	                    var widgetValue = val.desc;                    
	                    	moduleHtml += "<option value='" + widgetKey + "'>" + widgetValue + "</option>";
	                	});
	              
	                $("#ques_desc").html(moduleHtml);
	            } else
	            {
	            	 $("#ques_desc").html("<option value=''>Select feedback Question Description</option>");
	            }
	        },
		         error:function(){
		        	 alert("err");
		         }
	    });
}

function getSubQuesDescOpt(){
	var ques_desc = $("#ques_desc").val();
	 $.ajax({
	        type: 'POST',
	        url:  'QuestionnaireSubParamMastService',
	        data: 'fstatus=QDO&ques_desc='+ques_desc,
	        async: false,
	        success: function (response)
	        {
	            if (typeof response.quesDescOpt != 'undefined' && response.quesDescOpt.length > 0)
	            {
	                var moduleHtml = "<option value=''>Select Option Value</option>";
	                $.each(response.quesDescOpt, function (key, val) {
	                    var widgetKey = val.id;
	                    var widgetValue = val.desc;                    
		                    moduleHtml += "<option value='" + widgetKey + "'>" + widgetValue + "</option>";
	                	});
	              
	                $("#opt_val").html(moduleHtml);
	            } else
	            {
	            	 $("#opt_val").html("<option value=''>Select Option Value</option>");
	            }
	        },
		         error:function(){
		        	 alert("err");
		         }
	    });
}

function vldReset(){debugger;
	$("#ques_desc").val("");
	$("#opt_val").val("");
	$("#order_no").val("");
	$("#param_flag").val("");
	var num=0;
	var cnt = $("#count").val();
	for(var jj=0;jj<cnt.length;jj++){
		++num;
		$("#sub_opt"+num).val(""); 
		$(".remove_field").parent("div").remove();
	}
	$("#count").val("1");
	return true;
	
}
function editView(ques_name,status){
	$("#ifrm").hide();
	if(ques_name != ""){	
	$(".com-course-sem-map").show();
	$("#ques_name").attr("disabled",true);
	$("#btnView").hide();
	
	
	$("#btnReset1").hide();
	$("#btnBack1").hide();
	vldReset();
	getQuesDesc(ques_name);
	getList(ques_name,status);
	}
}

function view(ques_name,status){
	var ques_name = $("#ques_name").val();
	var status = $("#fstatus").val();
	
	/*if(ques_name==""||ques_name==null)
	  {
		showerr($("#ques_name")[0],"Questionnaire Name is required","block");
		$("#ques_name").focus();
		return false;
	  }*/
	
	$("#ques_name_hide").val(ques_name);
	$("#ques_name").attr("disabled",true);
	$("#btnView").hide();
	$("#btnReset1").hide();
	$("#btnBack1").hide();
	$(".com-course-sem-map").show();
	vldReset();
	editView(ques_name,status);
	getList(ques_name,status);
}

//add new row

function addRow(){debugger;
		var ques_name 	 = $("#ques_name").val();
		var ques_desc	 = $("#ques_desc").val();
		var opt_val		 = $("#opt_val").val();
		var order_no	 = $("#order_no").val();
		var param_flag	 = $("#param_flag").val();
		$("#flag").val("Y");
		var sub_opt="", span_sub_opt="";
		
		var cnt = $("#count").val();
		for(i=1;i<=cnt;i++){
			if($("#sub_opt"+i).val()!=undefined){
				sub_opt += $("#sub_opt"+i).val()+"~";
				span_sub_opt += $("#sub_opt"+i).val()+",";
			}
		}
		sub_opt = sub_opt.substring(0, sub_opt.length - 1);
		span_sub_opt = span_sub_opt.substring(0, span_sub_opt.length - 1);
		
		if(ques_desc==""||ques_desc==null)
		  {
				showerr($("#ques_desc")[0],"Feedback Question Description is required","block");
				$("#ques_desc").focus();
				return false;
	      }
		if(opt_val==""||opt_val==null)
		  {
				showerr($("#opt_val")[0],"Option Value is required","block");
				$("#opt_val").focus();
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
			for(i=1;i<=cnt;i++){
				if( $("#sub_opt"+i).val() !=undefined &&  $("#sub_opt"+i).val() ==""){
					showerr($("#sub_opt"+i)[0],"Sub Option Value is required","block");
					$("#sub_opt"+i).focus();
					return false;
				
				}
			}
			var sub_opt1="",sub_opt2="";
			for(var i=1;i<=cnt;i++){
				sub_opt1 = $("#sub_opt"+i).val();
				for(var j=i+1;j<=cnt;j++){
					sub_opt2 = $("#sub_opt"+j).val();
					if(sub_opt1 == sub_opt2){
						showerr($("#sub_opt"+j)[0],"Duplicate Sub Option Value found ","block");
						$("#sub_opt"+j).focus();
						return false;
					}
				}
				
			}
		}
		
		var ques_name_val	 = $("#ques_name option:selected").text();
		var ques_desc_val	 = $("#ques_desc option:selected").text();
		var opt_val_val		 = $("#opt_val option:selected").text();
		var param_flag_val 	 = $("#param_flag option:selected").text();
		
 var newRow = $("<tr>");	 
 var cols = "";
 var rr= $('#tot_row_count').val();
 var r = parseInt(rr)+1;
	cols += '<tr id="tr_'+r+ '">';
	cols += '<td style="width:3%"><span id="sno'+r+'"> '+r+'</span></td>';
	
	cols += '<td style="width:15%; text-align:center;"><span  id="span_ques_name'+r+'">'+ ques_name_val 	      	
		 +'</span><input type="hidden" class="form-control" name="ques_name_'+r+'" id="ques_name_'+r+'" value="'+ ques_name +'" readOnly/> </td>';	
		      	
	cols += '<td style="width:20% ;text-align:center;"><span id="span_ques_desc'+r+'">'+ques_desc_val	      	
		+'</span><input type="hidden" class="form-control" name="ques_desc_'+r+'" id="ques_desc_'+r+'" value="'+ ques_desc +'" readOnly/> </td>';	
	
	cols += '<td style="width:10% ;text-align:center;"><span id="span_opt_val'+r+'">'+opt_val_val	      	
	+'</span><input type="hidden" class="form-control" name="opt_val_'+r+'" id="opt_val_'+r+'" value="'+ opt_val +'" readOnly/> </td>';
	
	cols += '<td style="width:10%; text-align:right;"><span id="span_order_no'+r+'">'+order_no	      	
		+'</span><input type="hidden" class="form-control" name="order_no_'+r+'" id="order_no_'+r+'" value="'+ order_no +'" readOnly/>'
		+'<input type="hidden" class="form-control" name="ques_param_id_'+r+'" id="ques_param_id_'+r+'" value="" readOnly/>'	
		+'<input type="hidden" class="form-control" name="ques_sub_param_id_'+r+'" id="ques_sub_param_id_'+r+'" value="" readOnly/>'
		+'<input type="hidden" class="form-control" name="sub_param_det_id_'+r+'" id="sub_param_det_id_'+r+'" value="" readOnly/>'
		+'<input type="hidden" class="form-control" name="flg_'+r+'" id="flg_'+r+'" value="N" readOnly/></td>'
	
		
	cols += '<td style="width:10%; text-align:center;"><span id="span_param_flag'+r+'">'+param_flag_val	      	
		+'</span><input type="hidden" class="form-control" name="param_flag_'+r+'" id="param_flag_'+r+'" value="'+ param_flag +'" readOnly/> </td>';	

	cols += '<td style="width:10%; text-align:center;"><span id="span_sub_opt'+r+'">'+span_sub_opt	      	
		+'</span><input type="hidden" class="form-control" name="sub_opt_'+r+'" id="sub_opt_'+r+'" value="'+ sub_opt +'" readOnly/> </td>';	
	
	cols += ' <td class="text-center colr-blue-p" style="width:7%;"><a href="JavaScript:Void(0)"  id="editRow_'+r+'" style="font-size: 13px !important;cursor: pointer;width:10%" onclick="editGridRecord('+r+');" target="" ><i class="fa fa-edit p-l-3"></i>Edit</a></td>';
	
	cols += ' <td class="text-center colr-red-p" style="width:7%;"><a href="JavaScript:Void(0)"  id="Deleterow_'+r+'" style=" color:#d73925;font-size: 13px !important;cursor: pointer;width:10%" onclick="deleteGridRecord('+r+');" target="" ><i class="fa fa-trash p-l-3"></i>Delete</a></td>';
	
	cols += '</tr>';
	
	$('#searchTable').append(cols);
		cols="";                     
		$('#tot_row_count').val(r);	         	        
		$("#ques_desc").val("");
		$("#opt_val").val("");
		$("#order_no").val("");
		$("#param_flag").val("");
		
		$("#sno").val("");
		$("#span_ques_name").val("");
		$("#span_ques_desc").val("");
		$("#span_opt_val").val("");
		$("#span_order_no").val("");
		$("#span_param_flag").val("");
		$("#span_sub_opt").val("");
	
		var num=0;
		for(var jj=0;jj<cnt.length;jj++){
			++num;
			$("#sub_opt"+num).val(""); 
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
			opt_val			: $("#opt_val_"+i).val(),
			order_no		: $("#order_no_"+i).val(),
			param_flag	 	: $("#param_flag_"+i).val(),
			sub_opt			: $("#sub_opt_"+i).val(),	
			sub_param_det_id: $("#sub_param_det_id_"+i).val(),
			flg				: $("#flg_"+i).val(),
		});	
		}
	
	jsonArrayObj2=JSON.stringify(jsonArrayObj);
	
	 $.ajax({
			type : "POST",
			url: 'QuestionnaireSubParamMastService', 
		    data: {
		    	fstatus		: "S",		    	
		    	jsonObj 	: jsonArrayObj2,
		    	status		: status,
		    	ques_name	: $("#ques_name_hide").val(),
		    	  },
			success : function(data) { 
       	 //alert("data.status::"+data.status);
				if(data.status=="N" && data.flg=="Y"){ 
       		  setTimeout(function () {	
       		 // if(data.flg=="Y"){ 
    				displaySuccessMessages("errMsg1", data.errMsg,"");  
    			}, 2000);
       	  clearSuccessMessageAfterTenSecond("errMsg1");
			setTimeout(function () {
			      location.reload();
			     }, 3000); 
       		 // }
			 	 
				}
				else if(data.status == "E" && data.flg=="Y"){
							setTimeout(function () {		
								displaySuccessMessages("errMsg1", data.errMsg,"");  
							}, 2000);
			       	  clearSuccessMessageAfterTenSecond("errMsg1"); 
					
			       	setTimeout(function () {
			       	 document.location.href="quistionnaire_sub_parameter_master_d.jsp";
					     }, 3000); 
			       	  
			       		 	  
				}else{
					setTimeout(function () {		
						displaySuccessMessages("errMsg", data.errMsg,"");  
					}, 3000);
	       	  clearSuccessMessageAfterTenSecond("errMsg");
				}
		    },
		    
		    error: function(xhr, status, error) {
		    	alert("error");
		    	}
		    });
	
}

function vldSearch(){
	var ques_name = $("#ques_name").val();
	var ques_desc = $("#ques_desc").val();
	
	/*if(ques_name==""||ques_name==null)
	  {
		showerr($("#ques_name")[0],"Questionnaire Name is required","block");
		$("#ques_name").focus();
		return false;
	  }*/
	/*else if(ques_desc==""||ques_desc==null)
	  {
		showerr($("#ques_desc")[0],"Feedback Question Description is required","block");
		$("#ques_desc").focus();
		return false;
	  }*/
	/*else{*/
	frmQuesSubParamMastD.target="btmQuesSubParamMast";
	frmQuesSubParamMastD.action="quistionnaire_sub_parameter_master_l.jsp";
	frmQuesSubParamMastD.submit();
	/*}*/
 }

function editRecord(ques_name,ques_desc){
	parent.location.href="quistionnaire_sub_parameter_master_e.jsp?fstatus=E&ques_name="+ques_name+"&ques_desc="+ques_desc;
}

//onclick on view to fetch list.
function getList(ques_name,status){
	
	var data=""
	var r=0;
	data= 'fstatus=L&ques_name='+ques_name;
    $.ajax({
        type: 'POST',
        url:  'QuestionnaireSubParamMastService',
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
                	 var cnt = response.allList[i].cnt
                	 	cols += '<tr id="tr_'+r+ '">';
                		cols += '<td style="width:3%"><span id="sno'+r+'"> '+r+'</span></td>';
                		
                		cols += '<td style="width:15%; text-align:center;"><span  id="span_ques_name'+r+'">'+ response.allList[i].ques_name  	      	
                			 +'</span><input type="hidden" class="form-control" name="ques_name_'+r+'" id="ques_name_'+r+'" value="'+ response.allList[i].ques_id +'" readOnly/> </td>';	
                			      	
                		cols += '<td style="width:20%; text-align:center;"><span id="span_ques_desc'+r+'">'+response.allList[i].Feedback_desc      	
                			+'</span><input type="hidden" class="form-control" name="ques_desc_'+r+'" id="ques_desc_'+r+'" value="'+ response.allList[i].QUES_PARAM_ID +'" readOnly/> </td>';	
                		
                		cols += '<td style="width:10% ;text-align:center"><span id="span_opt_val'+r+'">'+response.allList[i].opt_val 	      	
                		+'</span><input type="hidden" class="form-control" name="opt_val_'+r+'" id="opt_val_'+r+'" value="'+ response.allList[i].opt_valId  +'" readOnly/> </td>';
                		
                		cols += '<td style="width:10%; text-align:center;"><span id="span_order_no'+r+'">'+response.allList[i].order_no
                			+'</span><input type="hidden" class="form-control" name="order_no_'+r+'" id="order_no_'+r+'" value="'+ response.allList[i].order_no +'" readOnly/>'
                			+'<input type="hidden" class="form-control" name="sub_param_det_id_'+r+'" id="sub_param_det_id_'+r+'" value="'+ response.allList[i].ques_sub_param_det_id +'" readOnly/>';
                		if(parseInt(cnt)>0){
                			cols +='<input type="hidden" class="form-control" name="flg_'+r+'" id="flg_'+r+'" value="Y" readOnly/>';
                		}else{
                			cols +='<input type="hidden" class="form-control" name="flg_'+r+'" id="flg_'+r+'" value="N" readOnly/>';
                		}
                		cols +='</td>';
                		
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
                		cols += '<td style="width:10%; text-align:center;"><span id="span_param_flag'+r+'">'+param_flag	      	
                			+'</span><input type="hidden" class="form-control" name="param_flag_'+r+'" id="param_flag_'+r+'" value="'+ response.allList[i].param_flag +'" readOnly/> </td>';	

                		var span_sub_opt_arr = response.allList[i].sub_opt.split("~");
                		var span_sub_opt = "";
                		for(var l=0;l<span_sub_opt_arr.length;l++){
                			span_sub_opt += span_sub_opt_arr[l]+",";
                		}
                		span_sub_opt = span_sub_opt.substring(0, span_sub_opt.length - 1);
                		cols += '<td style="width:10%; text-align:center;"><span id="span_sub_opt'+r+'">'+span_sub_opt		      	
                			+'</span><input type="hidden" class="form-control" name="sub_opt_'+r+'" id="sub_opt_'+r+'" value="'+ response.allList[i].sub_opt	 +'" readOnly/> </td>';	
                		
                		
                		if(parseInt(cnt)==0){
                			cols += ' <td class="text-center colr-blue-p" style="width:7%;"><a href="JavaScript:Void(0)"  id="editRow_'+r+'" style="font-size: 13px !important;cursor: pointer;width:10%" onclick="editGridRecord('+r+');" target="" ><i class="fa fa-edit p-l-3"></i>Edit</a></td>';

                    		cols += ' <td class="text-center colr-red-p" style="width:7%;"><a href="JavaScript:Void(0)"  id="Deleterow_'+r+'" style=" color:#d73925;font-size: 13px !important;cursor: pointer;width:10%" onclick="deleteGridRecord('+r+');" target="" ><i class="fa fa-trash p-l-3"></i>Delete</a></td>';

                		}else{
                			cols += '<td></<td>'
                			cols += '<td></<td>'
                		}
                		
                		                		
                		cols += '</tr>';
                		
                		$('#searchTable').append(cols);
                			/*cols="";                     
                			         	        
                			cols=""; */                    
                			$('#tot_row_count').val(r);	         	        
                			$("#ques_desc").val("");
                			$("#opt_val").val("");
                			$("#order_no").val("");
                			$("#param_flag").val("");
                			
                			$("#sno").val("");
                			$("#span_ques_name").val("");
                			$("#span_ques_desc").val("");
                			$("#span_opt_val").val("");
                			$("#span_order_no").val("");
                			$("#span_param_flag").val("");
                			$("#span_sub_opt").val("");     			
            	}
            } 
        },
	         error:function(){
	        	 alert("err");
	         }
    });
}

function getView(){
	var ques_name = $("#ques_name").val();
	var data=""
	var r=0;
	data= 'fstatus=onload';
	
    $.ajax({
        type: 'POST',
        url:  'QuestionnaireSubParamMastService',
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
                	 var cnt = response.allList[i].cnt
                	 	cols += '<tr id="tr_'+r+ '">';
                		cols += '<td style="width:3%"><span id="sno'+r+'"> '+r+'</span></td>';
                		
                		cols += '<td style="width:15%; text-align:center;"><span  id="span_ques_name'+r+'">'+ response.allList[i].ques_name  	      	
                			 +'</span><input type="hidden" class="form-control" name="ques_name_'+r+'" id="ques_name_'+r+'" value="'+ response.allList[i].ques_id +'" readOnly/> </td>';	
                			      	
                		cols += '<td style="width:20%; text-align:center;"><span id="span_ques_desc'+r+'">'+response.allList[i].Feedback_desc      	
                			+'</span><input type="hidden" class="form-control" name="ques_desc_'+r+'" id="ques_desc_'+r+'" value="'+ response.allList[i].QUES_PARAM_ID +'" readOnly/> </td>';	
                		
                		cols += '<td style="width:10% ;text-align:center"><span id="span_opt_val'+r+'">'+response.allList[i].opt_val 	      	
                		+'</span><input type="hidden" class="form-control" name="opt_val_'+r+'" id="opt_val_'+r+'" value="'+ response.allList[i].opt_valId  +'" readOnly/> </td>';
                		
                		cols += '<td style="width:10%; text-align:center;"><span id="span_order_no'+r+'">'+response.allList[i].order_no
                			+'</span><input type="hidden" class="form-control" name="order_no_'+r+'" id="order_no_'+r+'" value="'+ response.allList[i].order_no +'" readOnly/>'
                			+'<input type="hidden" class="form-control" name="sub_param_det_id_'+r+'" id="sub_param_det_id_'+r+'" value="'+ response.allList[i].ques_sub_param_det_id +'" readOnly/>';
                		if(parseInt(cnt)>0){
                			cols +='<input type="hidden" class="form-control" name="flg_'+r+'" id="flg_'+r+'" value="Y" readOnly/>';
                		}else{
                			cols +='<input type="hidden" class="form-control" name="flg_'+r+'" id="flg_'+r+'" value="N" readOnly/>';
                		}
                		cols +='</td>';
                		
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
                		cols += '<td style="width:10%; text-align:center;"><span id="span_param_flag'+r+'">'+param_flag	      	
                			+'</span><input type="hidden" class="form-control" name="param_flag_'+r+'" id="param_flag_'+r+'" value="'+ response.allList[i].param_flag +'" readOnly/> </td>';	

                		var span_sub_opt_arr = response.allList[i].sub_opt.split("~");
                		var span_sub_opt = "";
                		for(var l=0;l<span_sub_opt_arr.length;l++){
                			span_sub_opt += span_sub_opt_arr[l]+",";
                		}
                		span_sub_opt = span_sub_opt.substring(0, span_sub_opt.length - 1);
                		cols += '<td style="width:10%; text-align:center;"><span id="span_sub_opt'+r+'">'+span_sub_opt		      	
                			+'</span><input type="hidden" class="form-control" name="sub_opt_'+r+'" id="sub_opt_'+r+'" value="'+ response.allList[i].sub_opt	 +'" readOnly/> </td>';	
                		
                		
                		if(parseInt(cnt)==0){
                			cols += ' <td class="text-center colr-blue-p" style="width:7%;"><a href="JavaScript:Void(0)"  id="editRow_'+r+'" style="font-size: 13px !important;cursor: pointer;width:10%" onclick="editGridRecord('+r+');" target="" ><i class="fa fa-edit p-l-3"></i>Edit</a></td>';

                    		cols += ' <td class="text-center colr-red-p" style="width:7%;"><a href="JavaScript:Void(0)"  id="Deleterow_'+r+'" style=" color:#d73925;font-size: 13px !important;cursor: pointer;width:10%" onclick="deleteGridRecord('+r+');" target="" ><i class="fa fa-trash p-l-3"></i>Delete</a></td>';

                		}else{
                			cols += '<td></<td>'
                			cols += '<td></<td>'
                		}
                		
                		                		
                		cols += '</tr>';
                		
                		$('#searchTable').append(cols);
                			/*cols="";                     
                			         	        
                			cols=""; */                    
                			$('#tot_row_count').val(r);	         	        
                			$("#ques_desc").val("");
                			$("#opt_val").val("");
                			$("#order_no").val("");
                			$("#param_flag").val("");
                			
                			$("#sno").val("");
                			$("#span_ques_name").val("");
                			$("#span_ques_desc").val("");
                			$("#span_opt_val").val("");
                			$("#span_order_no").val("");
                			$("#span_param_flag").val("");
                			$("#span_sub_opt").val("");     			
            	}
            	
            } 
        },
	         error:function(){
	        	 alert("err");
	         }
        
    });

}


//delete grid row
function deleteGridRecord(id){
var t =id;	
var row = $("#tot_row_count").val();

for(i=t;i<row;i++)
{
var k=parseInt(i)+1;
$("#ques_name_"+i).val($("#ques_name_"+k).val());
$("#ques_desc_"+i).val($("#ques_desc_"+k).val());
$("#opt_val_"+i).val($("#opt_val_"+k).val());
$("#order_no_"+i).val($("#order_no_"+k).val());
$("#param_flag_"+i).val($("#param_flag_"+k).val());
$("#sub_opt_"+i).val($("#sub_opt_"+k).val());
$("#flg_"+i).val($("#flg_"+k).val());


$("#span_ques_name"+i).text($("#span_ques_name"+k).text());
$("#span_ques_desc"+i).text($("#span_ques_desc"+k).text());
$("#span_opt_val"+i).text($("#span_opt_val"+k).text());
$("#span_order_no"+i).text($("#span_order_no"+k).text());
$("#span_param_flag"+i).text($("#span_param_flag"+k).text());
$("#span_sub_opt"+i).text($("#span_sub_opt"+k).text());
$("#editRow_"+i).text($("#editRow_"+k).text());
$("#Deleterow_"+i).text($("#Deleterow_"+k).text());

}
$('#searchTable tr:last').remove();
$("#tot_row_count").val((parseInt(row)-1));
}


//edit grid row
function editGridRecord(id){
	$("#btnModifyRow").show();
	$("#btnAddRow").hide();
	$("#btnReset2").hide();
	$("#modRec").val(id);
	var cnt = $("#count").val();
	for(var jj=0;jj<cnt.length;jj++){
		$(".remove_field").parent("div").remove();
	}
	
	var ques_desc	 = $("#ques_desc_"+id).val();
	var opt_val		 = $("#opt_val_"+id).val();
	var order_no	 = $("#order_no_"+id).val();
	var param_flag	 = $("#param_flag_"+id).val();
	var sub_opt		 = $("#sub_opt_"+id).val();
	var sub_opt_hide = $("#sub_param_det_id_"+id).val();
	
	$("#ques_desc").val(ques_desc);
	getSubQuesDescOpt();
	$("#opt_val").val(opt_val);
	$("#order_no").val(order_no);
	$("#param_flag").val(param_flag);
	$("#sub_param_det_id").val(sub_opt_hide);
	
	onChangeParam_flag();
	
	var opt  = sub_opt.split("~");
	var num =1;
	var wrapper   	   = $(".input_fields_wrap"); 
	var num=0;
	for(var i=0;i<opt.length;i++){
		num++;
		if(i==0){
			$("#sub_opt"+num).val(opt[i]);
		}else{
			$(wrapper).append('  <div class="" <div class="form-group"><div class="col-sm-12" id="divOpt'+num+'"> <div class="row"><label class="manageTxt1 col-sm-2 col-form-label required-field" style="">Sub Option  Value </label> '
					+'<div class="col-sm-4"><input type="text" class="manageTxt form-control" id="sub_opt'+ num +'" name="sub_opt'+ num +'" style="width: 100%" value="'+opt[i]+'" placeholder="Enter Option Value" maxlength="225"/></div> <a href="#" class="remove_field"><i class="fa fa-times-circle fa-2x"style="padding-left: 30px; padding-top: 5px; color:red"></i></a></div></div></div></div>');
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
	var opt_val		 = $("#opt_val").val();
	var order_no	 = $("#order_no").val();
	var param_flag	 = $("#param_flag").val();
	
	var ques_name_val	 	= $("#ques_name option:selected").text();
	var ques_desc_val	 	= $("#ques_desc option:selected").text();
	var param_flag_val 		= $("#param_flag option:selected").text();
	var opt_val_val			= $("#opt_val option:selected").text();
	
	var cnt = $("#count").val();
	var span_opt="", span_opt_val="", sub_opt_hide="";
	
	for(ii=1;ii<=cnt;ii++){
		if($("#sub_opt"+ii).val()!=undefined){
			span_opt += $("#sub_opt"+ii).val()+"~";
			span_opt_val += $("#sub_opt"+ii).val()+",";
			sub_opt_hide += $("#sub_param_det_id").val()+"~";
		}
	}
	span_opt = span_opt.substring(0, span_opt.length - 1);
	span_opt_val = span_opt_val.substring(0, span_opt_val.length - 1);
	sub_opt_hide = sub_opt_hide.substring(0, sub_opt_hide.length - 1);
	
	if(ques_desc==""||ques_desc==null)
	  {
			showerr($("#ques_desc")[0],"Feedback Question Description is required","block");
			$("#ques_desc").focus();
			return false;
	  }
	
	if(opt_val==""||opt_val==null)
	  {
			showerr($("#opt_val")[0],"Option Value is required","block");
			$("#opt_val").focus();
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
	if(param_flag !="M" && param_flag !="A" && param_flag !="N"){
		for(j=1;j<=cnt;j++){
			if( $("#sub_opt"+j).val() !=undefined &&  $("#sub_opt"+j).val() ==""){
				showerr($("#sub_opt"+j)[0],"Sub Option Value is required","block");
				$("#sub_opt"+j).focus();
				return false;
			}
		}
		
		var sub_opt1="",sub_opt2="";
		for(var k=1;k<=cnt;k++){
			sub_opt1 = $("#sub_opt"+k).val();
			for(var j=k+1;j<=cnt;j++){
				sub_opt2 = $("#sub_opt"+j).val();
				if(sub_opt1 == sub_opt2){
					showerr($("#sub_opt"+j)[0],"Duplicate Sub Option Value found ","block");
					$("#sub_opt"+j).focus();
					return false;
				}
			}
			
		}
	}
	
	if( param_flag==""||param_flag==null )
	  {
		showerr($("#param_flag")[0],"Parameter Flag is required","block");
		$("#param_flag").focus();
		return false;
	  }
	
	else{
		$("#ques_desc_"+i).val(ques_desc);
		$("#opt_val_"+i).val(opt_val);
		$("#order_no_"+i).val(order_no);
		$("#param_flag_"+i).val(param_flag);
		$("#sub_opt_"+i).val(span_opt);
		$("#sub_param_det_id_"+i).val(sub_opt_hide);

	$("#sno"+i).text(i);
	$("#span_ques_name"+i).text(ques_name_val);
	$("#span_ques_desc"+i).text(ques_desc_val);
	$("#span_opt_val"+i).text(opt_val_val);
	$("#span_order_no"+i).text(order_no);
	$("#span_param_flag"+i).text(param_flag_val);
	$("#span_sub_opt"+i).text(span_opt_val);
	
	
	$("#ques_desc").val("");
	$("#opt_val").val("");
	$("#order_no").val("");
	$("#param_flag").val("");
	var num=0;
	var op = span_opt.split("~");
	for(var jj=0;jj<op.length;jj++){
		++num;
		$("#sub_opt"+num).val(""); 
		$(".remove_field").parent("div").remove();
	}
	$("#count").val("1");
	return true;
	}
}

function delRecord(ques_desc){
	var del=confirm("Are You Sure?")
    if(del==true){
	$.ajax({
		type : "POST",
		url:  'QuestionnaireSubParamMastService',
        data: 'fstatus=D&ques_desc='+ques_desc,	
		success : function(data) {			
			var errMsg=data.errMsg; 
			if(errMsg=="Questionnaire Sub Parameter Master Deleted successfully"){ 
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



