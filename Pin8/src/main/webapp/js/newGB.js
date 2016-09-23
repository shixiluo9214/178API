(function($){
	$(document).ready(function() {
		checkUserInfoExist(location.href);
		var vm = new Vue({
			el: "body",
			methods: {
				openQuick: function(){
					location.href = "./quickGB_info.html";
				},
				openCommon: function(){
					location.href = "./commonGB_info.html";
				},
				cancelNew: function(){

				}
			}
		});
	});	
})(jQuery);
