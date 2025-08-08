package com.ctci.domain.model;

import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNotificationTemplate {
    String message() default "Invalid notification template configuration";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
