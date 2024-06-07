/**
 * @author Ashwani kumar  (03-Jan-2024)
 * 
 */

$(document).ready(function(){
	$("#btnReset").click(function(){
		 document.location.reload();
	});
	
	$('#btnback').click(function(){   
		document.location.href="administrative_configuration_e.jsp";
	});
	  getAcademicSession();
});
// collapseOne
function addAdministrativeDetails() {
	var chk=0;
	var cnt = $("#totRow").val();
	var cnt1 = document.getElementById("AdministrativeDetails_table").rows.length;
	var sno = parseInt(cnt1)+1;
	   for (var k = 1; k <= cnt; k++) {
		   var amenities_details_ = $("#program_" + k).val();
	       var avlble_room        = $("#CP_PERI_TYP_" + k).val();
	       if(amenities_details_==""|| amenities_details_==null && amenities_details_ != undefined)
			  {
				chk=1;
				$("#program_"+k).val("");
				$("#program_"+k).focus();
				showerr($("#program_"+k)[0], "Administrative  type is Required", "block");
				return false;
			  }
			if(avlble_room==""|| avlble_room==null && avlble_room != undefined)
			{
				chk=1;
				$("#CP_PERI_TYP_"+k).val("");
				$("#CP_PERI_TYP_"+k).focus();
				showerr($("#CP_PERI_TYP_"+k)[0], "Available Carpet is Required To Enter", "block");
				return false;
			 }
			   if(cnt>1){  //add more same Administrative save again
					for(var j=k+1; j<=cnt; j++){
						if($("#program_"+k).val()==$("#program_"+j).val()){
							 $("#program_"+j).focus();
							 if($("#program_"+j).val()!=undefined && $("#program_"+j).val()!=""){
							showerr($("#program_"+j)[0],"Administrative Type is Already Selected!","block");
							return false;
						}
					 }
					}
				}
	   }
	var newRow = $("<tr>");	 
	var cols = "";
	var r = parseInt(cnt)+1;
	cols += '<tr id="tr_'+r+ '">';
	cols += '<td style="width:5%"><span id="sno_'+r+'"> '+ parseInt(sno) +'</span></td>';
	cols += '<td style="width:20%"><input type="text" id="program_'+r+'" name="program_'+r+'" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Administrative Type " value=""></td>';
	cols += '<td style="width:10%"><input type="text" id="CP_PERI_TYP_'+r+'" onkeypress="return allowOnlyNumeric(event)" name="CP_PERI_TYP_'+r+'" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms" value=""></td>';
	
	
	cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
	cols +='><span id="sno_'+r+'"" onclick="deleteDetailsdata(-1,'+ r+ ',this,'+ "'"+ ''+ "sno"+ ''+ "'"+ ','+ "'"+ ''+ "AD"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
		 
	$('#AdministrativeDetails_table').append(cols);
	cols="";    
	$("#totRow").val(r);
}
//accordian collapseTwo
function addInfrastructureDetails() { 
	var chk=0;
	var id =$("#admc_id").val();
	var cnt = $("#totRowDetails").val();
	var cnt1 = document.getElementById("Infrastructure_table").rows.length;
	var sno = parseInt(cnt1)+1;
	   for (var k = 1; k <= cnt; k++) {
		   var information = $("#infrastructure_" + k).val();
	       var information_room   = $("#Carpet_" + k).val();
	       if(information==""|| information==null && information != undefined)
			  {
				chk=1;
				$("#infrastructure_"+k).val("");
				$("#infrastructure_"+k).focus();
				showerr($("#infrastructure_"+k)[0], "Administrative  type is Required", "block");
				return false;
			  }
			if(information_room==""|| information_room==null && information_room != undefined)
			{
				chk=1;
				$("#Carpet_"+k).val("");
				$("#Carpet_"+k).focus();
				showerr($("#Carpet_"+k)[0], "Available Carpet is Required To Enter", "block");
				return false;
			 }
			if(cnt>1){  //add more same Computer Peripheral Type  save again
				for(var j=k+1; j<=cnt; j++){
					if($("#infrastructure_"+k).val()==$("#infrastructure_"+j).val()){
						 $("#infrastructure_"+j).focus();
						 if($("#infrastructure_"+j).val()!=undefined && $("#infrastructure_"+j).val()!=""){
						showerr($("#infrastructure_"+j)[0],"Infrastructure Type is Already Selected!","block");
						return false;
					}
				  }
				}
			}
	   }
	var newRow = $("<tr>");	 
	var cols = "";
	var r = parseInt(cnt)+1;
	cols += '<tr id="tr_'+r+ '">';
	cols += '<td style="width:5%"><span id="sno2_'+r+'"> '+ parseInt(sno) +'</span></td>';
	cols += '<td style="width:20%"><input type="text" id="infrastructure_'+r+'" name="infrastructure_'+r+'" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Infrastructure Type" value=""></td>';
	cols += '<td style="width:10%"><input type="text" id="Carpet_'+r+'" onkeypress="return allowOnlyNumeric(event)" name="Carpet_'+r+'" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms" value=""></td>';
	
	cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
		cols +='><span id="sno2_'+r+'"" onclick="deleteDetailsdata(-1,'+ r+ ',this,'+ "'"+ ''+ "sno2"+ ''+ "'"+ ','+ "'"+ ''+ "ID"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	$('#Infrastructure_table').append(cols);
	cols="";    
	$("#totRowDetails").val(r);
}
// collapseThree
function addAmenitiesDetails() {
	var chk=0;
	var id =$("#admc_id").val();
	var cnt = $("#totRowID").val();
	var cnt1 = document.getElementById("Amenities_Details").rows.length;
	var sno = parseInt(cnt1)+1;
	   for (var k = 1; k <= cnt; k++) {
		   var Amenities = $("#Amenities_" + k).val();
	       var Room   = $("#Room_" + k).val();
	       if(Amenities==""|| Amenities==null && Amenities != undefined)
			  {
				chk=1;
				$("#Amenities_"+k).val("");
				$("#Amenities_"+k).focus();
				showerr($("#Amenities_"+k)[0], "Amenities Type is Required", "block");
				return false;
			  }
	       if(Room==""|| Room==null && Room != undefined)
			  {
				chk=1;
				$("#Room_"+k).val("");
				$("#Room_"+k).focus();
				showerr($("#Room_"+k)[0], "Available Carpet is Required", "block");
				return false;
			  }
	   	if(cnt>1){  //add more same Amenities Type save again
			for(var j=k+1; j<=cnt; j++){
				if($("#Amenities_"+k).val()==$("#Amenities_"+j).val()){
					 $("#Amenities_"+j).focus();
					 if($("#Amenities_"+j).val()!=undefined && $("#Amenities_"+j).val()!=""){
					showerr($("#Amenities_"+j)[0],"Amenities Type is Already Selected!","block");
					return false;
				}
			  }
			}
		}
	   }
	var newRow = $("<tr>");	 
	var cols = "";
	var r = parseInt(cnt)+1;
	cols += '<tr id="tr_'+r+ '">';
	cols += '<td style="width:5%"><span id="sno1_'+r+'"> '+ parseInt(sno) +'</span></td>';
	cols += '<td style="width:20%"><input type="text" id="Amenities_'+r+'" name="Amenities_'+r+'" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Amenities Type" value=""></td>';
	cols += '<td style="width:10%"><input type="text" id="Room_'+r+'"  name="Room_'+r+'" class="form-control" onkeypress="return allowOnlyNumeric(event)" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms" value=""></td>';
	cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
		cols +='><span id="sno1_'+r+'"" onclick="deleteDetailsdata(-1,'+ r+ ',this,'+ "'"+ ''+ "sno1"+ ''+ "'"+ ','+ "'"+ ''+ "AM"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	$('#Amenities_Details').append(cols);
	cols="";    
	$("#totRowID").val(r);
}

