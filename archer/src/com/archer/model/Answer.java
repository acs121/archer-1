package com.archer.model;

public class Answer {
	private String id;
	private String st;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public Answer(){
	}
	public Answer(String id,String st){
		this.id=id;
		this.st=st;
	}
}
