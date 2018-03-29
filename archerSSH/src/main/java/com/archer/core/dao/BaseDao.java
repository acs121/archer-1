package com.archer.core.dao;

import java.io.Serializable;
import java.util.List;

import com.archer.core.utils.PageResult;
import com.archer.core.utils.QueryHelper;


public interface BaseDao<T> {
	/**
	 * ����
	 * @param entity
	 */
	public void save(T entity);
	/**
	 * ����
	 * @param entity
	 */
	public void update(T entity);
	/**
	 * ����idɾ��
	 * @param id
	 */
	public void delete(Serializable id);
	/**
	 * ����id����
	 * @param id
	 * @return
	 */
	public T findObjectById(Serializable id);
	/***
	 * �����б�
	 * @return
	 */
	public List<T> findObjects();
	/**
	 * ������ѯʵ���б�
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<T> findObjects(String hql, List<Object> parameters);
	/**
	 * ������ѯʵ���б�--��ѯ����queryHelper
	 * @param queryHelper
	 * @return
	 */
	public List<T> findObjects(QueryHelper queryHelper);
	/**
	 * ��ҳ������ѯʵ���б�--��ѯ����queryHelper
	 * @param queryHelper
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);

}
