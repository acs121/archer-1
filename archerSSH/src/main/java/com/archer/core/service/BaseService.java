package com.archer.core.service;

import java.io.Serializable;
import java.util.List;

import com.archer.core.utils.PageResult;
import com.archer.core.utils.QueryHelper;


public interface BaseService<T> {
	//����
	public void save(T entity);
	//����
	public void update(T entity);
	//����idɾ��
	public void delete(Serializable id);
	//����id����
	public T findObjectById(Serializable id);
	//�����б�
	public List<T> findObjects();
	//������ѯʵ���б�
	@Deprecated
	public List<T> findObjects(String hql, List<Object> parameters);
	//������ѯʵ���б�--��ѯ����queryHelper
	public List<T> findObjects(QueryHelper queryHelper);
	//��ҳ������ѯʵ���б�--��ѯ����queryHelper
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);

}
