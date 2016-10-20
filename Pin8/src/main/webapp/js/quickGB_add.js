(function($){
	$(document).ready(function() {
		var userInfo = sessionData("userInfo");
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
					// $.when(this.submitInfo,this.submitLists).done(function(){
					// 	location.href="./quickGB_success.html";
					// });
					this.submitInfo();
					// this.submitLists();
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
							console.log("submit info successfully!", result);
							//to delete
							location.href="./quickGB_success.html";
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				submitLists: function(){
					var self = this;
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
							    "detail": "一段奶粉",
							    "pics": [
							        {
							            "picLink": "\\upload\\groupbuyItemFolder\\335_1_0_1454571467709.jpg"
							        }
							    ]
							},
							{
							    "gbId": "533",
							    "userId": "41",
							    "name": "奶粉2",
							    "listPrice": "14121.33",
							    "quantity": "0",
							    "unit": "罐",
							    "quantityLimit": "20",
							    "detail": "二段奶粉",
							    "pics": [
							        {
							            "picLink": "\\upload\\groupbuyItemFolder\\335_1_0_1454571467709.jpg"
							        }
							    ]
							}
						]),
						dataType: 'text/plain',
						contentType: 'multipart/form-data',
						charset: 'UTF-8',
						success: function(result){
							console.log("submit info successfully!", result);
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
