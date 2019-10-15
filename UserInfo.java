package cn.ipays.beans;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String custNo;
	private String token;
	private String cName;
	private String eName;
	private String alias;
	private String mobilePhone;
	private String eMail;
	private String sex;
	private String ctfType;
	private String ctfNo;
	private String status;
	private String authStatus;
	private String level;
	private String wxNo;
	private String address;
	private List<AccInfo> accInfos;
	
	
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCtfType() {
		return ctfType;
	}
	public void setCtfType(String ctfType) {
		this.ctfType = ctfType;
	}
	public String getCtfNo() {
		return ctfNo;
	}
	public void setCtfNo(String ctfNo) {
		this.ctfNo = ctfNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWxNo() {
		return wxNo;
	}
	public void setWxNo(String wxNo) {
		this.wxNo = wxNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<AccInfo> getAccInfos() {
		return accInfos;
	}
	public void setAccInfos(List<AccInfo> accInfos) {
		this.accInfos = accInfos;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
