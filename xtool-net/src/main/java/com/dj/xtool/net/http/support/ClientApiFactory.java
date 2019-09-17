package com.dj.xtool.net.http.support;

import com.dj.xtool.net.http.config.HttpConfig;

import java.lang.reflect.Proxy;

/**
 * 描述:
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2019-01-22 18:32
 */
public class ClientApiFactory {

    private static HttpConfig okHttpConfig;

    /**
     *
     * @param httpConfig
     */
    public static void initHttpConfig(HttpConfig httpConfig) {
        if(okHttpConfig == null) {
            ClientApiFactory.okHttpConfig = httpConfig;
        }
    }

    public static <T> T newInstance(Class<T> apiInterface) {
        if(okHttpConfig == null) {
            ClientApiFactory.okHttpConfig = new HttpConfig();
            ClientApiFactory.okHttpConfig.setBaseUrl("http://localhost:8089/uc/");
        }
        return newInstance(new ClientProxy<T>(apiInterface,okHttpConfig));
    }

    private static <T> T newInstance(ClientProxy<T> clientProxy) {
        return (T) Proxy.newProxyInstance(clientProxy.getApiInterface().getClassLoader(),
                new Class[]{clientProxy.getApiInterface()}, clientProxy);
    }


}
