(function($){
	$(document).ready(function() {
		var userInfo = sessionData("userInfo");
		$(".save").on("click", function() {
			var province = $(".distpicker select")[0].value,
				city = $(".distpicker select")[1].value,
				district = $(".distpicker select")[2].value,
				name = $(".name input")[0].value,
				number = $(".number input")[0].value;
			if (!province) {
				alert("请选择省份");
			} else if (!city) {
				alert("请选择城市");
			} else if (!district) {
				alert("请选择区域");
			} else if (!name) {
				alert("请填写小区名");
			} else if (!number) {
				alert("请填写门牌号");
			} else {
				$.ajax({
					type: 'POST',
					url: '/Pin8/user/addAddress',
					data: JSON.stringify({
						"userId": userInfo.id,
						"country": "中国",
						"province": province,
						"city": city,
						"district": district,
						"commName": name,
						"address": number
					}),
					dataType: 'json',
					contentType: 'application/json',
					success: function(result){
						if(result.status==0){
							history.back();
						}
					},
					error: function(result){
					  	console.log('error',result);
					}
				});
			}
		});
	});	
})(jQuery);