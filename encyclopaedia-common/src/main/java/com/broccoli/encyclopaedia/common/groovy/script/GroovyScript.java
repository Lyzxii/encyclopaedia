package com.broccoli.encyclopaedia.common.groovy.script;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.Map;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 13:35
 * @Description:
 */
public abstract class GroovyScript extends Script implements DynamicScript {
    @Override
    public Object exec(Map<String, ?> binding) {
        setBinding(new Binding(binding));
        return run();
    }
}
