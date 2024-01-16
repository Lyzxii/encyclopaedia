package com.broccoli.encyclopaedia.common.groovy.script;

import lombok.Data;

/**
 * @Auther: yanpeidong
 * @Date: 2024/1/16
 * @Description: groovy 脚本数据源对账
 */
@Data
public class GroovyScriptSource {
    private String name;

    private String md5;

    private String sourceCode;

    private Class<?> clazz;
}