//Add more computer periferal

//collapseFour
function addComputersDetails() {
	var chk=0;
	var id =$("#admc_id").val();
	var cnt = $("#totComRowID").val();
	var cnt1 = document.getElementById("Computer_peripherals").rows.length;
	var sno = parseInt(cnt1)+1;
	   for (var k = 1; k <= cnt; k++) {
		   var Computer = $("#Computer_" + k).val();
	       var avilavle   = $("#avilavle_" + k).val();
	       if(Computer==""|| Computer==null && Computer != undefined)
			  {
				chk=1;
				$("#Computer_"+k).val("");
				$("#Computer_"+k).focus();
				showerr($("#Computer_"+k)[0], "Computer Type is Required", "block");
				return false;
			  }
	       if(avilavle==""|| avilavle==null && avilavle != undefined)
			  {
				chk=1;
				$("#avilavle_"+k).val("");
				$("#avilavle_"+k).focus();
				showerr($("#avilavle_"+k)[0], "Available Carpet is Required", "block");
				return false;
			  }
	   	if(cnt>1){  //add more same Amenities Type save again
			for(var j=k+1; j<=cnt; j++){
				if($("#Computer_"+k).val()==$("#Computer_"+j).val()){
					 $("#Computer_"+j).focus();
					 if($("#Computer_"+j).val()!=undefined && $("#Computer_"+j).val()!=""){
					showerr($("#Computer_"+j)[0],"Computer Type is Already Selected!","block");
					return false;
				}
			  }
			}
		}
	   }
	var newRow = $("<tr>");	 
	var cols = "";
	var r = parseInt(cnt)+1;
	cols += '<tr id="tr_'+r+ '">';
	cols += '<td style="width:5%"><span id="sno3_'+r+'"> '+ parseInt(sno) +'</span></td>';
	cols += '<td style="width:20%"><input type="text" id="Computer_'+r+'" name="Computer_'+r+'" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Computer Type" value=""></td>';
	cols += '<td style="width:10%"><input type="text" id="avilavle_'+r+'"  name="avilavle_'+r+'" class="form-control" onkeypress="return allowOnlyNumeric(event)" maxlength="5" style="height: 20px;" placeholder="Enter Available" value=""></td>';
	cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
		cols +='><span id="sno3_'+r+'"" onclick="deleteDetailsdata(-1,'+ r+ ',this,'+ "'"+ ''+ "sno3"+ ''+ "'"+ ','+ "'"+ ''+ "CP"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	$('#Computer_peripherals').append(cols);
	cols="";    
	$("#totComRowID").val(r);
}


