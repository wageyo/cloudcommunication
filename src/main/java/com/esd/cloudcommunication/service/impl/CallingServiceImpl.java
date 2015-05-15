package com.esd.cloudcommunication.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.cloudcommunication.bean.Calling;
import com.esd.cloudcommunication.common.Constants;
import com.esd.cloudcommunication.common.PaginationRecordsAndNumber;
import com.esd.cloudcommunication.dao.CallingDao;
import com.esd.cloudcommunication.service.CallingService;

/**
 * 电话投票service 实现类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 */
@Service
public class CallingServiceImpl implements CallingService {

	@Autowired
	private CallingDao dao;

	@Override
	public Boolean save(Calling t) {
		return dao.insertSelective(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean delete(String id) {
		return dao.deleteByPrimaryKey(id) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean update(Calling t) {
		return dao.updateByPrimaryKey(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Calling getByPrimaryKey(String id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<Calling, Number> getPaginationRecords(
			Calling t, Integer page, Integer pageSize) {
		if (page == null || page <= 1) {
			page = Constants.PAGE_START;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = Constants.PAGE_SIZE;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("calling", t);
		map.put(Constants.START, page);
		map.put(Constants.SIZE, pageSize);
		PaginationRecordsAndNumber<Calling, Number> prn = new PaginationRecordsAndNumber<Calling, Number>();
		prn.setNumber(dao.retrieveCount(map));
		prn.setRecords(dao.retrieveByPage(map));
		return prn;
	}

}
