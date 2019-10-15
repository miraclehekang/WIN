package cn.ipays.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.ipays.service.IService;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * 类名: GetMessageController
 * 描述说明: 获取验证码
 * 创建人:lz
 * 创建时间: 2018年7月2日 下午3:39:28
 *
 */
public class GetMessageService implements IService{

	private static Logger log = Logger.getLogger(GetMessageService.class);
			

	@Override
	public boolean run() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public JSONObject excute(JSONObject req) {
		if(log.isDebugEnabled()) {
			log.debug("短信发送请求参数：" + req);
		}
		JSONObject retJson = new JSONObject();
		String  paramkey = "S_YY_SMS";
		String userPhone = req.getString("userPhone");
		String oldPhone = req.getString("oldPhone");
		Record user = Db.findFirst("select * from s_user_info where USER_PHONE = ?", oldPhone);
		String userCardNum = user.get("USER_CARD_NUM");
		String userCardDate = user.get("USER_CARD_DATE");
		Integer num = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(new Date());
		if(userCardDate == null || userCardDate == ""){
			userCardDate = nowDate;
		}else if(!userCardDate.equals(nowDate)){
			userCardDate = nowDate;
			userCardNum = "0";
		}else{
			userCardDate = nowDate;
		}
		
		if(userCardNum ==null || userCardNum == ""){
			userCardNum = "1";
		}else{
			num = Integer.valueOf(userCardNum) + 1; 
			userCardNum = String.valueOf(num);
		}
		Record eaparam = Db.findFirst("select * from eaparam where PARAMKEY = ?", paramkey);
		String value = "";
		Integer values = 0;
		if(eaparam != null){
			value = eaparam.get("PARAMVALUE");
			values = Integer.valueOf(value);
		}
		if(num > values){
			retJson.put("retCode", "000001");
			retJson.put("retMsg", "您今日发送短信已达最大限制");
			return retJson;
		}
		
		
		
		String mes  = PropKit.get("msg.ipays.msg");//验证码提示信息
		String userPhoneCode = req.getString("userPhoneCode");
		mes = mes.replace("[1]", userPhoneCode);
		JSONObject excute = new IPaysSmsService().excute(userPhone, mes);
		if(excute.getString("retCode").equals("000000")){
			Db.update("update s_user_info set USER_CARD_NUM = ?,USER_CARD_DATE = ? where USER_PHONE = ?", userCardNum, userCardDate, oldPhone);
			// 1.响应码
			retJson.put("retCode", "000000");
			// 2.响应信息
			retJson.put("retMsg", "验证码发送成功。");
		}else{
			// 1.响应码
			retJson.put("retCode", "000001");
			// 2.响应信息
			retJson.put("retMsg", "验证码发送失败。");
		}
		
		if(log.isDebugEnabled()) {
			log.debug("短信费发送下送参数：" + retJson);
		}
		return retJson;
	}
	}
