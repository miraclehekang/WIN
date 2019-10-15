package cn.ipays.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.ipays.commons.ErrCode;
import cn.ipays.service.IService;
import cn.ipays.utils.EncodingParamsUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * QueryCommentInfoService
 * 
 * @remark：查询评论列表
 * @author : lz
 * @2018年1月11日 : 2018年4月2日 上午16:30:20
 */
public class QueryCommentInfoService implements IService {

	private static Logger log = Logger.getLogger(QueryCommentInfoService.class);

	@Override
	public boolean run() throws SQLException {
		return false;
	}

	@Override
	public JSONObject excute(JSONObject req) {
		if (log.isDebugEnabled()) {
			log.debug("查询评论列表" + req);
		}
		JSONObject retJson = new JSONObject();
		String isOk = EncodingParamsUtils.judgeJson(req, "contextId,appHead");
		if (!isOk.equals("ok")) {
			log.error("请求参数[" + isOk + "]未上送");
			return ErrCode.errMesExt(ErrCode.MESSAGE_IS_NOT_EXACTLY, "请求参数["
					+ isOk + "]未上送");
		}
		// 获取系统头
		// JSONObject sysHead = new JSONObject();
		// sysHead = req.getJSONObject("sysHead");
		// 获取应用头
		JSONObject appHead = new JSONObject();
		appHead = req.getJSONObject("appHead");
		// 公共处理-结束
		String contextId = req.getString("contextId");
		Page<Record> find = null;
		JSONArray array = new JSONArray();
		find = Db.paginate(Integer.parseInt(appHead.getString("pageNum")),
				Integer.parseInt(appHead.getString("pageRows")), "SELECT * ",
				"FROM jpress_comment WHERE content_id = ? ORDER BY id DESC",
				Long.parseLong(contextId));
		if (find != null) {
			for (Record record : find.getList()) {
				JSONObject data = new JSONObject();
				// date转String
				Date systemDate = record.getDate("created");
				if(systemDate == null){
					data.put("creatDate", "");
				}else{
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String nowTime = sdf.format(systemDate);
					data.put("creatDate", nowTime);
				}
				data.put("userId", String.valueOf(record.getBigInteger("user_id")));
				data.put("name", record.get("author"));
				data.put("comment", record.get("text"));
				array.add(data);
			}
			appHead.put("totalNum", find.getTotalRow());
			retJson.put("appHead", appHead);
			retJson.put("commentInfo", array);
			retJson.put("retCode", ErrCode.SUCCESS);
			retJson.put("retMsg", ErrCode.getMsg(ErrCode.SUCCESS));
		} else {
			log.debug("分页查询失败，参数错");
			retJson.put("retCode", ErrCode.UNKOWN_EXCEPTION);
			retJson.put("retMsg", ErrCode.getMsg(ErrCode.UNKOWN_EXCEPTION));
		}
		log.debug(retJson);
		return retJson;
	}
}
