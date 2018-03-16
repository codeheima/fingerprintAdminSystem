package org.ma.db.jdbc.pool.init;

import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.ma.util.ComplexUtil;
import org.ma.util.Dom4jUtil;
import org.ma.util.EmptyUtil;
import org.ma.util.freemaker.FSimpleContext;

public class DBConfigBean {
	
	public DBConfigBean(Document doc) {
		initByDoc(doc);
	}


	private boolean showsql ;
	private boolean reloadResource ;
	
	private List<SinglePoolConfig> poolConfigList = null;
	
	private List<Resource> resourceList = null;
	
	private Set<Table> tables = null;
	
	public void initByDoc(Document doc) {
		Element root = doc.getRootElement();
		
		showsql = Boolean.parseBoolean(Dom4jUtil.getText(root,"common/properties/property[@name='showsql']","true"));
		reloadResource = Boolean.parseBoolean(Dom4jUtil.getText(root,"common/properties/property[@name='resource.reload']","true"));
		
		List<Node> pools = root.selectNodes("conn/pool");
		
		poolConfigList =ComplexUtil.list();
		
		for(Node n : pools){
		//	parse(n);
			poolConfigList.add(new SinglePoolConfig(n));
		}
		
		List<Node> models = root.selectNodes("tsd/model");
		
		resourceList = ComplexUtil.list();
		List<String> urls = ComplexUtil.list();
		for(Node n : models){
			Resource r = new Resource(n);
			if(EmptyUtil.isEmptyStr(r.getUrl())){
				continue;
			}
			urls.add(r.getUrl());
			resourceList.add(r);
		}
			
		FSimpleContext.readUrls(urls);
		
		parseTables(root);
		
	}
	


	private void parseTables(Element r) {
		List<String> tableResources = Dom4jUtil.attrValList(r,"tables/table","resource","");
		tables  = ComplexUtil.set();
		for(String re : tableResources){
			Document doc = Dom4jUtil.getCpDoc(re);
			Element root = doc.getRootElement();
			
			List<Node> tablesNodes = root.selectNodes("tables");
			for(Node tsNode : tablesNodes){
				String dbName = Dom4jUtil.attrVal((Element)tsNode,"", "db","");
				List<Node> tNodes  =tsNode.selectNodes("table");
				for(Node tNode : tNodes){
					String id = Dom4jUtil.attrVal((Element)tNode, "", "id","");
					String name = Dom4jUtil.attrVal((Element)tNode, "", "name","");
					Table t =new Table();
					t.setId(id);
					t.setName(name);
					t.setDb(dbName);
					tables.add(t);
				}
				
			}
			
		}
		
	}

	

	public Set<Table> getTables() {
		return tables;
	}



	public boolean isShowsql() {
		return showsql;
	}


	public void setShowsql(boolean showsql) {
		this.showsql = showsql;
	}


	public boolean isReloadResource() {
		return reloadResource;
	}


	public void setReloadResource(boolean reloadResource) {
		this.reloadResource = reloadResource;
	}


	public List<SinglePoolConfig> getPoolConfigList() {
		return poolConfigList;
	}


	public void setPoolConfigList(List<SinglePoolConfig> poolConfigList) {
		this.poolConfigList = poolConfigList;
	}
	
	
	
}
