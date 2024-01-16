package com.broccoli.encyclopaedia.common.groovy.script;

import java.util.Map;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 13:32
 * @Description: groovy 脚本执行器
 */
public interface ScriptExecutor {
    Object execute(String scriptName, Map<String, Object> binding);

}
