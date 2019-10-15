package cn.ipays.service;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
/**
 * 
 * 类名: IPaysSmsService
 * 描述说明: 验证码发送请求工具
 * 创建人:lz
 * 创建时间: 2018年7月2日 下午3:31:45
 *
 */
public class IPaysSmsService {
	private Logger log = Logger.getLogger(this.getClass());
	public JSONObject excute(String phone,String mes){
		JSONObject retJson = new JSONObject();
		if(phone==null||phone.equals("")){
			log.error("乐付通短信平台短信发送失败，失败原因：手机号不能为空");
			retJson.put("retCode", "999999");
			retJson.put("retMsg", "手机号不能为空");
			return retJson;
		}
		try {
			String pushUrl = PropKit.get("msg.ipays.push.url");
			String orgId = PropKit.get("msg.ipays.orgid");
			String user = PropKit.get("msg.ipays.user");
			String pwd = PropKit.get("msg.ipays.pwd");
			
			//组织请求报文
			JSONObject reqData = new JSONObject();
			reqData.put("service", "groupSms");
			reqData.put("orgid", orgId);
			reqData.put("userid", user);
			reqData.put("pwd", pwd);
			reqData.put("telno", phone);
			reqData.put("msg", mes);
			if(log.isDebugEnabled()){
				log.debug("[乐付通短信平台]短信发送,地址为:"+pushUrl+",内容为:"+reqData);
			}
			String retStr = HttpKit.post(pushUrl, reqData.toJSONString());
			if (log.isDebugEnabled()) {
				log.debug("通道[乐付通短信平台]发送结果为：" + retStr);
	        }
			
			JSONObject retData = JSONObject.parseObject(retStr);
			log.debug("短息发送返回结果=========="+retData.toString());
			String flag = retData.getString("flag");
			if(flag.equals("0000")){
				retJson.put("retCode", "000000");
				retJson.put("retMsg", "发送成功");
				return retJson;
			}else{
				String msg = retData.getString("msg");
				log.error("乐付通短信平台短信发送失败，失败原因："+msg);
				retJson.put("retCode", "999999");
				retJson.put("retMsg", msg);
				return retJson;
			}
		} catch (Exception e) {
			log.error("乐付通短信平台短信发送失败",e);
			retJson.clear();
			retJson.put("retCode", "999999");
			retJson.put("retMsg", "乐付通短信平台短信发送失败");
			return retJson;
		}
	}
}