//Add more Library F

//collapseFour
function addLibraryDetails() {
	var chk=0;
	var id =$("#admc_id").val();
	var cnt = $("#totLibRowID").val();
	var cnt1 = document.getElementById("library_Facility").rows.length;
	var sno = parseInt(cnt1)+1;
	   for (var k = 1; k <= cnt; k++) {
		   var library = $("#library_" + k).val();
	       var avilType   = $("#avilType_" + k).val();
	       if(library==""|| library==null && library != undefined)
			  {
				chk=1;
				$("#library_"+k).val("");
				$("#library_"+k).focus();
				showerr($("#library_"+k)[0], "Library Facility Type is Required", "block");
				return false;
			  }
	       if(avilType==""|| avilType==null && avilType != undefined)
			  {
				chk=1;
				$("#avilType_"+k).val("");
				$("#avilType_"+k).focus();
				showerr($("#avilType_"+k)[0], "Available is Required", "block");
				return false;
			  }
	   	if(cnt>1){  //add more same Amenities Type save again
			for(var j=k+1; j<=cnt; j++){
				if($("#library_"+k).val()==$("#library_"+j).val()){
					 $("#library_"+j).focus();
					 if($("#library_"+j).val()!=undefined && $("#library_"+j).val()!=""){
					showerr($("#library_"+j)[0],"Library Facility Type is Already Selected!","block");
					return false;
				}
			  }
			}
		}
	   }
	var newRow = $("<tr>");	 
	var cols = "";
	var r = parseInt(cnt)+1;
	cols += '<tr id="tr_'+r+ '">';
	cols += '<td style="width:5%"><span id="sno4_'+r+'"> '+ parseInt(sno) +'</span></td>';
	cols += '<td style="width:20%"><input type="text" id="library_'+r+'" name="library_'+r+'" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Library Facility Type" value=""></td>';
	cols += '<td style="width:10%"><input type="text" id="avilType_'+r+'"  name="avilType_'+r+'" class="form-control" onkeypress="return allowOnlyNumeric(event)" maxlength="5" style="height: 20px;" placeholder="Enter Available" value=""></td>';
	cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
		cols +='><span id="sno4_'+r+'"" onclick="deleteDetailsdata(-1,'+ r+ ',this,'+ "'"+ ''+ "sno4"+ ''+ "'"+ ','+ "'"+ ''+ "LF"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	$('#library_Facility').append(cols);
	cols="";    
	$("#totLibRowID").val(r);
}



function getDetailsId() {
	var id =$("#admc_id").val();
	$.ajax({
        type: 'POST',
        url: "AdministrativeConfigurationService",
        data: {fstatus: 'IN', id: id,type :"AD"},
        success: function (response) {
            // Check if response has data
           if (typeof response.detailList !== 'undefined' && response.detailList.length > 0) {
            	$(".hide").hide();
                generateRows(response.detailList, 'AS');
              
            } else {
                $(".hide").show();
                generateRows(response.detailList, 'NS');
            }
        },
        error: function (xhr, status, error, response) {
            // Handle error
        }
    });
}
function getInfrastructureDetailsId() {
	var id =$("#admc_id").val();
	$.ajax({
        type: 'POST',
        url: "AdministrativeConfigurationService",
        data: {fstatus: 'IN', id: id,type :"ID"},
        success: function (response) {
            // Check if response has data
           if (typeof response.detailList !== 'undefined' && response.detailList.length > 0) {
            	$(".hide").hide();
            	InfrastructuregenerateRows(response.detailList, 'AS');
            } else {
                $(".hide").show();
                InfrastructuregenerateRows(response.detailList, 'NS');
            }
        },
        error: function (xhr, status, error, response) {
            // Handle error
        }
    });
}

