package supersports.com.lib_annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by rocky on 2019/1/18.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //类型
    // 名字
    // 设置方法名字
    Class<?> listenerType();

    String listenerSetter();

    String methodName();

}
