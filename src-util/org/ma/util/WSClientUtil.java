package org.ma.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.handler.HandlerResolver;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.ma.util.dom.NSDom;

/**
 * 客户端测试webservice
 * 与cxf 部分jar不兼容...
 */
public class WSClientUtil {
	public static final String DEFAULT = "default";
	

	
	/**
	 * 得到服务的接口实
	 * @param wsdlURL  "http://localhost:80/ps/cyberpolice/dbResultCommitService?wsdl"
	 * @param c   服务的interface
	 * @return    服务的interface类实
	 * @throws MalformedURLException 
	 */
	public static <T> T getService(String wsdlURL, Class<T> c)
			throws MalformedURLException {
		return getService(new URL(wsdlURL),null,c);
	}
	

	public static <T> T getService(URL url,HandlerResolver handlerResolver, Class<T> c){
		QName qname = getQName(url,c);
		Service service = Service.create(url, qname);
		service.setHandlerResolver(handlerResolver);
		
		T t = service.getPort(c);
		return t;
		
	}
	
	public static  QName getQName(URL url,Class<?> c){
		QName qname = null;
		if(c.isAnnotationPresent(WebService.class)){
			WebService ws = c.getAnnotation(WebService.class);
			String serviceName = ws.serviceName();
			if(EmptyUtil.isEmptyStr(serviceName )){
				serviceName= ws.name();
			}
			
			qname = new QName(ws.targetNamespace(),serviceName);
			
		}else{
			Document doc = getDom(url);
			Element root =  doc.getRootElement();
			String targetNamespace =root.attributeValue("targetNamespace");
			NSDom nsRoot = Dom4jUtil.createNSDom(root);
			Element serviceEl = (Element)nsRoot.selectSingleNode( NSDom.DEFAULT + ":service");
			if(serviceEl == null){
				 serviceEl = (Element)nsRoot.selectSingleNode("wsdl:service");
			}
			
			String serviceName = serviceEl.attributeValue("name");
			
			qname = new QName(targetNamespace, serviceName); // targetNamespace,serviceName
		}	
		return qname;
	}

	private static Document getDom(URL url) {
		SAXReader saxReader = new SAXReader();  
        try {
			Document doc = saxReader.read(url);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
        
		return null;
	}
	
	
	public static void main(String[] args) throws Exception {
		//"C:/Documents and Settings/Administrator/桌面/tea/123/GAB_ZIP_INDEX.xml"
/*		ManageService hello = WSClientUtil.getService(
				"http://localhost/zd/services/manageService?wsdl",
				ManageService.class);

		System.out.println(hello.execute("这个是中文"));
*/
	}
}

