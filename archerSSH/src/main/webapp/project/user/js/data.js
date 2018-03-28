/*
* @Author: Administrator
* @Date:   2017-04-15 21:39:51
* @Last Modified by:   Administrator
* @Last Modified time: 2017-05-01 19:40:13
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
  //  显示回退出
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
  //	加载题目内容
  var URL = document.location.toString();
  var arr = URL.split('=');
  $.ajax({
    url: "../../ques_getQuestionnaire",
    type: "POST",
    data:{
      'q.id':arr[1]
    },
    success: function(data){
		var contentjson=JSON.parse(data.内容);
      for(var i=0;i<contentjson.length;i++){
        var selectNum="A";
      	if(contentjson[i].类型==1||contentjson[i].类型==2){
      		$("#content").append('<div class="chooseQuestion">'+
						'<label class="num">1.</label>'+
						'<p class="childTitle">'+contentjson[i].题目+'</p>'+
						'<ul class="select">'+
						'</ul>'+
						'<div class="img">'+
						'</div>'+
					'</div>');
					for(var j=0;j<contentjson[i].选项.length;j++){
						$($(".select")[i]).append("<li>"+selectNum+"."+contentjson[i].选项[j]+"</li>");
            selectNum=selectNum.charCodeAt();
            selectNum++;
            selectNum=String.fromCharCode(selectNum);
					}
      	}
      	if(contentjson[i].类型==3){
      		$("#content").append('<div class="writeQuestion">'+
						'<label class="num">1.</label>'+
						'<p class="childTitle">'+contentjson[i].题目+'</p>'+
						'<div class="link">'+
						'导出答案Excle表'+
						'</div>'+
					'</div>');
      	}
      }
      //  题目排序
      for(var i=0;i<$(".num").length;i++){
        $($(".num")[i]).text(i+1+".");
      }
      //  加载图片
      for(var i=0;i<$(".img").length;i++){
        var q_id=$($(".img")[i]).prev().prev().prev().text();
        q_id=q_id.substring(0,q_id.length-1);
        $($(".img")[i]).append("<img src='../../ans_getAnswerDataByQid?q.id="+arr[1]+"&ans.qid="+q_id+"' class='imgStyle'>");
      }
	  var urlarr=URL.split("/");
	  
      //  加载链接
      for(var i=0;i<$(".link").length;i++){
        $($(".link")[i]).click(function(){
          var q_id=$(this).prev().prev().text();
          q_id=q_id.substring(0,q_id.length-1);
          layer.open({
            type: 1,
            title: '是否导出答案',
            area: ['300px', '150px'],
            skin: 'demo-class1',
            content: $(".export")
          });
          $("#export-yes").click(function(){
			window.open(URL.substring(0,URL.indexOf(urlarr[urlarr.length-3]))+'ans_getAnswerDataByQid?q.id='+arr[1]+'&ans.qid='+q_id); 
			$(".layui-layer-close").trigger("click");
          });
          $("#export-no").click(function(){
            $(".layui-layer-close").trigger("click");
          });
        });
      }
    },
    error: function(){
      layer.msg('访问失败', hint);
    }
  });
});