function getAmenitiesDetailsId() {
	var id =$("#admc_id").val();
	$.ajax({
        type: 'POST',
        url: "AdministrativeConfigurationService",
        data: {fstatus: 'IN', id: id,type :"AM"},
        success: function (response) {
            // Check if response has data
           if (typeof response.detailList !== 'undefined' && response.detailList.length > 0) {
            	$(".hide").hide();
            	AmenitiesRows(response.detailList, 'AS');
              
            } else {
                $(".hide").show();
                AmenitiesRows(response.detailList, 'NS');
            }
        },
        error: function (xhr, status, error, response) {
            // Handle error
        }
    });
}

//Add Computer perifarall
function getComputersId() {
	var id =$("#admc_id").val();
	$.ajax({
        type: 'POST',
        url: "AdministrativeConfigurationService",
        data: {fstatus: 'IN', id: id,type :"CP"},
        success: function (response) {
            // Check if response has data
           if (typeof response.detailList !== 'undefined' && response.detailList.length > 0) {
            	$(".hide").hide();
            	ComputerRows(response.detailList, 'AS');
              
            } else {
                $(".hide").show();
                ComputerRows(response.detailList, 'NS');
            }
        },
        error: function (xhr, status, error, response) {
            // Handle error
        }
    });
}


//Add Library Facilities
function getLibFacDet() {
	var id =$("#admc_id").val();
	$.ajax({
        type: 'POST',
        url: "AdministrativeConfigurationService",
        data: {fstatus: 'IN', id: id,type :"LF"},
        success: function (response) {
            // Check if response has data
           if (typeof response.detailList !== 'undefined' && response.detailList.length > 0) {
            	$(".hide").hide();
            	LibraryRows(response.detailList, 'AS');
              
            } else {
                $(".hide").show();
                LibraryRows(response.detailList, 'NS');
            }
        },
        error: function (xhr, status, error, response) {
            // Handle error
        }
    });
}
function generateRows(detailList, type) {
    var cols = "";
    var r = 0;
    var admc_id=$("#admc_id").val();
    if(type=="AS"){
	    $.each(detailList, function (key, val) {
	        r++;
	        cols += '<tr id="tr_' + r + '">';
	        cols += '<td style="width:5%"><span id="sno_' + r + '"> ' + r + '</span></td>';
	        cols += '<td style="width:20%"><input type="text" id="program_' + r + '" name="COMP_PERI_NAME_' + r + '" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Administrative Type" value="' + val.administrative_type + '"></td>';
	        cols += '<td style="width:10%"><input type="text" id="CP_PERI_TYP_' + r + '" name="CP_PERI_TYP_' + r + '" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="3" style="height: 20px;" placeholder="Enter Available Rooms" value="' + val.available_carpet + '"></td>';
	        cols += '<input type="hidden" id="dt_id_' + r + '" name="dt_id_' + r + '" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms" value="' + val.ADMC_detail_id + '">';
	        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
	        	cols +='><span id="sno_'+r+'"" onclick="deleteDetailsdata(' + val.ADMC_detail_id + ','+ r+ ',this,'+ "'"+ ''+ "sno"+ ''+ "'"+ ','+ "'"+ ''+ "AD"+ ''+ "'"+ ')"><i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	        cols += '</tr>';
	       
	    });
    }
    if(type=="NS"){
    	cols = "";
        cols += '<tr id="tr_">';
        cols += '<td style="width:5%"><span id="sno_">'+1+'</span></td>';
        cols += '<td style="width:20%"><input type="text" id="program_1" name="program_1" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Administrative  Type" value="" ></td>';
        cols += '<td style="width:10%"><input type="text" id="CP_PERI_TYP_1" name="CP_PERI_TYP_1" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms"></td>';
        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
        	cols +='><span id="admc_id_'+r+'"" onclick="deleteDetailsdata('+admc_id+','+ r+ ',this,'+ "'"+ ''+ "admc_id"+ ''+ "'"+ ','+ "'"+ ''+ "AD"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
        cols += '</tr>';
        r=1;
    }
    // Append the default rows to the table
    $("#AdministrativeDetails_table").append(cols);
    $("#totRow").val(r);
}

