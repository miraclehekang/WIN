/*package cn.ipays.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.ipays.beans.IUserInfo;
import cn.ipays.beans.JpressContent;
import cn.ipays.commons.ErrCode;
import cn.ipays.service.IService;
import cn.ipays.test.DateUtils;
import cn.ipays.utils.EncodingParamsUtils;
import cn.ipays.wordfilter.WordFilterConfig;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

*//**
 * CommitCommentService
 * 
 * @remark：评论提交
 * @author : lz
 * @2018年1月11日 : 2018年4月2日 上午16:30:20
 *//*
public class CommitCommentService implements IService {

	private static Logger log = Logger.getLogger(CommitCommentService.class);

	@Override
	public boolean run() throws SQLException {
		return false;
	}

	@Override
	public JSONObject excute(JSONObject req) {
		if (log.isDebugEnabled()) {
			log.debug("评论提交入口" + req);
		}
		JSONObject retJson = new JSONObject();
		String isOk = EncodingParamsUtils.judgeJson(req, "contextId,comment,userId");
		if (!isOk.equals("ok")) {
			log.error("请求参数[" + isOk + "]未上送");
			return ErrCode.errMesExt(ErrCode.MESSAGE_IS_NOT_EXACTLY, "请求参数["
					+ isOk + "]未上送");
		}
		// 获取系统头
		String userId = req.getString("userId");
		// 公共处理-结束
		String contextId = req.getString("contextId");
		String comment = req.getString("comment");
		//对自动发送的消息进行关键词过滤检查
		comment=WordFilterConfig.simpleFilter(comment, "");
		if (log.isDebugEnabled()) {
			log.debug("评论内容经过敏感词过滤后为：" + comment);
		}
		JpressContent jc = JpressContent.dao.findContentById(contextId);
		if (jc == null) {
			log.debug("文章不存在");
			retJson.put("retCode", ErrCode.UNKOWN_EXCEPTION);
			retJson.put("retMsg", ErrCode.getMsg(ErrCode.UNKOWN_EXCEPTION));
		}
		String module = jc.get("module");
		IUserInfo userInfo = IUserInfo.dao.findUserById(userId);
		String name = userInfo.get("USER_NAME"); 
		//String date = DateUtils.getSystemDate("yyyy-MM-dd HH:mm:ss");
		String date = DateUtils.getDateTimeFormat();
		// 先添加，评论添加成功在修改评论次数
		Record commentInfo = new Record();
		commentInfo.set("content_id", contextId);
		commentInfo.set("content_module", module);
		// commentInfo.set("ip", "192.168.1.156");
		commentInfo.set("user_id", userId);
		commentInfo.set("author", name);
		commentInfo.set("type", "comment");
		commentInfo.set("text", comment);
		// commentInfo.set("agent", "Safari/537.36");
		commentInfo.set("status", "normal");
		commentInfo.set("created", date);
		boolean addComment = Db.save("JPRESS_COMMENT", commentInfo);
		if (addComment) {
			JpressContent content = JpressContent.dao
					.findContentById(contextId);
			Record jpressContent = new Record();
			String count = String.valueOf(content.getLong("comment_count"));
			Integer con = Integer.valueOf(count);
			Integer viewCount = con + 1;
			jpressContent.set("id", contextId);
			jpressContent.set("comment_count", viewCount);
			boolean update = Db.update("JPRESS_CONTENT", "id", jpressContent);
			if (update) {
				retJson.put("retCode", ErrCode.SUCCESS);
				retJson.put("retMsg", ErrCode.getMsg(ErrCode.SUCCESS));
				if (log.isDebugEnabled()) {
					log.debug("评论提交成功返回报文为：" + retJson);
				}
			} else {
				log.debug("评论次数增加失败");
				retJson.put("retCode", ErrCode.UNKOWN_EXCEPTION);
				retJson.put("retMsg", ErrCode.getMsg(ErrCode.UNKOWN_EXCEPTION));
			}
		} else {
			log.debug("评论提交失败");
			retJson.put("retCode", ErrCode.UNKOWN_EXCEPTION);
			retJson.put("retMsg", ErrCode.getMsg(ErrCode.UNKOWN_EXCEPTION));
		}
		return retJson;
	}
}
*/