package cn.ipays.beans;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.model.annotation.ModelAno;

@SuppressWarnings("serial")
@ModelAno(table= "t_sys_desc")
public class SysDesc extends Model<SysDesc> {
	public static final SysDesc dao = new SysDesc();

	/**
	 * 获取系统描述信息
	 * */
	public SysDesc getSysDesc() {
		return this.findFirst("SELECT * FROM T_SYS_DESC");
	}
	
}
