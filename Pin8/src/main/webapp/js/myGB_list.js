(function($){
	$(document).ready(function() {
		checkUserInfoExist(location.href);
		var userInfo = sessionData("userInfo");
		Vue.filter("getStatus", function(value){
			var o;
			switch(value){
				case 0:
					o = '召集中';
					break;
				case 1:
					o = '采购中';
					break;
				case 2:
					o = '结算中';
					break;
				case 10:
					o = '已结束';
					break;
			}
			return o;
		});
		Vue.filter("getDueTime", function(dueTime){
			var targetTime = new Date(Date.parse(dueTime.replace(/-/g,"/"))).getTime() + 24*60*60*1000;
			var myDate = new Date();
			var todayTime = myDate.getTime();
			if(targetTime > todayTime){
				var h = parseInt(targetTime-todayTime)/(1000*60*60);
				var d = parseInt(h/24);
				var t = parseInt(h%24);
				if(d){
					return d+'天'+t+'小时后截止';
				}else{
					return t+ '小时后截止';
				}
			}
			
		})
		var vm = new Vue({
			el: "body",
			data: {
				lists: ""
			},
			methods: {
				enterDetail: function(list){
					switch(list.status){
						case 0:
						case 1:
						case 2:
							location.href="./myGB_detail.html?status="+list.status+"&id="+list.id;
							break;
						case 10:
							location.href="./myGB_detail.html?status=3&id="+list.id;
							break;
					}
				},
				getList: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: '../groupbuy/getList',
						data: JSON.stringify({
							//to do
							"userId": '7',
							"filterType":"ParticipateByMe"
						}),
						dataType: 'json',
						contentType: 'application/json',
						success: function(result){
							if(result.status==0){
								self.lists = result.bean;
							}
							console.log("My Gb list");
							self.$log("lists");
						},
						error: function(result){
						  	console.log('error',result);
						}
					});
				}
			},
			ready: function(){
				this.getList();
			}
		});
	});	
})(jQuery);
