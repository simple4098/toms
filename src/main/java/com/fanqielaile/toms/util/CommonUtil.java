package com.fanqielaile.toms.util;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static Map<String, Object> setSuccessInfo() {
        return setInfo(null, null, Constants.HTTP_SUCCESS, Constants.SUCCESS);
    }

    public static Map<String, Object> setSuccessInfo(Map<String, Object> result) {
        return setInfo(result, null, Constants.HTTP_SUCCESS, Constants.SUCCESS);
    }

    public static Map<String, Object> setSuccessInfo(Map<String, Object> result, String message, String type) {
        return setInfo(result, message, Constants.HTTP_SUCCESS, type);
    }

    public static Map<String, Object> setErrorInfo(String message) {
        return setInfo(null, message, Constants.HTTP_ERROR, Constants.FAILURE);
    }

    public static Map<String, Object> setErrorInfo(String message, String type) {
        return setInfo(null, message, Constants.HTTP_ERROR, type);
    }

    public static Map<String, Object> setErrorInfo(Map<String, Object> result, String message) {
        return setInfo(result, message, Constants.HTTP_ERROR, Constants.FAILURE);
    }

    public static Map<String, Object> setErrorInfo(Map<String, Object> result, String message, String type) {
        return setInfo(result, message, Constants.HTTP_ERROR, type);
    }

    public static Map<String, Object> setError800(Map<String, Object> result, String message) {
        return setInfo(result, message, Constants.HTTP_800, Constants.FAILURE);
    }

    /**
     * 设置返回信息
     *
     * @param result
     * @param message 错误信息说明
     * @param status  错误代码
     * @param type    错误代码类型
     */
    public static Map<String, Object> setInfo(Map<String, Object> result, String message,
                                              int status, String type) {
        return setMessage(result, message, status, type);
    }

    private static Map<String, Object> setMessage(Map<String, Object> result, String message,
                                                  int status, String type) {
        if (result == null)
            result = new HashMap<>();
        if (message != null)
            result.put(Constants.MESSAGE, message);
        result.put(Constants.STATUS, status);
        if (type != null)
            result.put(Constants.TYPE, type);
        return result;
    }

    /**
     * 获取配置文件的值
     *
     * @param key properties 文件中的key值
     */
    public static String obtProperties(String key) {
        if (StringUtils.isNotEmpty(key)) {
            Properties p = new Properties();
            InputStream in = CommonUtil.class
                    .getResourceAsStream("/sso-info.properties");
            try {
                p.load(in);
            } catch (IOException e) {
                logger.error("读取配置文件异常:" + e);
            }
            return p.getProperty(key);
        }
        return null;
    }

    /**
     * 获取配置文件的值
     *
     * @param key properties 文件中的key值
     */
    public static String obtProperties(String key, String propertiesUrl) {
        if (StringUtils.isNotEmpty(key)) {
            Properties p = new Properties();
            InputStream in = CommonUtil.class.getResourceAsStream(propertiesUrl);
            try {
                p.load(in);
            } catch (IOException e) {
                logger.error("读取配置文件异常:" + e);
            }
            return p.getProperty(key);
        }
        return null;
    }

    /**
     * 如果集合不为空 返回true
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isListNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    /**
     * 根据name[] value[] 封装List<NameValuePair>
     *
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public static List<NameValuePair> initNameValuePairList(String[] name,
                                                            String[] value) throws Exception {
        if (name.length != value.length)
            throw new RuntimeException("NameValuePair中name长度和value长度不一致");
        List<NameValuePair> list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            BasicNameValuePair nvp = new BasicNameValuePair(name[i], value[i]);
            list.add(nvp);
        }
        return list;
    }

    /**
     * ? 基本数据类型 List<?>转为已逗号分隔的字符串
     */
    public static String ArrayToStrbyComma(
            @SuppressWarnings("rawtypes") List list) {
        StringBuffer st = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                st.append(list.get(i));
            } else {
                st.append(list.get(i) + ",");
            }
        }
        return st.toString();
    }


    /**
     * 以|分隔的字符串转换为指定类型的集合
     *
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> StrByVerticalLineToArray(String str, Class<T> clazz) {
        return StrByCoustomToArray("\\|", str, clazz);
    }

    /**
     * coustom分隔的字符串转换为指定类型的集合
     *
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> StrByCoustomToArray(String partten, String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        String[] strArray = str.split(partten);
        T[] obj = (T[]) Array.newInstance(clazz, strArray.length);
        Method method = null;
        try {
            if (clazz.getName().equals(String.class.getName())) {
                obj = (T[]) strArray;
            } else {
                method = clazz.getMethod("valueOf", String.class);
                for (int i = 0; i < strArray.length; i++) {
                    obj[i] = (T) method.invoke(null, strArray[i]);
                }
            }
        } catch (Exception e) {
            logger.error("StrByCoustomToArray error", e);
        }
        List accList = Arrays.asList(obj);
        return accList;
    }


    /**
     * 以逗号分隔的字符串转换为指定类型的集合
     *
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> StrByCommaToArray(String str, Class<T> clazz) {
        return StrByCoustomToArray(",", str, clazz);
    }

    private static void test2() {
        System.out.println(String.class.getName());
        System.out.println(String.class.getSimpleName());
        System.out.println(String.class.toString());
    }

    public static void test1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        System.out.println(ArrayToStrbyComma(list));
    }

    public static void testArray() {
        String aa = "12,23,13,14";
        List<Integer> list = StrByCommaToArray(aa, Integer.class);
        List<String> list1 = StrByCommaToArray(aa, String.class);
        System.out.println(list);
        System.out.println(list1);
    }


    /**
     * string数组转string字符串，可带分隔符
     *
     * @param param
     * @param patten
     * @return
     */
    public static String arrayConvertToSplitString(String[] param, String patten) {
        StringBuilder result = new StringBuilder();
        if (param.length == 0) {
            return result.toString();
        }
        for (String str : param) {
            if (StringUtils.isNotBlank(str)) {
                result.append(str + patten);
            }
        }
        return result.toString().substring(0, result.toString().length() - 1);
    }

    /**
     * 去掉字符串最后一个符号
     *
     * @param str
     * @param str
     * @return
     */
    public static String substrLastPatten(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return str.substring(0, str.length() - 1);
    }

}
