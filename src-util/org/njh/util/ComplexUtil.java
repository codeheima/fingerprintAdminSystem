package org.ma.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public abstract class ComplexUtil {
	public static <T,R> Map<T,R> map(){
		return new HashMap<T, R>();
	}

	public static <T> List<T> list() {
		return new ArrayList<T>();
	}
	
	public static <T> Set<T> set() {
		return new HashSet<T>();
	}
	
	
	public static <T> Set<T> listToSet(List<T> list){
		if(list == null)
			return null;
		Set<T> set = set();
		for(T t : list){
			set.add(t);
		}
		return set;
	}
	
	public static <T,ID> Map<ID,T>  listToMap(List<T> list,GetId<T,ID> getId){
		Map<ID,T> map = ComplexUtil.map();
		if(EmptyUtil.isEmptyList(list)){
			return map; 
		}
		for(int i = 0 ;i < list.size(); i++){
			T t = list.get(i);
			ID id = getId.getId(t);
			map.put(id, t);
		}
		return map;
	}
	
	
	
	public static <T,ID> List<T>  delMultiple(List<T> list){
		GetId<T,T> getId = new GetId<T, T>() {
			public T getId(T t) {
				return t;
			}
		};
		return delMultiple(list,getId);
	}
	
	/**
	 * 拆分list，解决一个xml只能传固定数目的问题
	 * List<String> list = ...
	 * list<List<String>> newList = ComplexUtil.getSplitList(list,1000);  
	 * 
	 * @param l  原List
	 * @param split  需要拆分的数值
	 * @return  
	 */
	public static <T> List<List<T>> getSplitList(List<T> l,int split) 
	{
		List<List<T>> list = ComplexUtil.list();
		List<T> temp = ComplexUtil.list();
		for(int i = 0 ;i < l.size(); i++){
			if(temp.size() == split){
				list.add(temp);
				temp = ComplexUtil.list();
			}
			temp.add(l.get(i));
		}
		list.add(temp);
		
		return list;
	}
	
	/**
	 * 去掉重复的数据
	 * @param list
	 * @param getId
	 * @return
	 */
	public static <T,ID> List<T>  delMultiple(List<T> list,GetId<T,ID> getId){
		List<T> result =ComplexUtil.list();
		Set<ID> set = ComplexUtil.set();
		for(int i = 0 ;i < list.size(); i++){
			T t = list.get(i);
			ID id = getId.getId(t);
			if(!set.contains(id)){
				set.add(id);
				result.add(t);
			}
		}
		return result;
	}
	
	public static <K,V> void eachMap(Map<K,V> map,Each<K,V> each){
		Set<Map.Entry<K,V>> entrySet = map.entrySet();
		Iterator<Map.Entry<K,V>> it = entrySet.iterator();
		while(it.hasNext()){
			Map.Entry<K,V> entry = it.next();
			each.each(entry.getKey(), entry.getValue());
		}
	}
	
	public static <D> List<D> findList(List<D> list,FindData<D> find){
		if(EmptyUtil.isEmptyList(list))
			return list;
		List<D> chlidList = ComplexUtil.list();
		for(D d : list){
			if(find.find(d)){
				chlidList.add(d);
			}
		}
		return chlidList;
	}
	
	public static void main(String [] arguments){
		//haha
		/*List<String> list = new ArrayList<String>();
		
		for(int i = 0; i< 5000000; i++){
			list.add(String.valueOf(new Random().nextInt(3000)));
		}
		long l1 = System.currentTimeMillis();
		System.out.println("start..");
		
		list = delMultiple(list);
		
		long l2 = System.currentTimeMillis();
		System.out.println(list.size());
		System.out.println(l2 - l1);*/
		Map<String,Object> map = map();
		map.put("1", "a1");
		map.put("2", "b1");
		map.put("3", "c1");
		map.put("4", "d1");
		map.put("5", "e1");
		eachMap(map,new Each<String, Object>() {
			public void each(String k, Object v) {
				System.out.println(k + "=" + v);
			}
		});
	}
	
	
	public static <K,ID> boolean contains(List<K> l,K d,GetId<K,ID> getId){
		for(K k:l){
			if(getId.getId(k).equals(getId.getId(d))){
				return true;
			}
		}
		return false;
		
	}
	
	public interface Each<K,V>{
		void each(K k, V v);
	}
	
	public interface GetId<T,ID>{
		public ID getId(T t);
	}
	
	
	public interface FindData<D>{
		public boolean find(D d);
	}
}
