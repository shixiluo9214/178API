(function($){
	$(document).ready(function() {
		var status = urlData("status");
		var shopId = urlData("id");
		var userInfo = sessionData("userInfo");
		var vm = new Vue({
			el: "body",
			data: {
				gbStatus: ['召集中','采购中','结算中','已结束'],
				statusIndex: status,
				// lists: [{"name":"Name1","mark":"","price":12,"quantity":"2"},
				// 		{"name":"Name2","mark":"Mark","price":35,"quantity":"3"}],
				// lists2:[{"name":"加强护膝一双","money":"160.00","preMoney":"150.00","number":2},
				// 		{"name":"防狼安全喷雾","money":"90.00","number":3},
				// 		{"name":"荧光水壶腰包","money":"136.00","number":4}],
				// lists3:{"shipping":"20.00","total":"566.00",
				// 		"lists":[{"name":"加强护膝一双","money":"160.00","number":2},
				// 				{"name":"防狼安全喷雾","money":"90.00","number":3},
				// 				{"name":"荧光水壶腰包","money":"136.00","number":4}]
				// 		}
				gbDetail: '',
				owner: ''
			},
			ready: function(){
				this.getShopDetail();
			},
			methods: {
				// activeStatus: function(i){
				// 	this.statusIndex = i;
				// },
				decrease: function(list){
					if(list.totalQuantity || list.totalQuantity.toString()!=""){
						list.totalQuantity--;
					}
				},
				increase: function(list){
					if((list.totalQuantity || list.totalQuantity.toString()!="") && list.totalQuantity<list.quantityLimit){
						list.totalQuantity++;
					}
				},
				submitPage1: function(){
					this.submitUpdate();
				},
				returnPage1: function(){
					location.href = "./myGB_quit.html?id="+shopId;
				},
				payPage3: function(){
					location.href = "./myGB_list.html";
				},
				confirmPage3: function(){
					this.confirmReceive();
				},
				getShopDetail: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../groupbuy/get',
						data: JSON.stringify({
							"userId": userInfo.id,
							"gbId":shopId
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.gbDetail = result.bean;
								self.getOwner();
							}
							console.log("My gb detail:");
							self.$log("gbDetail");
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				getOwner: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../user/getUser',
						data: JSON.stringify({
							"id": self.gbDetail.createdBy
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.owner = result.bean;
							}
							console.log("Owner infomation:");
							self.$log("owner");
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				confirmReceive: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../groupbuy/receive',
						data: JSON.stringify({
							"id": shopId
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								console.log("Confirm receive successfully.");
								location.href = "./myGB_list.html";
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				submitUpdate: function(){
					var self = this;
					var items = [];
					for(var i=0;i<self.gbDetail.items.length;i++){
						items.push({
							"gbiId": self.gbDetail.items[i].id,
							"quantity": self.gbDetail.items[i].totalQuantity
						})
					}
					$.ajax({
						type: 'POST',
						url: '../groupbuy/participate',
						data: JSON.stringify({
							"gbId": shopId,
							"userId": userInfo.id,
							"items": items
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								console.log("Submit update successfully.");
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
