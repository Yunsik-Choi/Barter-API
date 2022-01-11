package com.project.barter.user.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthdayValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Birth {

    String message() default "Birthday fail Validation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
