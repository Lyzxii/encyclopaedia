package com.broccoli.encyclopaedia.common.groovy.loader;

import com.broccoli.encyclopaedia.common.groovy.script.DynamicScript;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 13:28
 * @Description: groovy脚本加载
 */
public interface ScriptLoader {
    DynamicScript load(String scriptName);

}
