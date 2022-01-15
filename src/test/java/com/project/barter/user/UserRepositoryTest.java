package com.project.barter.user;

import com.project.barter.user.domain.Birthday;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUser(){
        User user = User.builder()
                .userId("userId")
                .password("password")
                .name("이름")
                .birthday(new Birthday(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();

        userRepository.save(user);
        User findUser = userRepository.findById(1L).get();
        Assertions.assertThat(1L).isEqualTo(findUser.getId());
        Assertions.assertThat("이름").isEqualTo(findUser.getName());
    }

    @Test
    public void alreadyExistsUserId(){
        User user1 = User.builder()
                .userId("userId")
                .password("password")
                .name("유저1")
                .birthday(new Birthday(2010,12,12))
                .email("naver@gmail.com")
                .phoneNumber("01012345678")
                .build();
        User user2 = User.builder()
                .userId("userId")
                .password("pass")
                .name("유저2")
                .birthday(new Birthday(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();

        userRepository.save(user1);

        org.junit.jupiter.api.Assertions.assertThrows(
                DataIntegrityViolationException.class,
                ()-> userRepository.save(user2));
    }


}
