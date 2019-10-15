package cn.ipays.beans;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.model.annotation.ModelAno;


@SuppressWarnings("serial")
@ModelAno(table= "JPRESS_COMMENT")
public class JpressComment extends Model<JpressComment>{

	public static final JpressComment dao = new JpressComment();
	
	public List<JpressComment> findAll(){
		return this.find("select * from jpress_comment");
	}
	
	public List<JpressComment> findcomListById(String contextId){
		return this.find("select * from jpress_comment where content_id = ?", contextId);
	}
	
	public List<JpressComment> findcommentCountById(String contextId, String userId){
		return this.find("select * from jpress_comment where content_id = ? and user_id = ?", contextId, userId);
	}
}
