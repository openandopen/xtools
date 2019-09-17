package com.dj.xtool.utils.textplace;



import java.util.Map;

/**
 * 描述:
 * <p>
 *
 * @author : <a href="mailto:zuiwoxing@qq.com">dejian.liu</a>
 * @version : Ver 1.0
 * @date : 2019-09-17 15:52
 */
public class TextPlaceUtil {
    /**
     * 替换
     * @param source 源内容
     * @param parameter 占位符参数
     * @param prefix 占位符前缀 例如:${
     * @param suffix 占位符后缀 例如:}
     * @param enableSubstitutionInVariables 是否在变量名称中进行替换 例如:${system-${版本}}
     *
     * 转义符默认为'$'。如果这个字符放在一个变量引用之前，这个引用将被忽略，不会被替换 如$${a}将直接输出${a}
     * @return
     */
    public static String replace(String source, Map<String, Object> parameter,
                                 String prefix, String suffix,
                                 boolean enableSubstitutionInVariables){

        //StrSubstitutor不是线程安全的类
        return new StringSubstitutor(parameter,prefix, suffix)
                //是否在变量名称中进行替换
                .setEnableSubstitutionInVariables(enableSubstitutionInVariables).replace(source);
    }
}
