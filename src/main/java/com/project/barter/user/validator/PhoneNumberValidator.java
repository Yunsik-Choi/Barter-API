package com.project.barter.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

   @Override
   public void initialize(PhoneNumber constraint) {
   }

   @Override
   public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
      String validNumber = phoneNumber;
      if(validNumber.substring(0,3).equals("010") && validNumber.matches("^[0-9]+$") && phoneNumber.length()==11){
         return true;
      }
      return false;
   }

}
