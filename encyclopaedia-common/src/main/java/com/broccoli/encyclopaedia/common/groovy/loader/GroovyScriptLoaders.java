package com.broccoli.encyclopaedia.common.groovy.loader;

import groovy.lang.GroovyClassLoader;

public class GroovyScriptLoaders {

    public static GroovyScriptLoader localScriptLoader() {
        GroovyClassLoader classLoader = new GroovyClassLoader(GroovyScriptLoaders.class.getClassLoader());
        return new GroovyLocalScriptLoader(classLoader);
    }

}
