package com.archer.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;

import com.archer.model.User;
import com.archer.service.UserService;
import com.archer.utils.ValiImg;

public class UserAction implements SessionAware{
	private Map<String, Object> session;
	@Resource
	private ValiImg code;//生成验证码工具
	@Resource
	private UserService userService;//user业务逻辑处理
	
	private String type;//验证码类型
	private InputStream source;//验证码返回流
	private Map<String,Object> jsonData;//返回的json数据
	private User user;//前台传来的user参数
	private String authCode;
	/**
	 * 验证码获取请求<br>
	 * 参数需要：type--验证码类型（loginCode/registCode）<br>
	 * @return 输入流 in
	 * @throws IOException
	 */
	public String codeImg() throws IOException{
		if(type!=null){//验证码类型：目前只用于两处①登录②注册
			source=code.getIn();//生成验证码图片
			if(type.equals("loginCode")){
				session.put(type, code.getAuthCode());//写入生成验证码的值用于判断用户是否输入正确
			}
			if(type.equals("registCode")){
				session.put(type, code.getAuthCode());
			}
		}
		return "png";
	}
	/**
	 * 注册用户请求<br>
	 * 参数需要：user.username、user.password、user.nickname、authCode<br>
	 * 
	 * @return json数据 jsonData
	 */
	public String regist(){
		jsonData=new HashMap<String, Object>();
		//验证数据是否为空
		String registCode=(String) session.get("registCode");
		if(authCode!=null&&registCode!=null&&registCode.equalsIgnoreCase(authCode)){
			if(user.getNickname()!=null&&user.getPassword()!=null&&user.getUsername()!=null){
				//如果数据库中不存在用户，则允许注册
				if(userService.findObjectById(user.getUsername())==null){
					userService.save(user);
					jsonData.put("status", true);
					jsonData.put("userExist", false);
					jsonData.put("codeIsOK", true);
				}else{//用户存在不允许注册
					jsonData.put("status", false);
					jsonData.put("userExist", true);
					jsonData.put("codeIsOK", true);
				}
			}else{
				jsonData.put("status", false);
				jsonData.put("userExist", true);
				jsonData.put("codeIsOK", true);
			}
		}else{
			jsonData.put("status", false);
			jsonData.put("codeIsOK", false);
		}
		session.remove("registCode");
		return "json";
	}
	/**
	 * 用户登录请求<br>
	 * 参数需要：user.username、user.password、authCode<br>
	 * 
	 * @return json数据 jsonData 在session中添加user信息
	 */
	public String login(){
		jsonData=new HashMap<String, Object>();
		String loginCode=(String) session.get("loginCode");
		if(authCode!=null&&loginCode!=null&&loginCode.equalsIgnoreCase(authCode)){
			if(user.getUsername()!=null&&user.getPassword()!=null){
				//用户不存在
				if((user=userService.findObjectById(user.getUsername()))==null){
					jsonData.put("status", false);
					jsonData.put("userExist", false);
					jsonData.put("codeIsOK", true);
				}else{//登录成功
					jsonData.put("status", true);
					jsonData.put("userExist", true);
					jsonData.put("codeIsOK", true);
					session.put("user", user);
				}
			}else{
				jsonData.put("status", false);
				jsonData.put("userExist", false);
				jsonData.put("codeIsOK", true);
			}
		}else{//验证码错误
			jsonData.put("status", false);
			jsonData.put("codeIsOK", false);
		}
		session.remove("loginCode");
		return "json";
	}
	/**
	 * 用户登出操作<br>
	 * 参数需要：<br>
	 * 
	 * @return json数据 jsonData
	 */
	public String logout() {
		session.remove("user");
		jsonData=new HashMap<String, Object>();
		jsonData.put("status", true);
		return "json";
	}
	public String nickname() {
		User u=(User)session.get("user");
		jsonData=new HashMap<String, Object>();
		jsonData.put("nickname", u.getNickname());
		return "json";
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public void setSession(Map<String, Object> arg0) {
		session=arg0;
	}
	public Map<String, Object> getRespData() {
		return jsonData;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public InputStream getSource() {
		return source;
	}
	public void setSource(InputStream source) {
		this.source = source;
	}
}
