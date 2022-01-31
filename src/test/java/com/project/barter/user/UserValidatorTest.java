package com.project.barter.user;

import com.project.barter.user.validator.BirthdayValidator;
import com.project.barter.user.validator.PhoneNumberValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

public class UserValidatorTest {

    @DisplayName("생년월일 검증기 테스트")
    @ParameterizedTest
    @MethodSource("birthdayList")
    public void Birthday_Test(Boolean bool, LocalDate birthday){
        BirthdayValidator birthdayValidator = new BirthdayValidator();
        Assertions.assertThat(bool).isEqualTo(birthdayValidator.isValid(birthday,null));
    }

    public static Stream<Arguments> birthdayList(){
        return Stream.of(
                Arguments.of(true,LocalDate.of(1998,01,01)),
                Arguments.of(true,LocalDate.now()),
                Arguments.of(false,LocalDate.of(3000,01,01))
        );
    }

    @DisplayName("전화번호 검증기 테스트")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void PhoneNumber_Test(Boolean bool, String phoneNumber){
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
