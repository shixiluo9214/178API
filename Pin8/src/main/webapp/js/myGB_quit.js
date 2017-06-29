(function($){
	$(document).ready(function() {
		var shopId = urlData("id");
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
							"id": JSON.parse(shopId),
							"userId": userInfo.id,
							"message": self.quitOption + self.quitReason,
							"surveys": []
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
