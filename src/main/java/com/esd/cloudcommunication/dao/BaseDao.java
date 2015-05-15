package com.esd.cloudcommunication.dao;

import java.util.List;
import java.util.Map;

/**
 * 公用dao接口 public dao interface
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 * @param <T>
 */
public interface BaseDao<T> {

	/**
	 * insertSelective ：有对对象的属性进行非空判断处理, 即只有非空字段才能插入
	 * 
	 * @param t
	 * @return
	 */
	int insertSelective(T t);

	/**
	 * delete : 按主键删除一条数据
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * update : 根据id, 更新一条数据
	 * 
	 * @param t
	 * @return
	 */
	int updateByPrimaryKey(T t);

	/**
	 * retrieveByPrimaryKey : 根据id, 查询一条数据
	 * 
	 * @param id
	 * @return
	 */
	T retrieveByPrimaryKey(String id);

	/**
	 * retrieveByPage : 分页查询
	 * 
	 * @param map
	 *            map中放入对象的类名首字母小写, 起始索引(start), 返回量{size}: 例：查询呼叫类(Calling)的10条数据, 参数应为:
	 *            map.put("calling",calling); 
	 *            map.put("start",0);
	 *            map.put("size",10);
	 * @return
	 */
	List<T> retrieveByPage(Map<String, Object> map);

	/**
	 * retrieveCount--provided to the paging query uses, using the same params
	 * 查询数据总条数, 提供给分页查询用, 参数和分页查询一样
	 * 
	 * @param map
	 * @return
	 */
	int retrieveCount(Map<String, Object> map);
}
