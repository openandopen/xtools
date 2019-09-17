package com.dj.xtool.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 * <p>
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-14 16:24
 */
public class GsonUtil {


    private static volatile Gson gson = null;

    static {
        if (gson == null) {
            GsonBuilder builder = gson.newBuilder();
            //时间转化为特定格式
            builder.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            //对结果进行格式化，增加换行
            builder.setPrettyPrinting();
            //防止特殊字符出现乱码
            builder.disableHtmlEscaping();
            //不对没有用@Expose注解的属性进行操作
            builder.excludeFieldsWithoutExposeAnnotation();
            //当Map的key为复杂对象时,需要开启该方法
            builder.enableComplexMapKeySerialization();
            //当字段值为空或null时，依然对该字段进行转换
            builder.serializeNulls();
            //   .registerTypeAdapter(User.class, new UserAdapter()) //为某特定对象设置固定的序列或反序列方式，自定义Adapter需实现JsonSerializer或者JsonDeserializer接口
            gson = builder.create();

        }
    }

    private GsonUtil() {
    }

    /**
     * 对象转成json
     *
     * @param object
     * @return json
     */
    public static String toJson(Object object) {
        String json = null;
        if (gson != null) {
            json = gson.toJson(object);
        }
        return json;
    }

    /**
     * Json转成对象
     *
     * @param json
     * @param cls
     * @return 对象
     */
    public static <T> T toBean(String json, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, cls);
        }
        return t;
    }

    /**
     * json转成list<T>
     *
     * @param json
     * @param cls
     * @return list<T>
     */
    public static <T> List<T> gsonToList(String json, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * json转成list中有map的
     *
     * @param json
     * @return List<Map < String, T>>
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String json) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * json转成map的
     *
     * @param json
     * @return Map<String, T>
     */
    public static <T> Map<String, T> gsonToMaps(String json) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}

