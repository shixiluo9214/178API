(function($){
	$(document).ready(function() {
		var vm = new Vue({
			el: "body",
			data: {
				title: '',
				description: '',
				checkStatus: false
			},
			computed: {
				nextAvail: function(){
					return (this.title && this.description);
				}
			},
			methods: {
				nextStep: function(){
					var obj = {
						title: this.title,
						description: this.description,
						checkStatus: this.checkStatus
					};
					sessionData("addInfo",obj);
					location.href = "quickGB_add.html";
				}
			}
		});
	});	
})(jQuery);