function InfrastructuregenerateRows(detailList, type) {
    var cols = "";
    var r = 0;
    var admc_id=$("#admc_id").val();
    if(type=="AS"){
	    $.each(detailList, function (key, val) {
	        r++;
	        cols += '<tr id="tr_' + r + '">';
	        cols += '<td style="width:5%"><span id="sno2_' + r + '"> ' + r + '</span></td>';
	        cols += '<td style="width:20%"><input type="text" id="infrastructure_' + r + '" name="infrastructure_' + r + '" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Infrastructure Type" value="' + val.administrative_type + '"></td>';
	        cols += '<td style="width:10%"><input type="text" id="Carpet_' + r + '" name="Carpet_' + r + '" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms" value="' + val.available_carpet + '"></td>';
	        cols += '<input type="hidden" id="dt_idi_' + r + '" name="dt_idi_' + r + '"  class="form-control"   style="height: 20px;"   value="' + val.ADMC_detail_id + '">';
	        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
	        	cols +='><span id="sno2_'+r+'"" onclick="deleteDetailsdata(' + val.ADMC_detail_id + ','+ r+ ',this,'+ "'"+ ''+ "sno2"+ ''+ "'"+ ','+ "'"+ ''+ "ID"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	        cols += '</tr>';
	    });
    }
    if(type=="NS"){
    	cols = "";
        cols += '<tr id="tr_">';
        cols += '<td style="width:5%"><span id="sno_"> ' + 1 + '</span></td>';
        cols += '<td style="width:20%"><input type="text" id="infrastructure_1" name="infrastructure_1" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Infrastructure Type" value="" ></td>';
        cols += '<td style="width:10%"><input type="text" id="Carpet_1" name="Carpet_1" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms"></td>';
        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
        	cols +='><span id="admc_id_'+r+'"" onclick="deleteDetailsdata('+admc_id+','+ r+ ',this,'+ "'"+ ''+ "admc_id"+ ''+ "'"+ ','+ "'"+ ''+ "ID"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
        cols += '</tr>';
        r=1;
    }
    // Append the default rows to the table
    $("#Infrastructure_table").append(cols);
    $("#totRowDetails").val(r);
}

function AmenitiesRows(detailList, type) {
    var cols = "";
    var r = 0;
    var admc_id=$("#admc_id").val();
    if(type=="AS"){
	    $.each(detailList, function (key, val) {
	        r++;
	        cols += '<tr id="tr_' + r + '">';
	        cols += '<td style="width:5%"><span id="sno1_' + r + '"> ' + r + '</span></td>';
	        cols += '<td style="width:20%"><input type="text" id="Amenities_' + r + '" name="Amenities_' + r + '" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Computer Peripheral Type" value="' + val.administrative_type + '"></td>';
	        cols += '<td style="width:10%"><input type="text" id="Room_' + r + '" name="Room_' + r + '" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms" value="' + val.available_carpet + '"></td>';
	        cols += '<input type="hidden" id="dt_amid_' + r + '" name="dt_amid_' + r + '"  class="form-control"   style="height: 20px;"   value="' + val.ADMC_detail_id + '">';
	        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
	        	cols +='><span id="sno1_'+r+'"" onclick="deleteDetailsdata(' + val.ADMC_detail_id + ','+ r+ ',this,'+ "'"+ ''+ "sno1"+ ''+ "'"+ ','+ "'"+ ''+ "AM"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	        cols += '</tr>';
	    });
    }
    if(type=="NS"){
    	cols = "";
        cols += '<tr id="tr_">';
        cols += '<td style="width:5%"><span id="sno_"> ' + 1 + '</span></td>';
        cols += '<td style="width:20%"><input type="text" id="Amenities_1" name="Amenities_1" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Amenities Type" value="" ></td>';
        cols += '<td style="width:10%"><input type="text" id="Room_1" name="Room_1" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Rooms"></td>';
        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
        	cols +='><span id="admc_id_'+r+'"" onclick="deleteDetailsdata('+admc_id+','+ r+ ',this,'+ "'"+ ''+ "sno"+ ''+ "'"+ ','+ "'"+ ''+ "AM"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
        cols += '</tr>';
        r=1;
    }
    // Append the default rows to the table
    $("#Amenities_Details").append(cols);
    $("#totRowID").val(r);
}
//Add computer periferal

