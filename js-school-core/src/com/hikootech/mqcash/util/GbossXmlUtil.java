package com.hikootech.mqcash.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;  
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
  
  
 
/**  
 *   
 * GbossXmlUtil  
 *   
 * @function:(解析国政通接口返回数据，并封装成bean对象)  
 * @create time:Oct 9, 2015 2:54:54 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class GbossXmlUtil {  
	private static Logger log = LoggerFactory.getLogger(GbossXmlUtil.class);

    /** 
     * xml字符串转换成bean对象 
     *  
     * @param xmlStr xml字符串 
     * @param clazz 待转换的class 
     * @return 转换后的对象 
     */  
    public static Object xmlStrToBean(String xmlStr, Class clazz) {  
        Object obj = null;  
        try {  
            // 将xml格式的数据转换成Map对象  
            Map<String, Object> map = xmlStrToMap(xmlStr);  
            //将map对象的数据转换成Bean对象  
            obj = mapToBean(map, clazz);  
        } catch(Exception e) {  
            e.printStackTrace();  
        }  
        return obj;  
    }  
      
    /** 
     * 将xml格式的字符串转换成Map对象 
     *  
     * @param xmlStr xml格式的字符串 
     * @return Map对象 
     * @throws Exception 异常 
     */  
    public static Map<String, Object> xmlStrToMap(String xmlStr) throws Exception {  
        if("".equals(xmlStr)) {  
            return null;  
        }  
        Map<String, Object> map = new HashMap<String, Object>();  
        //将xml格式的字符串转换成Document对象  
        Document doc = DocumentHelper.parseText(xmlStr);  
        //获取根节点  
        Element root = doc.getRootElement();  
        //获取根节点下的所有元素  
        List children = root.elements();  
        //循环所有子元素  
        if(children != null && children.size() > 0) {  
            for(int i = 0; i < children.size(); i++) {  
                Element child = (Element)children.get(i);  
               // map.put(child.getName(), child.getTextTrim()); 
                List childrensList = child.elements(); 
                
                if ("message".equals(child.getName())) { //第一级子节点为message
                      
                      if(childrensList != null && childrensList.size() > 0) {  
                          for(int j = 0; j < childrensList.size(); j++) {  
                              Element childrens2 = (Element)childrensList.get(j);  
                              
                              if ("status".equals(childrens2.getName())) {
                            	  childrens2.setName("queryStatus");
								 }
                              
                              map.put(childrens2.getName(), childrens2.getTextTrim());  
                          }  
                      }  
				}
                
                	//国政通学历信息信用解析
                if ("eduInfos".equals(child.getName())) { //第一级子节点为eduInfos
                	
                	if(childrensList != null && childrensList.size() > 0) {  
                        for(int j = 0; j < childrensList.size(); j++) {  
                            Element childrens2 = (Element)childrensList.get(j);  
                            List childrens2List = childrens2.elements(); 
                            
                            if(childrens2List != null && childrens2List.size() > 0) {  
                                for(int k = 0; k < childrens2List.size(); k++) {  
                                    Element childrens3 = (Element)childrens2List.get(k);  
                                    map.put(childrens3.getName(), childrens3.getTextTrim());
                                    
                                    if ("message".equals(childrens3.getName())) {
                                    	  List childrens3List = childrens3.elements(); 
                                    	 if(childrens3List != null && childrens3List.size() > 0) {  
                                             for(int m = 0; m < childrens3List.size(); m++) {  
                                                 Element childrens4 = (Element)childrens3List.get(m); 
                                                 
                                                 if ("status".equals(childrens4.getName())) {
                                                	 childrens4.setName("resultStatus");
                   								 }
                                                 
                                                 map.put(childrens4.getName(), childrens4.getTextTrim());  
                                             }  
                                         }  
									}
                                }  
                            }  
                            
                        }  
                    }  
					
				}
                
                //国政通手机信用用户解析
           	 if ("telCredits".equals(child.getName())) { //第一级子节点为eduInfos
             	
             	if(childrensList != null && childrensList.size() > 0) {  
                     for(int j = 0; j < childrensList.size(); j++) {  
                         Element childrens2 = (Element)childrensList.get(j); 
                         System.out.println(childrens2.attributeValue("mobile"));
                         map.put("mobileNumber", childrens2.attributeValue("mobile")); 
                         List<Element> childrens2List = childrens2.elements(); 
                         List<Object> lists = null;
                         if(childrens2List != null && childrens2List.size() > 0) { 
                         	 lists = new ArrayList<Object>();
                         	 Class<?> cl = (Class<?>) Class.forName("com.hikootech.mqcash.po.UserMobileCreditDetailInfo");
                             for(int k = 0; k < childrens2List.size(); k++) {  
                                 Element childrens3 = (Element)childrens2List.get(k);  
                                 map.put(childrens3.getName(), childrens3.getTextTrim());
                                 
                                 if ("message".equals(childrens3.getName())) {
                                 	  List<Element> childrens3List = childrens3.elements(); 
                                 	 if(childrens3List != null && childrens3List.size() > 0) {  
                                          for(int m = 0; m < childrens3List.size(); m++) {  
                                              Element childrens4 = (Element)childrens3List.get(m); 
                                              
                                              if ("status".equals(childrens4.getName())) {
                                             	 childrens4.setName("queryStatus");
                								 }
                                              
                                              map.put(childrens4.getName(), childrens4.getTextTrim());  
                                          }  
                                      }  
									}
                                 
                                
                                 
                                 	 if ("telCreditDetail".equals(childrens3.getName())) {
                                 		
                   							List<Element> li = childrens3.elements();
                   							
                   									
                   							Object ob = cl.newInstance();
                   							for (Element element2 : li) {
                   								String name = element2.getName();
                   								String value = element2.getText();
                   								Field field = ob.getClass().getDeclaredField(name);
                   								
                   								field.setAccessible(true);
                   								convertValue(field,ob,value);
                   							}
                   							lists.add(ob);
                   						}
                                 
                             }  
                             map.put(cl.getSimpleName(), lists);
                         }  
                         
                     }  
                 }  
					
				}
           	 map.put(child.getName(), child.getTextTrim());
              
                
            }  
        }  
        return map;  
    }  
      
    /** 
     * 将Map对象通过反射机制转换成Bean对象 
     *  
     * @param map 存放数据的map对象 
     * @param clazz 待转换的class 
     * @return 转换后的Bean对象 
     * @throws Exception 异常 
     */  
    public static Object mapToBean(Map<String, Object> map, Class clazz) throws Exception {  
        Object obj = clazz.newInstance();  
        if(map != null && map.size() > 0) {  
            for(Map.Entry<String, Object> entry : map.entrySet()) {  
                String propertyName = entry.getKey();  
                Object value = entry.getValue();  
                String setMethodName = "set"  
                        + propertyName.substring(0, 1).toUpperCase()  
                        + propertyName.substring(1);  
                Field field = getClassField(clazz, propertyName);  
                if (field != null) {
                	 Class fieldTypeClass = field.getType();  
                     value = convertValType(value, fieldTypeClass);  
                     clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);  
				}
               
            }  
        }  
        return obj;  
    }  
      
    /** 
     * 将Object类型的值，转换成bean对象属性里对应的类型值 
     *  
     * @param value Object对象值 
     * @param fieldTypeClass 属性的类型 
     * @return 转换后的值 
     */  
    private static Object convertValType(Object value, Class fieldTypeClass) {  
        Object retVal = null;  
        if(Long.class.getName().equals(fieldTypeClass.getName())  
                || long.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Long.parseLong(value.toString());  
        } else if(Integer.class.getName().equals(fieldTypeClass.getName())  
                || int.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Integer.parseInt(value.toString());  
        } else if(Float.class.getName().equals(fieldTypeClass.getName())  
                || float.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Float.parseFloat(value.toString());  
        } else if(Double.class.getName().equals(fieldTypeClass.getName())  
                || double.class.getName().equals(fieldTypeClass.getName())) {  
            retVal = Double.parseDouble(value.toString());  
        } else {  
            retVal = value;  
        }  
        return retVal;  
    }  
  
    /** 
     * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类) 
     *  
     * @param clazz 指定的class 
     * @param fieldName 字段名称 
     * @return Field对象 
     */  
    private static Field getClassField(Class clazz, String fieldName) {  
        if( Object.class.getName().equals(clazz.getName())) {  
            return null;  
        }  
        Field []declaredFields = clazz.getDeclaredFields();  
        for (Field field : declaredFields) {  
            if (field.getName().equals(fieldName)) {  
                return field;  
            }  
        }  
  
        Class superClass = clazz.getSuperclass();  
        if(superClass != null) {// 简单的递归一下  
            return getClassField(superClass, fieldName);  
        }  
        return null;  
    }   
    
    
    public static void convertValue(Field field, Object obj, String value)
            throws Exception {

        if (field.getGenericType().toString().equals("class java.lang.Integer")) {
            field.set(obj, Integer.parseInt(value));
        }  else {
            field.set(obj, value);
        }
    }
    
    
    
    /**
	* 将字符串转为图片
	* @param imgStr
	* @return
	*/
	public static boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		
	if (imgStr == null) // 图像数据为空
	return false;
	BASE64Decoder decoder = new BASE64Decoder();
	try {
	// Base64解码
	byte[] b = decoder.decodeBuffer(imgStr);
	for (int i = 0; i < b.length; ++i) {
	if (b[i] < 0) {// 调整异常数据
	b[i] += 256;
	}
	}
	// 生成jpeg图片
	String imgFilePath = imgFile;// 新生成的图片
	OutputStream out = new FileOutputStream(imgFilePath);
	log.info("生成成功");
	out.write(b);
	out.flush();
	out.close();
	return true;
	} catch (Exception e) {
	throw e;
	}
}
	
	
    /**  
     * savePhoto(将图片保存)  
     * @param path	存放图片的路径，可配置
     * @param photoStr	图片字符串格式
     * @param photoName   生成后的图片名称
     * void 
     * @create time： 2015年10月14日 上午10:44:25 
     * @author：张海达  
     * @since  1.0.0
     */
    public static String  savePhoto(String path,String photoStr ,String photoName,String datePath) {
    	
    	//创建目录
    	   try {
   			
   		   	 File file = new File(path + "/" + datePath);
   	    	   if(!file.exists()){
   	    	    file.mkdirs();
   	    	   }
   			if (generateImage(photoStr,path + "/" + datePath + "/" + photoName)) {
   				log.info("生成照片地址绝对路径为：" + path + "/" + datePath + "/" + photoName);
				return datePath + "/" + photoName;
			}
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
    	   return null;
    }
    
    
    /**
     * 判断该手机号码是否是移动手机号段<br>
     *
     * @param phone
     * @return true or false
     * @throws Exception
     */
    public static boolean isMobileNumber(String phone) throws Exception {
        boolean isExist = false;

        phone = phone.trim();
        if (phone == null || phone.length() < 7) {
            try {
                throw new Exception("wrong phone length");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String code = phone.substring(0, 7);

        // 134、135、136、137、 138、139、158、159、157（TD专属号段）、150、151、187（3G）、188（3G）
        if (code.startsWith("134") || code.startsWith("135")
                || code.startsWith("136") || code.startsWith("137")
                || code.startsWith("138") || code.startsWith("139")
                || code.startsWith("159") || code.startsWith("158")
                || code.startsWith("150") || code.startsWith("157")
                || code.startsWith("151") || code.startsWith("188")
                || code.startsWith("187")) {
            isExist = true;
        }
        return isExist;

    }
    
    public static void main(String[] args) {
    	String month = "36月";
			Pattern pattern = Pattern.compile("\\d*");
			Matcher m = pattern.matcher(month);
			
/*			List<String> result=new ArrayList<String>();
			while(m.find()){
				result.add(m.group());
			}
			for(String s1:result){
				System.out.println(s1);
			}*/
	        while (m.find()) {
	            System.out.println(m.group());
	        }
			
	}
    
/*    public static void main(String[] args) {
        String str = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd";
        Pattern pattern = Pattern.compile("http://.*(?=TR/xhtml1.*)");
        Matcher matcher = pattern.matcher(str);
         
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }*/
    
}  