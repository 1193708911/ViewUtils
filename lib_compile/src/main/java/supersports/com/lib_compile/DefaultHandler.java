package supersports.com.lib_compile;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by rocky on 2019/1/18.
 */

public class DefaultHandler implements InvocationHandler {

    private WeakReference<Object> handlerRef;

    private HashMap<String, Method> methodHashMap = new HashMap<>();

    public DefaultHandler(Object object) {

        handlerRef = new WeakReference<>(object);

    }

    public void addMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object handler = handlerRef.get();
        if (handler != null) {
            String methodName = method.getName();
            Method m = methodHashMap.get(methodName);
            m.invoke(handler, args);

        }
        return null;
    }
}
