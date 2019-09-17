package com.dj.xtool.net.http.model;


import com.dj.xtool.net.http.enums.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2018-11-27 13:08
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -6547410396516217748L;

    /**
     * 请求唯一ID
     */
    private String reqUid;

    private long reqTime = System.currentTimeMillis();

    private long resTime = 0;


    /**
     * 响应对象
     */
    private T result;

    /**
     * 响应备注
     */
    private String message;

    /**
     * 响应状态
     */
    private HttpStatus status = HttpStatus.OK;

    /**
     * 响应编码
     */
    private int code;

    /**
     * 响应参数(扩展参数)
     */
    private Map<String, Object> params = new HashMap<String, Object>();


    public Response() {
    }

    public Response(HttpStatus status) {
        this.status = status;
    }


    public Map<String, Object> getParams() {
        return params;
    }



    public Response addParam(String key,Object value) {
        this.params.put(key,value);
        return this;
    }

    public Response setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public T getResult() {
        return result;
    }

    public Response<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public long getReqTime() {
        return reqTime;
    }

    public Response<T> setReqTime(long reqTime) {
        this.reqTime = reqTime;
        return this;
    }

    public long getResTime() {
        return resTime;
    }

    public Response<T> setResTime(long resTime) {
        this.resTime = resTime;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Response<T> setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }



    public String getMessage() {
        if (message == null || "".equals(message)) {
            message = status.getReasonPhrase();
        }
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }


    public int getCode() {
        if (code <= 0) {
            code = this.getStatus().value();
        }
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public String getReqUid() {
        return reqUid;
    }

    public Response<T> setReqUid(String reqUid) {
        this.reqUid = reqUid;
        return this;
    }
}
