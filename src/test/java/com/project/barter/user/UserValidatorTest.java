package com.project.barter.user;

import com.project.barter.user.domain.Birthday;
import com.project.barter.user.validator.BirthdayValidator;
import com.project.barter.user.validator.PhoneNumberValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

public class UserValidatorTest {

    @ParameterizedTest
    @MethodSource("birthdayList")
    public void BirthdayTest(Boolean bool, Birthday birthday){
        BirthdayValidator birthdayValidator = new BirthdayValidator();
        Assertions.assertThat(bool).isEqualTo(birthdayValidator.isValid(birthday,null));
    }

    public static Stream<Arguments> birthdayList(){
        LocalDate now = LocalDate.now();
        return Stream.of(
                Arguments.of(true,new Birthday(1998,01,01)),
                Arguments.of(true,new Birthday(now.getYear(),now.getMonthValue(),now.getDayOfMonth())),
                Arguments.of(false,new Birthday(2200,12,12))
        );
    }

    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void phoneNumberTest(Boolean bool, String phoneNumber){
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        Assertions.assertThat(bool).isEqualTo(phoneNumberValidator.isValid(phoneNumber,null));
    }

    public static Stream<Arguments> phoneNumberList(){
        return Stream.of(
                Arguments.of(true, "01012345678"),
                Arguments.of(false, "01112345678"),
                Arguments.of(false, "0102345678"),
                Arguments.of(false, "010234567a8")
        );
    }

}
