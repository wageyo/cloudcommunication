package com.esd.cloudcommunication.service;

import java.util.List;

import com.esd.cloudcommunication.bean.ResultModel;
import com.esd.cloudcommunication.bean.Vote;

/**
 * 投票service接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 */
public interface VoteService extends BaseService<Vote> {

	/**
	 * 根据来电 和 项目编号 查询该项目是否投过票~~~
	 * 
	 * @param fromCalling
	 * @param projectno
	 * @return
	 */
	public Vote getbyPhoneAndProjectno(String fromCalling, String projectno);
	
	/**
	 * 得到投票结果
	 * @return
	 */
	public List<ResultModel> getResult();
}
