package com.esd.cloudcommunication.bean;

/**
 * 投票结果集
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-29
 */
public class ResultModel {

	private String projectno; // 项目编号
	private Integer nums; // 票数
	private String projectname; // 项目名称

	@Override
	public String toString() {
		return "ResultModel [projectno=" + projectno + ", nums=" + nums
				+ ", projectname=" + projectname + "]";
	}

	public String getProjectno() {
		return projectno;
	}

	public void setProjectno(String projectno) {
		this.projectno = projectno;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

}
