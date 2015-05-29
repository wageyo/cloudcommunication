package com.esd.cloudcommunication.bean;

/**
 * 投票pojo类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-22
 */
public class Vote extends PrimaryKey {

	private String callid; // 如果为电话投票~~
	private String fromCalling;// 主叫电话号码
	private String toCalling;// 被叫电话号码
	private String verificationCode;// 验证码
	private String verificationCodeDeadline;// 验证码有效截止时间
	private String projectno;// 投票的项目编号
	private String type;// 投票方式, 网络, 电话或短信
	private Boolean isSuccess; // 投票是否成功
	private String remark; // 备注信息

	@Override
	public String toString() {
		return "Vote [callid=" + callid + ", fromCalling=" + fromCalling
				+ ", toCalling=" + toCalling + ", verificationCode="
				+ verificationCode + ", verificationCodeDeadline="
				+ verificationCodeDeadline + ", projectno=" + projectno
				+ ", type=" + type + ", isSuccess=" + isSuccess + ", remark="
				+ remark + "]";
	}

	public String getCallid() {
		return callid;
	}

	public void setCallid(String callid) {
		this.callid = callid;
	}

	public String getFromCalling() {
		return fromCalling;
	}

	public void setFromCalling(String fromCalling) {
		this.fromCalling = fromCalling;
	}

	public String getToCalling() {
		return toCalling;
	}

	public void setToCalling(String toCalling) {
		this.toCalling = toCalling;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getVerificationCodeDeadline() {
		return verificationCodeDeadline;
	}

	public void setVerificationCodeDeadline(String verificationCodeDeadline) {
		this.verificationCodeDeadline = verificationCodeDeadline;
	}

	public String getProjectno() {
		return projectno;
	}

	public void setProjectno(String projectno) {
		this.projectno = projectno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
