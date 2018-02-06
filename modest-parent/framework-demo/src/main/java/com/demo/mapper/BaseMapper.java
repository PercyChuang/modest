package com.demo.mapper;


import java.util.List;

public interface BaseMapper<T> {
    int SUCCESS = 1;

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
    public Integer add(T objects);

    /**
     * 更新
     * @param objects
     * @return
     */
    public Integer update(T objects);

    /**
     * 更新
     * @param objects
     * @return
     */
    public void updateWithEmpty(T objects);

    /**
     * 根据主键删除对象
     * @param id
     * @return
     */
    public void deleteByPrimaryKey(Object id);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    public T getByPrimaryKey(Object id);

    /**
     * 条件查询
     * @param objects
     * @return
     */
    public List<T> list(T objects);

    /**
     * 条件查询
     * @param objects
     * @return
     */
    public T query(T objects);



}
