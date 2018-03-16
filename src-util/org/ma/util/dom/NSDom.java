package org.ma.util.dom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.XPath;

public class NSDom {
	
	public static final String DEFAULT = "default";
	
	private Element root = null;

	private Map<String,String>	nsMap = new HashMap<String,String>();
	
	public NSDom(Element root) {
		super();
		this.root = root;
		List<Namespace> nss = (List<Namespace>) root.declaredNamespaces();
		for(Namespace ns : nss){
			String name = ns.getPrefix();
			if("".equals(name)){
				name = DEFAULT;
			}
			nsMap.put(name, ns.getURI());
		}
	}
	
	/**
	 * @param xpath  "//default:service[@name='dbExecuteService']/default:port/soap:address"
	 * @return
	 */
	public Node selectSingleNode(String xpath){
		XPath xsub= root.createXPath(xpath);
	    xsub.setNamespaceURIs(nsMap);
	    return xsub.selectSingleNode(root);
	}
	
	
	
	public List selectNodes(String xpath){
		XPath xsub= root.createXPath(xpath);
	    xsub.setNamespaceURIs(nsMap);
	    return xsub.selectNodes(root);
	}
	
}
