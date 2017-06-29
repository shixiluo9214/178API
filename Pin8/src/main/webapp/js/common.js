InteralServer = "http://zilian.co:8080/Pin8/";
PublicKeyString = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANq7c/hOqvzWWbW+7dzdygNO7/yB8DVDpieFun7xTF655ny/TCjdfWuE7Snd71HYnGCjoKhO2IpYE9eghmgVyTsCAwEAAQ==';

function sessionData(key, value){
	if(typeof(value) == "undefined"){
		//check undefined
		return JSON.parse(localStorage.getItem(key));
	}else if(!value && typeof(value)!="undefined" && value!=0){
		//check null
		localStorage.removeItem(key);
	}else if(value){
		localStorage.setItem(key,JSON.stringify(value));
	}
}

function urlData(name){
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return (r[2]); return null;
}

function checkUserInfoExist(currentPage){
	if($.isEmptyObject(sessionData("userInfo"))){
		sessionData("currentPage",currentPage);
		location.href = '/Pin8';
	}
}

if (!$("#loading").length) {
	$("body").append("<div id='loading' style='position:absolute;top:0;left:0;right:0;bottom:0;height:100%;background:rgba(0,0,0,.2);display:none;z-index:1000;'><img src='../img/loading.gif' style='position: relative;top:46%;left:46%;'></div>");
}
$(document).ajaxStart(function(){
	$("#loading").show();
}).ajaxStop(function() {
	$("#loading").hide();
});
