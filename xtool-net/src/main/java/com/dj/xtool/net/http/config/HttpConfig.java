package com.dj.xtool.net.http.config;

import java.io.Serializable;
import java.net.Proxy;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.EventListener;
import okhttp3.Interceptor;

/**
 * 描述:
 * <p>
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-14 11:55
 */
public class HttpConfig implements Serializable {

    public static int CONNECTION_TIMEOUT = 50000;
    public static  int CALL_TIMEOUT = 60000;


    /**
     * 服务根地址
     */
    private String baseUrl;

    private List<Interceptor> interceptors;

    /**
     * 网络拦截
     */
    private List<Interceptor> networkInterceptors;

    /**
     * 认证
     */
    private Authenticator authenticator;

    /**
     * 调用超时(ms)
     */
    private int callTimeout = CALL_TIMEOUT;

    /**
     *
     */
    private EventListener eventListener;
    /**
     * 连接超时(ms)
     */
    private int connectTimeout = CONNECTION_TIMEOUT;

    /**
     * 代理
     */
    private Proxy proxy;

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public HttpConfig setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public HttpConfig setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public List<Interceptor> getNetworkInterceptors() {
        return networkInterceptors;
    }

    public HttpConfig setNetworkInterceptors(List<Interceptor> networkInterceptors) {
        this.networkInterceptors = networkInterceptors;
        return this;
    }

    public int getCallTimeout() {
        return callTimeout;
    }

    public HttpConfig setCallTimeout(int callTimeout) {
        this.callTimeout = callTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public HttpConfig setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
        return this;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public HttpConfig setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }
}
