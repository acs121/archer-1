/*
* @Author: Administrator
* @Date:   2017-04-02 16:32:39
* @Last Modified by:   Administrator
* @Last Modified time: 2017-04-16 22:11:34
*/

$(document).ready(function(){
	//	显示昵称
  $.ajax({
    url: "../../user_nickname",
    type: "POST",
    success: function(data){
      $("#nickName").text(data.nickname);
        //  显示回收站和退出
      $("#nickName").click(function(){
        if($(this).next().css('display')=='none'){
          $(this).next().css("display","block");
        }else{
          $(this).next().css("display","none");
        }
      });
    },
    error: function(){
      window.location.href="index.html";
    }
  });
  //	生成url链接
  var URL = document.location.toString();
  var arr = URL.split('=');
  var Data = arr[1];
  var linkArr=URL.split("/");
  
  $("#link").val(URL.substring(0,URL.indexOf(linkArr[linkArr.length-1]))+"writeQst.html?id="+Data);
  //生成二维码
  $('#code').qrcode(URL.substring(0,URL.indexOf(linkArr[linkArr.length-1]))+"writeQst.html?id="+Data);
  //  复制链接
    var clip = new ZeroClipboard.Client();
    clip.setHandCursor(true);
    var text= $("#link").val();
    clip.setText(text);
    clip.addEventListener( 'complete', function(){layer.msg('复制成功', sucess);});
    clip.glue("copy");
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
});