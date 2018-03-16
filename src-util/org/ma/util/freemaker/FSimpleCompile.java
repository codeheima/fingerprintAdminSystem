package org.ma.util.freemaker;

import java.io.StringWriter;
import java.util.Map;

import org.ma.util.ComplexUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;


/**
 * @author Archmage
 */
public class FSimpleCompile {
	
	public static String compileString(String String, Map<String, Object> root) {
		try {
			Configuration cfg = new Configuration();
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			cfg.setTemplateLoader(new StringTemplateLoader(String));
			cfg.setDefaultEncoding("UTF-8");
			StringWriter writer = new StringWriter();
			try {
				Template template = cfg.getTemplate("");
//				template.setNumberFormat("#.##");
				template.setDateFormat("yyyy-MM-dd");
				template.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
				template.setTimeFormat("HH:mm:ss");
				template.process(root, writer);
				return writer.toString();
			} finally {
				writer.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String compileString(String template,Map<String, Object> params, IValueChange change) {
		params = getNewMap(params,change);
		return compileString(template,params);
	}

	private static Map<String, Object> getNewMap(Map<String, Object> params,
			final IValueChange change) {
		if(change == null)
			return params;
		final Map<String, Object> newMap = ComplexUtil.map();
		ComplexUtil.eachMap(params,new ComplexUtil.Each<String, Object>() {
			public void each(String k, Object v) {
				v = change.changeVal(k, v);
				newMap.put(k, v);
			}
		});
		return newMap;
	}
	
	

}
