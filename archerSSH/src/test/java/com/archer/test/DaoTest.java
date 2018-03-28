package com.archer.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.archer.core.utils.PageResult;
import com.archer.model.Questionnaire;
import com.archer.model.User;
import com.archer.service.QuestionnaireService;
import com.archer.service.UserService;
import com.archer.utils.QuestionnaireUtils;



public class DaoTest {
	public static void main(String[] args) {
//		Configuration cfg=new Configuration().configure();
//		Session s=cfg.buildSessionFactory().openSession();
//		s.beginTransaction();
//		System.out.println(s.createQuery("SELECT COUNT(*) FROM Questionnaire q WHERE q.user.username=?").setParameter(0, "aaa").uniqueResult());
//		
//		
//		s.getTransaction().commit();
//		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
//		QuestionnaireService qs=(QuestionnaireService) ctx.getBean("questionnaireService");
//		UserService us=ctx.getBean("userService",UserService.class);
//		User user=new User();
//		user.setUsername("joker");
//		user.setPassword("123456");
//		user.setNickname("haha");
//		us.save(user);
		Questionnaire q=new Questionnaire();
		q.setQuestionnaire_name("问卷测试");
		q.setQuestionnaire_content("[{'题目':'当代大学生为何这么无能','类型':1,'选项':['1asdd','2asdds','asss']},{'题目':'aiaia','类型':2,'选项':['1asas','2爱的ds','assd','saasd']},{'题目':'写一个作文','类型':3}]");
		List<String> ans= QuestionnaireUtils.cheakAnswerFormatIsRight(q.getQuestionnaire_content(),"['1','1','adsd456']");
		System.out.println(ans);

	}
}
