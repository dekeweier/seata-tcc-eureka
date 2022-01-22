package com.test.constatnt.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by 李传伟 on 2022/1/9 17:18
 */
public class ResultHolder {

    private static Map<Class<?>, Map<String, String>> map = new ConcurrentHashMap<Class<?>, Map<String, String>>();

    public static void setResult(Class<?> actionClass, String xid, String v){

        Map<String, String> result = map.get(actionClass);

        if(result == null){
            synchronized (map){
                if(result == null){
                    result = new ConcurrentHashMap<String, String>();

                    map.put(actionClass,result);
                }
            }
        }
        result.put(xid,v);
    }

    public static String getResult(Class<?> actionClass,String xid){
        Map<String, String> result = map.get(actionClass);
        if(result != null){

            return result.get(xid);
        }

        return null;
    }

    public static void removeResult(Class<?> actionClass,String xid){
        Map<String, String> result = map.get(actionClass);

        if(result != null){
            result.remove(xid);
        }
    }



}
