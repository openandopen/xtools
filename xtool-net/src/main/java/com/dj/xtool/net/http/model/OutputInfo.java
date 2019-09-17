package com.dj.xtool.net.http.model;

import java.io.Serializable;

/**
 * 描述:
 * <p>
 * 输出
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-16 14:34
 */
public class OutputInfo<T> implements Serializable {

    private T result;

    /**
     * 后台返回异常信息
     */
    private Exception exception;

    public T getResult() {
        return result;
    }

    public OutputInfo<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public OutputInfo<T> setException(Exception exception) {
        this.exception = exception;
        return this;
    }
}
