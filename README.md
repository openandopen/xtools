### xtool 提供android 环境下的工具类

````
xtool-net 
android http网络请求工具类似 Retrofit 
1. 具体使用如下:
在xxxApplication中的onCreate时添加
    //config中可以配置http请求的所有拦截，认证操作
        HttpConfig config = new HttpConfig();
        String baseUrl = "http://192.168.2.119:8080/uc";
        config.setBaseUrl(baseUrl);
        //初始化请求
        RequestUtil.initConfig(config);

````
````
2. 定义接口 
 例如
public interface UserService {
    /**
     * 登录
     * @param userInfo 请求实体
     * @param callback 回调接口
     * @return
     */
    @RequestMapping(value = "login/login",method = RequestMethod.POST)
    public void login(@RequestBody UserInfo userInfo, Callback<Response<UserInfo>> callback);
    
    /**
     * 发送短信
     * @param mobile
     * @param type register|login
     * @return
     */
    @RequestMapping(value = "verifycode/sms/{type}/{mobile}", method = RequestMethod.GET)
    public void asyncSendMessage(@PathVariable("mobile") Long mobile,
                                 @PathVariable("type") String type,
                                 Callback<Response<Boolean>> callback);
}
注解是从springframework中copy过来的。所以用过springmvc的同学这个应该不陌生

````

````
3. 在xxxActivity中开始调用
 
        CommonService commonService = RequestUtil.wrapGet(CommonService.class);
        commonService.asyncSendMessage(Long.valueOf(mobileStr.trim()), "login",
                new Callback<Response<Boolean>>() {
                    @Override
                    public void accept(Response<Boolean> result, Exception e) {
                        if (e != null) {
                            XToastUtils.error("验证码发送失败:"+e.getMessage(), 4000);
                        } else {
                            if (result != null && result.getResult()) {
                                XToastUtils.success("验证码发送成功", 2000);
                            } else {
                                XToastUtils.warning(result.getMessage(), 2000);

                            }
                        }
                    }
                });
                
              commonService.login(userInfo, 
                      new Callback<Response<UserInfo>>() {
                        @Override
                        public void accept(Response<UserInfo> result, Exception e) {
                             if (e != null) {
                                XToastUtils.error("登录失败:" + e.getMessage(), 4000);
                            } else {
                                UserInfo userInfo1 = result.getResult();
                                if(userInfo1 != null &&  StringUtils.isNotBlank(userInfo1.getToken())) {
                                  System.out.println(JsonUtils.toJson(userInfo1,true));
                                    ActivityUtils.startActivity(IndexActivity.class);
                                } else {
                                    XToastUtils.error("登录失败", 4000);
                                }
                              
                             }
                        }
                       });

````

