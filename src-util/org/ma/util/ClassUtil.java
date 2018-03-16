package org.ma.util;

public class ClassUtil {
	public static <T> T newInstance(Class<T> c) {
		try {
			T t = c.newInstance();
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
