package cn.ipays.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ipays.commons.ErrCode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;



/**
 * @Title:QueryTaxonoList
 * @remark：文章列表信息信息查询
 * @author : MaYang
 * @2018年1月11日 : 2018年1月11日 下午5:02:33
 */
public class QueryTaxonoListService implements IService {

	private Logger log = Logger.getLogger(QueryTaxonoListService.class);
	
	@Override 
	public boolean run() throws SQLException {
		return false;
	}

	@Override
	public JSONObject excute(JSONObject reqJson) {
		if (log.isDebugEnabled()) {
			log.debug("文章列表查询入口：" + reqJson);
		}
		JSONObject retJson = new JSONObject();
		int pageNum = Integer.valueOf(reqJson.getString("pageNum"));
		int rowStart = (pageNum - 1) * 10;
		String sql = "select * from jpress_content" +" order by created DESC"+ " limit "+rowStart+", 10";
		if(log.isDebugEnabled()) {
			log.debug("请求查询sql.....="+sql);
		}
		List<Record> find =  Db.find(sql);
		retJson.put("list", JSONArray.parse(JsonKit.toJson(find)));
		retJson.put("retCode", ErrCode.SUCCESS);
		retJson.put("retMsg", ErrCode.getMsg(ErrCode.SUCCESS));
		return retJson;
	}

}
