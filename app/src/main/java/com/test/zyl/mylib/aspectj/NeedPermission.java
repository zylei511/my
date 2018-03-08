package com.test.zyl.mylib.aspectj;

import android.Manifest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.acl.Permission;

/**
 * Created by ex-zhangyuelei001 on 2018/2/24.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface NeedPermission {
    String value() default "";
    int requestCode() default 0;
}

