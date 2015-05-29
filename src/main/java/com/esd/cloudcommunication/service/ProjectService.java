package com.esd.cloudcommunication.service;

import com.esd.cloudcommunication.bean.Project;

/**
 * 项目编号 service接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 */
public interface ProjectService extends BaseService<Project> {

	/**
	 * 根据项目编号查询项目对象
	 * 
	 * @param project
	 * @return
	 */
	public Project getByProjectno(String projectno);
}
