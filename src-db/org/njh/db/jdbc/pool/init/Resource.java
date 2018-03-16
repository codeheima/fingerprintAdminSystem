package org.ma.db.jdbc.pool.init;

import org.dom4j.Node;
import org.ma.util.Dom4jUtil;

public class Resource {
	private  String url;

	public Resource(Node n) {
		url = Dom4jUtil.attrVal(n, "", "resource", "");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
