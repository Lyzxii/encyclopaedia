import com.broccoli.encyclopaedia.common.groovy.Bindings
import com.broccoli.encyclopaedia.common.groovy.script.GroovyScript

class PostPayDbsTemplate extends GroovyScript {

    @Override
    Object run() {
        //创建Bindings
        def bindings = Bindings.of(binding)
        //从spring容器中获取对象
        bindings.bean()
        return null
    }
}
