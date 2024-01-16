package com.broccoli.encyclopaedia.common.groovy.loader;

import groovy.lang.GroovyResourceLoader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 14:14
 * @Description: 远程groovy脚本加载器（TODO）
 */
public abstract class GroovyRemoteResourceLoader implements GroovyResourceLoader {

    protected final GroovyResourceLoader parentLoader;

    public GroovyRemoteResourceLoader(GroovyResourceLoader parentLoader) {
        this.parentLoader = parentLoader;
    }

    @Override
    public URL loadGroovySource(String filename) throws MalformedURLException {
        URL resourceURL = null;

        if (parentLoader != null) {
            resourceURL = parentLoader.loadGroovySource(filename);
        }

        if (resourceURL == null) {
            resourceURL = createURL(filename);
        }

        return resourceURL;
    }

    protected abstract URL createURL(String resourceName) throws MalformedURLException;
}
