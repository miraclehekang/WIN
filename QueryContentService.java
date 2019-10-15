package cn.ipays.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ipays.beans.Ieaparam;
import cn.ipays.commons.Consts;
import cn.ipays.commons.ErrCode;
import cn.ipays.utils.DateUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Const;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;



/**
 * @Title:QueryTaxonoList
 * @remark：文章列表信息信息查询
 * @author : MaYang
 * @2018年1月11日 : 2018年1月11日 下午5:02:33
 */
public class QueryContentService implements IService {

	private Logger log = Logger.getLogger(QueryContentService.class);
	
	@Override 
	public boolean run() throws SQLException {
		return false;
	}

	@Override
	public JSONObject excute(JSONObject req) {
		JSONObject reqJson = new JSONObject();
		JSONArray array = new JSONArray();
		Ieaparam eaparam = Ieaparam.dao.getParams("CONTENT_NUM");
		List<Record> contentList = Db.find("SELECT * FROM jpress_content WHERE id in (SELECT content_id FROM jpress_mapping WHERE taxonomy_id = "
				+ "(SELECT id FROM jpress_taxonomy WHERE title = ?)) ORDER BY created DESC LIMIT 0,?",
				Consts.info_type.INFO, Integer.valueOf(eaparam.getStr("paramvalue")));
		if(contentList.size() > 0 || contentList != null){
			for(Record record : contentList){
				JSONObject json = new JSONObject();
				String id = String.valueOf(record.getBigInteger("id"));
				String slug = record.get("slug");
				String viewCount = String.valueOf(record.getLong("view_count"));
				json.put("id", id);
				json.put("slug", slug);
				json.put("viewCount", viewCount);
				json.put("created", DateUtil.formateDate(record.getDate("created"), "yyyy-MM-dd"));
				json.put("thumbnail", record.getStr("thumbnail"));
				array.add(json);
			}
		}
		
		//查询轮播图片
		List<Record> carouselList = Db.find("SELECT * FROM jpress_content WHERE id in (SELECT content_id FROM jpress_mapping WHERE taxonomy_id = "
				+ "(SELECT id FROM jpress_taxonomy WHERE title = ?)) ORDER BY created DESC",
				Consts.info_type.CAROUSEL);
		JSONArray carouseArray = new JSONArray();
		for (Record record : carouselList) {
			JSONObject json = new JSONObject();
			String id = String.valueOf(record.getBigInteger("id"));
			String slug = record.get("slug");
			String viewCount = String.valueOf(record.getLong("view_count"));
			json.put("id", id);
			json.put("slug", slug);
			json.put("viewCount", viewCount);
			json.put("created", DateUtil.formateDate(record.getDate("created"), "yyyy-MM-dd"));
			json.put("thumbnail", record.getStr("thumbnail"));
			carouseArray.add(json);
		}
		
		
		
		reqJson.put("data", array);
		reqJson.put("carouseArray", carouseArray);
		reqJson.put("retCode", ErrCode.SUCCESS);
		reqJson.put("retMsg", ErrCode.getMsg(ErrCode.SUCCESS));
		return reqJson;
	}
}
