(function($){
	$(document).ready(function() {
		var addInfo = sessionData("addInfo");
		var addLists = sessionData("addLists");
		var gbId = urlData("id");
		var vm = new Vue({
			el: "body",
			data: {
				info: '',
				lists: '',
				type: urlData("type")  //1 for new group buy    2 for near buy 
			},
			methods: {
				previewPage: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: './groupbuy/create',
						data: JSON.stringify({
							"title": this.info.title,
							"catalog": "食品",
							"dueDate": this.info.time,
							"memberLimit": this.info.count,
							"freightCal": this.info.rule,
							"createdBy": "41",
							"description": this.info.description,
							"participateScope": "RegUser",
							"visibleScope": "Private",
							"contactType": "Wechat",
							"deliverInfo": this.info.position,
							"type":"0",
							"canAddItem": this.info.checkStatus?'1':'0'
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								location.href="./quickGB_success.html";
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				joinPin: function(){
					console.log("join");
				},
				decrease: function(list){
					if(list.quantity || list.quantity.toString()!=""){
						list.quantity--;
						sessionData("addLists",this.lists);
					}
				},
				increase: function(list){
					if(list.quantity || list.quantity.toString()!=""){
						list.quantity++;
						sessionData("addLists",this.lists);
					}
				},
				getDetailFunction: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: './groupbuy/get',
						data: JSON.stringify({
							"gbId": gbId,
							"userId": "41"
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								console.log('result data',result);
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				}
			},
			ready: function(){
				if(this.type == 1){
					this.info = addInfo;
					this.lists = addLists;	
				}else if(this.type == 2){
					this.getDetailFunction();
				}
				

			}
		});
	});	
})(jQuery);
