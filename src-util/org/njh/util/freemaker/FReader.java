package org.ma.util.freemaker;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.ma.util.Dom4jUtil;

/**
 * @author Archmage
 */
public class FReader {
	public static final String rootName = "manager";
	
	/**
	 * 1 ns 和name 不能为空
	 * 2 当模板内容为空时，该模板不加入FManager
	 * @param fm
	 * @param urls
	 */
	public static void readURL(FManager fm, List<String> urls) {
		for (String url : urls) {
			Document doc = Dom4jUtil.getCpDoc(url);
			Element root = doc.getRootElement();
			if(!root.getName().equals(rootName)){
				throw new RuntimeException("分析模板xml出错：根元素名必须为：" + rootName +" . 出错模板为:" + url);
			}
			
			List<Element> modelsEls = root.selectNodes("models");
			
			for(Element modelsEl : modelsEls){
				String ns = modelsEl.attributeValue("ns");
				if(null == ns || "".equals(ns))
					throw new RuntimeException("分析模板xml出错：ns 不能为空." + " 出错模板为:" + url);
				List<Element> innerModelEls = modelsEl.selectNodes("model");
				for(Element innerModelEl : innerModelEls){
					String name = innerModelEl.attributeValue("name");
					String text = innerModelEl.getText();
					if (text != null && !text.trim().equals("")) {
						FModel model = new FModel(ns,name,text.trim());
						model.setUrl(url);
						fm.addModel(model);
					}
				}
			}
		}
	}

	public static String reloadTemplate(FModel fModel) {
		Document doc = Dom4jUtil.getCpDoc(fModel.getUrl());
		Element root = doc.getRootElement();
		return  root.selectSingleNode("models[@ns='"+fModel.getNs() +"']/model[@name='" +fModel.getName() +"']").getText().trim();
	}
	
	
/*	public static void main(String [] args){
		FManager fm = new FManager();
		List<String> urls = ComplexUtil.list();
		urls.add("taiji/snappy/plugin/pmi/tookit/freemarker/temp.xml");
		urls.add("taiji/snappy/plugin/pmi/tookit/freemarker/t1.xml");
		readURL(fm,urls);
		
		System.out.println(fm);
	}*/

}
