package com.bookingsystem.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String module() default "";  // 模块名
    String type() default "";    // 操作类型，比如新增、修改、删除
    String description() default ""; // 描述
}
