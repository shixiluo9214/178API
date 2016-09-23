(function($){
	$(document).ready(function() {
		var addInfo = sessionData("addInfo");
		var vm = new Vue({
			el: "body",
			data: {
				locationList: sessionData("locationList"),
				locationSelect: ''
			},
			methods: {
			},
			ready: function(){
			},
			watch: {
				'locationSelect': function(val){
					sessionData("backFromLocation", true);

					addInfo.position = this.locationSelect;
					sessionData("addInfo",addInfo);
				}
			}
		});
	});	
})(jQuery);
