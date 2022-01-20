package com.project.barter.user.validator;

import com.project.barter.user.domain.Birthday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthdayValidator implements ConstraintValidator<Birth,Birthday> {

    @Override
    public void initialize(Birth constraintAnnotation) {
    }

    @Override
    public boolean isValid(Birthday birthday, ConstraintValidatorContext context) {
        LocalDate tomorrow = LocalDate.now().plusDays(1L);
        LocalDate past = LocalDate.of(birthday.getYear(), birthday.getMonth(), birthday.getDay());
        if(tomorrow.isAfter(past)){
            return true;
        }
        return false;
    }
}
