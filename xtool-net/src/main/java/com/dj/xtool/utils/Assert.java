package com.dj.xtool.utils;



import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 描述:
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2018-10-17 17:27
 */
public enum Assert {
    /**
     * 实例
     */
    INS;
    /**
     * 邮箱
     */
    public Pattern EMAIL = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    /**
     * 手机号
     */
    public Pattern MOBILE = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|19[0|1|2|3|5|6|7|8|9])\\d{8}$");

    /**
     * 身份证
     */
    public Pattern IDENTITY_CARD = Pattern.compile("^\\d{15}|\\d{18}$");

    /**
     * 字符串不能为空
     *
     * @param message  断言字符串
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息
     */
    public Assert notEmpty(String message, RuntimeException e, String errorMsg) {
        if (StringUtils.isBlank(message)) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notEmpty(String message, String errorMsg) {
        if (StringUtils.isBlank(message)) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }

    /**
     * 对象不能为空
     *
     * @param obj      断言对象
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源对象
     */
    public Assert notEmpty(Object obj, RuntimeException e, String errorMsg) {
        if (obj == null) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notEmpty(Object obj, String errorMsg) {
        if (obj == null) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }

    /**
     * 集合不能为空
     *
     * @param cols     断言集合
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源集合
     */
    public Assert notEmpty(Collection<?> cols, RuntimeException e,
                           String errorMsg) {
        if (cols == null || cols.isEmpty()) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notEmpty(Collection<?> cols,
                           String errorMsg) {
        if (cols == null || cols.isEmpty()) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }

    /**
     * map 对象不能为空
     *
     * @param map      断言MAP
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源MAP
     */
    public Assert notEmpty(Map<?, ?> map, RuntimeException e, String errorMsg) {
        if (map == null || map.isEmpty()) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notEmpty(Map<?, ?> map, String errorMsg) {
        if (map == null || map.isEmpty()) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }

    /**
     * 字符串 message1  message2 是否相等
     *
     * @param message1 字符串
     * @param message2 字符串
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息(message1)
     */
    public Assert notEqual(String message1, String message2, RuntimeException e, String errorMsg) {
        if (StringUtils.equals(message1, message2)) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notEqual(String message1, String message2, String errorMsg) {
        if (StringUtils.equals(message1, message2)) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }

    /**
     * 字符串 message1  message2 是否相等 (忽略大小写)
     *
     * @param message1 字符串
     * @param message2 字符串
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息(message1)
     */
    public Assert notEqualIgnoreCase(String message1, String message2, RuntimeException e, String errorMsg) {
        if (StringUtils.equalsIgnoreCase(message1, message2)) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notEqualIgnoreCase(String message1, String message2, String errorMsg) {
        if (StringUtils.equalsIgnoreCase(message1, message2)) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }

    /**
     * 正则匹配
     *
     * @param message  断言字符串
     * @param regexp   正则表达式对象
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息
     */
    public Assert notMatch(String message, Pattern regexp, RuntimeException e, String errorMsg) {
        notEmpty(regexp, null, "regexp can't be null!");
        if (!regexp.matcher(message).matches()) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notMatch(String message, Pattern regexp, String errorMsg) {
        notEmpty(regexp, null, "regexp can't be null!");
        if (!regexp.matcher(message).matches()) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }

    /**
     * 正则匹配
     *
     * @param message  断言字符串
     * @param regexp   正则表达式(字符串)
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息
     */
    public Assert notMatch(String message, String regexp, RuntimeException e, String errorMsg) {
        Pattern pattern = Pattern.compile(regexp);
        if (!pattern.matcher(message).matches()) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notMatch(String message, String regexp, String errorMsg) {
        Pattern pattern = Pattern.compile(regexp);
        if (!pattern.matcher(message).matches()) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }


    /**
     * 对象不相等
     *
     * @param obj1     对象1
     * @param obj2     对象2
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源对象obj1
     */
    public Assert notEqual(Object obj1, Object obj2, RuntimeException e, String errorMsg) {
        notEmpty(obj1, null, "obj1 can't be null!").notEmpty(obj2, null, "obj2 can't be null!");
        if (!obj1.equals(obj2)) {
            exceptionHandler(e, errorMsg);
        }
        return this;
    }

    public Assert notEqual(Object obj1, Object obj2, String errorMsg) {
        notEmpty(obj1, null, "obj1 can't be null!").notEmpty(obj2, null, "obj2 can't be null!");
        if (!obj1.equals(obj2)) {
            exceptionHandler(null, errorMsg);
        }
        return this;
    }


    /**
     * 直接抛出异常
     *
     * @param e
     * @param errorMsg
     */
    public void exception(RuntimeException e, String errorMsg) {
        if (e != null) {
            throw e;
        } else {
            throw new IllegalArgumentException(errorMsg);
        }
    }


    /**
     * 异常处理
     *
     * @param e
     * @param errorMsg
     */
    private void exceptionHandler(RuntimeException e, String errorMsg) {
        if (e != null) {
            throw e;
        } else {
            throw new IllegalArgumentException(errorMsg);
        }
    }


}
