package com.esd.cloudcommunication.dao;

import com.esd.cloudcommunication.bean.Project;

/**
 * 项目编号dao接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-22
 */
public interface ProjectDao extends BaseDao<Project> {

	/**
	 * 根据项目编号获得一个项目对象
	 * @param projectno
	 * @return
	 */
	public Project retrieveByProjectno(String projectno);
}