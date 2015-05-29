package com.esd.cloudcommunication.bean;

/**
 * 项目编号pojo类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-22
 */
public class Project extends PrimaryKey {

	private String projectno; // 项目编号
	private String name;// 项目名称
	private String description;// 项目描述
	private String remark; // 备注信息

	@Override
	public String toString() {
		return "Projectno [projectno=" + projectno + ", name=" + name
				+ ", description=" + description + ", remark=" + remark + "]";
	}

	public String getProjectno() {
		return projectno;
	}

	public void setProjectno(String projectno) {
		this.projectno = projectno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
