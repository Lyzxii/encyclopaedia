package com.broccoli.encyclopaedia.common.groovy;

import com.broccoli.encyclopaedia.common.groovy.executor.GroovyScriptSpringExecutor;
import com.broccoli.encyclopaedia.common.groovy.loader.GroovyScriptLoaders;
import com.broccoli.encyclopaedia.common.groovy.loader.ScriptLoader;
import com.broccoli.encyclopaedia.common.groovy.script.ScriptExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicScriptConfiguration {

    @Bean
    public ScriptLoader scriptLoader() {
        return GroovyScriptLoaders.localScriptLoader();
    }

    @Bean
    public ScriptExecutor scriptExecutor(ScriptLoader scriptLoader) {
        return new GroovyScriptSpringExecutor(scriptLoader);
    }

}
