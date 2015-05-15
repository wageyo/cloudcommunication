package com.esd.cloudcommunication.bean;

import java.util.Date;

/**
 * 主键
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 */
public class PrimaryKey {

	private String id;
	private Date createTime;// 数据创建时间
	private Date updateTime;// 数据更新时间
	private Integer updateCheck;

	@Override
	public String toString() {
		return "PrimaryKey [id=" + id + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", updateCheck=" + updateCheck
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateCheck() {
		return updateCheck;
	}

	public void setUpdateCheck(Integer updateCheck) {
		this.updateCheck = updateCheck;
	}

}
