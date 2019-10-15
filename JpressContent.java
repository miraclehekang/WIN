package cn.ipays.beans;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.model.annotation.ModelAno;


@SuppressWarnings("serial")
@ModelAno(table= "JPRESS_CONTENT")
public class JpressContent extends Model<JpressContent>{

	public static final JpressContent dao = new JpressContent();
	
	
	public JpressContent findContentById(String contextId){
		return this.findFirst("select * from jpress_content where id = ? for update", contextId);
	}
}

