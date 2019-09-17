package com.dj.xtool.net.http.model;




import com.dj.xtool.net.http.enums.HttpMethod;
import com.dj.xtool.utils.textplace.TextPlaceUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 描述:
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2019-01-23 23:24
 */
public class ApiInfo implements Serializable {

    private HttpMethod httpMethod;

    private String reqUri;


    private Type returnType;

    /**
     *
     */
    private Type returnActualType;

    /**
     * 请求类别
     */
    private Class requestType;

    /**
     * 请求实际类别
     */
    private Type requestActualType;

    /**
     * path参数
     * key=name
     * value=参数位置
     */
    private Map<String, Integer> pathParamsMap = new HashMap<>();

    /**
     * url请求参数
     */
    private Map<String, Integer> paramsMap = new HashMap<>();

    /**
     * @RequestBody 的index
     */
    private Integer bodyIndex;





    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public ApiInfo setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getReqUri() {
        return reqUri;
    }

    public ApiInfo setReqUri(String reqUri) {
        this.reqUri = reqUri;
        return this;
    }

    public Type getReturnType() {
        return returnType;
    }

    public ApiInfo setReturnType(Type returnType) {
        this.returnType = returnType;
        return this;
    }

    public Class getRequestType() {
        return requestType;
    }

    public ApiInfo setRequestType(Class requestType) {
        this.requestType = requestType;
        return this;
    }

    public Type getRequestActualType() {
        return requestActualType;
    }

    public ApiInfo setRequestActualType(Type requestActualType) {
        this.requestActualType = requestActualType;
        return this;
    }

    public Type getReturnActualType() {
        return returnActualType;
    }

    public ApiInfo setReturnActualType(Type returnActualType) {
        this.returnActualType = returnActualType;
        return this;
    }

    public Map<String, Integer> getPathParamsMap() {
        return pathParamsMap;
    }

    public ApiInfo setPathParamsMap(Map<String, Integer> pathParamsMap) {
        this.pathParamsMap = pathParamsMap;
        return this;
    }

    public ApiInfo addPathParam(String key, Integer object) {

        pathParamsMap.put(key, object);
        return this;
    }

    public Map<String, Integer> getParamsMap() {
        return paramsMap;
    }

    public ApiInfo setParamsMap(Map<String, Integer> paramsMap) {
        this.paramsMap = paramsMap;
        return this;
    }

    public ApiInfo addParam(String key, Integer object) {

        this.paramsMap.put(key, object);
        return this;
    }

    public Integer getBodyIndex() {
        return bodyIndex;
    }

    public ApiInfo setBodyIndex(Integer bodyIndex) {
        this.bodyIndex = bodyIndex;
        return this;
    }

    /**
     * 解析请求实体对象
     *
     * @param args
     * @return
     */
    public Object parseRequestBody(Object[] args) {
        Object reqObj = null;
        if (args != null) {
            if (bodyIndex != null && bodyIndex < args.length) {
                reqObj = args[bodyIndex];
            } else {
                reqObj = args[0];
            }
        }
        return reqObj;
    }

    /**
     * 补全URL
     *
     * @param pathParams
     * @return
     */
    public String buildUrl(Map<String, Object> pathParams) {
         return TextPlaceUtil.replace(this.getReqUri(),pathParams,"{","}",true);
    }

    /**
     * 解析参数 到 pathmap
     *
     * @param args
     * @return
     */
    public Map<String, Object> parseArgsToPathMap(Object[] args) {
        if (args != null) {
            Map<String, Object> nameValueMap = new HashMap<>();
            if (this.pathParamsMap != null && !this.pathParamsMap.isEmpty()) {
                for (Iterator<Map.Entry<String, Integer>> it = pathParamsMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, Integer> entry = it.next();
                    String key = entry.getKey();
                    Integer index = entry.getValue();
                    if (index < args.length) {
                        nameValueMap.put(key, args[index]);
                    }
                }
            }
            return nameValueMap;
        }
        return null;
    }

    /**
     * 解析参数到 param
     *
     * @param args
     * @return
     */
    public Map<String, Object> parseArgsToParamMap(Object[] args) {
        if (args != null) {
            Map<String, Object> nameValueMap = new HashMap<>();
            if (this.paramsMap != null && !this.paramsMap.isEmpty()) {
                for (Iterator<Map.Entry<String, Integer>> it = paramsMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, Integer> entry = it.next();
                    String key = entry.getKey();
                    Integer index = entry.getValue();
                    if (index < args.length) {
                        nameValueMap.put(key, args[index]);
                    }
                }
            }
            return nameValueMap;
        }
        return null;
    }

}