function ComputerRows(detailList, type) {
    var cols = "";
    var r = 0;
    var admc_id=$("#admc_id").val();
    if(type=="AS"){
	    $.each(detailList, function (key, val) {
	        r++;
	        cols += '<tr id="tr_' + r + '">';
	        cols += '<td style="width:5%"><span id="sno3_' + r + '"> ' + r + '</span></td>';
	        cols += '<td style="width:20%"><input type="text" id="Computer_' + r + '" name="Computer_' + r + '" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Computer Peripheral Type" value="' + val.administrative_type + '"></td>';
	        cols += '<td style="width:10%"><input type="text" id="avilavle_' + r + '" name="avilavle_' + r + '" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Carpet" value="' + val.available_carpet + '"></td>';
	        cols += '<input type="hidden" id="dt_cpid_' + r + '" name="dt_cpid_' + r + '"  class="form-control"   style="height: 20px;"   value="' + val.ADMC_detail_id + '">';
	        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
	        	cols +='><span id="sno3_'+r+'"" onclick="deleteDetailsdata(' + val.ADMC_detail_id + ','+ r+ ',this,'+ "'"+ ''+ "sno3"+ ''+ "'"+ ','+ "'"+ ''+ "CP"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	        cols += '</tr>';
	    });
    }
    if(type=="NS"){
    	cols = "";
        cols += '<tr id="tr_">';
        cols += '<td style="width:5%"><span id="sno_"> ' + 1 + '</span></td>';
        cols += '<td style="width:20%"><input type="text" id="Computer_1" name="Computer_1" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Computer Peripheral Type" value="" ></td>';
        cols += '<td style="width:10%"><input type="text" id="avilavle_1" name="avilavle_1" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available Carpet"></td>';
        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
        	cols +='><span id="admc_id_'+r+'"" onclick="deleteDetailsdata('+admc_id+','+ r+ ',this,'+ "'"+ ''+ "admc_id"+ ''+ "'"+ ','+ "'"+ ''+ "CP"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
        cols += '</tr>';
        r=1;
    }
    // Append the default rows to the table
    $("#Computer_peripherals").append(cols);
    $("#totComRowID").val(r);
}



//Add Library Facilites

function LibraryRows(detailList, type) {
    var cols = "";
    var r = 0;
    var admc_id=$("#admc_id").val();
    if(type=="AS"){
	    $.each(detailList, function (key, val) {
	        r++;
	        cols += '<tr id="tr_' + r + '">';
	        cols += '<td style="width:5%"><span id="sno4_' + r + '"> ' + r + '</span></td>';
	        cols += '<td style="width:20%"><input type="text" id="library_' + r + '" name="library_' + r + '" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Libaray Facility Type" value="' + val.administrative_type + '"></td>';
	        cols += '<td style="width:10%"><input type="text" id="avilType_' + r + '" name="avilType_' + r + '" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available" value="' + val.available_carpet + '"></td>';
	        cols += '<input type="hidden" id="dt_lfid_' + r + '" name="dt_lfid_' + r + '"  class="form-control"   style="height: 20px;"   value="' + val.ADMC_detail_id + '">';
	        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
	        	cols +='><span id="sno4_'+r+'"" onclick="deleteDetailsdata(' + val.ADMC_detail_id + ','+ r+ ',this,'+ "'"+ ''+ "sno4"+ ''+ "'"+ ','+ "'"+ ''+ "LF"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
	        cols += '</tr>';
	    });
    }
    if(type=="NS"){
    	cols = "";
        cols += '<tr id="tr_">';
        cols += '<td style="width:5%"><span id="sno_"> ' + 1 + '</span></td>';
        cols += '<td style="width:20%"><input type="text" id="library_1" name="library_1" class="form-control" maxlength="90" style="height: 20px;" placeholder="Enter Libaray Facility Type" value="" ></td>';
        cols += '<td style="width:10%"><input type="text" id="avilType_1" name="avilType_1" onkeypress="return allowOnlyNumeric(event)" class="form-control" maxlength="5" style="height: 20px;" placeholder="Enter Available"></td>';
        cols += '<td class="colr-red-p text-center" style="width:6%;color:red;"'
        	cols +='><span id="admc_id_'+r+'"" onclick="deleteDetailsdata('+admc_id+','+ r+ ',this,'+ "'"+ ''+ "admc_id"+ ''+ "'"+ ','+ "'"+ ''+ "LF"+ ''+ "'"+ ')"> <i class="fa fa-trash p-l-3"></i>Delete</span></td> '
        cols += '</tr>';
        r=1;
    }
    // Append the default rows to the table
    $("#library_Facility").append(cols);
    $("#totLibRowID").val(r);
}

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

