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
	var vm,goodsInfo = JSON.parse(localStorage.getItem("detailGoods"));
	$(document).ready(function(){
		initPage();
		console.log(goodsInfo);
		vm = new Vue({
			el: ".succeed-box",
			data: {
				info: goodsInfo
			}
		});
	});
	
	function initPage(){
		$("body").height( $(window.height ));
	}
});