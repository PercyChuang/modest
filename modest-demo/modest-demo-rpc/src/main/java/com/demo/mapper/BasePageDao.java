package com.demo.mapper;

import java.util.List;

/**
 * 
 * @描述：分页基础dao
 *
 * @author yangyi
 * @时间  2016年8月9号
 *
 */
public interface BasePageDao<T>{

	/**
	 * 批量新增
	 * @param 
	 * @return
	 */
	public Integer addBatch(List<T> list);
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	public Integer updateBatch(List<T> list);
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	public Integer updateBatchWithEmpty(List<T> list);
	
	/**
	 * 批量删除
	 * @param objects
	 * @return
	 */
	public Integer deleteBatch(Object[] objects);
	
	/**
	 * 新增
	 * @param objects
	 * @return
	 */
	public void add(T t);
	
	/**
	 * 更新
	 * @param objects
	 * @return
	 */
	public void update(T t);
	
	/**
	 * 更新
	 * @param objects
	 * @return
	 */
	public void updateWithEmpty(T t);

	/**
	 * 根据主键删除对象
	 * @param objects
	 * @return
	 */
	public void deleteByPrimaryKey(Object id);
	
	/**
	 * 根据主键获取对象
	 * @param objects
	 * @return
	 */
	public T getByPrimaryKey(Object id);

	/**
	 * 条件查询
	 * @param objects
	 * @return
	 */
	public List<T> list(T t);
	
	/**
	 * 条件查询
	 * @param objects
	 * @return
	 */
	public T query(T t);

}
