package com.dj.xtool.net.http.support;

import com.dj.xtool.net.http.config.HttpConfig;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 描述:
 * 客户端代理
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2019-01-22 21:04
 */
public class ClientProxy<T> extends ClientSupport implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 7180456064046071954L;

    private Class<T> apiInterface;


    public ClientProxy(Class<T> apiInterface, HttpConfig okHttpConfig) {
        this.apiInterface = apiInterface;
        super.setHttpConfig(okHttpConfig);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> methodCls = method.getDeclaringClass();
        if (Object.class.equals(methodCls)) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                throw unwrapThrowable(t);
            }
        }
        return execute(cachedApiInfo(method), args);

    }




    private Throwable unwrapThrowable(Throwable wrapped) {
        Throwable unwrapped = wrapped;
        while (true) {
            if (unwrapped instanceof InvocationTargetException) {
                unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
            } else if (unwrapped instanceof UndeclaredThrowableException) {
                unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
            } else {
                return unwrapped;
            }
        }
    }

    public Class<T> getApiInterface() {
        return apiInterface;
    }

    public ClientProxy<T> setApiInterface(Class<T> apiInterface) {
        this.apiInterface = apiInterface;
        return this;
    }
}
