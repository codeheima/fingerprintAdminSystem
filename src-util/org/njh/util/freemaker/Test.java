package org.ma.util.freemaker;

import java.util.List;
import java.util.Map;

import org.ma.db.jdbc.DataObject;
import org.ma.util.ComplexUtil;


/**
 * @author Archmage
 */
public class Test {
	public static void main(String [] args){
		
		List<String> urls = ComplexUtil.list();
		urls.add("org/ma/util/freemaker/temp.xml");
		FSimpleContext.readUrls(urls);
		
		helloworld();
//		testText();
	}
	
	private static void helloworld() {
		FModel model= FSimpleContext.fm.findModel("hello.world", "two");
		System.out.println(model.getTemplate());
		System.out.println("-------------");
		Map<String ,Object> map = ComplexUtil.map();
		
		
		List<String> list = ComplexUtil.list();
		list.add("hello");
		list.add("world");
		map.put("myList",list);
		String str = model.simpleCompile(map);
		System.out.println(str);
		
		System.out.println("-------------");
	//	map.remove("attrPageMax");
		System.out.println(model.simpleCompile(map));
		
		
		model= FSimpleContext.fm.findModel("taiji.com.a.b.c","one");
		DataObject params = new DataObject();
		
		DataObject student = new DataObject();
		student.put("studentAge", 18);
		student.put("studentName","wq");
		student.put("bb","67");
		params.put("student", student);
		
		System.out.println(model.simpleCompile(params));
		
		
	}

	private static void testText() {
		FModel model= FSimpleContext.fm.findModel("form.element", "text");
		Map<String ,Object> map = ComplexUtil.map();
		
		map.put("attrId","name");
		map.put("attrPageMax",10);
		map.put("attrName","姓名");
		map.put("attrPageRequired","true");
		map.put("value","123");
		String str = model.simpleCompile(map);
		System.out.println(str);
		
		map.remove("attrPageMax");
		System.out.println(model.simpleCompile(map));
	}

	private static void testPrefix(){
		FModel model= FSimpleContext.fm.findModel("form.element", "label.prefix");
		Map<String ,Object> map = ComplexUtil.map();
		
		map.put("attrId","id12345");
		map.put("attrName","姓名");
		
		
		map.put("attrPageRequired","false");
		String str = model.simpleCompile(map);
		System.out.println(str);
	}
}
