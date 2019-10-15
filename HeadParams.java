package cn.ipays.beans;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.ipays.commons.Consts;
import cn.ipays.test.IdUtilVo;
import cn.ipays.utils.DateUtil;
import cn.ipays.utils.IpaysUtils;
import cn.ipays.utils.JsonMd5Utils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;

public class HeadParams implements Serializable{
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(HeadParams.class);
	
	private String svcName;
	private String branchId = Consts.WALLET_PARAMS.BRANCH_ID;
	private String merchArea = Consts.WALLET_PARAMS.MERCH_AREA;
	private String tranDate = DateUtil.formateDate(new Date(), "yyyyMMdd");
	private String tranTime = DateUtil.formateDate(new Date(), "HH:mm:ss");
	private String productType = Consts.WALLET_PARAMS.PRODUCT_TYPE;
	private String terminalId;
	private String userIp;
	private String nonceStr = IdUtilVo.getUUID();
	private String version = Consts.WALLET_PARAMS.VERSION;
	private String token;
	private String insCard;
	private String sign;
	private String custNo;
	private String custType = Consts.WALLET_PARAMS.WALLET_CUST_TYPE;
	
	public HeadParams(HttpServletRequest req, String svcName) {
		this.setTerminalId(req.getHeader("user-agent"));
		this.setUserIp(this.getUserRealIp(req));
		this.setToken((String) CacheKit.get("session", "token" + IpaysUtils.getUniId(req)));
		this.setCustNo((String) CacheKit.get("session", "custNo" + IpaysUtils.getUniId(req)));
		this.setSvcName(svcName);
	}
	
	private String getUserRealIp(HttpServletRequest req){
		String userIp = req.getRemoteAddr();
		if(userIp.equals("127.0.0.1")){
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userIp = inet.getHostAddress();
		}
		if(userIp == null || userIp .length() == 0 || "unknown".equalsIgnoreCase(userIp)){
			userIp = req.getHeader("x-forwarded-for");
		}
		if(userIp == null || userIp .length() == 0 || "unknown".equalsIgnoreCase(userIp)){
			userIp = req.getHeader("Proxy-Client-IP");
		}
		if(userIp == null || userIp .length() == 0 || "unknown".equalsIgnoreCase(userIp)){
			userIp = req.getHeader("WL-Proxy-Client-IP");
		}
	
		if(userIp != null && userIp.length() > 15){
			if(userIp.indexOf(",") > 0){
				userIp = userIp.substring(0, userIp.indexOf(","));
			}
		}
		return userIp;
	}
	
	public JSONObject toSign(JSONObject mes) throws Exception {
		JSONObject sysHead = JSONObject.parseObject(JsonKit.toJson(this));
		sysHead.putAll(mes);
		return JsonMd5Utils.json2Md5ByASCII(sysHead, this.getToken());
	}
	
	public JSONObject commonSend(JSONObject mes) throws Exception{
		mes = this.toSign(mes);
		if(log.isDebugEnabled()) {
			log.debug("查询请求参数为：" + mes);
		}
		String retStr = HttpKit.post(Consts.WALLET_PARAMS.REQ_URL, mes.toJSONString(), Consts.commHead);
		if(log.isDebugEnabled()) {
			log.debug("返回原始参数：" + retStr);
		}
		
		if(StrKit.isBlank(retStr)) {
			return null;
		}
		return JSONObject.parseObject(retStr);
	}

	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getMerchArea() {
		return merchArea;
	}

	public void setMerchArea(String merchArea) {
		this.merchArea = merchArea;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getInsCard() {
		return insCard;
	}

	public void setInsCard(String insCard) {
		this.insCard = insCard;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
}