function deleteDetailsdata(id, index, s_id, typ,f_typ) { 

	// alert("1:"+$('.Supp').length);
	if (f_typ == 'AD') {
		var rowcountAfterDelete1 = parseInt($("#totRow").val());
		var rowcountAfterDelete = document.getElementById("AdministrativeDetails_table").rows.length;
	}else if(f_typ=="ID"){
		var rowcountAfterDelete1 = parseInt($("#totRowDetails").val());
		var rowcountAfterDelete = document.getElementById("Infrastructure_table").rows.length; 
	  } else if(f_typ=="AM") {
		var rowcountAfterDelete1 = parseInt($("#totRowID").val());
		var rowcountAfterDelete = document.getElementById("Amenities_Details").rows.length; 
	}
	else if(f_typ=="CP") {
		var rowcountAfterDelete1 = parseInt($("#totComRowID").val());
		var rowcountAfterDelete = document.getElementById("Computer_peripherals").rows.length; 
	}
	else {
		var rowcountAfterDelete1 = parseInt($("#totLibRowID").val());
		var rowcountAfterDelete = document.getElementById("library_Facility").rows.length; 
	}

	// alert("rowcountAfterDelete 11 :"+rowcountAfterDelete);
	if (rowcountAfterDelete != 1) {
		var chk = 0;
		if (id != '-1' && id != undefined) {
			if (!deletemastdata(id, typ))
				chk = 1;
		}
		if (chk == 0) {
			var x = parseInt($("#" + typ + "_" + index).text());
			for (var i = parseInt(index); i <= rowcountAfterDelete1; i++) {
				if ($("#" + typ + "" + (i + 1)).text() != undefined
						&& $("#" + typ + "_" + (i + 1)).text() != "") {
					$("#" + typ + "_" + (i + 1)).text(x);
					++x;
				}
			}
			$(s_id).parents("tr").remove();
		}
		// $("#count").val(index-1);
	} else {
		displaySuccessMessages("errMsg2",
				"At least 1 rows should be present in the table", "");
		clearSuccessMessageAfterFiveSecond("errMsg2");

	}
}
function deletemastdata(id,typ) {
	var retVal = confirm("Data will be deleted permanently. \nAre you sure you want to remove this Data?");
	if (retVal == true) {
		try {
			$.ajax({
				type : "POST",
				url : "AdministrativeConfigurationService",
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

function validateData(){
	var session = $("#session_id").val();
	var Date = $("#XFDATE1").val();
	var req_id = $("#req_id").val();
	if (session == "" || session == null) {
		showerr($("#session_id")[0], "Session is required", "block");
		$("#session_id").focus();
		return false;
	}
	if (Date == "" || Date == null) {
		showerr($("#XFDATE1")[0], "Effective Date is required", "block");
		$("#XFDATE1").focus();
		return false;
	}

	if (req_id == "" || req_id == null) {
		showerr($("#req_id")[0], "Request Type is required", "block");
		$("#req_id").focus();
		return false;
	}
	
	saveRecord();
}

function saveRecord(){
try{
		var fstatus = $("#fstatus").val();
		var cntLib = parseInt($("#totRow").val());
		var cnt = parseInt($("#totRowDetails").val());
		var count = parseInt($("#totRowID").val());
		var countCom = parseInt($("#totComRowID").val());
		var countLib = parseInt($("#totLibRowID").val());
		var compuPeripheral = [];
		  if (cntLib > 0) {
			for (var i = 1; i <= cntLib; i++) {
						for(var j=i+1; j<=cntLib; j++){   //add more same Administrative type save again
							if($("#program_"+i).val()==$("#program_"+j).val()){
								 $("#program_"+j).focus();
								 if($("#program_"+j).val()!=undefined && $("#program_"+j).val()!=""){
								showerr($("#program_"+j)[0],"Administrative Type is Already Selected!","block");
								return false;
							}
						 }
						}
					
			if($("#program_"+i).val()!=undefined && $("#program_"+i).val()!=""){
				compuPeripheral.push({
					"comPeriTyp" : $("#program_" + i).val(),
					"avlbleRoom" : $("#CP_PERI_TYP_" + i).val(),
					"dt_id_" : $("#dt_id_" + i).val(),
					"TYPE" : "AD",
				});
				}
			}
		}
	
	if(cnt>0){   
			for (var j = 1; j <= cnt; j++) {
				for(var p=j+1; p<=cnt; p++){//add more same Computer Peripheral Type t save again
					if($("#infrastructure_"+j).val()==$("#infrastructure_"+p).val()){
						 $("#infrastructure_"+p).focus();
						 if($("#infrastructure_"+p).val()!=undefined && $("#infrastructure_"+p).val()!=""){
						showerr($("#infrastructure_"+p)[0],"Computer Peripheral Type is Already Selected!","block");
						return false;
					}
				 }
				}
				if($("#infrastructure_"+j).val()!=undefined && $("#infrastructure_"+j).val()!=""){
				compuPeripheral.push({
					"comPeriTyp" : $("#infrastructure_" + j).val(),
					"avlbleRoom" : $("#Carpet_" + j).val(),
					"dt_id_" : $("#dt_idi_" + j).val(),
					"TYPE" : "ID",
				});
				}
			}}
	if(count>0){  
	       for (var k = 1; k <= count; k++) {
	    	   for(var l=k+1; l<=count; l++){//add more same Amenities Type save again
					if($("#Amenities_"+k).val()==$("#Amenities_"+l).val()){
						 $("#Amenities_"+l).focus();
						 if($("#Amenities_"+l).val()!=undefined && $("#Amenities_"+l).val()!=""){
						showerr($("#Amenities_"+l)[0],"Amenities Type is Already Selected!","block");
						return false;
					}
				 }
				}
	    	   if($("#Amenities_"+k).val()!=undefined && $("#Amenities_"+k).val()!=""){
				compuPeripheral.push({
					"comPeriTyp" : $("#Amenities_" + k).val(),
					"avlbleRoom" : $("#Room_" + k).val(),
					"dt_id_" : $("#dt_amid_" + k).val(),
					"TYPE" : "AM",
				});
	    	   }
			}}
	
	
	if(countCom>0){  
	       for (var k = 1; k <= countCom; k++) {
	    	   for(var l=k+1; l<=countCom; l++){//add more same Amenities Type save again
					if($("#Computer_"+k).val()==$("#Computer_"+l).val()){
						 $("#Computer_"+l).focus();
						 if($("#Computer_"+l).val()!=undefined && $("#Computer_"+l).val()!=""){
						showerr($("#Computer_"+l)[0],"Computer Periferal is Already Selected!","block");
						return false;
					}
				 }
				}
	    	   if($("#Computer_"+k).val()!=undefined && $("#Computer_"+k).val()!=""){
	    		   compuPeripheral.push({
						"comPeriTyp" : $("#Computer_" + k).val(),
						"avlbleRoom" : $("#avilavle_" + k).val(),
						"dt_id_" : $("#dt_cpid_" + k).val(),
						"TYPE" : "CP",
					});	
	    	   }
			}}
	if(countLib>0){  
	       for (var k = 1; k <= countLib; k++) {
	    	   for(var l=k+1; l<=countLib; l++){//add more same Amenities Type save again
					if($("#library_"+k).val()==$("#library_"+l).val()){
						 $("#library_"+l).focus();
						 if($("#library_"+l).val()!=undefined && $("#library_"+l).val()!=""){
						showerr($("#library_"+l)[0],"Library Facility is Already Selected!","block");
						return false;
					}
				 }
				}
	    	   if($("#library_"+k).val()!=undefined && $("#library_"+k).val()!=""){
	    		   compuPeripheral.push({
						"comPeriTyp" : $("#library_" + k).val(),
						"avlbleRoom" : $("#avilType_" + k).val(),
						"dt_id_" : $("#dt_lfid_" + k).val(),
						"TYPE" : "LF",
					});	
	    	   }
			}}
	 var objjson = {
			 "session"      : $("#session_id").val(),
			 "effective"    : $("#XFDATE1").val(),
			 "req_type"     : $("#req_id").val(),
			 "admc_id"      : $("#admc_id").val(),
		     "Perilist"		: compuPeripheral	
	 	}
	// alert("json:" + JSON.stringify(objjson));
	 var encData = encAESData($("#AESKey").val(), objjson);
		$.ajax({
				type : "POST",
				url  : "AdministrativeConfigurationService",
				data : {
				encData : encData,
				fstatus :fstatus,
				},
					success : function(data) {
						var data = JSON.parse(data);
						if (data.flg == "V") {
							displaySuccessMessages("errMsg1", data.errMsg, "");
							clearSuccessMessageAfterTwoSecond("errMsg1");
							setTimeout(
									function() {
										window.location.href = "administrative_configuration_e.jsp";
									}, 2000);
							if (data.fstatus == "E") {
								setTimeout(
										function() {
											window.location.href = "administrative_configuration_e.jsp";
										}, 2000);
							} else {
								setTimeout(function() {
									location.reload();
								}, 3000);
							}
						} else {
							displaySuccessMessages("errMsg2", data.errMsg, "");
							clearSuccessMessageAfterTwoSecond("errMsg2");
						}
					},
			error : function(e) {
				alert("Save" + e);
			}
		});
}catch (e) {
	alert(e);
}
}

function editRecord(id,fstatus){
	try {
	 if(fstatus=="D"){
			var del=confirm("Are You Sure?")
		    if(del==true){
		    	$.ajax({
		    		type : "POST",
		    		url:  'AdministrativeConfigurationService',
		    		data: 'fstatus=D&admc_id='+id,	
		    		success : function(response){
				    	if(response.flg == "Y"){
				    		displaySuccessMessages("errMsg1", response.errMsg, "");
				    		clearSuccessMessageAfterFiveSecond("errMsg1");
				    		setTimeout(function () {
				    			location.reload();
				    		}, 5000);			    		
				    	  }else{
				    		    displaySuccessMessages("errMsg2", response.errMsg, "");
				    		    clearSuccessMessageAfterFiveSecond("errMsg2");
				    	  }
				        
				    }
		    	});
		    }
		}else{
				document.getElementById("admc_id").value=id;
				document.getElementById("opt_typ").value=fstatus;
				document.frmAdministrativeL.target = "_parent";
				document.frmAdministrativeL.action = "administrative_configuration_e.jsp";
				document.frmAdministrativeL.submit();
			}
} catch (err) {alert(err);}
}
