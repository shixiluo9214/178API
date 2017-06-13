(function($){
	$(document).ready(function() {
		checkUserInfoExist(location.href);
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
				valuation: null,
				valuationText: null
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
						url: '/Pin8/groupbuy/create',
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
						url: '/Pin8/groupbuy/addItem',
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
					for(var i=0;i<self.details.items.length;i++){
						items.push({
							"gbiId": self.details.items[i].id,
							"quantity": self.details.items[i].totalQuantity
						})
					}
					$.ajax({
						type: 'POST',
						url: '/Pin8/groupbuy/participate',
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
								location.href="./quickGB_success.html";
							}else{
								alert(result.errorMessage);
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				decreaseDetail: function(detail){
					if(detail.totalQuantity || detail.totalQuantity.toString()!=""){
						detail.totalQuantity--;
						// sessionData("previewDetail",this.details);
					}
				},
				increaseDetail: function(detail){
					if(detail.totalQuantity.toString()!="" && detail.totalQuantity<detail.quantityLimit || detail.quantityLimit==-1){
						detail.totalQuantity++;
						// sessionData("previewDetail",this.details);
					}
				},
				getDetailFunction: function(invitationCode){
					var self = this;
					// invitationCode存在，即为分享的拼单
					// invitationCode不存在，即为附近的拼单
					if (invitationCode) {
						var ajaxData = {
							"gbId": gbId,
							"invitationCode": invitationCode
						}
					} else {
						var ajaxData = {
							"gbId": gbId,
							"userId": userInfo.id
						}
					}
					$.ajax({
						type: 'POST',
						url: '/Pin8/groupbuy/get',
						data: JSON.stringify(ajaxData),
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
									createdBy: result.bean.createdBy,
									type: result.bean.status //召集中=0，采购中=1，结算中=2，3，已结束=10，20
								}
								self.details = result.bean;
								console.log('result data');
								self.$log("details");
								self.getOwnerInfo(function() {
									if (self.info.type == 10 || self.info.type == 20) {
										self.getValuation();
									}
								});
								
								var items = self.details.items;
								for(var i=0;i<items.length;i++) {
									for(var j=0;j<items[i].pics.length;j++) {
										self.imageLists.push({
											"src": items[i].pics[j].picLink,
											"id": items[i].pics[j].id
										})
									}
								}
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				getOwnerInfo: function(cb){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '/Pin8/user/getUser',
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
								cb && cb();
							}
						},
						error: function(result){
							console.log("error",result);
						}
					});
				},
				getValuation: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '/Pin8/comm/getValuations',
						data: JSON.stringify({
						    "valuator": userInfo.id,
						    "valuatee": this.ownerInfo.id,
						    "eventType": "EVENT_GROUPBUY",
						    "eventId": gbId,
						    "valuateType": "GroupBuyOwner"
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.valuation = result.bean;
								console.log("get valuation:");
								self.$log("valuation");
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				submitValuation: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '/Pin8/comm/valuate',
						data: JSON.stringify({
						    "valuator": userInfo.id,
						    "valuatee": this.owner.id,
						    "eventType": "EVENT_GROUPBUY",
						    "eventId": shopId,
						    "valuateType": "GroupBuyOwner",
						    "detail": this.valuationText,
						    "scale": this.valuateScore
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.valuation = result.bean[0];
								console.log("get valuation:");
								self.$log("valuation");
							}
						},
						error: function(result){
						  	console.log('error',result);
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
				},
				clickItem: function(index) {
					$('.carousel').carousel(index);	
				}
			},
			ready: function(){
				this.getDetailFunction(urlData("invitationCode"));
				
				$('.carousel').carousel({
				  interval: false
				})
			}
		});
	});	
})(jQuery);
