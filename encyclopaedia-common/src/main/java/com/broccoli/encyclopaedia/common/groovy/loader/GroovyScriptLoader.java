package com.broccoli.encyclopaedia.common.groovy.loader;

import com.broccoli.encyclopaedia.common.groovy.script.DynamicScript;
import com.broccoli.encyclopaedia.common.groovy.script.GroovyScript;
import com.broccoli.encyclopaedia.common.groovy.script.GroovyScriptSource;
import groovy.lang.GroovyClassLoader;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilationFailedException;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 13:38
 * @Description:
 */
@Slf4j
public abstract class GroovyScriptLoader implements ScriptLoader {

    private final GroovyClassLoader groovyClassLoader;

    public GroovyScriptLoader(GroovyClassLoader groovyClassLoader) {
        this.groovyClassLoader = groovyClassLoader;
    }

    private final static ConcurrentHashMap<String, GroovyScriptSource> SOURCE_CACHE = new ConcurrentHashMap<>();

    /**
     * 抽象方法，子类实现去加载脚本
     * @param scriptName 脚本名称
     * @return 脚本对象
     * @throws IOException
     */
    protected abstract GroovyScriptSource scriptSource(String scriptName) throws IOException;

    @Override
    public DynamicScript load(String scriptName) {
        try {
            Class<?> scriptClass = parseClass(scriptSource(scriptName));
            return (GroovyScript) scriptClass.newInstance();
        } catch (CompilationFailedException e) {
            throw new RuntimeException("groovy script compilation failed", e);
        } catch (IOException e) {
            throw new RuntimeException("groovy script loading failed", e);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("groovy script instantiation failed", e);
        }
    }

    private Class<?> parseClass(final @NotNull GroovyScriptSource groovyScriptSource){
        return SOURCE_CACHE.compute(groovyScriptSource.getName(), (k,value) ->{
            // 缓存中的脚本对象为空
            if(value == null){
                doParseClass(groovyScriptSource);
                log.info("groovy script {} loaded", groovyScriptSource.getName());
                return groovyScriptSource;
            // 缓存中的脚本对象已被修改
            }else if(!value.getMd5().equals(groovyScriptSource.getMd5())){
                // help gc
                groovyClassLoader.clearCache();
                doParseClass(groovyScriptSource);
                log.info("groovy script {} reloaded", groovyScriptSource.getName());
                return groovyScriptSource;
            }else {
                return value;
            }
        }).getClass();
    }

    /**
     * groovy 脚本转成class对象
     * @param scriptSource groovy 脚本
     * @return Class对象
     */
    private Class<?> doParseClass(GroovyScriptSource scriptSource) {
        Class<?> loaded = groovyClassLoader.parseClass(scriptSource.getSourceCode(), scriptSource.getName());
        if (loaded.getSuperclass() == null) {
            throw new ClassCastException("groovy script class is not subtype of GroovyScript");
        }
        // 为了使脚本能在运行时编译，脚本之间不允许互相依赖
        if (!loaded.getSuperclass().getName().equals(GroovyScript.class.getName())) {
            throw new ClassCastException("groovy script's super class must be GroovyScript");
        }
        scriptSource.setClazz(loaded);
        //scriptSource.setSourceCode(null); // help gc
        return loaded;
    }

    protected Optional<GroovyScriptSource> getCachedSource(String scriptName) {
        return Optional.ofNullable(SOURCE_CACHE.get(scriptName));
    }
}
