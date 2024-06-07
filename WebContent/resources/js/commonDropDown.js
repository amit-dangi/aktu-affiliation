function getQuesSubTypeByType(type, obj, sub_type) {
	try {
		$.ajax({
			type: 'POST',
			url:  '../common/dropdowndata',
			data: 'action=getQuesSubTypeByType&type='+type,
			async: false,
			success: function (response){
				var moduleHtml = "<option value=''>Select Questionnaire Sub Type</option>";
				if(typeof response.TypeList != 'undefined' && response.TypeList.length > 0){
					$.each(response.TypeList, function (key, val) {
						var widgetKey = val.id;
						var widgetValue = val.name;
						var sel = (widgetKey === sub_type) ? "selected" : "";
						moduleHtml += "<option value='" + widgetKey + "'" + sel + ">" + widgetValue + "</option>";
					});
	                $("#"+obj).html(moduleHtml);
	            } else {
	                $("#"+obj).html("<option value=''>Select Questionnaire Sub Type</option>");
	            }
	        },
	        error: function (data) {
	            alert("Error");
	        }
	    });
	} catch (e) {
		// TODO: handle exception
		alert("ERROR in commonDropDown.js - getQuesSubType :"+e)
	}
}