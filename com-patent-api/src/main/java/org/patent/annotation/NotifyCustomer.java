package org.patent.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotifyCustomer {
  /** handler for slove the notify action */
  public String handler() default "";

  /** action - type */
  public String method() default "";
}