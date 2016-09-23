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
	$(document).ready(function(){
		initPage();
		bindEvent();
		
		if($(".swiper-container").length){
			var swiper = new Swiper('.img-display', {
		        pagination: '.swiper-pagination',
		        paginationClickable: true
		    });
		}
	});
	


	function initPage(){
		$("body").height( $(window.height ));
	}

	function bindEvent(){

		$("#register-confirm").on("click",function(){
			location.href = "views/detail.html";
		});

		$("#detail-confirm").on("click",function(){
			location.href = "views/succeed.html";
		});

		$(".btn-dec").on("click",function(){
			var $selectNum = $(this).parent().parent().find(".num-select");
			var $overNum = $(this).parent().parent().find(".num-over");
			if(parseInt($selectNum.text())>0 && parseInt($overNum.text())>=0){
				if(isNaN($overNum.text())) alert("1");
				$selectNum.text( parseInt($selectNum.text())-1 );
				$overNum.text( parseInt($overNum.text())+1 );
			}
		});

		$(".btn-inc").on("click",function(){
			var $selectNum = $(this).parent().parent().find(".num-select");
			var $overNum = $(this).parent().parent().find(".num-over");
			if(parseInt($overNum.text())>0){
				$selectNum.text( parseInt($selectNum.text())+1 );
				$overNum.text( parseInt($overNum.text())-1 );
			}
		});

		$(".list-title").on("click",function(){
			$(".img-view-container").show();
			var swiper = new Swiper('.img-view', {
		        pagination: '.swiper-pagination',
		        paginationClickable: true
		    });
		});

		$(".img-close-btn").on("click",function(){
			$(".img-view-container").hide();
		});
	}
});