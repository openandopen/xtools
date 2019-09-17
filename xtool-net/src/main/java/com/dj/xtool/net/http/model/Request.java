package com.dj.xtool.net.http.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2018-11-27 13:00
 */
public class Request<T> implements Serializable {

    /**
     * 请求唯一ID 可以为空
     */
    private String reqUid;
    /**
     * 请求开始时间 可以为空
     */
    private long reqTime;
    /**
     * 请求实体，不能空
     */
    private T body;

    /**
     * 分页使用，开始索引
     */
    private Integer start;

    /**
     * 限制返回记录数
     */
    private Integer limit;

    /**
     * 扩展参数
     */
    private Map<String,Object>  extendParams;

    public T getBody() {
        return body;
    }



    public Integer getStart() {
        if (start == null || start <= 0) {
            this.start = 0;
        }
        return start;
    }

    public Request<T> setStart(Integer start) {
        this.start = start;
        return this;
    }

    public Integer getLimit() {
        if (limit == null || limit <= 0) {
            this.limit = 10;
        }
        return limit;
    }

    public Request<T> setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public String getReqUid() {
        return reqUid;
    }

    public Request<T> setReqUid(String reqUid) {
        this.reqUid = reqUid;
        return this;
    }

    public long getReqTime() {
        return reqTime;
    }

    public Request<T> setReqTime(long reqTime) {
        this.reqTime = reqTime;
        return this;
    }

    public Request<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public Map<String, Object> getExtendParams() {
        return extendParams;
    }
    public Request<T> addParam(String key,Object value) {
        if(this.extendParams == null) {
            this.extendParams = new HashMap<>();
        }
        this.extendParams.put(key,value);
        return this;
    }
    public Request<T> addParams(Map<String,Object> params) {
        if(this.extendParams == null) {
            this.extendParams = new HashMap<>();
        }
        this.extendParams.putAll(params);
        return this;
    }

    public Request<T> setExtendParams(Map<String, Object> extendParams) {
        this.extendParams = extendParams;
        return this;
    }
}
