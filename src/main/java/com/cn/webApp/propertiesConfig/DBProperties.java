
package com.cn.webApp.propertiesConfig;

import java.util.Properties;
import java.util.Set;

public class DBProperties {

	public DBProperties() {
	}

	public static void put(String key, Object value) {
		prop.put(key, value);
	}

	public static Object get(String key) {
		return prop.get(key);
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	@SuppressWarnings("rawtypes")
	public static Set keys() {
		return prop.keySet();
	}

	private static Properties prop = new Properties();

}
