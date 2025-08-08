package com.ctci.domain.model;

import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNotificationChannels {
    String message() default "At least one notification channel must be specified";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
