package com.broccoli.encyclopaedia.common.groovy.executor;

import com.broccoli.encyclopaedia.common.groovy.loader.ScriptLoader;
import com.broccoli.encyclopaedia.common.groovy.script.ScriptExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 14:20
 * @Description: groovy 脚本执行期
 */
@Slf4j
public class GroovyScriptExecutor implements ScriptExecutor {

    private final ScriptLoader scriptLoader;

    public GroovyScriptExecutor(ScriptLoader scriptLoader) {
        this.scriptLoader = scriptLoader;
    }

    @Override
    public Object execute(String scriptName, Map<String, Object> binding) {
        if (binding == null) {
            binding = new HashMap<>();
        }

        try {
            return scriptLoader.load(scriptName).exec(binding);
        }  catch (Exception e) {
            log.error("groovy script execution failed, script name : {}", scriptName, e);
            throw e;
        }
    }
}
