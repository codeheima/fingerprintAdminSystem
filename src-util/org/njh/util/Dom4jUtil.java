package org.ma.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.ma.util.dom.NSDom;

public class Dom4jUtil {

	
	
	public static Document getCpDoc(String path){
		InputStream in = ReflectUtil.getResourceAsStream(path);
		return createByInputStream(in);
	}
	
	public static Document createByInputStream(InputStream in){
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(in);
			return document;
		}catch(Exception err){
			throw new RuntimeException(err);
		}
		
	}

	/**
	 * 返回指定节点下特定路径的节点的属性值
	 * @param node  起始节点 
	 * @param xpath  路径
	 * @param attr  属性名称
	 * @param defaultVal  默认值
	 * @return 属性值
	 */
	public static Integer attrValInteger(Node node, String xpath, String attr, Integer defaultVal) {
		String strVal = attrVal( node,  xpath,  attr,null);
		if("".equals(strVal)){
			return defaultVal;
		}
		return Integer.parseInt(strVal);
	}
	
	/**
	 * 返回指定节点下特定路径的节点的属性值
	 * @param node  起始节点 
	 * @param xpath  路径
	 * @param attr  属性名称
	 * @param defaultVal  默认值
	 * @return 属性值
	 */
	public static String attrVal(Node node, String xpath, String attr, String defaultVal) {
		if(defaultVal == null)
			defaultVal = "";
		Node n = null;
		if(EmptyUtil.isEmptyStr(xpath)){
			n = node;
		}else
			n = node.selectSingleNode(xpath);
		if(null == n)
			return defaultVal;
		Element e = (Element)n;
		String val = e.attributeValue(attr);
		if(null == val || "".equals(val))
			return defaultVal;
		return val;
	}
	
	public static String getText(Node node, String xpath, String defaultVal) {
		if(defaultVal == null)
			defaultVal = "";
		Node n = null;
		if(EmptyUtil.isEmptyStr(xpath)){
			n = node;
		}else
			n = node.selectSingleNode(xpath);
		if(null == n)
			return defaultVal;
		String val = n.getText();
		if(null == val || "".equals(val))
			return defaultVal;
		return val;
	}
	
	
	public static void changeAttrVal(Node node, String xpath, String attr, String val) {
		if(val == null)
			val = "";
		Element  n = (Element)node.selectSingleNode(xpath);
		if(null == n)
			return;
		n.addAttribute(attr, val);
	}
	
	
	/**
	 * 返回指定节点下特定路径的很多节点的属性值
	 * @param node  起始节点 
	 * @param xpath  路径
	 * @param attr  属性名称
	 * @param defaultVal  默认值
	 * @return 属性值列表
	 */
	public static List<String> attrValList(Node node, String xpath, String attr, String innerDefaultVal) {
		if(innerDefaultVal == null)
			innerDefaultVal = "";
		List<String> list = new ArrayList<String>();
		List<Element> eleList =  node.selectNodes(xpath);
		for(int i = 0 ; i< eleList.size(); i++){
			Element e = eleList.get(i);
			String val =e.attributeValue(attr);
			if(null == val || "".equals(val)){
				list.add(innerDefaultVal);
			}else{
				list.add(val);
			}
		}
		return list;
	}
	
	
	public static String getItemVal(Element dataEl, String nameVal) {
		Element el = (Element)dataEl.selectSingleNode("ITEM[@key='"+nameVal +"']");
		if(el ==  null)
			return "";
		String val =  el.attributeValue("val");
		if(val == null)
			return "";
		else
			return val;
		
	}
	

	
	
	
	/**
	 * 获取字符串 
	 * @param node
	 * @return
	 */
	public static String getElStr(Node node){
		StringWriter sw = new StringWriter();
		printNode(node,sw);
		return sw.toString();
	}
	
	
	/**
	 * @param node : Node is parent class of Element or Document
	 * @param out
	 */
	public static void printNode(Node node,Writer out) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		// OutputFormat.createCompactFormat(); //紧凑格式
		// OutputFormat.createPrettyPrint();
	
		//指定输出流为 PrintWriter
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(out, format);
			writer.write(node);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 处理在根节点处加了命名空间时，xpath不好使的问题，
	 * 如果命名空间不在根节点下，请自行处理。
	 * @param rootEl 根节点
	 * @return 
	 */
	public static NSDom createNSDom(Element rootEl){
		NSDom nsDom = new NSDom(rootEl);
		return nsDom;
	}

	public static void copyNodes(Element src, Element target, String xpath) {
		List<Element> list = (List<Element>)src.selectNodes(xpath);
		for(Element e : list){
			target.add((Element)e.clone());
		}
	}

	public static void delEl(Element target,String path){
		List<Element> children = target.selectNodes(path);
		if(children == null || children.size() == 0)
			return;
		Element parent = children.get(0).getParent();
		for(Element el: children){
			parent.remove(el);
		}
	}
	
	public static void delAttr(Element el, String attrName) {
		Attribute attr = el.attribute(attrName);
		if(attr != null){
			el.remove(attr);
		}
	}
	public static void delAttr(Element elSrc,String xPath, String attrName) {
		Element el = (Element)elSrc.selectSingleNode(xPath);
		if(el == null)
			return;
		
		Attribute attr = el.attribute(attrName);
		if(attr != null){
			el.remove(attr);
		}
	}

	public static void delAllChild(Element parent) {
		List<Element> list = (List<Element>)parent.elements();
		
		for(Element c : list){
			parent.remove(c);
		}
	}

	public static List<Element> findAllChildrenEl(List<Element> parents,
			String elName) {
		List<Element> list = ComplexUtil.list();
		
		findAllChildrenEl(parents,elName,list);
		return list;
	}

	public static void findAllChildrenEl(List<Element> parents, String elName,
			List<Element> list) {
		if(parents == null || parents.size() ==0 )
			return;
		for(Element p : parents){
			if(p.getName().equals(elName)){
				list.add(p);
			}
			findAllChildrenEl(p.elements(),elName,list);
		}
		
	}
	

	
	
}
