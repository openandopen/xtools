package com.dj.xtool.net.http.support;

import android.os.AsyncTask;

import com.dj.xtool.net.http.model.ApiInfo;
import com.dj.xtool.net.http.model.InputInfo;
import com.dj.xtool.net.http.model.OutputInfo;
import com.dj.xtool.net.http.model.Response;

import java.util.Arrays;
import java.util.Map;

/**
 * 描述:
 * <p>
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-16 10:39
 */
public class HttpAsyncTask extends AsyncTask<InputInfo, Integer, OutputInfo> {


    private ClientSupport clientSupport;

    private Callback responseCallback;

    private InputInfo inputInfo;

    private HttpAsyncTask(ClientSupport clientSupport, Callback responseCallback) {
        this.clientSupport = clientSupport;
        this.responseCallback = responseCallback;
    }


    /**
     * 后台线程(不能有前台UI组件使用)
     */
    @Override
    protected OutputInfo doInBackground(InputInfo... inputInfos) {
        inputInfo = inputInfos[0];
        OutputInfo outputInfo = new OutputInfo<>();
        Response response = null;
        try {
            ApiInfo apiInfo = inputInfo.getApiInfo();
            Object[] args = inputInfo.getArgs();
            Map<String, Object> params = apiInfo.parseArgsToParamMap(args);
            Map<String, Object> pathParams = apiInfo.parseArgsToPathMap(args);
            String fullUrl = apiInfo.buildUrl(pathParams);
            Object body = apiInfo.parseRequestBody(args);
            ApiType apiType = null;
            // Process.killProcess(Process.myPid());
            if (apiInfo.getReturnType() != null) {
                apiType = new ApiType(apiInfo.getReturnType());
            }
            response = clientSupport.request(fullUrl, params, apiInfo.getHttpMethod(), body, null, apiType, null);
        } catch (Exception e) {
            outputInfo.setException(e);
        }
        return outputInfo.setResult(response);
    }

    /**
     * 前台线程
     *
     * @param outputInfo
     */
    @Override
    protected void onPostExecute(OutputInfo outputInfo) {
        Object object = outputInfo.getResult();
        Exception exception = outputInfo.getException();
        if (exception != null) {
            responseCallback.accept(object, exception);
        } else {
            responseCallback.accept(object, null);
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        System.out.println("values:" + Arrays.toString(values));
    }

    /**
     * @param clientSupport
     * @param responseCallback
     * @return
     */
    public static HttpAsyncTask build(ClientSupport clientSupport, Callback responseCallback) {
        return new HttpAsyncTask(clientSupport, responseCallback);
    }

}
