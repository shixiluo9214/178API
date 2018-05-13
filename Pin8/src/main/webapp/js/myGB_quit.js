(function($){
	$(document).ready(function() {
		var shopId = urlData("id");
		var gbpId = urlData("gbpid");
		var userInfo = sessionData("userInfo");
		var vm = new Vue({
			el: "body",
			data: {
				quitOption: '临时改变主意',
				quitReason: ''
			},
			ready: function(){
			},
			methods: {
				quitConfirm: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../groupbuy/terminateParticipation',
						data: JSON.stringify({
							"id": JSON.parse(gbpId),
							"userId": userInfo.id,
							"message": self.quitOption,
							"surveys": [{
								"question": "question1",
								"number": 1,
								"answer": self.quitReason
							}]
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								console.log("quit success!");
								location.href = "./myGB_list.html";
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				}
			}
		});
	});	
})(jQuery);
