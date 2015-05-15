package com.esd.cloudcommunication.bean;

/**
 * 呼叫电话pojo类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-11
 */
public class Calling extends PrimaryKey {

	private String fromCalling;// 主叫电话号码
	private String toCalling;// 被叫电话号码
	private Boolean direction;// 呼叫方向，取值有0（呼入），1（呼出）
	private String appid;// 应用id
	private String verificationCode;// 验证码
	private String projectno;// 投票的项目编号
	private Boolean isSuccess; // 投票是否成功
	private Integer verificationTry;	// 尝试验证码次数
	private Integer projectnoTry;	//项目编号尝试次数
	private String remark; // 备注信息

	@Override
	public String toString() {
		return "Calling [fromCalling=" + fromCalling + ", toCalling="
				+ toCalling + ", direction=" + direction + ", appid=" + appid
				+ ", verificationCode=" + verificationCode + ", projectno="
				+ projectno + ", isSuccess=" + isSuccess + ", remark=" + remark
				+ "]";
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

	public Boolean getDirection() {
		return direction;
	}

	public void setDirection(Boolean direction) {
		this.direction = direction;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getProjectno() {
		return projectno;
	}

	public void setProjectno(String projectno) {
		this.projectno = projectno;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public Integer getVerificationTry() {
		return verificationTry;
	}

	public void setVerificationTry(Integer verificationTry) {
		this.verificationTry = verificationTry;
	}

	public Integer getProjectnoTry() {
		return projectnoTry;
	}

	public void setProjectnoTry(Integer projectnoTry) {
		this.projectnoTry = projectnoTry;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
