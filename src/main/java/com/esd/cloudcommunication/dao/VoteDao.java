package com.esd.cloudcommunication.dao;

import java.util.List;
import java.util.Map;

import com.esd.cloudcommunication.bean.ResultModel;
import com.esd.cloudcommunication.bean.Vote;

/**
 * 投票dao接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 */
public interface VoteDao extends BaseDao<Vote> {

	/**
	 * 根据来电电话号码和项目编号, 验证该手机号是否给该项目投过票
	 * 
	 * @param map
	 * @return
	 */
	public Vote retrieveByCallingAndProjectno(Map<String, Object> map);
	
	/**
	 * 查询投票结果
	 * @return
	 */
	public List<ResultModel> retrieveResult();
}