package com.test.zyl.mylib;

import android.app.Activity;
import android.util.Log;


import com.test.zyl.mylib.aspectj.NeedPermission;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by ex-zhangyuelei001 on 2018/2/23.
 */

@Aspect
public class MethodAspectj {
    private static final String METHOD_ON_CREATE="execution(protected void com.test.zyl.mylib.MainActivity.onCreate(android.os.Bundle))";
    private static final String METHOD_OPEN_CAMERA="execution(* com.test.zyl.mylib.MainActivity.openCamera())";
    private static final String METHOD_FIELD_PARAM="get(* com.test.zyl.mylib.MainActivity.METHOD_LOG)";
    private static final String METHOD_LOG="MethodAspectj";
    /**
     * 自定义注解
     */
    private static final String METHOD_ANNOTATION_NEED_PERMISSION="execution(* *.*(..)) && @annotation(com.test.zyl.mylib.aspectj.NeedPermission)";

//    @Before(METHOD_ON_CREATE)
//    public void beforeOnCreate(JoinPoint joinPoint) {
//        Log.e(METHOD_LOG, "MethodAspectj中的beforeOnCreate()执行啦："+joinPoint.toString());
//    }
//
//    @After(METHOD_ON_CREATE)
//    public void afterOnCreate(JoinPoint joinPoint){
//        Log.e(METHOD_LOG, "MethodAspectj中的afterOnCreate()执行啦："+joinPoint.toString());
//    }

    @Around(METHOD_ON_CREATE)
    public void aroundOnCreate(ProceedingJoinPoint proceedingJoinPoint){
        Log.e(METHOD_LOG, "MethodAspectj中的aroundOnCreate()执行啦( before ) :"+proceedingJoinPoint.toString());
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.e(METHOD_LOG, "MethodAspectj中的aroundOnCreate()执行啦( after ) :"+proceedingJoinPoint.toString());

    }

    @Around(METHOD_ANNOTATION_NEED_PERMISSION)
    public void aroundNeedPermission(ProceedingJoinPoint joinPoint) {

        Log.e(METHOD_LOG, "MethodAspectj中的aroundNeedPermission()执行啦 ( before ) ：" + joinPoint.toString());
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Method[] methods = joinPoint.getSignature().getDeclaringType().getMethods();
        for (Method method :methods) {
            if (method.isAnnotationPresent(NeedPermission.class)) {
                NeedPermission annotation = method.getAnnotation(NeedPermission.class);
                Log.e(METHOD_LOG, "MethodAspectj中的aroundNeedPermission()执行啦 ( after ) ："
                        +"annotation.value() = "+ annotation.value()
                        +"annotation.requestCode() = "+annotation.requestCode());

                Log.e(METHOD_LOG,"method.getDeclaringClass() = "+ method.getDeclaringClass());
                Class<? extends Activity> tClass = method.getDeclaringClass().asSubclass(Activity.class);
                try {
                    Activity activity = tClass.newInstance();
                    Log.e(METHOD_LOG,"activity.toString() = "+ activity.toString());
                    activity.checkCallingOrSelfPermission(annotation.value());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    @Before(METHOD_FIELD_PARAM)
    public void changeFieldParam(JoinPoint joinPoint){
        Log.e(METHOD_LOG, "MethodAspectj中的changeFieldParam()执行啦：" );
    }
}
