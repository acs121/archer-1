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
	private ValiImg code;//������֤�빤��
	@Resource
	private UserService userService;//userҵ���߼�����
	
	private String type;//��֤������
	private InputStream source;//��֤�뷵����
	private Map<String,Object> jsonData;//���ص�json����
	private User user;//ǰ̨������user����
	private String authCode;
	/**
	 * ��֤���ȡ����<br>
	 * ������Ҫ��type--��֤�����ͣ�loginCode/registCode��<br>
	 * @return ������ in
	 * @throws IOException
	 */
	public String codeImg() throws IOException{
		if(type!=null){//��֤�����ͣ�Ŀǰֻ���������ٵ�¼��ע��
			source=code.getIn();//������֤��ͼƬ
			if(type.equals("loginCode")){
				session.put(type, code.getAuthCode());//д��������֤���ֵ�����ж��û��Ƿ�������ȷ
			}
			if(type.equals("registCode")){
				session.put(type, code.getAuthCode());
			}
		}
		return "png";
	}
	/**
	 * ע���û�����<br>
	 * ������Ҫ��user.username��user.password��user.nickname��authCode<br>
	 * 
	 * @return json���� jsonData
	 */
	public String regist(){
		jsonData=new HashMap<String, Object>();
		//��֤�����Ƿ�Ϊ��
		String registCode=(String) session.get("registCode");
		if(authCode!=null&&registCode!=null&&registCode.equalsIgnoreCase(authCode)){
			if(user.getNickname()!=null&&user.getPassword()!=null&&user.getUsername()!=null){
				//������ݿ��в������û���������ע��
				if(userService.findObjectById(user.getUsername())==null){
					userService.save(user);
					jsonData.put("status", true);
					jsonData.put("userExist", false);
					jsonData.put("codeIsOK", true);
				}else{//�û����ڲ�����ע��
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
	 * �û���¼����<br>
	 * ������Ҫ��user.username��user.password��authCode<br>
	 * 
	 * @return json���� jsonData ��session�����user��Ϣ
	 */
	public String login(){
		jsonData=new HashMap<String, Object>();
		String loginCode=(String) session.get("loginCode");
		if(authCode!=null&&loginCode!=null&&loginCode.equalsIgnoreCase(authCode)){
			if(user.getUsername()!=null&&user.getPassword()!=null){
				//�û�������
				if((user=userService.findObjectById(user.getUsername()))==null){
					jsonData.put("status", false);
					jsonData.put("userExist", false);
					jsonData.put("codeIsOK", true);
				}else{//��¼�ɹ�
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
		}else{//��֤�����
			jsonData.put("status", false);
			jsonData.put("codeIsOK", false);
		}
		session.remove("loginCode");
		return "json";
	}
	/**
	 * �û��ǳ�����<br>
	 * ������Ҫ��<br>
	 * 
	 * @return json���� jsonData
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
