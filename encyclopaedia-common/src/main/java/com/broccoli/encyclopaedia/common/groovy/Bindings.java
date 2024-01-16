package com.broccoli.encyclopaedia.common.groovy;

import groovy.lang.Binding;
import groovy.lang.MissingPropertyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public class Bindings {

    public static final String APPLICATION_CONTEXT_BINDING_NAME = "applicationContext";

    /**
     * Binding类代表了一个脚本的变量绑定，可以从脚本对象外部进行修改，或者在脚本外部创建并传递给脚本对象。
     * 在Groovy中，Binding类提供了一种在脚本对象和外部环境之间传递变量绑定的方式。通过将Binding对象与脚本对象相关联，可以在脚本中引用并修改外部环境中的变量。这使得外部环境的变量可以在脚本中使用，也允许脚本修改外部环境的变量。
     * 然而，需要注意的是，Binding实例不应该在多线程环境中使用。这是因为Binding对象在多个线程之间共享时可能引发不可预测的并发问题。如果需要在多线程环境中使用变量绑定，应考虑使用线程安全的数据结构或其他并发机制。
     * 总之，Binding类提供了一种传递和共享变量绑定的机制，允许脚本对象和外部环境之间进行数据交互。但请注意，在多线程环境中使用时需要谨慎处理，以避免可能的并发问题。
     */
    private Binding binding;

    private Bindings(Binding binding) {
        this.binding = binding;
    }

    public static Bindings of(Binding binding) {
        return new Bindings(binding);
    }

    public ApplicationContext applicationContext() {
        try {
            return (ApplicationContext) this.binding.getVariable(APPLICATION_CONTEXT_BINDING_NAME);
        } catch (MissingPropertyException e) {
            log.error("Missing applicationContext binding" +
                    ", please make sure the script is running in spring environment");
            throw e;
        }
    }

    public <T> T bean(Class<T> clazz) {
        return applicationContext().getBean(clazz);
    }

}
