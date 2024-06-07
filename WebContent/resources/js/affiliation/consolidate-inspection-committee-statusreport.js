/**
 * @author Amit DanGi
 */
$(document).ready(function(){
	$(".hidediv").hide();
	$("#btnReset").click(function(){
		location.reload();
	});
	// use for the get  AcademicSession  
	getAcademicSession();
});

function getApplicationDetail(){ 
	
	try{
		var session_id = $('#session_id').val();
		if (session_id == "") {
			$('#session_id').focus();
			showerr($("#session_id")[0], "Session is required.", "block");
			return false;
		}
		
		   $("#consolidateinspectionreportD").attr("action","consolidate_inspection_committee_statusreport_l.jsp");
	  	   $("#consolidateinspectionreportD").attr("method","post");
	  	   $("#consolidateinspectionreportD").attr("target","consolidateinspectionreportL");
	  	   $("#consolidateinspectionreportD").submit();

	}catch(err){
		alert("Error :"+err);
	}

}


/*
function getApplicationDetail(){ 
	
	//Validate file as click on view
	var session_id = $('#session_id').val();
	if (session_id == "") {
		$('#session_id').focus();
		showerr($("#session_id")[0], "Session is required.", "block");
		return false;
	}
	var inst_name=$('#inst_name').val();
	var reg_no=$('#reg_no').val();
	var final_status=$('#final_status').val();
try {
	$.ajax({
		type: "POST",
		url: "../ConsolidateInspectionByCommitteeService",
		data:{"fstatus":"GETREPORT_DETAILS", "session_id":session_id,"inst_name":inst_name,"reg_no":reg_no,
				"final_status":final_status},
		async: false,
		success: function (response){
			$('#stable').html("");
			console.log("response in consolidated report :");
			console.log(response.Applicationlist);
			 var index=0;
			
			 if (typeof response.Applicationlist!= 'undefined' && response.Applicationlist.length > 0) {
				 
				 var cols ='<table id="searchTable" class="table table-striped table-bordered table-hover" >'
					 +'<thead><tr><th class="text-center">S.No.</th>'
					 +'<th class="text-center">Session</th>'
					 +'<th class="text-center">Reg No.</th>'
					 +'<th class="text-center">Institute Name</th>'
					 +'<th class="text-center">Society/ Trust/ Section Name</th>'
					 +'<th class="text-center">Remarks</th>'
					 +'<th class="text-center">Recommendation</th>'
					 +'<th class="text-center">Inspection Panel</th>'
					 +'<th class="text-center">Status</th>'
					 +'<th class="text-center">Action Date</th><tr></thead>'
					 +'<tbody>'
				 
				$.each(response.Applicationlist, function (key, val) {
					$("#searchTable").show();
					var session 	= val.session;
                    var AF_REG_ID 	= val.AF_REG_ID;
                    var email 		= val.email;
                    var contact 	= val.contact;
                    var REG_NO 		= val.REG_NO;
                    var PROP_INST_NAME = val.PROP_INST_NAME;
                    var REG_FOR_NAME = val.REG_FOR_NAME;
                    var panel_code 	= val.panel_code;
                    var remarks 	= val.remarks;
                    var panel_name  =val.panel_name;
                    var cons_remark =val.cons_insp_remarks;
                    var cons_recm   =val.cons_insp_recm;
                    var consolidate_status   =val.consolidate_status;
                    var isfinalsubmited_dt=val.isfinalsubmited_dt;
                    
                    index=index+1;
                    var bgcolor=(consolidate_status=="Granted"?"#1b82216e":consolidate_status=="ReOpen"?"#ff630014":"");
                    cols +='<tr style="background-color:'+bgcolor+'" >'
                    	+'<td style="text-align:center; width:3%;">'+index+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+session+'</td>'
                    	+'<td style="text-align:center; width:5%;">'+REG_NO+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+PROP_INST_NAME+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+REG_FOR_NAME+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+cons_remark+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+cons_recm+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+panel_name+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+consolidate_status+'</td>'
                    	+'<td style="text-align:center; width:8%;">'+isfinalsubmited_dt+'</td>'
                    	+'</tr>'
        				//$('#stable').append(cols);
                    	//$("#searchTable").show();
                    	//$("#headerdiv").show();
                	    
				});
				 cols +='</tbody></table>'
					 $('#datatablediv').append(cols);
				var t = $('#searchTable').DataTable( {
        	    	"lengthMenu": [[-1,10, 25, 50, 100, 250, 500], ['All',10, 25, 50, 100, 250, 500]],
        	    	"searching" : false,
           			//"scrollY": "250px",
                    "scrollX": true,
                    "scrollCollapse": true,
                    "paging": true,
        	    	"columnDefs": [ {
        	            "searchable": false,
        	            "orderable": false,
        	            "targets": 0
        	        } ],
        	         dom :"<'row'<'col-sm-4 text-left'.h5><'col-sm-4 text-center'.h6> <'col-sm-4 text-right'B>>" +
                	"<'row'<'col-sm-6'l><'col-sm-6'>>" +
           		    "<'row'<'col-sm-12'tr>>" +
            	    "<'row'<'col-sm-5'i><'col-sm-7'p>>",   
    	        	buttons: [
    	            	{	
       	                	extend: 'excelHtml5',
       	                 	filename: 'Document Master',
       	                	className: 'btn btn-view',
       	               	    title: 'AFFILIATION MANAGEMENT',
       	                	text	:'Download',
       	                	exportOptions: {
       	                    	columns: [ 0,1,2,3,4,5,6,7,8,9]
       	                	},
       	           		 }
    	        	],
        	        columnDefs: [{ orderable: false, "targets": [0,4,5,6,7] },
       				  
       				 ],
        	        "order": [[ 1, 'asc' ]]
        	    } );
        	 
        	    t.on( 'order.dt search.dt', function () {
        	        t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        	            cell.innerHTML = i+1;
        	            t.cell(cell).invalidate('dom');
        	        } );
        	    } ).draw();
				
			 }else{
				  $('#stable').html("");
				  var data ='No Data Found';
				    displaySuccessMessages("errMsg2", data, "");
					clearSuccessMessageAfterFiveSecond("errMsg2");
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

}*/