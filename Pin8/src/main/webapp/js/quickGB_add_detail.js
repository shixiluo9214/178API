(function($){
	$(document).ready(function() {
		var vm = new Vue({
			el: "body",
			data: {
				addName: "",
				addMark: "",
				addPrice: "",
				addQuantity: "",
				imgBox: []
			},
			methods: {
				confirmAdd: function(){
					var obj = {
						name: this.addName,
						mark: this.addMark,
						price: this.addPrice,
						totalQuantity: this.addQuantity,
						imgBox: this.imgBox,
						quantity: 0
					};
					sessionData("addDetail",obj);
					location.href = "./quickGB_add.html";
				},
				addOneImage: function(){
					$("#imgFile").click();
				},
				inputChange: function(e){
					var self = this;
					var file = e.target.files||e.dataTransfer.files;
					 if(file){
	                 	var reader = new FileReader();
                 	 	reader.readAsDataURL(file[0]);  
	                 	reader.onloadend=function(){
                        	self.imgBox.push({
                        		'src': this.result
                        	});
	                    }

	                 }
				}
			}
		});
	});	
})(jQuery);
