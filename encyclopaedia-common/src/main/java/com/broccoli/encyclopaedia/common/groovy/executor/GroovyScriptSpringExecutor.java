package com.broccoli.encyclopaedia.common.groovy.executor;

import com.broccoli.encyclopaedia.common.groovy.Bindings;
import com.broccoli.encyclopaedia.common.groovy.loader.ScriptLoader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 14:23
 * @Description: Spring 脚本执行器，将ApplicationContext放入脚本参数中
 */
public class GroovyScriptSpringExecutor extends GroovyScriptExecutor implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public GroovyScriptSpringExecutor(ScriptLoader scriptLoader) {
        super(scriptLoader);
    }

    @Override
    public Object execute(String scriptName, Map<String, Object> binding) {
        if (binding == null) {
            binding = new HashMap<>();
        }
        // inject spring context
        binding.put(Bindings.APPLICATION_CONTEXT_BINDING_NAME, applicationContext);
        return super.execute(scriptName, binding);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
