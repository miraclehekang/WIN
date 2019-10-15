package cn.ipays.beans;

import java.util.List;


import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.model.annotation.ModelAno;

@ModelAno(table="t_sys_param")
@SuppressWarnings("serial")
public class Ieaparam extends Model<Ieaparam> {
	public static final Ieaparam dao = new Ieaparam();

	public String getParamValue(String paramkey) {

		Ieaparam param = this.findFirst( "SELECT PARAMVALUE FROM t_sys_param WHERE PARAMKEY=?", paramkey);
		if(param == null){
			return "";
		}
		if(StrKit.notBlank(param.getStr("PARAMVALUE"))){
			return param.get("PARAMVALUE").toString();
		}else{
			return "";
		}
		 
	}
	
	/**
	 * 获取系统级参数
	 * @param 
	 * @return t_sys_param
	 * */
	public List<Ieaparam> getParamsAll() {
		return this.find("SELECT * FROM t_sys_param ");
	}

	
	/**
	 * 获取系统级参数
	 * @param PARAMKEY 参数键名
	 * @return Ieaparam
	 * */
	public Ieaparam getParams(String paramkey) {
		return this.findFirst("SELECT * FROM t_sys_param WHERE PARAMKEY=?",
				paramkey);
	}
}
