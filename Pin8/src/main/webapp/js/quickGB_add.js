(function($){
	$(document).ready(function() {
		var userInfo = sessionData("userInfo");
		var vm = new Vue({
			el: "body",
			data: {
				lists: sessionData("addLists"),
				info: sessionData("addInfo")
			},
			methods: {
				previewPage: function(){
					location.href="./quickGB_preview.html?type=1";
				},
				releasePage: function(){
					// $.when(this.submitInfo,this.submitLists).done(function(){
					// 	location.href="./quickGB_success.html";
					// });
					this.submitInfo();
				},
				submitInfo: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../groupbuy/create',
						data: JSON.stringify({
							"title": this.info.title,
							"catalog": this.info.category,
							"dueDate": this.info.time,
							"memberLimit": this.info.count,
							"freightCal": this.info.rule.val,
							"createdBy": userInfo.id,
							"description": this.info.description,
							"participateScope": "RegUser",
							"visibleScope": this.info.checkStatus?"InComm":"Private",
							"contactType": "Wechat",
							"deliverInfo": this.info.position.commName,
							"type":"0",
							"canAddItem": "1"
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0) {
								console.log("submit info successfully!", result);
								self.submitLists(result);
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				submitLists: function(result){
					var self = this;
					var ajaxData = [];
					self.$log('lists');
					for(var i=0;i<self.lists.length;i++) {
						ajaxData.push({
							"gbId": result.bean.id,
						    "userId": userInfo.id,
						    "name": self.lists[i].name,
						    "listPrice": self.lists[i].price,
						    "quantity": self.lists[i].quantity,
						    "unit": "罐",
						    "quantityLimit": self.lists[i].totalQuantity,
						    "detail": self.lists[i].mark,
						    "pics": self.lists[i].imgBox
						});
					}
					$.ajax({
						type: 'POST',
						url: '../groupbuy/addItem',
						data: JSON.stringify(ajaxData),
						beforeSend: function(XMLHttpRequest) {
							XMLHttpRequest.setRequestHeader("Content-Type", "multipart/form-data; boundary=6Mzk3h9whuKqi5YM_8irDye2i5_ulrErzYLB2");
						},
						contentType: 'multipart/form-data',
						charset: 'UTF-8',
						success: function(result){
							console.log("submit info successfully!", result);
							location.href="./quickGB_success.html";
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
						  	console.log('error',XMLHttpRequest);
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
