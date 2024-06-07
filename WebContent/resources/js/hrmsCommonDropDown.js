
// added by ashwani kumar use for get  Academic Session Date 28-Dec-2023
function getAcademicSessionold(curr_academic_year, obj) {
	var hrmsApi = $("#hrmsApi").val().trim(); // http://localhost:8099/hrms-api/
	var sel = "";
	$.ajax({
		type : "GET",
		url : hrmsApi + "rest/apiServices/getAcademicSession",
		jsonp : "parseResponse",
		dataType : "jsonp",
		success : function(response) {
			var moduleHtml = "<option value=''>Select Session</option>";
			if (typeof response.academicDropDown != 'undefined'
					&& response.academicDropDown.length > 0) {
				$.each(response.academicDropDown, function(key, val) {
					var widgetKey = val.id;
					var widgetValue = val.session;
					if (curr_academic_year == widgetKey) {
						moduleHtml += "<option selected value='"
								+ val.id + "'>" + val.session
								+ "</option>";
					} else {
						moduleHtml += "<option  value='" + val.id
								+ "'>" + val.session + "</option>";
					}
				});
			}
			if(obj!=undefined && obj!=''){
			    $("#"+obj).html(moduleHtml);
		    }else{
			$("#session_id").html(moduleHtml);
	            }
		}
	});
}


// Replicate from poral by amit dangi 22 May
function getAcademicSession(curr_academic_year, obj) {
	var hrmsApi = $("#hrmsApi").val();
	var sel = "";
	$.ajax({
		type : "GET",
		url : hrmsApi + "rest/apiServices/getAcademicSessionDetails",
		jsonp : "parseResponse",
		dataType : "jsonp",
		success : function(response) {
			var moduleHtml = "<option value=''>Select Session</option>";
			if (typeof response.academicDropDown != 'undefined' && response.academicDropDown.length > 0) {
				$.each(response.academicDropDown, function(key, val) {
					var temp=val.id;
					console.log("temp||"+temp);
					const myArray = temp.split("~");
					var	widgetKey = myArray[0];
		    		var  value = myArray[1];
					var widgetValue = val.name;
					var widgetValue = val.session;
					var ssn = parseInt(widgetValue) + 1; 
					if (curr_academic_year == widgetKey) {
						moduleHtml += "<option selected value='" + widgetKey+ "'>" + val.session +"~"+ssn+ "</option>";
					} else {
						if(value=='Y'){
							moduleHtml += "<option selected value='" + widgetKey+ "'>" + val.session +"~"+ssn+ "</option>";
						}else{
							moduleHtml += "<option  value='" + widgetKey + "'>"+ val.session +"~"+ssn+ "</option>";
						}
					}
				});
			}
			if (obj != undefined && obj != '') {
				$("#" + obj).html(moduleHtml);
			} else {
				$("#session_id").html(moduleHtml);
			}
		}
	});
}


//added by ashwani kumar use for get  Deprtment Date 08-JAN-2024

function getDeprtment(deptId,obj){
	var hrmsApi =$("#hrmsApi").val().trim();  
	
	 try{
		 $.ajax({
			 type: "GET",
				url: hrmsApi+"rest/apiServices/getDepartmentDetail",
				jsonp: "parseResponse",
				dataType: "jsonp",
			 success: function (response){
				 var department_details=response.department;
				 var dropdown="<option value=''>Select Department</option>";
				 $.each(department_details, function(index, department_details) {
					 var widgetKey = department_details.departmentId;
						var widgetValue = department_details.departmentName;
					 if(deptId==widgetKey){
						 dropdown += "<option selected value='" +widgetKey+ "'>" +widgetValue+ "</option>";
					 }else{
						 dropdown+="<option  value='" + widgetKey + "'>" + widgetValue + "</option>";
					 }
				 });
				  if(obj!=undefined && obj!=''){
					    $("#"+obj).html(dropdown);
				    }else{
					$("#dept").html(dropdown);
			            }
			 },
			 error: function(xhr, status, error, response) {
				 alert("xhr : "+xhr.responseText);
				 alert("status :"+status.responseText);
				 alert("error :"+error.responseText);
				 alert("response :"+ response);
				 alert("Stringified response: "+JSON.stringify(response));
			 }
		 });
	 }catch(err){
		 alert("Error caused in getDeprtment() :"+err);
	 }
}


//added by ashwani kumar use for get  Designation Date 08-JAN-2024

