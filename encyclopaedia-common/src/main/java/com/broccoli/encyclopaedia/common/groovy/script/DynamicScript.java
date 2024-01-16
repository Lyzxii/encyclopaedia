package com.broccoli.encyclopaedia.common.groovy.script;

import java.util.Map;

/**
 * @author ：yanpeidong
 * @date : 2024年01月16日
 * @Description: groovy 动态脚本
 */
public interface DynamicScript {
    Object exec(Map<String, ?> binding);
}
