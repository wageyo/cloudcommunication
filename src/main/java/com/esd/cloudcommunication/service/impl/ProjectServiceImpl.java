package com.esd.cloudcommunication.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.cloudcommunication.bean.Project;
import com.esd.cloudcommunication.common.Constants;
import com.esd.cloudcommunication.common.PaginationRecordsAndNumber;
import com.esd.cloudcommunication.dao.ProjectDao;
import com.esd.cloudcommunication.service.KitService;
import com.esd.cloudcommunication.service.ProjectService;

/**
 * 项目编号service 实现接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-22
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao dao;
	
	@Override
	public Boolean save(Project t) {
		if(t.getId() == null || "".equals(t.getId())){
			t.setId(KitService.getRandomPrimaryKey());
		}
		return dao.insertSelective(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean delete(String id) {
		return dao.deleteByPrimaryKey(id) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean update(Project t) {
		return dao.updateByPrimaryKey(t) == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Project getByPrimaryKey(String id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public Project getByProjectno(String projectno) {
		Project project = dao.retrieveByProjectno(projectno);
		return project;
	}

	@Override
	public PaginationRecordsAndNumber<Project, Number> getPaginationRecords(
			Project t, Integer page, Integer pageSize) {
		if (page == null || page <= 1) {
			page = Constants.PAGE_START;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = Constants.PAGE_SIZE;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", t);
		map.put(Constants.START, page);
		map.put(Constants.SIZE, pageSize);
		PaginationRecordsAndNumber<Project, Number> prn = new PaginationRecordsAndNumber<Project, Number>();
		prn.setNumber(dao.retrieveCount(map));
		prn.setRecords(dao.retrieveByPage(map));
		return prn;
	}

}
