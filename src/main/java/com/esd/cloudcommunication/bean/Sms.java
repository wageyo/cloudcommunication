package com.esd.cloudcommunication.bean;

/**
 * 短信验证码pojo类
 * @author yufu
 * @email ilxly01@126.com
 * 2015-2-15
 */
public class Sms extends PrimaryKey {

	private String cellNumber; //接收验证码手机号码
	private String verifyCode; //验证码
	private Integer timeLimit; //有效分钟数
	private String remark; //接收验证码手机号码
	@Override
	public String toString() {
		return "Sms [cellNumber=" + cellNumber + ", verifyCode=" + verifyCode
				+ ", timeLimit=" + timeLimit + ", remark=" + remark + "]";
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
