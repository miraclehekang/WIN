package cn.ipays.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;



/**
 * @Title:QueryContentInfo
 * @remark：文章详细信息查询
 * @author : MaYang
 * @2018年1月11日 : 2018年1月11日 下午5:02:33
 */
public class QueryContentInfoService implements IService {

	private Logger log = Logger.getLogger(QueryContentInfoService.class);
	
	@Override 
	public boolean run() throws SQLException {
		return false;
	}

	@Override
	public JSONObject excute(JSONObject req) {
		if (log.isDebugEnabled()) {
			log.debug("文章详细信息查询入口" + req);
		}
		JSONObject retJson = new JSONObject();
		// 获取系统头
		JSONObject sysHead = new JSONObject();
		sysHead = req.getJSONObject("sysHead");

		//上送数据
		String contextId = req.getString("contextId");
		if(StrKit.isBlank(contextId)){
			retJson.put("retCode", "000001");
			retJson.put("retMsg", "文章id为空");
			return retJson;
		}
		Record content = Db.findFirst(" select 	id content_id,a.title title,a.text text,a.comment_count comment_count, "
	                                    +" a.view_count view_count,a.thumbnail imgInfo,a.created created from jpress_content a "
			                         	+ " where  id = "+Long.parseLong(contextId));
	   if(content != null){
			JSONObject taxono = new JSONObject();
			taxono.put("contextId", String.valueOf(content.get("content_id")));
			taxono.put("title", content.getStr("title"));
			taxono.put("text", content.getStr("text"));
			taxono.put("commentCount", String.valueOf(content.get("comment_count")));
			taxono.put("viewCount", String.valueOf(content.get("view_count")));
			taxono.put("created", String.valueOf(content.get("created")));
			
			retJson.put("taxonoinfo", taxono);
			retJson.put("retCode", "000000");
			retJson.put("retMsg", "查询成功");
		}else{
		
			retJson.put("retCode", "000001");
			retJson.put("retMsg", "系统无此文章！");
		}
		return retJson;
	}

}
