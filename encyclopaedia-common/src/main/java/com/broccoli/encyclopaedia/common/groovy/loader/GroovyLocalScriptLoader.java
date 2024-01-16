package com.broccoli.encyclopaedia.common.groovy.loader;

import com.broccoli.encyclopaedia.common.groovy.script.GroovyScriptSource;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16 13:38
 * @Description: 本地groovy脚本加载器
 */
@Slf4j
public class GroovyLocalScriptLoader extends GroovyScriptLoader {
    public GroovyLocalScriptLoader(GroovyClassLoader groovyClassLoader) {
        super(groovyClassLoader);
    }

    /**
     * 将本地groovy脚本转化成GroovyScriptSource 对象
     *
     * @param scriptPath 脚本路径
     * @return GroovyScriptSource 对象
     * @throws IOException
     */
    @Override
    protected GroovyScriptSource scriptSource(String scriptPath) throws IOException {
        // 本地groovy文件打包后不会变更，若缓存存在则直接取
        Optional<GroovyScriptSource> cached = getCachedSource(scriptPath);
        if (cached.isPresent()) {
            return cached.get();
        }
        try (InputStream is = Objects.requireNonNull(
                GroovyLocalScriptLoader.class.getClassLoader().getResourceAsStream(scriptPath),
                "Groovy script not found : " + scriptPath)) {
            byte[] sourceCode = IOUtils.toByteArray(is);
            String md5 = DigestUtils.md5DigestAsHex(sourceCode);
            GroovyScriptSource groovySource = new GroovyScriptSource();
            groovySource.setName(scriptPath);
            groovySource.setMd5(md5);
            groovySource.setSourceCode(new String(sourceCode, StandardCharsets.UTF_8));
            return groovySource;
        }
    }
}
