package com.cyg.framework.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 系统配置文件读取工具
 * @author 王道兵
 * @date 2014-8-14
 */
public class SystemPropertiesUtil {

	private static final Logger log = LoggerFactory.getLogger(SystemPropertiesUtil.class);

	public static final String CONFIG_FILE = "system.xml";

	public static PropertiesHelper helper = null;

	static {
		try {
			helper = new PropertiesHelper();
			helper.loadStaticPropertiesFile(CONFIG_FILE);
			log.debug("装载主配置文件成功");
			String sp = helper.getProperty("extend.static.file");
			if (StringUtil.isNotBlank(sp)) {
				if (sp.contains(",")) {
					helper.loadStaticPropertiesFile(sp.split(","));
				} else {
					helper.loadStaticPropertiesFile(sp);
				}
			}
			log.debug("装载静态扩展文件成功");
			String dp = helper.getProperty("extend.dynamic.file");
			if (StringUtil.isNotBlank(dp)) {
				if (dp.contains(",")) {
					helper.loadDynamicPropertiesFile(dp.split(","));
				} else {
					helper.loadDynamicPropertiesFile(dp);
				}
			}
			log.debug("装载动态扩展文件成功");
			if (log.isDebugEnabled()) {
				log.debug("---------------------静态属性-------------------");
				Map<String, String> sps = helper.getStaticProperties();
				for (Map.Entry<String, String> en: sps.entrySet()) {
					log.debug("key:" + en.getKey() + "------------------value:"
							+ en.getValue());
				}
				log.debug("---------------------动态属性-------------------");
				Map<String, String> dps = helper.getDynamicProperties();
				for (Map.Entry<String, String> en: dps.entrySet()) {
					log.debug("key:" + en.getKey() + "------------------value:"
							+ en.getValue());
				}
			}
		} catch (IOException e) {
			try {
				throw new Exception("加载系统配置文件时发生错误", e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	

	public static String getProperty(String key) {
		return helper.getProperty(key);
	}

	public static String getRequiredProperty(String key) {
		String value=SystemPropertiesUtil.getProperty(key);
		if (value == null || "".equals(value.trim())) {
			throw new IllegalStateException(key + "的值不能为空");
		}
		return value;
	}

	public static int getRequiredInt(String key) {
		String value=SystemPropertiesUtil.getRequiredProperty(key);
		return Integer.parseInt(value);
	}

	public static boolean getRequiredBoolean(String key) {
		String value=SystemPropertiesUtil.getRequiredProperty(key);
		return Boolean.parseBoolean(value);
	}

	public static String getProperty(String key, String defaultValue) {
		String value=SystemPropertiesUtil.getProperty(key);
		if (value == null || "".equals(value.trim())) {
			return defaultValue;
		}
		return value;
	}
	
	 public static PropertiesHelper getHelper() {
		return helper;
	}

	public static class PropertiesHelper {

		private Map<String, Map<String, String>> sv=new HashMap<>();

		private Map<String, Map<String, String>> dv=new HashMap<>();
		
		private Map<String,String> dvm=new TreeMap<>();
		
		private Map<String,String> svm=new TreeMap<>();
		
		private Map<String,String> propertiesMap=new HashMap<>();
		
		private String getProperty(String key){
			return propertiesMap.get(key);
		}
		
		/**
		 * 加载静态文件
		 * @param resourceNames
		 * @throws IOException
		 */
		private void loadStaticPropertiesFile(String... resourceNames)
				throws IOException {
			for (String resourceName : resourceNames) {
				try {
					Properties loadProperties = loadProperties(resourceName);
					Map<String, String> propertys = getPropertys(loadProperties);
					sv.put(resourceName, propertys);
				} catch (IOException e) {
					log.error("加载【" + resourceName + "】时发生错误。", e);
					throw e;
				}
			}
			syncPropertiesMap();
		}

		/**
		 * 加载动态文件
		 * @param resourceNames
		 * @throws IOException
		 */
		private void loadDynamicPropertiesFile(String... resourceNames)
				throws IOException {
			for (String resourceName : resourceNames) {
				try {
					Properties loadProperties = loadProperties(resourceName);
					Map<String, String> propertys = getPropertys(loadProperties);
					dv.put(resourceName, propertys);
				} catch (IOException e) {
					log.error("加载【" + resourceName + "】时发生错误。", e);
					throw e;
				}
			}
			syncPropertiesMap();
		}
		
		public Map<String,String> getStaticProperties(){
			return Collections.unmodifiableSortedMap((SortedMap<String, String>)svm);
		}
		
		public Map<String,String> getDynamicProperties(){
			return Collections.unmodifiableSortedMap((SortedMap<String, String>)dvm);
		}
		
		public void updateProperties(String key, String value) {
			updateProperties(key,value,null);
		}

		public void updateProperties(String key, String value, String comment) {
			OutputStream out = null;
			try {
				String resourceName = findResourceName(key);
				Properties p = loadProperties(resourceName);
				p.setProperty(key, value);
				out = getOutputStream(resourceName);
				if(resourceName.endsWith("xml")){
					p.storeToXML(out, comment);
				}else{
					p.store(out, comment);
				}
				Map<String, String> propertys = getPropertys(p);
				dv.put(resourceName, propertys);
				syncPropertiesMap();
			} catch (Exception e) {
				log.error(ExceptionUtil.getExceptionMessage(e));
			} finally {
				try {
					if(out!=null)out.close();
				} catch (IOException e) {
				}
			}
		}
		
		private void syncPropertiesMap(){
			if(sv!=null){
				Collection<Map<String, String>> values = sv.values();
				for(Map<String,String> v:values)
					svm.putAll(v);
			}
			if(dv!=null){
				Collection<Map<String, String>> values = dv.values();
				for(Map<String,String> v:values)
					dvm.putAll(v);
			}
			propertiesMap.putAll(svm);
			propertiesMap.putAll(dvm);
		}

		private String findResourceName(String key) {
			for (Map.Entry<String, Map<String, String>> en : dv.entrySet()) {
				Map<String, String> properMap = en.getValue();
				if (properMap.containsKey(key))
					return en.getKey();
			}
			throw new IllegalStateException("没有资源文件与【 "+key+" 】匹配");
		}

		private Map<String, String> getPropertys(Properties p) {
			Map<String, String> map = new HashMap<String, String>();
			for (String propertyName : p.stringPropertyNames()) {
				map.put(propertyName, p.getProperty(propertyName));
			}
			return map;
		}

		private Properties loadProperties(String resourceName)
				throws IOException {
			Properties p = new Properties();
			InputStream input = null;
			try {
				input = getInputStream(resourceName);
				if (resourceName.endsWith(".xml")) {
					p.loadFromXML(input);
				} else {
					p.load(input);
				}
			} catch(Exception e){
				log.error(ExceptionUtil.getExceptionMessage(e));
			}
			finally {
				if (input != null) {
					input.close();
				}
			}
			return p;
		}

		private InputStream getInputStream(String resourceName) {
			return getClass().getClassLoader()
					.getResourceAsStream(resourceName);
		}

		private OutputStream getOutputStream(String resourceName) throws FileNotFoundException {
			URL resource = getClass().getClassLoader()
					.getResource(resourceName);
			File file = new File(resource.getFile());
			FileOutputStream out = new FileOutputStream(file);
			return out;
		}
	}
}
