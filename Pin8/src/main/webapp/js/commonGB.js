(function($){
	$(document).ready(function() {
		Vue.transition("popupTransition",{
			enterClass: "fadeInUp",
			leaveClass: "fadeOutDown"
		})
		var vm = new Vue({
			el: "body",
			data: {
				showPopup: false,
				catalogInfo: '',
				addressInfo: '',
				scrollInfo: '',

				submitData: {
					title: '',
					description: '',
					category: '',
					time: '',
					rule: '',
					count: '',
					position: '',
					checkStatus: false
				}
			},
			computed: {
				nextAvail: function(){
					return (this.submitData.title && this.submitData.description && 
						this.submitData.category && this.submitData.count && 
						this.submitData.time && this.submitData.rule && this.submitData.position);
				}
			},
			methods: {
				toShowCategory: function(){
					this.showPopup = true;
					this.scrollInfo = this.catalogInfo.items;
					vm.$once("updateSelected", function(value){
						this.submitData.category = value.name;
					});
				},
				toShowTime: function(){
					$("#deadline").focus();
				},
				toShowRule: function(){
					this.showPopup = true;
					this.scrollInfo = [{'name':'按商品数量分配','val':'byQty'},{'name':'参与者均分','val':'byPeople'},{'name':'免运费','val':'byOthers'}];
					vm.$once("updateSelected", function(value){
						this.submitData.rule = value;
					});
				},
				toShowPosition: function(){
					// this.showPopup = true;
					// this.scrollInfo = this.addressInfo;
					// vm.$once("updateSelected", function(value){
					// 	this.submitData.position = value;
					// });
					
					sessionData("addInfo",this.submitData);
					sessionData("locationList",this.addressInfo);
					location.href = "./locationList.html";
				},

				selectPopup: function(value){
					this.showPopup = false;
					vm.$emit("updateSelected", value);
				},
				nextStep: function(){
					sessionData("addInfo",this.submitData);
					location.href = "quickGB_add.html";
				},
				getConfig: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../comm/getConfig',
						data: JSON.stringify({
							//to do
							"configType": "groupbuy_catalog"
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.catalogInfo = result.bean[0];
							}
							console.log("catalog success");
							self.$log("catalogInfo");
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				},
				getAddress: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../user/getAddresses',
						data: JSON.stringify({
							//to do
							"userId": "76"
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.addressInfo = result.bean;
							}
							console.log("address success");
							self.$log("addressInfo");
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				}
			},
			ready: function(){
				if(sessionData("backFromLocation")){
					//back from location list page
					this.submitData = sessionData("addInfo");
					sessionData("backFromLocation",null);
				}
				this.getConfig();
				this.getAddress();
			},
		});
	});	
})(jQuery);
