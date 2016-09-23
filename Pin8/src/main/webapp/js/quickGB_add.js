(function($){
	$(document).ready(function() {
		var vm = new Vue({
			el: "body",
			data: {
				lists: [],
				info: sessionData("addInfo")
			},
			methods: {
				previewPage: function(){
					location.href="./quickGB_preview.html?type=1";
				},
				releasePage: function(){
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
				addOne: function(){
					location.href="./quickGB_add_detail.html";
				},
				decrease: function(list){
					if(list.quantity || list.quantity.toString()!=""){
						list.quantity--;
						sessionData("addLists",this.lists);
					}
				},
				increase: function(list){
					if(list.quantity.toString()!="" && list.quantity<list.totalQuantity){
						list.quantity++;
						sessionData("addLists",this.lists);
					}
				}
			},
			ready: function(){
				this.lists = sessionData("addLists")?sessionData("addLists"):[];
				if(sessionData("addDetail")){
					var addOne = sessionData("addDetail");
					this.lists.push(addOne);
					sessionData("addLists",this.lists);
					sessionData("addDetail",null);
				}

			}
		});
	});	
})(jQuery);
