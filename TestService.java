package cn.ipays.service;

import java.sql.SQLException;

import cn.ipays.beans.Ieaparam;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;

//@RpcHandler("realNameResultReqMq")
public class TestService implements IService{

	@Override
	public boolean run() throws SQLException {
		return false;
	}

	@Override
	public JSONObject excute(JSONObject req) {
		JSONObject retJson = new JSONObject();
		retJson.put("value", Ieaparam.dao.getParamValue("ZBUS_URL"));
		if(Db.tx(this)) {
			
		}
		return retJson;
	}

}
