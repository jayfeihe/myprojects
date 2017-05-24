package com.gome.bi.monitor.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本工具类
 * 
 * @author QYANZE
 *
 */
public class CommonUtils {

	/**
	 * 对象转map
	 * 
	 * @param obj
	 */
	public static Map<String, Object> obj2Map(Object obj) {
		Class<?> clz = obj.getClass();
		Field[] field = clz.getDeclaredFields();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < field.length; i++) {
			Field f = field[i];
			int mod = f.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }
			f.setAccessible(true);
			try {
				map.put(f.getName(), f.get(obj));
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
