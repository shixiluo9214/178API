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
				loginDialog("请填写正确的用户名。");
			}else if(loginInfo.password.length < 6){
				loginDialog("请填写正确的密码。");
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
				registerDialog("请填写正确的用户名。");
			}else if(!nickName){
				registerDialog("请填写昵称。");
			}else if(passWord.length<6){
				registerDialog("请填写正确的密码。");
			}else if(!(passWordConfirm.length>5 && passWord == passWordConfirm)) {
				registerDialog("请填写正确的确认密码。");
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
<<<<<<< .merge_file_9Bs6k4
			// security code button
=======
			//security code button
			if (!$(this).hasClass("disabled")) {
				displaySTime();
				securityCallServers();
			}
		});

		$("#forget-btn").on("click", function() {
>>>>>>> .merge_file_uSvXTJ

		});
	}
	
	function getUrlVars() {
	    var vars = {};
	    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
	    vars[key] = value;
	    });
	    return vars;
	}

	function userLoginCallServers(loginInfo) {
		var invitationCode = getUrlVars()["invitationCode"];
		//console.log('invitationCode:' + invitationCode);
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
<<<<<<< .merge_file_9Bs6k4
					if(invitationCode!=null){//need to jump to gb details page
						//location.href = "./views/myGB_detail.html";;
						location.href = "./views/quickGB_preview.html?type=3&invitationCode="+invitationCode;
						return;
					}
					if(currentPage == null){
						currentPage = "./views/myGB_list.html";
					}
=======
>>>>>>> .merge_file_uSvXTJ
					location.href = currentPage;
				}else if(result.status == 1){
					loginDialog("用户名或者密码错误。")
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
			url: '../user/add',
			data: JSON.stringify(info),
			dataType: 'json',
			contentType: 'application/json',
			success: function(result){
				console.log('add user success',result);
				if(result.status == 0){
					location.href = "../views/detail.html";
				}else if(result.status == 1){
					registerDialog("Your phone have been registered.")
				}
			},
			error: function(result){
			  	console.log('error',result);
			}
		});
	}

	function securityCallServers() {
		// security server
		
	}

	function randomString(len) {
	　　len = len || 32;
	　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /** **默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1*** */
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

	function displaySTime(time) {
		if (time == 0) {
			$("#security-code-btn").html("发送验证码").removeClass("disabled");
		} else {
			if (!time) time = 59;
			$("#security-code-btn").html(time + 's').addClass("disabled");
			setTimeout(function() {
				displaySTime(--time);
			}, 1000);
		}
	}

})(jQuery);