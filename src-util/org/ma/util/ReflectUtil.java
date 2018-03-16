package org.ma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * 反射工具类
 * @author Justin
 *
 */
public class ReflectUtil {

	protected static final Map<Class<?>, String> cachedNames = new HashMap<Class<?>, String>();

	
	
	public static String getStreamData(String path){
		InputStream is = getResourceAsStream(path);
		return IOUtil.getStreamData(is);
	}
	
	public static URL getResource(String name) {
		URL url = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			url = classLoader.getResource(name);
		}
		if (url == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			url = classLoader.getResource(name);
			if (url == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtil.class.getClassLoader();
				url = classLoader.getResource(name);
			}
		}
		try {
			url = new URL(url.toURI().toASCIIString());
		} catch (Exception e) {
			throw new RuntimeException("error url:" + url, e);
		}
		return url;
	}

	public static InputStream getResourceAsStream(String name) {
		InputStream resourceStream = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			resourceStream = classLoader.getResourceAsStream(name);
		}
		if (resourceStream == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			resourceStream = classLoader.getResourceAsStream(name);
			if (resourceStream == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtil.class.getClassLoader();
				resourceStream = classLoader.getResourceAsStream(name);
			}
		}
		return resourceStream;
	}



	/**
	 * findMethod 获取对象的方法
	 * @param clazz 超类类型
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static Method findMethod(Class<? extends Object> clazz,
			String methodName, Object[] args) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName)
					&& matches(method.getParameterTypes(), args)) {
				return method;
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return findMethod(superClass, methodName, args);
		}
		return null;
	}

	public static boolean matches(Class<?>[] parameterTypes, Object[] args) {
		if ((parameterTypes == null) || (parameterTypes.length == 0)) {
			return ((args == null) || (args.length == 0));
		}
		if ((args == null) || (parameterTypes.length != args.length)) {
			return false;
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			if ((args[i] != null)
					&& (!parameterTypes[i].isAssignableFrom(args[i].getClass()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取对象的属性
	 * Returns the field of the given class or null if it doesnt exist.
	 */
	public static Field getField(String fieldName, Class<?> clazz) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new RuntimeException("not allowed to access field " + field
					+ " on class " + clazz.getCanonicalName());
		} catch (NoSuchFieldException e) {
			// for some reason getDeclaredFields doesnt search superclasses
			// (which getFields() does ... but that gives only public fields)
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null) {
				return getField(fieldName, superClass);
			}
		}
		return field;
	}

	public static void setField(Field field, Object object, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(false);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		}
	}

	public static ClassLoader getCustomClassLoader() {
		// TODO customerContext.getClassLoader()

		return null;
	}

	public static BufferedReader getBufferReader(File file) throws IOException {
		return new BufferedReader(new FileReader(file));
	}

	public static String getClassNameWithoutPackage(Object object) {
		return getClassNameWithoutPackage(object.getClass());
	}

	public static String getClassNameWithoutPackage(Class<?> clazz) {
		String unqualifiedClassName = cachedNames.get(clazz);
		if (unqualifiedClassName == null) {
			String fullyQualifiedClassName = clazz.getName();
			unqualifiedClassName = fullyQualifiedClassName
					.substring(fullyQualifiedClassName.lastIndexOf('.') + 1);
			cachedNames.put(clazz, unqualifiedClassName);
		}
		return unqualifiedClassName;
	}

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(String pack) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					System.out.println("file类型的扫描-------------------");
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					System.out.println("jar类型的扫描----------------------");
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											// 添加到classes
											addClass(classes, packageName + '.'
													+ className);

										} catch (ClassNotFoundException e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	private static void addClass(Set<Class<?>> classes, String className)
			throws ClassNotFoundException {
		classes.add(Thread.currentThread().getContextClassLoader()
				.loadClass(className));
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					addClass(classes, packageName + '.' + className);
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					// classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName
					// + '.' + className));
				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
}

