(function($){
	$(document).ready(function() {
		var vm = new Vue({
			el: "body",
			data: {
				// lists: [{"name":"加强护膝一双","money":"160.00","number":"2"},
				// 	   {"name":"防狼安全喷雾","money":"90.00","number":"1"},
				// 	   {"name":"荧光水壶腰包","money":"136.00","number":"0"}],
				lists: sessionData("addLists")
			},
			computed: {
				total: function(){
					var n = 0;
					for(var i=0;i<this.lists.length;i++){
						n+=this.lists[i].price*this.lists[i].quantity;
					}
					return n;
				}
			},
			methods: {
				pageMyGB: function(){
					location.href="./myGB_list.html";
				}
			}
		});
	});	
})(jQuery);
