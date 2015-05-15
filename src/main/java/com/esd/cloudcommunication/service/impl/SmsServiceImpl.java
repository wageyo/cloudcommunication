package com.esd.cloudcommunication.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.cloudcommunication.bean.Sms;
import com.esd.cloudcommunication.common.Constants;
import com.esd.cloudcommunication.common.PaginationRecordsAndNumber;
import com.esd.cloudcommunication.dao.SmsDao;
import com.esd.cloudcommunication.service.SmsService;

/**
 * 短信验证码service实现类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-15
 */
@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private SmsDao dao;

	@Override
	public Boolean save(Sms t) {
		// 生成唯一id
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		t.setId(uuid);
		return dao.insertSelective(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean delete(String id) {
		return dao.deleteByPrimaryKey(id) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean update(Sms t) {
		return dao.updateByPrimaryKey(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Sms getByPrimaryKey(String id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<Sms, Number> getPaginationRecords(Sms t,
			Integer page, Integer pageSize) {
		if (page == null || page <= 1) {
			page = Constants.PAGE_START;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = Constants.PAGE_SIZE;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sms", t);
		map.put(Constants.START, page);
		map.put(Constants.SIZE, pageSize);
		PaginationRecordsAndNumber<Sms, Number> prn = new PaginationRecordsAndNumber<Sms, Number>();
		prn.setNumber(dao.retrieveCount(map));
		prn.setRecords(dao.retrieveByPage(map));
		return prn;
	}

}
