/*
* @Author: Administrator
* @Date:   2017-03-12 14:25:29
* @Last Modified by:   Administrator
* @Last Modified time: 2017-05-15 15:30:38
*/
var pubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqnWSmR+UbmzbAg2O2DA3fmDdnXLd5jdm4m8eMcHQre2UIF0zm/mTr+ESM1vfRH+FxEQpeHN+L3XrFkUGHepkoPEYV7s5cJZggnb/f5cvloTe2xE7f/t/3qRkMo8gOwqmQIir/qAntW64H/qjJzP6tyBU/NohDC5AUVmeiUM3bcwIDAQAB";
var encrypt = new JSEncrypt();
encrypt.setPublicKey(pubKey);
$(document).ready(function() {
	// 登陆获取验证码
	$("#authcode-one").focus(function(){
		//	加载验证码
		$(".authCode img").attr("src","../../user_codeImg?type=loginCode&"+Math.random());
		  
	});
	//	注册获取验证码
	$("#authcode-two").focus(function(){
			//	加载验证码
		$(".authCode img").attr("src","../../user_codeImg?type=registCode&"+Math.random());
	});
	//	注册验证

	//	用户名验证
	function checkName() {
		var reg=/^[\u4e00-\u9fa5_a-zA-Z0-9]{5,15}/;
		if(reg.test($("#nikename").val())){
				return true;
		}else{
			layer.msg("昵称长度为5——15,不能有非法字符",hint);
			return false;
		}
	}
	//	邮箱验证
	function checkEmail() {
		var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if(reg.test($("#email-two").val())){
			return true;
		}else{
			layer.msg("邮箱格式不正确",hint);
			return false;
		}
	}
	//	密码长度控制
	$("#password-two").keydown(function() {
		if($("#password-two").val().length>16){
			var value=$("#password-two").val();
			value=value.substring(0,16);
			$("#password-two").val(value);
		}
	});
	//	密码验证
	function checkPwd() {
		var reg=/^[a-zA-Z0-9]/;
		if(reg.test($("#password-two").val())){
			return true;
		}else{
			layer.msg("密码不能有非法字符且不为空",hint);
			return false;
		}
	}
	$("#register").click(function() {
		checkName();
		if(checkName())
		{
			checkEmail();
			if(checkEmail()){
				checkPwd();
				if(checkPwd()){
					// 验证注册验证码
					$.ajax({
					  url: "../../user_regist",
					  type: "POST",
					  data: {
					  	'authCode': $("#authcode-two").val(),
					  	'user.username': $("#email-two").val(),
					  	'user.nickname': $("#nikename").val(),
					  	'user.password': encrypt.encrypt($("#password-two").val())
					  },
					  success: function(data){
					  	//  注册
					    if(!data.codeIsOK){
					    	layer.msg('验证码不正确', hint);
					    }else if(data.userExist){
					    	layer.msg('用户名已存在', hint);
					    }else{
					    	layer.msg('注册成功', sucess);
					    }
					  },
					  error: function(){
					    layer.msg('访问失败', hint);
					  }
				  });
				}
			}
		}
	});
	//	登陆
	$("#userLogin").click(function(){
		$.ajax({
		  url: "../../user_login",
		  type: "POST",
		  data: {
		  	'authCode': $("#authcode-one").val(),
		  	'user.username': $("#email-one").val(),
		  	'user.password': encrypt.encrypt($("#password-one").val())
		  },
		  success: function(data){
		  	//  注册
		    if(data.codeIsOK==false){
		    	layer.msg('验证码不正确', hint);
		    }else if(data.status==false){
		    	layer.msg('用户名或密码错误', hint);
		    }else{
		    	window.location.href="myHome.html";
		    }
		  },
		  error: function(){
		    layer.msg('访问失败', hint);
		  }
	  });
	});

	$(document).keyup(function(event){
	  if(event.keyCode ==13){
	    $("#userLogin").trigger("click");
	  }
	});
});