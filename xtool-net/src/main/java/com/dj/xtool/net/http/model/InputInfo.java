package com.dj.xtool.net.http.model;

import java.io.Serializable;

/**
 * 描述:
 * <p>
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-16 12:03
 */
public class InputInfo implements Serializable {

    /**
     * API基础信息
     */
    private ApiInfo apiInfo;

    /**
     * 请求参数
     */
    private Object [] args;

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public InputInfo setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public InputInfo setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public static InputInfo build(ApiInfo apiInfo,Object [] objects) {
        return new InputInfo().setApiInfo(apiInfo).setArgs(objects);
    }

}
