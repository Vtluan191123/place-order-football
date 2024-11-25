package com.vtluan.place_order_football.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Payload;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponse {
    String message() default "Call Api Successful";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}