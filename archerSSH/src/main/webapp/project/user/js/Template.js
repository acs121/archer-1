/*
* @Author: Administrator
* @Date:   2017-05-15 15:47:09
* @Last Modified by:   Administrator
* @Last Modified time: 2017-05-16 19:32:23
*/

$(document).ready(function(){
	//	显示昵称
  $.ajax({
    url: "../../user_nickname",
    type: "POST",
    success: function(data){
      $("#nickName").text(data.nickname);
    },
    error: function(){
      window.location.href="index.html";
    }
  });
  //  显示回收站和退出
  $("#nickName").click(function(){
    if($(".collection").css('display')=='none'){
      $(".collection").css("display","block");
    }else{
      $(".collection").css("display","none");
    }
  });
   //  退出登录
  $("#quit").click(function(){
    $.ajax({
      url: "../../user_logout",
      type: "POST",
      success: function(data){
        window.location.href="index.html";
      },
      error: function(){
        layer.msg('访问失败', hint);
      }
    });
  });
  //超出长度显示省略号
  function displayPart(obj,length) {
    if(obj.length>length){
      obj=obj.substring(0,length)+"……";
    }
    return obj;
  }
  //	获取模板列表
  $.ajax({
  	url: "../../ques_getTemplateNameList",
    type: "POST",
    success: function(data){
    	for(var i=0;i<data.length;i++){
        //  获取问卷列表
        $(".row").append("<div class='col s4'>"+
          "<div class='card darken-1'>"+
            "<div class='card-content white-text'>"+
              "<span class='card-title'>"+displayPart(data[i],13)+"<input type='hidden' class='templateName' value='"+data[i]+"' >"+"</span>"+
              "<div class='contentMake'>"+
              	"<i class='material-icons lookTemplate'>my_location<span class='warn'>查看模板</span></i>"+
	            	"<i class='material-icons useTemplate'>description<span class='warn'>引用模板</span></i>"+
              "</div>"+
            "</div>"+
          "</div>"+
        "</div>");
      }
      $(".lookTemplate").click(function(){
        var name=$(this).parent().prev().children().val();
        window.location.href="writeQst.html?name="+name;
      });
      $(".useTemplate").click(function(){
        var name=$(this).parent().prev().children().val();
        window.location.href="question.html?name="+name;
      });
    },
    error: function(){
      layer.msg('获取列表失败', hint);
    }
  });

});