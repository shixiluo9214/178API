(function($){
	$(document).ready(function() {
		var status = urlData("status");
		var shopId = urlData("id");
		var userInfo = sessionData("userInfo");
		var vm = new Vue({
			el: "body",
			data: {
				userInfo: userInfo,
				gbStatus: ['召集中','采购中','结算中','已结束'],
				statusIndex: status,
				gbDetail: '',
				owner: '',
				payStatus: false,
				valuation: '',
				valuateScore: 0,
				valuationText: ''
			},
			computed: {
				fullNum: function(){
					return parseInt(this.owner.credit);
				},
				halfNum: function(){
					if(this.owner.credit!=5){
						return this.owner.credit-parseInt(this.owner.credit);
					}
				},
				emptyNum: function(){
					if(this.owner.credit<=4){
						return 5-Math.ceil(this.owner.credit);
					}
				}
			},
			ready: function(){
				this.getShopDetail();
			},
			methods: {
				// activeStatus: function(i){
				// 	this.statusIndex = i;
				// },
				decrease: function(list){
					if(list.quantity || list.quantity.toString()!=""){
						list.quantity--;
						list.totalQuantity--;
					}
				},
				increase: function(list){
					if((list.quantity || list.quantity.toString()!="") && list.totalQuantity<list.quantityLimit || list.quantityLimit==-1){
						list.quantity++;
						list.totalQuantity++;
					}
				},
				submitPage1: function(){
					this.submitUpdate();
				},
				returnPage1: function(){
					location.href = "./myGB_quit.html?id="+shopId+"&gbpid="+this.gbDetail.purchases[0].id;
				},
				payPage3: function(){
					this.payStatus = true;
				},
				confirmPage3: function(){
					this.confirmReceive();
				},
				historyPage4: function(){
					location.href = "./myGB_list.html";
				},
				sharePage4: function(){

				},
				getShopDetail: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '/Pin8/groupbuy/get',
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
							if (!status) {
								switch(result.bean.type) {
								case 0:
									self.statusIndex = 0;
									break;
								case 1:
									self.statusIndex = 1;
									break;
								case 2:
								case 3:
									self.statusIndex = 2;
									break;
								case 10:
								case 20:
									self.statusIndex = 3;
									break;
								}
							}
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
						url: '/Pin8/user/getUser',
						data: JSON.stringify({
							"id": self.gbDetail.createdBy
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.owner = result.bean;
								if(self.statusIndex==3){
									self.getValuation();
								}
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
						url: '/Pin8/groupbuy/receive',
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
					for(var i=0;i<self.gbDetail.purchases.length;i++){
						items.push({
							"gbiId": self.gbDetail.purchases[i].id,
							"quantity": self.gbDetail.purchases[i].quantity
						})
					}
					$.ajax({
						type: 'POST',
						url: '/Pin8/groupbuy/participate',
						data: JSON.stringify({
							"gbId": JSON.parse(shopId),
							"userId": userInfo.id,
							"items": items
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
//								console.log("Submit update successfully.");
								location.href="./quickGB_success.html?gbId="+result.bean.gbId;
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				finishPay: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '/Pin8/groupbuy/pay',
						data: JSON.stringify({
							"id": shopId
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								console.log("Finish pay successfully.");
								location.href = "./myGB_detail.html?status=3&id="+shopId;
							}
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				setValuateScore: function(e){
					this.valuateScore = $(e.target).index();
				},
				getValuation: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '/Pin8/comm/getValuations',
						data: JSON.stringify({
						    "valuator": userInfo.id,
						    "valuatee": this.owner.id,
						    "eventType": "EVENT_GROUPBUY",
						    "eventId": shopId,
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
				copyNumber: function(number) {
					var str = $("#"+number).text();
					var save = function(e){
				        e.clipboardData.setData('text/plain', str);
				        e.preventDefault();
				    }
				    document.addEventListener('copy', save);
				    document.execCommand('copy');
				    document.removeEventListener('copy',save);
				    alert('复制成功！');
				}
			}
		});
	});	
})(jQuery);
