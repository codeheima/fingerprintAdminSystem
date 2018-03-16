package org.ma.util.freemaker;

import java.util.Comparator;
import java.util.Map;


/**
 * 如果 ns 和 name 相同，直接覆盖，暂不做排他处理
 * @author Archmage
 */
public class FModel implements Comparator<FModel>{
	
	private FManager fm;
	/**
	 * namespace
	 */
	private String ns;
	/**
	 * key
	 */
	private String name;
	
	/**
	 * 具体的模板
	 */
	private String template;
	
	

	private String url;
	
	
	public FModel(String ns, String name) {
		super();
		this.ns = ns;
		this.name = name;
	}

	public FModel(String ns, String name, String template) {
		this(ns,name);
		this.template = template;
	}

	/**
	 * 检测是否符合要求,ns 和 name 不能为null
	 * @return 是否符合要求
	 */
	public boolean check() {
		if(ns == null || name == null)
			return false;
		return true;
	}


	/**
	 * 编译
	 * @param params
	 * @return
	 */
	public String simpleCompile( Map<String, Object> params){
		return simpleCompile(params,null);
	}	
	
	public String simpleCompile(Map<String, Object> params,IValueChange change){
		if(fm.isDebug()){
			template = FReader.reloadTemplate(this);
		}
		
		return FSimpleCompile.compileString(template , params , change);
	}

	public String getNs() {
		return ns;
	}


	public void setNs(String ns) {
		this.ns = ns;
	}

	public String getUrl() {
		return url;
	}

	public FManager getFm() {
		return fm;
	}

	public void setFm(FManager fm) {
		this.fm = fm;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ns == null) ? 0 : ns.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FModel other = (FModel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ns == null) {
			if (other.ns != null)
				return false;
		} else if (!ns.equals(other.ns))
			return false;
		return true;
	}

	public int compare(FModel m1, FModel m2) {
		if(m1 == null ||m2 == null)
			return 0;
		int a= m1.ns.compareTo(m2.ns);
		if(a != 0)
			return a;
		return m1.name.compareTo(m2.name);
	}

	public String toString() {
		return new StringBuilder("FModel [ns=").append(ns).append(", name=").append(name).append("]").toString();
	}
	
	

}
