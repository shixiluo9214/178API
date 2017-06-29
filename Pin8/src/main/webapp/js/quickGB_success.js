(function($){
	$(document).ready(function() {
		var gbId = urlData("gbId");
		var userInfo = sessionData("userInfo");
		var vm = new Vue({
			el: "body",
			data: {
				// lists: [{"name":"加强护膝一双","money":"160.00","number":"2"},
				// 	   {"name":"防狼安全喷雾","money":"90.00","number":"1"},
				// 	   {"name":"荧光水壶腰包","money":"136.00","number":"0"}],
				lists: sessionData("addLists"),
				details: ''
			},
			computed: {
				total: function(){
					var n = 0;
					var items = this.details.items;
					for(var i=0;i<items.length;i++){
						n+=items[i].listPrice*items[i].quantity;
					}
					return n;
				}
			},
			methods: {
				getLists: function() {
					var self = this;
					$.ajax({
						type: 'POST',
						url: '/Pin8/groupbuy/get',
						data: JSON.stringify({
							"userId": userInfo.id,
							"gbId": gbId
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.details = result.bean;
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				pageMyGB: function(){
					location.href="./myGB_list.html";
				}
			},
			ready: function() {
				this.getLists()
			}
		});
	});	
})(jQuery);
