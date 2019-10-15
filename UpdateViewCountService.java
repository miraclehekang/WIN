package cn.ipays.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.ipays.beans.JpressContent;
import cn.ipays.commons.Consts;
import cn.ipays.commons.ErrCode;
import cn.ipays.service.IService;
import cn.ipays.utils.EncodingParamsUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @Title:UpdateViewCountService
 * @remark：点击次数
 * @author : lz
 * @2018年1月11日 : 2018年4月2日 上午11:30:06
 */
public class UpdateViewCountService implements IService {

	private static Logger log = Logger.getLogger(UpdateViewCountService.class);
	JSONObject res = new JSONObject();
	JSONObject ret = new JSONObject();

	@Override
	public JSONObject excute(JSONObject reqJson) {
		if (log.isDebugEnabled()) {
			log.debug("点击数增加入口" + reqJson);
		}
		String isOk = EncodingParamsUtils.judgeJson(reqJson, "id");
		if (!isOk.equals("ok")) {
			log.error("请求参数[" + isOk + "]未上送");
			return ErrCode.errMesExt(ErrCode.MESSAGE_IS_NOT_EXACTLY, "请求参数["
					+ isOk + "]未上送");
		}
		res = reqJson;
		// 调用事务处理
		try {
			Db.tx(this);
		} catch (Exception e) {
			log.error("请求事务处理异常", e);
			if (StrKit.notBlank(ret.getString("retCode"))) {
				return ret;
			} else {
				return ErrCode.errMes(ErrCode.UNKOWN_EXCEPTION);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("点击数增加成功返回报文为：" + ret);
		}
		return ret;
	}
	
	@Override
	public boolean run() throws SQLException {
		JSONObject json = new JSONObject();
		String contextId = res.getString("id");
		JpressContent content = JpressContent.dao.findContentById(contextId);
		Record jpressContent = new Record();
		String count = String.valueOf(content.getLong("view_count"));
		Integer con = Integer.valueOf(count);
		Integer viewCount = con + 1;
		jpressContent.set("id", contextId);
		jpressContent.set("view_count", viewCount);
		boolean update = Db.update("JPRESS_CONTENT", "id", jpressContent);
		String jpressAddr = Consts.JPRESSLOCATE;
		if (update) {
			String title = content.get("title");
			String text = content.get("text"); 
			String viewCounts = String.valueOf(content.getLong("view_count"));
			json.put("title", title);
			
			Document doc = Jsoup.parse(text);
			Elements imgs = doc.select("img");
			Elements newImgs = new Elements();
			for(Element img : imgs) {
				String imgHref = img.attr("src");
				img.attr("src", jpressAddr + imgHref);
				newImgs.add(img);
			}
			for (Element newImg : newImgs) {
				imgs.remove(newImg);
				imgs.add(newImg);
			}
			
			text = doc.html();
			
			json.put("text", text);
			json.put("viewCount", viewCount);
			json.put("thumbnail", content.getStr("thumbnail"));
			ret.put("data", json);
			ret.put("retCode", ErrCode.SUCCESS);
			ret.put("retMsg", ErrCode.getMsg(ErrCode.SUCCESS));
			if (log.isDebugEnabled()) {
				log.debug("点击数增加成功返回报文为：" + ret);
			}
		}else {
			log.debug("点击数增加失败");
			ret.put("retCode", ErrCode.UNKOWN_EXCEPTION);
			ret.put("retMsg", ErrCode.getMsg(ErrCode.UNKOWN_EXCEPTION));
		}
		return true;
	}
}
