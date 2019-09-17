package com.dj.xtool.net.http.support;

import com.dj.xtool.net.http.model.ApiInfo;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述:
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2019-01-26 15:37
 */
public class ApiClientContext {
    private static final Map<Method, ApiInfo> API_CACHE = new ConcurrentHashMap<Method, ApiInfo>();

    public static Map<Method, ApiInfo> cacheApiInfo() {
        return API_CACHE;
    }

    public static ApiInfo getCacheApiInfo(Method method) {
        return API_CACHE.get(method);
    }

    /**
     *
     * @param method
     * @return
     */
    public static boolean exist(Method method) {
        return API_CACHE.containsKey(method);
    }

    public static void cacheApiInfo(Method method, ApiInfo apiInfo) {
        API_CACHE.put(method, apiInfo);
    }
    public static void cacheAllApiInfo(Map<Method,ApiInfo> apiInfoMap) {
        API_CACHE.putAll(apiInfoMap);
    }
}
