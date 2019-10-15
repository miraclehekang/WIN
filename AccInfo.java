package cn.ipays.beans;

import java.io.Serializable;

public class AccInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String custType;
	private String totalCount;
	private String custName;
	private String custNo;
	private String accNo;
	private String accType;
	private String ccy;
	private String openDate;
	private String status;
	private String bailAmt;
	private String availAmt;
	private String blockAmt;
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBailAmt() {
		return bailAmt;
	}
	public void setBailAmt(String bailAmt) {
		this.bailAmt = bailAmt;
	}
	public String getAvailAmt() {
		return availAmt;
	}
	public void setAvailAmt(String availAmt) {
		this.availAmt = availAmt;
	}
	public String getBlockAmt() {
		return blockAmt;
	}
	public void setBlockAmt(String blockAmt) {
		this.blockAmt = blockAmt;
	}
	
	
	
}
