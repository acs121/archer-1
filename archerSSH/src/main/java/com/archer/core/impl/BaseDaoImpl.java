package com.archer.core.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.archer.core.dao.BaseDao;
import com.archer.core.utils.PageResult;
import com.archer.core.utils.QueryHelper;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{

	Class<T> clazz;
	
	public BaseDaoImpl(){
		//��ȡ���Ͳ�����ֵ
		ParameterizedType pt =  (ParameterizedType)this.getClass().getGenericSuperclass();//BaseDaoImpl<User>
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
	}
	@Resource(name="sessionFactory")  
	public void setSessionFacotry(SessionFactory sessionFacotry) {  
	         super.setSessionFactory(sessionFacotry);   
	}
	
	
	/**
	 * ����ʵ��
	 */
	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}
	/**
	 * ����ʵ��
	 */
	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findObjectById(id));
	}

	@Override
	public T findObjectById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findObjects() {
		return currentSession().createQuery("from "+clazz.getSimpleName()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findObjects(String hql, List<Object> parameters) {
		Query q=currentSession().createQuery(hql);
		for(int i=0;i<(parameters==null?0:parameters.size());i++){
			q.setParameter(i, parameters.get(i));
		}
		return q.list();
	}
	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		Query query = currentSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}

	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
		Query query = currentSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		if(pageNo < 1) pageNo = 1;
		
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List items = query.list();
		Query queryCount = currentSession().createQuery(queryHelper.getQueryCountHql());
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				queryCount.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (Long)queryCount.uniqueResult();
		
		return new PageResult(totalCount, pageNo, pageSize, items);
	}

}
