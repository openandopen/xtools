package com.dj.xtool.net.http.support;


import android.os.Looper;

import com.dj.xtool.net.http.config.HttpConfig;
import com.dj.xtool.net.http.enums.HttpMethod;
import com.dj.xtool.net.http.model.ApiInfo;
import com.dj.xtool.net.http.model.InputInfo;
import com.dj.xtool.utils.Assert;
import com.dj.xtool.utils.json.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orhanobut.logger.Logger;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;




/**
 * 描述:
 *
 * @version : Ver1.0
 * @author:<a href="mailto:dejian.liu@bkjk.com">dejian.liu</a>
 * @date 2018-12-10 18:06
 */
public class ClientSupport<S extends ClientSupport> {

    private static volatile OkHttpClient httpClient;


    private HttpConfig httpConfig;

    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void setExecutorService(ExecutorService executorService) {
        ClientSupport.executorService = executorService;
    }

    public OkHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (this) {
                if (httpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    Assert.INS.notEmpty(httpConfig, "okHttpConfig can't be null");
                    List<Interceptor> interceptors = httpConfig.getInterceptors();
                    List<Interceptor> networkInterceptors = httpConfig.getNetworkInterceptors();
                    if (interceptors != null) {
                        for (Interceptor interceptor : interceptors) {
                            builder.addInterceptor(interceptor);
                        }
                    }

                    if (networkInterceptors != null) {
                        for (Interceptor interceptor : networkInterceptors) {
                            builder.addNetworkInterceptor(interceptor);
                        }
                    }

                    if (httpConfig.getAuthenticator() != null) {
                        builder.authenticator(httpConfig.getAuthenticator());
                    }

                    builder.callTimeout(httpConfig.getCallTimeout(), TimeUnit.MILLISECONDS);
                    builder.connectTimeout(httpConfig.getConnectTimeout(), TimeUnit.MILLISECONDS);

                    builder.addNetworkInterceptor(new Interceptor() {
                        @NotNull
                        @Override
                        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                            okhttp3.Request request = chain.request();
                            okhttp3.Request.Builder builder1 = request.newBuilder();
                            builder1.addHeader("Content-Type", "application/json")
                                    .addHeader("Accept", "application/json");
                            request = builder1.build();
                       /*     Headers headers = request.headers();
                            if (headers != null) {
                                //如果有自定义的就覆盖默认
                                Set<String> heaersNames = headers.names();
                                for (String name : heaersNames) {
                                    System.out.println(name + "===" + headers.get(name));
                                }
                            }*/
                            return chain.proceed(request);
                        }
                    });
                    if (httpConfig.getEventListener() != null) {
                        builder.eventListener(httpConfig.getEventListener());
                    }
                    if (httpConfig.getProxy() != null) {
                        builder.proxy(httpConfig.getProxy());
                    }

                    httpClient = builder.build();
                }
            }
        }
        return httpClient;
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public ClientSupport<S> setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
        return this;
    }

    /**
     * 构建请求路径
     *
     * @param serverUrl
     * @param uri
     * @return
     */
    public String buildReqUrl(String serverUrl, String uri) {

        if (StringUtils.isBlank(serverUrl)) {
            throw new RuntimeException("请求服务地址不能为空[baseUrl]!");
        }
        if (StringUtils.endsWith(serverUrl, "/")) {
            serverUrl = StringUtils.substringBeforeLast(serverUrl, "/");
        }
        if (StringUtils.startsWith(uri, "/")) {
            uri = StringUtils.substringAfter(uri, "/");
        }
        return serverUrl + "/" + uri;
    }


    /**
     * @param uri
     * @param urlParams
     * @param httpMethod
     * @param reqEntity
     * @param headers
     * @param returnType
     * @param responseCallback 回调接口，如果不为空则为异步请求
     * @param <T>
     * @return
     */
    public <T> com.dj.xtool.net.http.model.Response<T> request(String uri,
                                                         Map<String, Object> urlParams,
                                                         HttpMethod httpMethod,
                                                         Object reqEntity,
                                                         Map<String, String> headers,
                                                         final TypeReference<com.dj.xtool.net.http.model.Response<T>> returnType,
                                                         final Callback responseCallback
    ) {

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        com.dj.xtool.net.http.model.Response<T> result = new com.dj.xtool.net.http.model.Response<>();
        try {
            if (headers != null) {
                Set<String> names = headers.keySet();
                for (String headerName : names) {
                    builder.addHeader(headerName, headers.get(headerName));
                }
            }
            String url = buildReqUrl(getHttpConfig().getBaseUrl(), uri);
            url = buildParams(url, urlParams);
            builder = builder.url(url);

            if (httpMethod == HttpMethod.GET) {
                builder = builder.get();
            } else if (httpMethod == HttpMethod.PUT) {
                String bodyJson = JsonUtils.toJson(reqEntity);
                okhttp3.RequestBody body = okhttp3.RequestBody.create(bodyJson, MediaType.parse("application/json"));
                builder = builder.put(body);
            } else if (httpMethod == HttpMethod.POST) {
                String bodyJson = JsonUtils.toJson(reqEntity);
                okhttp3.RequestBody body = okhttp3.RequestBody.create(bodyJson, MediaType.parse("application/json"));
                builder = builder.post(body);
            } else if (httpMethod == HttpMethod.DELETE) {
                builder = builder.delete();
            } else {
                throw new RuntimeException("not support httpmethod!" + httpMethod);
            }


            //异步请求回调
            if (responseCallback != null) {
                getHttpClient().newCall(builder.build()).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Looper.prepare();
                        try {
                            responseCallback.accept(null, e);
                        }finally {
                            Looper.loop();
                        }

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                        Looper.prepare();
                        try {
                            if (response == null) {
                                responseCallback.accept(null, new RuntimeException("response is null"));
                            }
                            int code = response.code();
                            String message = response.message();
                            if (code < 300) {
                                okhttp3.ResponseBody responseBody = response.body();
                                if (responseBody != null) {
                                    String resultBody = responseBody.string();
                                    com.dj.xtool.net.http.model.Response<T> result = null;
                                    String errorMsg = "";
                                    try {
                                        result = JsonUtils.json2GenericObject(resultBody, returnType, null);
                                    } catch (Exception e) {
                                        errorMsg = e.getMessage();
                                    }
                                    if (StringUtils.isBlank(errorMsg) && result != null) {
                                        responseCallback.accept(result, null);
                                    } else {
                                        responseCallback.accept(result, new RuntimeException(errorMsg));
                                    }
                                }
                            } else if (code > 300 && code < 400) {
                                okhttp3.ResponseBody responseBody = response.body();
                                if (responseBody != null) {
                                    String resultBody = responseBody.string();
                                    com.dj.xtool.net.http.model.Response<T> result = JsonUtils.json2GenericObject(resultBody, returnType, null);
                                    responseCallback.accept(null, new RuntimeException(result.getMessage()));
                                    return;
                                }
                                responseCallback.accept(null, new RuntimeException(message));
                            } else {
                                responseCallback.accept(null, new RuntimeException(message));

                            }
                        }finally {
                            Looper.loop();
                        }
                    }
                });
            } else {
                //同步请求
                okhttp3.Response response = getHttpClient().newCall(builder.build()).execute();
                if (response == null) {
                    throw new RuntimeException("response is null");
                }
                int code = response.code();
                String message = response.message();
                okhttp3.ResponseBody responseBody = response.body();
                if (code < 300) {

                    if (responseBody != null) {
                        String resultBody = responseBody.string();
                        result = JsonUtils.json2GenericObject(resultBody, returnType, null);
                    }
                } else if (code > 300 && code < 400) {
                    if (responseBody != null) {
                        String resultBody = responseBody.string();
                        result = JsonUtils.json2GenericObject(resultBody, returnType, null);
                        message = result.getMessage();
                    }
                    throw new RuntimeException(message);
                } else {
                    if (responseBody != null) {
                        String resultBody = responseBody.string();
                        result = JsonUtils.json2GenericObject(resultBody, returnType, null);
                        message = result.getMessage();
                    }
                    throw new RuntimeException(message);
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * @param url
     * @param params
     * @return
     */
    private String buildParams(String url, Map<String, Object> params) {
        StringBuffer buffer = new StringBuffer(url);

        if (params != null && !params.isEmpty()) {
            if (StringUtils.indexOf(url, "?") != -1) {
                buffer.append("&");
            } else {
                buffer.append("?");
            }
            int index = 0;
            for (Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Object> entry = it.next();
                if (index == params.size() - 1) {
                    buffer.append(entry.getKey()).append("=").append(entry.getValue());
                } else {
                    buffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                index++;
            }
        }
        return buffer.toString();
    }


    /**
     * 根据泛型获取返回类别
     *
     * @param callback
     * @return
     */
    private static Type getReturnType(Callback callback) {
        try {

            Type type = callback.getClass().getGenericInterfaces()[0];
            if (type != null) {
                ParameterizedType paramType = (ParameterizedType) type;
                return paramType.getActualTypeArguments()[0];
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 从缓存中获取
     *
     * @param method
     * @return
     */
    public ApiInfo cachedApiInfo(Method method) {
        ApiInfo apiInfo = ApiClientContext.getCacheApiInfo(method);
        if (apiInfo == null) {
            apiInfo = AnnotationUtil.parseApiInfo(method);
            ApiClientContext.cacheApiInfo(method, apiInfo);
        }
        return apiInfo;
    }

    /**
     * @param apiInfo
     * @param args
     * @param <T>
     * @return
     */
    public <T> com.dj.xtool.net.http.model.Response<T> execute(ApiInfo apiInfo, Object[] args) {
        Map<String, Object> params = apiInfo.parseArgsToParamMap(args);
        Map<String, Object> pathParams = apiInfo.parseArgsToPathMap(args);
        String fullUrl = apiInfo.buildUrl(pathParams);
        ApiType apiType = new ApiType<>(apiInfo.getReturnActualType());
        Object body = apiInfo.parseRequestBody(args);
       Callback callback = null;
        if (args != null) {
            for (Object obj : args) {
                if (obj instanceof Callback) {
                    callback = (Callback) obj;
                    Type returnType = getReturnType(callback);
                    apiInfo.setReturnType(returnType);
                    if (returnType != null) {
                        apiType = new ApiType(returnType);
                    }
                    break;
                }
            }
        }
        //如果泛型为空，则默认为string
        if (apiType == null) {
            apiType = new ApiType<>(String.class);
        }
        if (callback != null) {
            //AsyncTask 执行
            HttpAsyncTask.build(this, callback)
                    .executeOnExecutor(executorService, InputInfo.build(apiInfo, args));
            return null;
        } else {
            return request(fullUrl, params, apiInfo.getHttpMethod(), body, null, apiType, callback);
        }
    }


}
