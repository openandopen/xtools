package com.dj.xtool.net.http;


import com.dj.xtool.net.http.config.HttpConfig;
import com.dj.xtool.net.http.support.ClientApiFactory;

/**
 * 描述:
 * <p>
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-14 16:50
 */
public class RequestUtil {

    /**
     *
     * @param config
     */
    public static void initConfig(HttpConfig config) {
        ClientApiFactory.initHttpConfig(config);

    }

    /**
     * 获取包装接口
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T wrapGet(Class<T> service) {
       return ClientApiFactory.newInstance(service);
    }




}
