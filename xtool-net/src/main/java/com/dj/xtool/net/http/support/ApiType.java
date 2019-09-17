package com.dj.xtool.net.http.support;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

/**
 * 描述:
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2019-01-26 0:31
 */
public  class ApiType<T> extends TypeReference<T> {
    private Type type;

    public ApiType(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }
}
