package com.archer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name="questionnaire")
public class Questionnaire {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String questionnaire_name;
	private String questionnaire_content;
	private int status;
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="u_id",referencedColumnName="username")
	private User user;
	@OneToMany(targetEntity=Answer.class,mappedBy="questionnaire")
	private Set<Answer> answers=new HashSet<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestionnaire_name() {
		return questionnaire_name;
	}
	public void setQuestionnaire_name(String questionnaire_name) {
		this.questionnaire_name = questionnaire_name;
	}
	@JSON(serialize=false) 
	public String getQuestionnaire_content() {
		return questionnaire_content;
	}
	public void setQuestionnaire_content(String questionnaire_content) {
		this.questionnaire_content = questionnaire_content;
	}
	@JSON(serialize=false) 
	public Set<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	public int getStatus() {
		return status;
	}
	/**
	 * 0-未发布，1-发布（可填写问卷），2-收集（不可填写），3-回收站状态
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	@JSON(serialize=false) 
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
