package supersports.com.lib_compile;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import supersports.com.lib_annot.BindView;
import supersports.com.lib_annot.Click;
import supersports.com.lib_annot.EventBase;
import supersports.com.lib_annot.ViewInject;


/**
 * Created by rocky on 2019/1/17.
 */

public class ViewHelper {

    public static void bindLayoutId(Activity activity) {

        try {

            Annotation annotation = activity.getClass().getAnnotation(ViewInject.class);
            if (annotation != null) {
                ViewInject viewInject = (ViewInject) annotation;
                int resId = viewInject.layout_id();
                activity.setContentView(resId);
            }

            //同时初始化当前的View
            Field[] fields = activity.getClass().getDeclaredFields();

            for (Field field : fields) {

                if (field.isAnnotationPresent(BindView.class)) {

                    BindView bindView = field.getAnnotation(BindView.class);

                    int resId = bindView.value();

                    field.setAccessible(true);

                    field.set(activity, activity.findViewById(resId));

                }

            }

            Method[] methods = activity.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Click.class)) {

                    DefaultHandler defaultHandler = new DefaultHandler(activity);

                    Click click = method.getAnnotation(Click.class);

                    EventBase eventBase = click.annotationType().getAnnotation(EventBase.class);

                    String methodName = eventBase.methodName();

                    Class<?> listenerType = eventBase.listenerType();

                    String listenerSetter = eventBase.listenerSetter();

                    Object invokerMethod = Proxy.newProxyInstance(activity.getClassLoader(), new Class[]{listenerType}, defaultHandler);

                    defaultHandler.addMethod(methodName, method);

                    int[] values = click.values();


                    for (int value : values) {

                        //首先考虑如果没有初始化当前的View需要进行初始化

                        Method findViewMethod = activity.getClass().getMethod("findViewById", int.class);

                        findViewMethod.setAccessible(true);

                        View mView = (View) findViewMethod.invoke(activity, value);


                        //然后开始初始化当前的setOnClickListener事件
                        Method mOnClickListenerMethod = mView.getClass().getMethod(listenerSetter,listenerType);

                        mOnClickListenerMethod.setAccessible(true);

                        mOnClickListenerMethod.invoke(mView,invokerMethod);

                    }


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
