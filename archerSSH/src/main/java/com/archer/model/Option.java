/** 
 * Project Name:archerSSH 
 * File Name:Option.java 
 * Package Name:com.archer.model 
 * Date:2018年4月22日下午4:44:38 
 * Copyright (c) 2018, 1255903774@qq.com All Rights Reserved. 
 * 
 */  

package com.archer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 选项信息（配合highcharts的格式）
 * @author Kunjin Guo
 */
public class Option {
	private String name;
	private List<Integer> data;
	
	public Option(String name, Integer integer) {
		super();
		this.name = name;
		this.data=new ArrayList<Integer>();
		data.add(integer);
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
