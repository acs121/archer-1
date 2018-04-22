/*
* @Author: Administrator
* @Date:   2017-04-01 20:14:48
* @Last Modified by:   Administrator
* @Last Modified time: 2017-05-15 17:09:43
*/

$(document).ready(function(){
	//	显示昵称
  $.ajax({
    url: "../../../../archerSSH/user_nickname",
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
  function initRecycle(){
  //  初始化回收站
	  $.ajax({
		url: "../../ques_getQuestionnaireList",
		type: "POST",
		async:false,
		data: {
		  'q.status':3,
		  'pageNo':1,
		  'pageSize':20
		},
		success: function(data){//-----------------------------------------------------------
		  for(var i=0;i<data.items.length;i++){
			var arr=data.items[i].questionnaire_name.split("$$$");
			$("tbody").append(
			"<tr id='recycle_"+data.items[i].id+"'>"+
				"<td>"+displayPart(arr[0],8)+"</td>"+
				"<td style='cursor:pointer;'><a class='recycleDel'>删除</a><input type='hidden' value='"+data.items[i].id+"'></td>"+
				"<td style='cursor:pointer;'><a class='recover'>恢复</a><input type='hidden' value='"+data.items[i].id+"'></td>"+
			  "</tr>");
		  }

		  for(var i=0;i<$("tbody").children().length;i++){
			$($("tbody tr")[i]).children(":first").nextAll().css({"bottom":"30px"});
		  }
		},
		error: function(){
		  layer.msg('初始化回收站失败', hint);
		}
	  });
	      //  彻底删除回收站
    $(".recycleDel").parent().click(function(){
      var object=$(this).children();
      var questionnaire_id=$(object).next().val();
      layer.open({
        type: 1,
        title: '是否彻底删除问卷',
        area: ['300px', '150px'],
        skin: 'demo-class1',
        content: $(".recycleDelete")
      });
      $("#recycle-delete-yes").click(function(){
        $.ajax({
            url: "../../ques_deleteQuestionnaire",
            type: "POST",
            data:{
              'q.id':questionnaire_id,
              'isRecycle':false
            },
            success: function(data){
              $("#recycle_"+questionnaire_id).remove();
              layer.msg('删除成功', sucess);
            },
            error: function(){
              layer.msg('删除失败', hint);
            }
          });
        $(".layui-layer-close").trigger("click");
      });
      $("#recycle-delete-no").click(function(){
        $(".layui-layer-close").trigger("click");
      });
    });
    //  恢复回收站问卷
    $(".recover").parent().click(function(){
      var object=$(this).children();
      var questionnaire_id=$(object).next().val();
      layer.open({
        type: 1,
        title: '是否恢复问卷',
        area: ['300px', '150px'],
        skin: 'demo-class1',
        content: $(".recoverModal")
      });
      $("#recover-yes").click(function(){
        $.ajax({
          url: "../../ques_changeStatus",
          type: "POST",
          data:{
            'q.id':questionnaire_id
          },
          success: function(data){
              $("#recycle_"+questionnaire_id).remove();
            layer.msg('恢复成功', sucess);
          },
          error: function(){
            layer.msg('访问失败', hint);
          }
        });
        $(".layui-layer-close").trigger("click");
      });
      $("#recover-no").click(function(){
        $(".layui-layer-close").trigger("click");
      });
    });
  }
	  //  显示回收站内容
$("#recycle").click(function(){
	$("tbody").empty();
	initRecycle();

	layer.open({
	  type: 1,
	  title: '回收站',
	  area: ['600px', '450px'],
	  skin: 'demo-class1',
	  content: $(".recycle")
	});
	

  });
  //超出长度显示省略号
  function displayPart(obj,length) {
    if(obj.length>length){
      obj=obj.substring(0,length)+"……";
    }
    return obj;
  }
  
  //--------------------------------------------------------------------------------加载问卷显示列表
  var clickFlag=1;//问卷状态
  var QpageNo;//问卷页码
  var QpageSize;//页大小
  var totalCount;//总数
  var totalPageCount;//总页数
  //未发布
  $("#noPublishQues").click(function(){
	  clickFlag=0;
	  $(this).parent().children("div").removeClass("strongDiv");
	  $(this).addClass("strongDiv");
	  $(".row").empty();  
	  loadQuestionnaire(clickFlag,1,10);
  });
  //已发布
  $("#publishQues").click(function(){
	  clickFlag=1;
	  $(this).parent().children("div").removeClass("strongDiv");
	  $(this).addClass("strongDiv");
	  $(".row").empty();  
	  loadQuestionnaire(clickFlag,1,10);
  });
  //点击收集完成
  $("#collected").click(function(){
	  clickFlag=2;
	  $(this).parent().children("div").removeClass("strongDiv");
	  $(this).addClass("strongDiv");
	  $(".row").empty();  
	  loadQuestionnaire(clickFlag,1,10);
  }); 
  //上一页
  $("#pageUp").click(function(){
	  $(".row").empty();  
	  loadQuestionnaire(clickFlag,QpageNo<=1?1:--QpageNo,QpageSize);
  }); 
  //下一页
  $("#pageDown").click(function(){
	  $(".row").empty();  
	  loadQuestionnaire(clickFlag,QpageNo>=totalPageCount?totalPageCount:++QpageNo,QpageSize);
  });
  //首页
  $("#firstPage").click(function(){
	  $(".row").empty();  
	  QpageNo=1;
	  loadQuestionnaire(clickFlag,QpageNo,QpageSize);
  });
  //尾页
  $("#endPage").click(function(){
	  $(".row").empty();  
	  QpageNo=totalPageCount;
	  loadQuestionnaire(clickFlag,QpageNo,QpageSize);
  });
  //按页大小转到指定页
   $("#gotoAimPage").click(function(){
		var aimPageSize=parseInt($("#pageSize").val());
		var aimPageNo=parseInt($("#aimPage").val());
	   if(!isNaN(aimPageNo)&&!isNaN(aimPageSize)){
			console.log(aimPageSize);
			console.log(aimPageNo);
			if(aimPageNo>0&&aimPageSize>0&&(aimPageNo-1)*aimPageSize<=totalCount){
				$(".row").empty();
				QpageNo=aimPageNo;
				QpageSize=aimPageSize;
				loadQuestionnaire(clickFlag,QpageNo,QpageSize); 
			}else{
				  layer.msg('请正确输入', hint);
			}
	   }
	  
  });
  
  
  function loadQuestionnaire(questionnaireStatus,pageNo,pageSize){
	  $.ajax({
			url: "../../ques_getQuestionnaireList",
			type: "POST",
			data:{
			  'q.status':questionnaireStatus,
			  'pageNo':pageNo,
			  'pageSize':pageSize
			},
			success: function(data){
				clickFlag=questionnaireStatus;
				QpageNo=pageNo;
				QpageSize=pageSize;
				totalCount=data.totalCount;
				totalPageCount=data.totalPageCount;
				$("#QpageNo").text("第"+QpageNo+"页");
				$("#totalPageCount").text("共"+totalPageCount+"页");
				$("#totalCount").text("共"+totalCount+"张问卷");
				$("#pageSize").text(QpageSize);
			  for(var i=0;i<data.items.length;i++){
				//  获取问卷列表
				var arr=data.items[i].questionnaire_name.split("$$$");
				$(".row").append(
				"<div class='col s4' id=ques_"+data.items[i].id+">"+
				  "<div class='card darken-1'>"+
					"<div class='card-content white-text'>"+
					  "<span class='card-title'>"+displayPart(arr[0],7)+"</span>"+
					  "<p>"+displayPart(arr[1],30)+"</p>"+
					  "<input type='hidden' value="+data.items[i].id+">"+
					  "<div class='contentMake'>"+
						"<i class='material-icons editorQst'>note_add<span class='warn'>编辑问卷</span></i>"+
						"<i class='material-icons questionDel'>delete<span class='warn'>删除问卷</span></i>"+
						"<i class='material-icons dataAnalysis'>insert_chart<span class='warn'>数据分析</span></i>"+
						"<i class='material-icons publishQst'>present_to_all<span class='warn'>发布问卷</span></i>"+
						"<i class='material-icons stopPublish'>play_arrow<span class='warn'>停止发布</span></i>"+
						"<i class='material-icons look'>my_location<span class='warn'>查看问卷</span></i>"+
						"<i class='material-icons link'>library_books<span class='warn'>查看链接</span></i>"+
						"<i class='material-icons continue'>music_note<span class='warn'>继续发布</span></i>"+
					  "</div>"+
					"</div>"+
				  "</div>"+
				"</div>");
			  }
			  //  判断按钮显示
			  for(var i=0;i<data.items.length;i++){
				if(data.items[i].status==0){
				  $($(".contentMake")[i]).children("i.dataAnalysis").css("display","none");
				  $($(".contentMake")[i]).children("i.stopPublish").css("display","none");
				  $($(".contentMake")[i]).children("i.link").css("display","none");
				  $($(".contentMake")[i]).children("i.continue").css("display","none");
				}
				if(data.items[i].status==1){
				  $($(".contentMake")[i]).children("i.editorQst").css("display","none");
				  $($(".contentMake")[i]).children("i.questionDel").css("display","none");
				  $($(".contentMake")[i]).children("i.publishQst").css("display","none");
				  $($(".contentMake")[i]).children("i.continue").css("display","none");
				}
				if(data.items[i].status==2){
				  $($(".contentMake")[i]).children("i.stopPublish").css("display","none");
				  $($(".contentMake")[i]).children("i.publishQst").css("display","none");
				  $($(".contentMake")[i]).children("i.link").css("display","none");
				  $($(".contentMake")[i]).children("i.editorQst").css("display","none");
				}
			  }
			  //  删除问卷
			  $(".questionDel").click(function(){
				layer.open({
				  type: 1,
				  title: '是否删除',
				  area: ['300px', '150px'],
				  skin: 'demo-class1',
				  content: $(".modalDel")
				});
				var questionnaire_id=$(this).parent().prev().val();
				$("#question-delete-yes").click(function(){
				  $.ajax({
					url: "../../ques_deleteQuestionnaire",
					type: "POST",
					data:{
					  'q.id':questionnaire_id,
					  'isRecycle':true
					},
					success: function(data){
						$("#ques_"+questionnaire_id).remove();
						layer.msg('删除成功', sucess);
					},
					error: function(){
					  layer.msg('删除失败', hint);
					}
				  });
				   $(".layui-layer-close").trigger("click");
				});
				$("#question-delete-no").click(function(){
				  $(".layui-layer-close").trigger("click");
				});
			  });
			  //  编辑问卷---
			  $(".editorQst").click(function(){
				var questionnaire_id=$(this).parent().prev().val();
				window.open("question.html?id="+questionnaire_id);
			  });
			  //  发布问卷
			  $(".publishQst").click(function(){
				var questionnaire_id=$(this).parent().prev().val();
				$.ajax({
				  url: "../../archer/ques_changeStatus",
				  type: "POST",
				  data:{
					'q.id':questionnaire_id
				  },
				  success: function(data){
					$("#ques_"+questionnaire_id).remove();
					window.open("publish.html?id="+questionnaire_id);
				  },
				  error: function(){
					layer.msg('发布失败', hint);
				  }
				});
			  });
			  //` 停止发布
			  $(".stopPublish").click(function(){
				var questionnaire_id=$(this).parent().prev().val();
				$.ajax({
				  url: "../../ques_changeStatus",
				  type: "POST",
				  data:{
					'q.id':questionnaire_id
				  },
				  success: function(data){
					$("#ques_"+questionnaire_id).remove();
					layer.msg('停止成功', sucess);
				  },
				  error: function(){
					layer.msg('访问失败', hint);
				  }
				});
			  });
			  //继续发布
			  $(".continue").click(function(){
				var questionnaire_id=$(this).parent().prev().val();
				$.ajax({
				  url: "../../ques_changeStatus",
				  type: "POST",
				  data:{
					'q.id':questionnaire_id
				  },
				  success: function(data){
					$("#ques_"+questionnaire_id).remove();
					layer.msg('成功回到发布状态，可继续填写', sucess);
				  },
				  error: function(){
					layer.msg('访问失败', hint);
				  }
				});
			  });
			  //  查看问卷
			  $(".look").click(function(){
				var questionnaire_id=$(this).parent().prev().val();
				window.open("myQuestionnaire.html?id="+questionnaire_id+"&look");
			  });
			  //  查看链接
			  $(".link").click(function(){
				var questionnaire_id=$(this).parent().prev().val();
				window.open("publish.html?id="+questionnaire_id);
			  });
			  //  查看数据分析
			  $(".dataAnalysis").click(function(){
				var questionnaire_id=$(this).parent().prev().val();
				window.open("dataHighCharts.html?id="+questionnaire_id);
			  });
			},
			error: function(){
			  layer.msg('访问失败', hint);
			}
		  });
		 
  }
  //--------------------------------------------------------------------------------
  
  
  loadQuestionnaire(0,1,10);
  
   //	新建问卷跳转
  $(".addButton").click(function(){
    window.open("question.html");
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
  //  跳转到模板页面
  $("#Template").click(function(){
    window.open("Template.html");
  });
});