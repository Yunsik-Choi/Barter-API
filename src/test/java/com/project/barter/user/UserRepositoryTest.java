package com.project.barter.user;

import com.project.barter.user.domain.Birthday;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        User findUser = userRepository.findById(1L);
        Assertions.assertThat(1L).isEqualTo(user.getId());
        Assertions.assertThat("이름").isEqualTo(user.getName());
    }


}
