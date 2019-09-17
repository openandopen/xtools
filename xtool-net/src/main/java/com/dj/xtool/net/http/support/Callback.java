package com.dj.xtool.net.http.support;

/**
 * 描述:
 * <p>
 * HTTP响应回调，其中T 为返回类型
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-15 18:49
 */
public interface Callback<T> {


    public void accept(T result, Exception e);

}
