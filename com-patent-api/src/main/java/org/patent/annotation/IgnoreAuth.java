package org.patent.annotation;

import java.lang.annotation.*;

/**
 * 忽略Token验证
 * @ interface是自定义注解的语法
 */
@Target(ElementType.METHOD)   //表示这是一个方法
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented  //说明该注解将被包含在javadoc中
public @interface IgnoreAuth {

}
