package cn.ipays.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.ipays.beans.HeadParams;
import cn.ipays.beans.UserInfo;
import cn.ipays.commons.Consts;
import cn.ipays.commons.ErrCode;
import cn.ipays.utils.IpaysUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.ehcache.CacheKit;

public class QueryUserInfoService{
	private static final Logger log = Logger.getLogger(QueryUserInfoService.class);
	
	public UserInfo excute(HttpServletRequest req) throws Exception {
		String svcName = Consts.WALLET_SVC_NAME.QRY_USER;
		
		JSONObject mes = new JSONObject();
		
		HeadParams headParams = new HeadParams(req, svcName);
		mes = headParams.commonSend(mes);
		if (mes == null || !mes.getString("retCode").equals(ErrCode.SUCCESS)) {
			log.error("钱包后端返回异常");
			return null;
		}
		UserInfo userInfo = JSONObject.toJavaObject(mes, UserInfo.class);
		if(userInfo != null) {
			//重新缓存用户信息
			String cId = IpaysUtils.getUniId(req);
			String token = CacheKit.get("session", "token" + cId);
	    	String custNo = CacheKit.get("session", "custNo" + cId);
	    	userInfo.setCustNo(custNo);
	    	userInfo.setToken(token);
			CacheKit.put("session", "userInfo" + cId, userInfo);
		}
		
		return userInfo;
	}
}
