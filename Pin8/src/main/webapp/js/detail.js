/* rem标准统一
 * 返回示例
 * 运行后  1rem 相当于 屏幕大小除于10的像素 例如 ：iPhone4S下面就是 1rem = 32px
 *
 */
function resize() {
	window.remFontSize = document.documentElement.clientWidth / 10;
	document.documentElement.style.fontSize = document.documentElement.clientWidth / 10 + "px"
}
var b = null;
window.addEventListener("resize", function() {
	clearTimeout(b),
		b = setTimeout(resize, 300)
}, !1);
resize();

Zepto(function($){
	var vm;
	var userId = localStorage.getItem("userId");
	$(document).ready(function(){
		initPage();
		getGroupBuyInfomation();
		bindEvent();
		
		vm = new Vue({
			el: "body",
			data: {
				info: "",
				openImgList: "",
				showImgList: ""
			},
			computed: {
				getStatus: function(){
					switch(this.info.status){
						case 0:
							return "召集中";
							break;
						case 1:
							return "采购中";
							break;
						case 2:
							return "结算中";
							break;
						case 10: 
							return "已结束";
							break;
						case 20:
							return "终止";
							break;
					}
				}
			},
			methods: {
				decreaseNumber: function(item){
					if(item.quantity > 0 && item.quantityLimit>=item.totalQuantity){
						item.quantity--;
						item.totalQuantity--;	
					}
					
				},
				increaseNumber:function(item){
					if(item.quantityLimit > item.totalQuantity || item.quantityLimit==-1){
						item.quantity++;
						item.totalQuantity++;	
					}
				},
				openImgView: function(item){
					if(item.pics.length){
						this.openImgList = item.pics;
						$(".img-view-container").show();
						if(!$(".img-view").hasClass("swiper-container-horizontal")){
							var swiper2 = new Swiper('.img-view', {
						        pagination: '.img-view .swiper-pagination',
						        paginationClickable: true
						    });
						}
					}
				}
			}
		});
	});
	
	function initPage(){
		$("body").height( $(window.height ));
	}

	function bindEvent(){
		$("#detail-confirm").on("click",function(){
			getGroupBuyParticipate();
		});

		$(".img-close-btn").on("click",function(){
			$(".img-view-container").hide();
		});
	}

	function getGroupBuyInfomation(){
		var info = {
			"invitationCode":localStorage.getItem("invitationCodeData"),
			"gbId":localStorage.getItem("gbId"),
			"userId":userId
		}
		$.ajax({
			type: 'POST',
			url: './groupbuy/get',
			data: JSON.stringify(info),
			dataType: 'json',
			contentType: 'application/json',
			success: function(result){
				if(result.status == 0){
					vm.info = result.bean;	
					var showImgArry = [];
					for(var i=0;i<result.bean.items.length;i++){
						for(var j=0;j<result.bean.items[i].pics.length;j++){
							result.bean.items[i].pics[j].picLink = result.bean.items[i].pics[j].picLink.replace(/\\/g,"/");
							showImgArry.push(result.bean.items[i].pics[j]);
						}
					}
					vm.showImgList = showImgArry;
					$(".detail-box").show();
					vm.$log();
		
					if(!$(".img-display").hasClass("swiper-container-horizontal")){
						var swiper = new Swiper('.img-display', {
					        pagination: '.img-display .swiper-pagination',
					        paginationClickable: true
					    });
					}
				}
			},
			error: function(result){
			  	console.log('error',result);
			}
		});
	}

	function getGroupBuyParticipate(){
		var info = {
			"gbId": vm.info.id,
			"userId":userId,
			"items":[]
		}; 
		$.each(vm.info.items,function(i,item){
			var appendItem = {
				"gbiId": item.id,
				"quantity": item.quantity
			};
			info.items.push(appendItem);
		});
		$.each(vm.info.purchases, function(i,purchase){		
			if(purchase.userId == userId){
				info.id = vm.info.purchases[0].id;
			}
		});
		console.log("participate info",info);
		$.ajax({
			type: 'POST',
			url: './groupbuy/participate',
			data: JSON.stringify(info),
			dataType: 'json',
			contentType: 'application/json',
			success: function(result){
				console.log('get group by success',result);
				if(result.status == 0){
					localStorage.setItem("detailGoods",JSON.stringify(vm.info));
					location.href = "./views/succeed.html";
				}else if(result.status == 1){
					alert(result.errorMessage);
				}
			},
			error: function(result){
			  	console.log('error',result);
			}
		});
	}
});