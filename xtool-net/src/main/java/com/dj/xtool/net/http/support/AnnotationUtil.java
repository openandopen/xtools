package com.dj.xtool.net.http.support;

import com.dj.xtool.net.http.annotation.PathVariable;
import com.dj.xtool.net.http.annotation.RequestBody;
import com.dj.xtool.net.http.annotation.RequestMapping;
import com.dj.xtool.net.http.annotation.RequestMethod;
import com.dj.xtool.net.http.annotation.RequestParam;
import com.dj.xtool.net.http.enums.HttpMethod;
import com.dj.xtool.net.http.model.ApiInfo;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * <p>
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-14 21:06
 */
public class AnnotationUtil {
    /**
     * @param method
     * @return
     */
    public static ApiInfo parseApiInfo(Method method) {
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setReqUri(parseUri(method));
        apiInfo.setHttpMethod(parseHttpMethod(method));
        apiInfo.setReturnType(method.getReturnType());
        apiInfo.setReturnActualType(method.getGenericReturnType());
        // apiInfo.setReturnActualType(method.getAnnotatedReturnType().getType());
        //  apiInfo.setRequestActualType(method.getAnnotatedReturnType().getType());
        Class<?>[] clssTypes = method.getParameterTypes();
        if (ArrayUtils.isNotEmpty(clssTypes)) {
            apiInfo.setRequestType(clssTypes[0]);
        }
        Type[] types = method.getGenericParameterTypes();
        if (ArrayUtils.isNotEmpty(types)) {
            apiInfo.setRequestActualType(types[0]);
        }
        parseParamAndParamPathInfo(method, apiInfo);
        return apiInfo;
    }


    /**
     * @param method
     * @return
     */
    private static String parseUri(Method method) {
        RequestMapping mapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        StringBuffer buffer = new StringBuffer();
        if (mapping != null) {
            String[] classPaths = mapping.value();
            if (ArrayUtils.isNotEmpty(classPaths)) {
                buffer.append(classPaths[0]);
            }
        }
        mapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] rm = mapping.method();
        if (mapping != null) {
            String[] classPaths = mapping.value();
            String methodPath = classPaths[0];
            if (!buffer.toString().endsWith("/") && !methodPath.startsWith("/")) {
                buffer.append("/");
            } else if (buffer.toString().endsWith("/") && methodPath.startsWith("/")) {
                methodPath = StringUtils.substringAfter(methodPath, "/");
            }
            buffer.append(methodPath);
        }
        return buffer.toString();
    }



    private static void parseParamAndParamPathInfo(Method method, ApiInfo apiInfo) {
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        if (mapping == null) {
            return;
        }
        String[] paramNames = mapping.params();
        //获取params
        if (paramNames != null) {
            int index =0;
            for (String pName : paramNames) {
                apiInfo.addParam(pName, index++);
            }
        }
        //获取注解param
        Annotation[][] annotations = method.getParameterAnnotations();
        if (annotations != null) {
            int index = 0;
            for (Annotation[] annos : annotations) {
                for (Annotation anno : annos) {
                    if (anno.annotationType() == RequestParam.class) {
                        RequestParam rp = (RequestParam) anno;
                        String name = rp.value();
                        apiInfo.addParam(name, index);
                    } else if (anno.annotationType() == PathVariable.class) {
                        PathVariable rp = (PathVariable) anno;
                        String name = rp.value();
                        apiInfo.addPathParam(name, index);
                    } else if (anno.annotationType() == RequestBody.class) {
                        apiInfo.setBodyIndex(index);
                    }
                }
                index++;
            }
        }
        //获取参数，定位位置

        Parameter[] parameters = method.getParameters();
        if (parameters != null) {
            int index = 0;
            for (Parameter parameter : parameters) {
                Object obj = apiInfo.getParamsMap().get(parameter.getName());
                if (obj != null) {
                    //记录参数名与args的下标位置
                    apiInfo.getParamsMap().put(parameter.getName(), index);
                }
                Object objPath = apiInfo.getPathParamsMap().get(parameter.getName());
                if (objPath != null) {
                    apiInfo.getPathParamsMap().put(parameter.getName(), index);
                }
                index++;
            }
        }
    }

    /**
     *
     * @param method
     * @return
     */
    private static HttpMethod parseHttpMethod(Method method) {
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        if (mapping == null) {
            return null;
        }
        RequestMethod[] rms = mapping.method();
        HttpMethod httpMethod = null;
        if (rms != null) {
            httpMethod = HttpMethod.resolve(rms[0].toString());
        } else {
            List<Class> annoParamTypes = parseAnnotationParamType(method);
            if (annoParamTypes != null && annoParamTypes.size() > 0) {
                if (annoParamTypes.contains(RequestBody.class)) {
                    httpMethod = HttpMethod.POST;
                } else {
                    httpMethod = HttpMethod.GET;
                }
            } else {
                Class[] clss = method.getParameterTypes();
                if (clss == null) {
                    httpMethod = HttpMethod.GET;
                } else {
                    httpMethod = HttpMethod.GET;
                    for (Class cls : clss) {
                        if (!isBaseType(cls)) {
                            httpMethod = HttpMethod.POST;
                        }
                    }
                }
            }
        }
        return httpMethod;
    }

    private static boolean isBaseType(Class cls) {
        if (cls == int.class || cls == Integer.class
                || cls == String.class || cls == BigDecimal.class
                || cls == Boolean.class || cls == boolean.class
                || cls == long.class || cls == Long.class || cls == Date.class
                || cls == byte.class || cls == Byte.class || cls == float.class || cls == Float.class
                || cls == double.class || cls == Double.class) {
            return true;
        }
        return false;
    }

    /**
     * @param method
     * @return
     */
    private static List<Class> parseAnnotationParamType(Method method) {
        List<Class> lists = new ArrayList<>();
        Annotation[][] anns = method.getParameterAnnotations();
        for (Annotation[] annotations : anns) {
            for (Annotation annotation : annotations) {
                lists.add(annotation.annotationType());
            }
        }
        return lists;
    }
}
