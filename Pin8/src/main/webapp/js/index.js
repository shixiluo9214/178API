(function($){

	// $('#submit').ajaxSubmit({
	// 	url: '../groupbuy/addItem',
	// 	data: JSON.stringify([
	// 		{
	// 		    "gbId": "533",
	// 		    "userId": "41",
	// 		    "name": "奶粉1",
	// 		    "listPrice": "14.31",
	// 		    "quantity": "5",
	// 		    "unit": "罐",
	// 		    "quantityLimit": "20",
	// 		    "detail": "一段奶粉",
	// 		    "pics": [
	// 		        {
	// 		            "picLink": "\\upload\\groupbuyItemFolder\\335_1_0_1454571467709.jpg"
	// 		        }
	// 		    ]
	// 		},
	// 		{
	// 		    "gbId": "533",
	// 		    "userId": "41",
	// 		    "name": "奶粉2",
	// 		    "listPrice": "14121.33",
	// 		    "quantity": "0",
	// 		    "unit": "罐",
	// 		    "quantityLimit": "20",
	// 		    "detail": "二段奶粉",
	// 		    "pics": [
	// 		        {
	// 		            "picLink": "\\upload\\groupbuyItemFolder\\335_1_0_1454571467709.jpg"
	// 		        }
	// 		    ]
	// 		}
	// 	]),
	// 	success: function(result){
	// 		console.log("submit info successfully!", result);
	// 	},
	// 	error: function(result){
	// 	  	console.log('error',result);
	// 	}
	// });



	$.ajax({
		type: 'POST',
		url: '../groupbuy/addItem',
		data: JSON.stringify([
			{
			    "gbId": "533",
			    "userId": "41",
			    "name": "奶粉1",
			    "listPrice": "14.31",
			    "quantity": "5",
			    "unit": "罐",
			    "quantityLimit": "20",
			    "detail": "一段奶粉"
			},
			{
			    "gbId": "533",
			    "userId": "41",
			    "name": "奶粉2",
			    "listPrice": "14121.33",
			    "quantity": "0",
			    "unit": "罐",
			    "quantityLimit": "20",
			    "detail": "二段奶粉"
			}
		]),
		dataType: 'json',
		contentType: 'application/json',
		success: function(result){
			console.log("submit info successfully!", result);
		},
		error: function(result){
		  	console.log('error',result);
		}
	});
	
	
	// $.ajaxFileUpload({
	// 	type: 'POST',
	// 	url: '../groupbuy/addItem',
	// 	secureuri : false,
	// 	fileElementId : "uploadFile",
	// 	data: '',
	// 	dataType: 'json',
	// 	contentType: 'multipart/form-data',
	// 	charset: 'UTF-8',
	// 	success: function(result){
	// 		console.log("submit info successfully!", result);
	// 	},
	// 	error: function(result){
	// 	  	console.log('error',result);
	// 	}
	// });
})(jQuery);