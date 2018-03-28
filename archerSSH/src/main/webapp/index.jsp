<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Archer问卷</h2>
<form action="ques_createQuestionnaire" method="post">
	name：<input type="text" name="q.questionnaire_name"/>
	content：<input type="text" name="q.questionnaire_content"/>
	<input type="submit" value="创建问卷">
</form>
</body>
</html>
