package org.ma.util.freemaker;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import freemarker.cache.TemplateLoader;

/**
 * 
 * @author ryan
 * 
 */
public class StringTemplateLoader implements TemplateLoader {

	private String template;

	public void closeTemplateSource(Object templateSource) throws IOException {
		((StringReader) templateSource).close();
	}

	public long getLastModified(Object arg0) {
		return 0;
	}

	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return (Reader) templateSource;
	}

	public StringTemplateLoader(String template) {
		this.template = template;
		if (this.template == null)
			this.template = "";
	}

	public Object findTemplateSource(String arg0) throws IOException {
		return new StringReader(template);
	}

}
