(function($){
	$(document).ready(function(){
		initPage();
		bindEvent();
	});
	


	function initPage(){
		$("body").height( $(window.height ));
	}

	function bindEvent(){
		$("#login-btn").on("click",function(){
			$(".login-dialog span").hide();
			var loginInfo = {
					"phone": $("#login-phone").val(),
					"password": $("#login-password").val()
				}
			if(loginInfo.phone.length<11){
				loginDialog("Please fill in correct phone");
			}else if(loginInfo.password.length < 6){
				loginDialog("Please fill in correct password");
			}else{
				var encrypt = new JSEncrypt();
				encrypt.setPublicKey(PublicKeyString);
				var encrypted = encrypt.encrypt(loginInfo.password+","+randomString(8));
				loginInfo.password = encrypted;

				userLoginCallServers(loginInfo);
			}
		});
		
		$("#register-confirm").on("click",function(){
			$(".register-dialog span").hide();
			var phoneNumber = $("#phone-input").val(),
				nickName = $("#nickName-input").val(),
				passWord = $("#password-input").val(),
				passWordConfirm = $("#password-confirm-input").val();
			var addUserInfo = {
				"nickName": nickName,
				"phone": phoneNumber,
				"password": passWord
			};
			if(phoneNumber.length<11){
				registerDialog("Please fill in correct phone");
			}else if(!nickName){
				registerDialog("Please fill in correct nick name");
			}else if(!(passWord.length>5 && passWordConfirm.length>5 && passWord == passWordConfirm)){
				registerDialog("Please fill in correct password");
			}else{
				var encrypt = new JSEncrypt();
				encrypt.setPublicKey(PublicKeyString);
				var encrypted = encrypt.encrypt(passWord+","+randomString(8));
				addUserInfo.password = encrypted;

				userAddCallServers(addUserInfo);
			}
			
		});
		
		$("#register-btn").on("click", function(){
			$(".login-container").hide();
			$(".register-container").show();
		});

		$("#security-code-btn").on("click", function(){
			//security code button

		});
	}

	function userLoginCallServers(loginInfo) {
		$.ajax({
			type: 'POST',
			url: '../user/login',
			data: JSON.stringify(loginInfo),
			dataType: 'json',
			contentType: 'application/json',
			success: function(result){
				if(result.status == 0){
					console.log('login success',result);
					sessionData("userInfo", result.bean);
					var currentPage = sessionData("currentPage");
					sessionData("currentPage",null);
					location.href = currentPage;
				}else if(result.status == 1){
					loginDialog("Your user name or password error.")
				}
			},
			error: function(result){
			  	console.log('error',result);
			}
		});
	}

	function userAddCallServers(info) {
		$.ajax({
			type: 'POST',
			url: './user/add',
			data: JSON.stringify(info),
			dataType: 'json',
			contentType: 'application/json',
			success: function(result){
				console.log('add user success',result);
				if(result.status == 0){
					location.href = "./views/detail.html";
				}else if(result.status == 1){
					registerDialog("Your phone have been registered.")
				}
			},
			error: function(result){
			  	console.log('error',result);
			}
		});
	}

	function randomString(len) {
	　　len = len || 32;
	　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
	　　var maxPos = $chars.length;
	　　var pwd = '';
	　　for (i = 0; i < len; i++) {
	　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	　　}
	　　return pwd;
	}

	function loginDialog(msg){
		$(".login-dialog span").show().text(msg);
	}

	function registerDialog(msg){
		$(".register-dialog span").show().text(msg);
	}
})(jQuery);