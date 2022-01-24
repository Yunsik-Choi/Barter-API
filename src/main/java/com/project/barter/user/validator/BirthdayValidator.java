package com.project.barter.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthdayValidator implements ConstraintValidator<Birth,LocalDate> {

    @Override
    public void initialize(Birth constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext context) {
        LocalDate tomorrow = LocalDate.now().plusDays(1L);
        if(tomorrow.isAfter(birthday)){
            return true;
        }
        return false;
    }
}
