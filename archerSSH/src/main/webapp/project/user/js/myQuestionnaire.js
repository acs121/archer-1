/*
* @Author: Administrator
* @Date:   2017-04-03 09:56:14
* @Last Modified by:   Administrator
* @Last Modified time: 2017-05-16 11:30:02
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
	//	获取问卷
	var URL = document.location.toString();
  
    var arr = URL.split('=');
    var Data = arr[1].split('&');
	
	//初始化问卷显示
     $.ajax({
      url: "../../ques_getQuestionnaire",
      type: "POST",
      data:{
        'q.id':Data[0]
      },
      success: function(data){
        //  初始化问卷题目
        var nameID=0;
        var idCount=0;
        var newArr=data.问卷名;
        newArr=newArr.split("$$$");
        $("#questionTitle").text(newArr[0]);
        $("#discribe").text(newArr[1]);
        //  初始化题目
		var contentjson=JSON.parse(data.内容);
        for(var i=0;i<contentjson.length;i++){
          if(contentjson[i].类型==1){
            $("#content").append("<div class='question'>"+
              "<label class='titleNum'>1.</label><p class='childTitle'>"+contentjson[i].题目+"</p>"+
              "<label class='string'></label>"+
              "<ul class='select'>"+
              "</ul>"+
            "</div>");
            for(var j=0;j<contentjson[i].选项.length;j++){
              $($(".select")[i]).append("<li>"+
                "<input type='radio' name='"+nameID+"' id='"+(++idCount)+"'/>"+
                "<label for='"+idCount+"'>"+contentjson[i].选项[j]+"</label>"+
              "</li>");
            }
          }
          if(contentjson[i].类型==2){
            $("#content").append("<div class='question'>"+
              "<label class='titleNum'>1.</label><p class='childTitle'>"+contentjson[i].题目+"</p>"+
              "<label class='string'></label>"+
              "<ul class='select'>"+
              "</ul>"+
            "</div>");
            for(var j=0;j<contentjson[i].选项.length;j++){
              $($(".select")[i]).append("<li>"+
                "<input type='checkbox' name='"+nameID+"' id='"+(++idCount)+"'/>"+
                "<label for='"+idCount+"'>"+contentjson[i].选项[j]+"</label>"+
              "</li>");
            }
          }
          if(contentjson[i].类型==3){
            $("#content").append("<div class='question'>"+
              "<label class='string'></label>"+
              "<label class='titleNum'>1.</label><p class='childTitle'>"+contentjson[i].题目+"</p>"+
              "<div class='fill' contenteditable='true'></div>"+
            "</div>");
          }
          nameID++;
        }
        //  题号排序
        for(var i=0;i<$(".titleNum").length;i++){
          $($(".titleNum")[i]).text(i+1+".");
        }
        //  给隐藏文本框赋值
        $("input[type='radio']").click(function(){
          var object=$(this);
          var name=$(this).attr("name");
          for(var i=0;i<$("input[name='"+name+"']").length;i++){
            if($($("input[name='"+name+"']")[i]).is(':checked')){
              $(object).parent().parent().prev().text(i+1);
            }
          }
        });
        $("input[type='checkbox']").click(function(){
          var object=$(this);
          $(object).parent().parent().prev().text('');
          var name=$(this).attr("name");
          for(var i=0;i<$("input[name='"+name+"']").length;i++){
            if($($("input[name='"+name+"']")[i]).is(':checked')){
              var str=$(object).parent().parent().prev().text();
              str=str+(i+1);
              $(object).parent().parent().prev().text(str);
            }
          }
        });
        $(".fill").blur(function(){
          var str=$(this).text();
          $(this).parent().children(":first").text(str);
        });
       
       
        $("#return").click(function(){
          window.location.href="myHome.html";
        })
      },
      error: function(){
        layer.msg('访问失败', hint);
      }
    });
  
});
