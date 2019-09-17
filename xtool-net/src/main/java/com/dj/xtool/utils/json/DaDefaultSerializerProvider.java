package com.dj.xtool.utils.json;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * TODO liudejian 类描述.
 *
 * @author : <a href="mailto:dejianliu@bkjk.com">liudejian</a>
 * @version : Ver 1.0
 * @date : 2015-10-15 下午4:28:55
 */
public class DaDefaultSerializerProvider extends DefaultSerializerProvider {


    private static final long serialVersionUID = -2891437067035919790L;

    public DaDefaultSerializerProvider() {
        super();
    }

    public DaDefaultSerializerProvider(DaDefaultSerializerProvider src) {
        super(src);
    }

    protected DaDefaultSerializerProvider(SerializerProvider src,
                                          SerializationConfig config, SerializerFactory f) {
        super(src, config, f);
    }

    @Override
    public DefaultSerializerProvider copy() {
        if (getClass() != DaDefaultSerializerProvider.class) {
            return super.copy();
        }
        return new DaDefaultSerializerProvider(this);
    }

    @Override
    public DaDefaultSerializerProvider createInstance(
            SerializationConfig config, SerializerFactory jsf) {
        return new DaDefaultSerializerProvider(this, config, jsf);
    }
}
