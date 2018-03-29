package com.archer.core.dao;

import java.io.Serializable;
import java.util.List;

import com.archer.core.utils.PageResult;
import com.archer.core.utils.QueryHelper;


public interface BaseDao<T> {
	/**
	 * 新增
	 * @param entity
	 */
	public void save(T entity);
	/**
	 * 更新
	 * @param entity
	 */
	public void update(T entity);
	/**
	 * 根据id删除
	 * @param id
	 */
	public void delete(Serializable id);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public T findObjectById(Serializable id);
	/***
	 * 查找列表
	 * @return
	 */
	public List<T> findObjects();
	/**
	 * 条件查询实体列表
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<T> findObjects(String hql, List<Object> parameters);
	/**
	 * 条件查询实体列表--查询助手queryHelper
	 * @param queryHelper
	 * @return
	 */
	public List<T> findObjects(QueryHelper queryHelper);
	/**
	 * 分页条件查询实体列表--查询助手queryHelper
	 * @param queryHelper
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);

}
