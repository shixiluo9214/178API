(function($){
	$(document).ready(function() {
		checkUserInfoExist(location.href);
		Vue.component("progress",{
			template: "#progress-template",
			props: ["left","total"],
			computed: {
				percent: function(){
					return this.left / this.total * 100 + '%';
				}
			}
		});
		var vm = new Vue({
			el: "body",
			data: {
				// lists:　[{"title":"一起吧~挪威进口三文鱼侧身","desc":"这周末我会一起背回家，请各位邻居届时来我家取~",
				// 		"attendList":[1,2,3,4,5],"attend":"6","total":"10","position":"五洲国际"},
				// 		{"title":"老家上好的柑橘，好货只和老邻居分享","desc":"五一自驾回老家，会带一些特产柑橘，老邻居们优先来拼啦",
				// 		"attendList":[1,2],"attend":"2","total":"20","position":"五洲国际"},
				// 		{"title":"ORAL-B电动牙刷美国人肉背回","desc":"四月底回国，各位同事们抓紧~",
				// 		"attendList":[1,2,3,4,5,6],"attend":"18","total":"20","position":"五洲国际"},
				// 		{"title":"临安采橙，想要的一起吧！限量50斤","desc":"走过路过，千万不要错过~",
				// 		"attendList":[1,2],"attend":"6","total":"10","position":"五洲国际"}],
				lists: ''
			},
			methods: {
				showDetail: function(val){
					location.href = "./quickGB_preview.html?type=2&id="+val.id;
				},
				getList: function(){
					var self = this;
					$.ajax({
						type: 'POST',
						url: './groupbuy/getList',
						data: JSON.stringify({
							//to do
							"userId": "7",
							"catalog":"食品",
							"filterType":"InSameComm"
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
