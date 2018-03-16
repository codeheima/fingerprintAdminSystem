package org.ma.util.freemaker;

import java.util.List;
import java.util.Map;

import org.ma.util.ComplexUtil;


/**
 * 只有一个静态FManager,保证单例
 * @author Archmage
 */
public class FSimpleContext {
	
	public static final FManager fm = new FManager();
	
	public static void readUrl(String url){
		if(null == url || url.equals(""))
			return;
		List<String> urls = ComplexUtil.list();
		urls.add(url);
		fm.readUrls(urls);
	}
	
	public static void readUrls(List<String> urls){
		fm.readUrls(urls);
	}

	public static void main(String [] args){
//		FManager fm = new FManager();
		List<String> urls = ComplexUtil.list();
		urls.add("taiji/snappy/plugin/pmi/tookit/freemarker/temp.xml");
		readUrls(urls);
		Map<String,Object> params = ComplexUtil.map();
		params.put("attrId", "123");
		System.out.println(fm.findModel("hello.world", "one").simpleCompile(params));
		
	}
}
