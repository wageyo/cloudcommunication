package com.esd.cloudcommunication.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.cloudcommunication.bean.ResultModel;
import com.esd.cloudcommunication.bean.Vote;
import com.esd.cloudcommunication.common.Constants;
import com.esd.cloudcommunication.common.PaginationRecordsAndNumber;
import com.esd.cloudcommunication.dao.VoteDao;
import com.esd.cloudcommunication.service.KitService;
import com.esd.cloudcommunication.service.VoteService;

/**
 * 电话投票service 实现类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 */
@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteDao dao;

	@Override
	public Boolean save(Vote t) {
		if (t.getId() == null || "".equals(t.getId())) {
			t.setId(KitService.getRandomPrimaryKey());
		}
		return dao.insertSelective(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean delete(String id) {
		return dao.deleteByPrimaryKey(id) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean update(Vote t) {
		return dao.updateByPrimaryKey(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Vote getByPrimaryKey(String id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public Vote getbyPhoneAndProjectno(String fromCalling, String projectno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromCalling", fromCalling);
		map.put("projectno", projectno);
		return dao.retrieveByCallingAndProjectno(map);
	}

	@Override
	public PaginationRecordsAndNumber<Vote, Number> getPaginationRecords(
			Vote t, Integer page, Integer pageSize) {
		if (page == null || page <= 1) {
			page = Constants.PAGE_START;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = Constants.PAGE_SIZE;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vote", t);
		map.put(Constants.START, page);
		map.put(Constants.SIZE, pageSize);
		PaginationRecordsAndNumber<Vote, Number> prn = new PaginationRecordsAndNumber<Vote, Number>();
		prn.setNumber(dao.retrieveCount(map));
		prn.setRecords(dao.retrieveByPage(map));
		return prn;
	}

	@Override
	public List<ResultModel> getResult() {
		return dao.retrieveResult();
	}

}
