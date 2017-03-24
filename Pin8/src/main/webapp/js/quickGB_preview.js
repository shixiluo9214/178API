(function($){
	$(document).ready(function() {
		var addInfo = sessionData("addInfo");
		var addLists = sessionData("addLists");
		var userInfo = sessionData("userInfo");
		var gbId = urlData("id");
		var vm = new Vue({
			el: "body",
			data: {
				info: '',
				lists: '',
				details: '',
				imageLists: [],
				userInfo: userInfo,
				ownerInfo: '',
				type: urlData("type")  //1 for new group buy    2 for near buy 
			},
			methods: {
				previewPage: function(){
					var self = this;
					var previewData = {
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
					};
					$.ajax({
						type: 'POST',
						url: '../groupbuy/create',
						data: JSON.stringify(previewData),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								// location.href="./quickGB_success.html";
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
				joinPin: function(){
					var self = this;
					var items = [];
					for(var i=0;i<self.details.length;i++){
						items.push({
							"gbiId": self.details[i].id,
							"quantity": self.details[i].totalQuantity
						})
					}
					$.ajax({
						type: 'POST',
						url: '../groupbuy/participate',
						data: JSON.stringify({
							"gbId": self.info.id,
							"userId": self.userInfo.id,
							"items": items
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								console.log('success participate');
								//to do    wait new gb to success page
								//location.href="./quickGB_success.html";
							}else{
								alert(result.errorMessage);
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
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
				},
				decreaseDetail: function(detail){
					if(detail.totalQuantity || detail.totalQuantity.toString()!=""){
						detail.totalQuantity--;
						// sessionData("previewDetail",this.details);
					}
				},
				increaseDetail: function(detail){
					if(detail.totalQuantity.toString()!="" && detail.totalQuantity<detail.quantityLimit){
						detail.totalQuantity++;
						// sessionData("previewDetail",this.details);
					}
				},
				getDetailFunction: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../groupbuy/get',
						data: JSON.stringify({
							"gbId": gbId,
							"userId": userInfo.id
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.info = {
									id: result.bean.id,
									title: result.bean.title,
									description: result.bean.description,
									count: result.bean.memberLimit,
									position: {
										commName: result.bean.deliverInfo
									},
									createdBy: result.bean.createdBy
								}
								self.details = result.bean.items;
								console.log('result data');
								self.$log("details");
								if(self.type == 2){
									self.getOwnerInfo();
								}
								
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				getDetailByInvitationCodeFunction: function(invitationCode){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../groupbuy/get',
						data: JSON.stringify({
							"gbId": 0,
							"invitationCode": invitationCode
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.info = {
									id: result.bean.id,
									title: result.bean.title,
									description: result.bean.description,
									count: result.bean.memberLimit,
									position: {
										commName: result.bean.deliverInfo
									},
									createdBy: result.bean.createdBy
								}
								self.details = result.bean.items;
								console.log('result data');
								self.$log("details");
								if(self.type == 2){
									self.getOwnerInfo();
								}
								
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				getOwnerInfo: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../user/getUser',
						data: JSON.stringify({
							"id": self.info.createdBy
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status == 0){
								self.ownerInfo = result.bean;
								console.log("owner info");
								self.$log("ownerInfo");
							}
						},
						error: function(result){
							console.log("error",result);
						}
					});
				},
				panLeft: function(){
					if(!$(".carousel .preview-img.active").hasClass("left") && !$(".carousel .preview-img.active").hasClass("right")){
						$('.carousel').carousel("next");
					}
				},
				panRight: function(){
					if(!$(".carousel .preview-img.active").hasClass("left") && !$(".carousel .preview-img.active").hasClass("right")){
						$('.carousel').carousel("prev");	
					}
				}
			},
			ready: function(){
				//1 for new group buy    2 for near buy  3 by invitation code
				if(this.type == 1){
					this.info = addInfo;
					this.lists = addLists;	
					for(var i=0;i<this.lists.length;i++){
						for(var j=0;j<this.lists[i].imgBox.length;j++){
							this.imageLists.push(this.lists[i].imgBox[j].src);
						}
					}
					this.$log("imageLists");
				}else if(this.type == 2){
					this.getDetailFunction();
				}
				else if(this.type == 3){//with invitation code
					var inviCode = urlData("invitationCode") ; 
					this.getDetailByInvitationCodeFunction(inviCode);
				}
				
				$('.carousel').carousel({
				  interval: false
				})
			}
		});
	});	
})(jQuery);