function getDesignation(desig_id,obj){
	var hrmsApi =$("#hrmsApi").val().trim();  
	$.ajax({
		type: "GET",
		url: hrmsApi+"rest/apiServices/getDesignationDetail",
		jsonp: "parseResponse",
		dataType: "jsonp",
		async: false,
		success: function (response)
		{
			if (typeof response.designationDetail != 'undefined' && response.designationDetail.length > 0){
				var moduleHtml = "<option value=''>Select Post Type</option>";
				$.each(response.designationDetail, function (key, val) {
				var widgetKey = val.designationID;
				var widgetValue = val.designationName;
				if(widgetKey==desig_id){
					moduleHtml += "<option value='" + widgetKey + "' selected>" + widgetValue + "</option>";
				}else{
					moduleHtml += "<option value='" + widgetKey + "'>" + widgetValue + "</option>";
				}
				});
			} if(obj!=undefined && obj!=''){
			    $("#"+obj).html(moduleHtml);
		    }else{
			$("#designation").html(moduleHtml);
	            }
		}
	});
}

//added by ashwani kumar use for get Employee Date 09-JAN-2024

function getEmployee(empid,obj){
	var hrmsApi = $("#hrmsApi").val().trim();
	//http://localhost:8082/aktu-api/rest/apiServices/getEmployee
	var sel="";
	try {
		$.ajax({
			type: "GET",
			url: hrmsApi+"rest/apiServices/getEmployee",
			jsonp: "parseResponse",
			dataType: "jsonp",
			success: function (response){
				var employee=response.employee;
				var dropdown="<option value=''>Select Employee</option>";
				$.each(employee, function(index, employee) {
					if(employee.employeeId==empid){
						sel="selected";
					} else {
						sel="";
					}
					dropdown+="<option  value='" + employee.employeeId + "'"+sel+">" + employee.employeeName+"</option>";
				});
			if(obj!=undefined && obj!=''){
		    $("#"+obj).html(dropdown);
	        }else{
		    $("#empname").html(dropdown);
            }}
		});	
	} catch (e) {
		alert(e);
	}
}

//added by Om kumar used to get State -- Date 30-JAN-2024

function getState(stateCode, obj) {
	var hrmsApi = $("#hrmsApi").val();
	var sel = "";
	$.ajax({
		type : "GET",
		url : hrmsApi + "rest/apiServices/getState",
		jsonp : "parseResponse",
		dataType : "jsonp",
		async : false,
		success : function(response) {
			var moduleHtml = "<option value=''>Select State</option>";
			if (typeof response.stateDetail != 'undefined'
					&& response.stateDetail.length > 0) {
				$.each(response.stateDetail, function(key, val) {
					var widgetKey = val.stateId;
					var widgetValue = val.stateName;
					sel = "";
					if (stateCode == widgetKey)
						sel = "selected";
					moduleHtml += "<option value='" + widgetKey + "'" + sel
							+ ">" + widgetValue + "</option>";
				});
			}
			if (obj != undefined && obj != '') {
				$("#" + obj).html(moduleHtml);
			} else {
				$("#state").html(moduleHtml);
			}
		}
	});
}
//added by Om kumar used to get District -- Date 30-JAN-2024

function getDist(stateCode, dist, obj) {
	var hrmsApi = $("#hrmsApi").val();
	var sel = "";
	$.ajax({
		type : "GET",
		url : hrmsApi + "rest/apiServices/getDist?state=" + stateCode,
		jsonp : "parseResponse",
		dataType : "jsonp",
		async : false,
		success : function(response) {
			var moduleHtml = "<option value=''>Select State</option>";
			if (typeof response.stateDetail != 'undefined'
					&& response.stateDetail.length > 0) {
				$.each(response.stateDetail, function(key, val) {
					var widgetKey = val.cityId;
					var widgetValue = val.cityName;
					sel = "";
					if (dist == widgetKey)
						sel = "selected";
					moduleHtml += "<option value='" + widgetKey + "'" + sel
							+ ">" + widgetValue + "</option>";
				});
			}
			if (obj != undefined && obj != '') {
				$("#" + obj).html(moduleHtml);
			} else {
				$("#district").html(moduleHtml);
			}
		}
	});
}

//added by Amit Dangi to get External Employee from user mast Date 06-May-2024

function getExternalEmployee(empid,obj){
	var hrmsApi = $("#hrmsApi").val().trim();
	var sel="";
	try {
		$.ajax({
			type: "GET",
			url: hrmsApi+"rest/apiServices/getExternalEmployee",
			jsonp: "parseResponse",
			dataType: "jsonp",
			success: function (response){
				var employee=response.employee;
				var dropdown="<option value=''>Select Employee</option>";
				$.each(employee, function(index, employee) {
					if(employee.employeeId==empid){
						sel="selected";
					} else {
						sel="";
					}
					dropdown+="<option  value='" + employee.employeeId + "'"+sel+">" + employee.employeeName+"</option>";
				});
			if(obj!=undefined && obj!=''){
		    $("#"+obj).html(dropdown);
	        }else{
		    $("#empname").html(dropdown);
          }}
		});	
	} catch (e) {
		alert(e);
	}
}

